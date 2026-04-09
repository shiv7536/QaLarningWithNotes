# QaLarningWithNotes

Short notes and examples for QA/manual testing.

## Structure
- `src/manual-testing/` — HTML notes and assets
- `bin/` — optional build/output

## Getting started
1. Clone the repository (after you create a remote):
   git clone <repo-url>
2. Open the project in Eclipse.

## How I initialized this repo locally
I added a `.gitignore` for Eclipse/Java artifacts and made an initial commit.

## Pushing to GitHub
- Create a GitHub repo (via web or `gh repo create`).
- Add remote and push:

```cmd
cd /d C:\Users\u123\eclipse-workspace\QaLarningWithNotes
git remote add origin https://github.com/<your-username>/<repo-name>.git
git branch -M main
git push -u origin main
```

If you use SSH:

```cmd
git remote add origin git@github.com:<your-username>/<repo-name>.git
git push -u origin main
```

If you want me to also create the GitHub repository for you, I can show the `gh` (GitHub CLI) command — but you'll need to confirm that `gh` is installed and authenticated on your machine.
