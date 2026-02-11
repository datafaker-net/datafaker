# Release Process

## Creating a New Release

The release process is now driven by GitHub Releases:

> [!CAUTION]
> In general datafaker releases should increment the minor portion of the semver, in 2.x.y the x should be incremented.

1. Go to the [Releases page](https://github.com/datafaker-net/datafaker/releases) of the repository
2. Click on "Draft a new release"
3. Create a new tag in the format `2.x.y` (where x and y are the minor and patch version numbers)
4. Set the release title (can be the same as the tag)
5. Add a description of the changes (optional - will be enhanced by automated changelog)
6. Click "Publish release"
7. Update documentation:
   - At least files
     * `docs/releases/current.version.md`
     * `docs/releases/next.version-SNAPSHOT.md`
     * `mkdocs.yml`
   - And probably file `docs/documentation/providers.md` (if new providers have been added)

The automated workflow will:
1. Extract the version from the tag
2. Update the Maven project version
3. Build and deploy to Maven Central
4. Generate a changelog and add it to the release notes
5. Create a PR to update the pom.xml to the next SNAPSHOT version

## Requirements

- The tag must follow semver format: `2.x.y` (where x and y are numeric)
- GitHub Release creation permissions are required to trigger the workflow
- All required secrets must be configured in the repository settings

## Verification

After publishing a release:
1. Check the workflow run in the Actions tab
2. Verify the artifact is published to Maven Central
3. Review the PR created to update the SNAPSHOT version
