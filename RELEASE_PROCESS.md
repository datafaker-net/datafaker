# Release Process

## Creating a New Release

The release process is now driven by GitHub Releases:

> [!CAUTION]
> In general datafaker releases should increment the minor portion of the semver, in 2.x.y the x should be incremented.

1. Go to the [Releases page](https://github.com/datafaker-net/datafaker/releases) of the repository
2. Click on "Draft a new release"
3. Create a new tag in the format `x.y.z` (semver `MAJOR.MINOR.PATCH`; e.g. `2.7.1` for a 2.x patch
4. Set the release title (can be the same as the tag)
5. Add a description of the changes (optional - will be enhanced by automated changelog)
6. Click "Publish release"

The automated workflow will then:
1. Extract the version from the tag
2. Update the Maven project version
3. Build and deploy to Maven Central
4. Generate a changelog and add it to the release notes

Followed by a separate job which will:
1. Create a PR to update the pom.xml to the next SNAPSHOT version
2. Update documentation:
   - At least files
     * `docs/releases/current.version.md`
     * `docs/releases/next.version-SNAPSHOT.md` (repo only; excluded from the docs site)
     * `mkdocs.yml` (see also [`material/README.md`](material/README.md) for docs site / theme dependency updates)
     * `docs/documentation/providers.md`

Snapshot release notes (`docs/releases/{version}-SNAPSHOT.md`) are optional draft pads between releases: excluded from the site and not linked in the Releases nav. The post-release workflow deletes `docs/releases/{released}-SNAPSHOT.md` when that version ships, then creates a fresh `docs/releases/{next}-SNAPSHOT.md` from the template. Stable releases appear at the top of the nav; older releases roll into `{major}.x` sections (e.g. `3.x`, `2.x`).

## Requirements

- The tag must follow semver format: `x.y.z` (where x, y, and z are numeric)
- GitHub Release creation permissions are required to trigger the workflow
- All required secrets must be configured in the repository settings

## Verification

After publishing a release:
1. Check the workflow run in the Actions tab
2. Verify the artifact is published to Maven Central
3. Review the PR created to update the SNAPSHOT version
