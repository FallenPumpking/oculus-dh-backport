# Contributing

Open a focused issue with reproduction steps before large changes. Use a small
pull request describing the problem, implementation and validation. Build and
run `projectionChecks`; renderer changes also need in-game evidence.
Preserve upstream copyright headers, record copied code's source and license,
and update NOTICE/THIRD_PARTY_NOTICES when dependencies change. Contributions
must be compatible with the applicable licenses; no copyright assignment is
required. Disclose AI-assisted changes and review/test them before submission.
Never commit credentials, launcher accounts, personal worlds, game binaries,
shader packs, logs with personal data, or downloaded dependency JARs.
Be respectful, discuss technical behavior, and avoid personal attacks.

## Branch and pull request workflow

`main` is the shared integration branch. Make each focused change on a short-lived
branch, then bring it into `main` through a pull request. Avoid long-lived `dev`
branches and direct pushes to `main`.

1. With a clean checkout, update `main` and create a branch:

   ```sh
   git switch main
   git pull --ff-only origin main
   git switch -c fix/short-description
   ```

   Use `fix/`, `feat/`, `docs/`, or `chore/` names as appropriate. External
   contributors can use the same workflow from a fork.
2. Make focused commits and push the branch:

   ```sh
   git push -u origin fix/short-description
   ```

3. Open a pull request targeting `main`. Use a draft while work is incomplete.
   Explain the concrete problem, resulting behavior, validation and limitations.
   Link the relevant issue where one exists. Disclose AI assistance.
4. Review the diff and wait for **Build and validate / build** to pass. This check
   builds the mod, runs regression and dependency-metadata checks, and verifies
   packaging/notices/source. Renderer changes also need relevant in-game testing;
   a green build does not establish gameplay compatibility.
5. If `main` changes, update the PR branch from `origin/main` and let checks rerun.
   Resolve review conversations, then use **Squash and merge**. Give the PR a
   descriptive title: it becomes the combined commit's title on `main`.
6. GitHub deletes the merged source branch automatically. Return to `main` and
   `git pull --ff-only origin main` before starting the next branch.

## Repository merge policy

- A pull request is required for `main`, including for administrators.
- The GitHub Actions `build` check must pass against an up-to-date base.
- Review conversations must be resolved; force pushes and deletion of `main`
  are blocked. Only squash merging is enabled, preserving linear main history.
- No second-person approval is required while the project has one maintainer.
  The maintainer reviews the diff, tests and limitations before merging; this
  does not count as an independent review.
- Automatic merging is not enabled. The maintainer or an authorized agent merges
  after validation. Do not bypass protection to merge a failing PR.

## Releases are a separate step

A merged PR updates the source on `main`; it does not publish a jar. Several PRs
may be collected into one release. Documentation and workflow changes can merge
without changing the mod version. CI artifacts are temporary test downloads,
not published releases.

For a new mod release, use a release-preparation PR for the version, changelog,
notices and test record. After merging, build/package the exact clean commit on
`main`, tag it, and publish the jar with matching complete source and checksums.
See [the publication checklist](docs/PUBLISHING.md). Existing release tags and
downloads remain tied to their original code; fixes receive a new version.
