# Repository workflow

- Start changes on a short-lived branch from current `origin/main`, such as
  `fix/act-lighting`, `feat/shader-support`, or `chore/build-tools`.
- Do not commit or push directly to `main`. Push the branch and open a focused
  pull request with the problem, final change, validation and known limitations.
- Follow CONTRIBUTING.md. Preserve licenses, provenance and AI-assistance
  disclosures. Do not commit private game data or redistributed shader packs.
- Review the complete diff and wait for GitHub's required `build` check on the
  current PR revision. Record relevant in-game evidence for renderer changes.
  Resolve review conversations before merging. Do not bypass protection or
  weaken checks to get a failing change merged.
- Use squash merge when merging is within the user's authorized task. Otherwise
  leave the PR ready for the maintainer. Do not invent a second-person review.
- Publishing is separate from merging. Follow docs/PUBLISHING.md; publish only
  within the user's authorized release scope. Never overwrite an existing
  version's artifacts or move its tag to include later changes.
- Documentation or workflow changes do not require a new mod version or release.
  Keep the released jar and corresponding source associated with their exact tag.
