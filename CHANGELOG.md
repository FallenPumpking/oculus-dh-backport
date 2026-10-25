# Changelog

## 1.8.1-dh3.3.3-backport.3-alpha — 2026-10-25

- Fix startup rejection of the intended Embeddium build: Forge compares its full
  version `0.3.18+mc1.18.2`, which did not match alpha 2's exact `[0.3.18]` pin.
- Validate dependency ranges from the actual remapped release JAR against the
  downloaded mods' metadata using Maven's version-range implementation in CI.
- Retain the narrow tested-version requirement; no renderer changes.

## 1.8.1-dh3.3.3-backport.2-alpha — 2026-10-25

- Public alpha packaging of the locally tested Forge 1.18.2 / DH 3.3.3 bridge.
- Complete source/build scripts, pinned build inputs, projection checks and CI.
- Corrected combined-distribution license labeling and embedded third-party notices.
- Added repository links, explicit dependency metadata, issue forms and testing guide.
- No intended rendering behavior changes from the private backport.1 prototype.

The private prototype passed a local Complementary Reimagined r5.3 test. The
packaged alpha still requires tester acceptance; see docs/TESTING.md.
