package json;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.*;

public class AnalyticsWithSKU {

    static List<String> reportData = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "localhost:9222");

        ChromeDriver driver = new ChromeDriver(options);

        String jsTrack = "track.min.js";
        String cssTrack = "track.min.css";

  
        List<String> domains = Arrays.asList(
                "https://stage.ubuy.co.in/",
                "https://stage.ubuy.com.sa/en/",
                "https://stage.a.ubuy.com.kw/en/",
                "https://www.a.ubuy.com.kw/en/",
                "https://www.ubuy.co.in/"
        );


        List<String> keywords = Arrays.asList(
                "laptop", "mobile", "shirt", "water bottle", "chair"
        );

    
        List<String> skuList = Arrays.asList(
                "B0B8STRJYJ",
                "B0DW9BW61Y",
                "B0DWS9D8LW"
        );

       
        reportData.add("TYPE | INPUT | STATUS | URL");

  
        for (String domain : domains) {

            System.out.println("\n🌐 Opening Domain: " + domain);

            driver.get(domain);
            Thread.sleep(7000);

            checkAnalytics(driver, "DOMAIN", domain, jsTrack, cssTrack);
        }

  
        for (String keyword : keywords) {

            String searchUrl = "https://stage.ubuy.co.in/search/?ref_p=chm_tp&q="
                    + keyword.replace(" ", "+");

            System.out.println("\n🔍 Searching → " + searchUrl);

            driver.get(searchUrl);
            Thread.sleep(7000);

            checkAnalytics(driver, "SEARCH", keyword, jsTrack, cssTrack);
        }


        for (String sku : skuList) {

            String productUrl = "https://www.ubuy.co.in/search/index/view/product/" + sku;

            System.out.println("\n📦 Opening SKU: " + sku);
            System.out.println("👉 URL: " + productUrl);

            driver.get(productUrl);
            Thread.sleep(7000);

            checkAnalytics(driver, "SKU", sku, jsTrack, cssTrack);
        }

  
        saveReport();

        System.out.println("\n✅ Report Generated: analytics_report.txt");
    }


    public static void checkAnalytics(WebDriver driver, String type, String input,
                                      String jsTrack, String cssTrack) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object result = js.executeScript(
                "return performance.getEntriesByType('resource').map(r => r.name);"
        );

        List<String> resources = new ArrayList<>();

        if (result instanceof List<?>) {
            for (Object obj : (List<?>) result) {
                if (obj != null) {
                    resources.add(obj.toString());
                }
            }
        }

        boolean found = false;
        String foundUrl = "";

        for (String url : resources) {

            if (url.contains(jsTrack) || url.contains(cssTrack)) {

                System.out.println("✅ JSON Found → " + input);
                System.out.println("👉 URL: " + url);

                found = true;
                foundUrl = url;
                break;
            }
        }

        if (!found) {
            System.out.println("❌ JSON NOT Found → " + input);
        }

        
        reportData.add(type + " | " + input + " | " + (found ? "FOUND" : "NOT FOUND") + " | " + foundUrl);
    }


    public static void saveReport() throws Exception {

        BufferedWriter writer = new BufferedWriter(new FileWriter("analytics_report.txt"));

        for (String line : reportData) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();
    }
}