# Publication checklist

This repository is prepared for alpha distribution. Do not mark a release stable
or submit to a mod platform until the maintainer has reviewed the testing results.
No upload tokens or platform project IDs are configured. CI never publishes to
CurseForge or Modrinth automatically.

1. Complete docs/TESTING.md and resolve release-blocking issues. Record limitations.
2. Review license notices, source provenance and dependency versions. Use
   AGPL-3.0-only for the combined distribution and link NOTICE/third-party notices.
3. Supply an original human-made project avatar (CurseForge minimum 400×400).
   Do not reuse upstream Oculus branding as this project's listing avatar.
4. Update gradle.properties and CHANGELOG.md, run CI, tag the exact commit.
5. Run scripts/package-release.py. Publish the JAR, full corresponding-source ZIP,
   tester bundle and SHA256SUMS together in a GitHub prerelease. Keep source available
   for every binary version; do not substitute a moving branch link.
6. On the selected platform, use Minecraft 1.18.2 / Forge / client environment.
   Use alpha release type until broader acceptance. Add Embeddium 0.3.18 and
   Distant Horizons 3.3.3 as required dependencies for the advertised setup.
   Record Oculus/Iris/OptiFine as incompatible where supported, and document
   the tested Euphoria Patcher/shader pair from README. Shader packs are separate downloads.
7. Set source and issues links to this repository. Use the listing draft below,
   review its wording personally and include applicable AI disclosures.
8. Upload the tested release and its source. Review the platform's moderation
   response; prepared documentation does not guarantee acceptance.

## Current platform rules checked 2026-10-25

- [CurseForge submission guide](https://support.curseforge.com/support/solutions/articles/9000199552-project-submission-guide-and-tips):
  distinct project name, original avatar, accurate fork licensing and credits.
- [Modrinth content rules](https://modrinth.com/legal/rules/): license-compliant forks
  must meaningfully diverge and credit sources. Our AI-assisted code/documentation
  requires the appropriate AI-content disclosure. Primarily AI-generated projects
  and AI-generated listing/gallery images are prohibited. Assess eligibility
  honestly at submission; do not hide assistance or claim moderation approval.

## Listing draft

Name: Oculus DH Backport

Summary: Distant Horizons shader integration for Minecraft 1.18.2 on Forge.

Description: An independent Oculus fork adapting DH 3.3.3 shader integration to
Minecraft 1.18.2. The initial alpha was developed for testing in Vault Hunters
Paradise Edition with Embeddium 0.3.18 and Complementary Reimagined r5.3.
Replace your existing Oculus JAR, install the matching DH and Embeddium versions,
and obtain a compatible shader separately. Test a copied instance first.
This alpha does not claim every feature of modern Oculus/Iris or compatibility
with every shader. Alpha 4 also tests Complementary Unbound r5.9.3 with Euphoria
Patcher 1.10.5, including settings edits and DH rendering. Advanced Euphoria
features remain unverified. Do not combine with OptiFine,
Iris or another Oculus JAR. See the release's testing matrix for verified behavior.

Based on Iris/Oculus, Teqed's renderer fork and fangyu-mc's DH bridge, with full
credits and licensing in the source repository. Not endorsed by those projects.
Developed with OpenAI Codex assistance on the backport and documentation.

Source: https://github.com/FallenPumpking/oculus-dh-backport
Issues: https://github.com/FallenPumpking/oculus-dh-backport/issues
License: AGPL-3.0-only (combined distribution; retained component licenses documented)
