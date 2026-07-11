# Documentation site (MkDocs Material)

This directory is the MkDocs `custom_dir` for [datafaker.net](https://www.datafaker.net). It holds **template overrides only** — not a copy of the Material theme.

## Architecture

- Built with [MkDocs](https://www.mkdocs.org/) and [Material for MkDocs](https://squidfunk.github.io/mkdocs-material/).
- Theme version is pinned in [`requirements-docs.txt`](../requirements-docs.txt) at the repo root.
- **`material/overrides/` is the only content that belongs here.** Base templates, partials, icons, and bundled JS/CSS come from the pip-installed theme.
- **Never vendor or copy the full Material theme into `material/`.** That blocks upgrades and bloats the repo.

## Layout

```
mkdocs.yml              # site config, theme features, plugins
requirements-docs.txt   # pinned Python deps (Material, plugins)
docs/                   # Markdown content and static assets
  assets/images/        # favicon, hero illustration
  stylesheets/extra.css # site-specific CSS
material/overrides/     # Jinja template overrides only
  home.html             # homepage hero (see docs/index.md front matter)
  main.html             # announce bar and global template hooks
.github/workflows/
  deploy-docs.yml       # production: main → www.datafaker.net
```

## Local development

> [!NOTE]
> Use a Python virtual environment. Modern macOS and many Linux distros block system-wide `pip install` (PEP 668). A venv keeps docs dependencies isolated from the Java project and matches what CI does in a clean environment.

```bash
python3 -m venv .venv-docs
source .venv-docs/bin/activate   # Windows: .venv-docs\Scripts\activate

pip install -r requirements-docs.txt
mkdocs serve          # http://127.0.0.1:8000
mkdocs build --strict
```

`.venv-docs/` is gitignored. Use any venv path you prefer; `.venv-docs` is just a convention for this repo.

## Deploy

- **Production:** push to `main` on [datafaker-net/datafaker](https://github.com/datafaker-net/datafaker) → [`deploy-docs.yml`](../.github/workflows/deploy-docs.yml) → **`gh-pages`** branch → https://www.datafaker.net
- **Manual:** `mkdocs gh-deploy` — only when you intend to update the `gh-pages` branch on the repo your git remote points at.

> [!NOTE]
> GitHub Pages serves the **`gh-pages`** branch, not your source branch (`main`). The deploy workflow builds from `main` and pushes the static site to `gh-pages`.

## Upgrading Material (or related packages)

1. Read the [Material upgrade guide](https://squidfunk.github.io/mkdocs-material/upgrade/) for the target version.
2. Bump the pin in `requirements-docs.txt` (e.g. `mkdocs-material==9.7.6` → next release).
3. Run `pip install -r requirements-docs.txt && mkdocs build --strict`.
4. Review `theme.features` in [`mkdocs.yml`](../mkdocs.yml) — many behaviors are opt-in since Material 9.x (`content.code.copy`, `content.action.edit`, `navigation.footer`, etc.). Keep `navigation.sections` disabled unless you verify that **Releases → 2.x / 1.x** sidebar groups still collapse after re-enabling it (Material 9 treats section items as always expanded).
5. Visually test: homepage hero, search, edit link, footer navigation, **release nav collapse**, mobile layout.
6. If templates break, fix `material/overrides/` and `docs/stylesheets/extra.css` only. Compare with [upstream overrides](https://github.com/squidfunk/mkdocs-material/tree/master/material/overrides). Do **not** re-vendor the theme.
7. Update this README if the workflow or layout changes.

## Customization guidelines

- Prefer `docs/stylesheets/extra.css` and Material CSS variables over vendored or minified CSS bundles.
- Prefer `extra_css` / `extra_javascript` in `mkdocs.yml` over custom JS bundles unless necessary.
- Keep template overrides minimal; extend theme templates (`base.html`, `main.html`) rather than copying them.

## Plugins

Current set in `mkdocs.yml`:

- `search` (Material built-in)
- `macros` (version placeholders in docs, driven by `extra.datafaker` in `mkdocs.yml`)
- `minify`
- `allscreenshots-og-screenshot`

Test each plugin after upgrades; third-party plugins may lag new Material releases.

> [!NOTE]
> **Allscreenshots / Open Graph:** The `allscreenshots-og-screenshot` plugin injects `og:image` URLs via [og.allscreenshots.com](https://og.allscreenshots.com). That service only renders screenshots for verified domains (`www.datafaker.net` is registered).

## Agent note

When asked to upgrade MkDocs Material: use a Python venv, bump `requirements-docs.txt`, run `mkdocs build --strict`, fix `material/overrides/` and `docs/stylesheets/extra.css` only, and never copy theme files into `material/`.
