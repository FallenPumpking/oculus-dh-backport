# Oculus DH Backport

An unofficial Oculus fork bringing **Distant Horizons 3.3.3 shader rendering to
Minecraft 1.18.2 on Forge**.

**Alpha — testing in progress.** Verified in a Vault Hunters Paradise Edition
single-player test world with Complementary Unbound r5.9.3 + Euphoria Patches
1.10.5 (alpha 5), and previously Complementary Reimagined r5.3. This is not a full
backport of every feature in modern Oculus or Iris. Report problems here rather
than to upstream maintainers.

[Downloads](https://github.com/FallenPumpking/oculus-dh-backport/releases) ·
[Report an issue](https://github.com/FallenPumpking/oculus-dh-backport/issues/new/choose) ·
[Testing guide](docs/TESTING.md) · [Build guide](docs/BUILDING.md)

## Supported test combination

| Component | Tested version |
| --- | --- |
| Minecraft | 1.18.2 |
| Loader | Forge 40.3.11 |
| Renderer | Embeddium 0.3.18 for 1.18.2 |
| Distant Horizons | 3.3.3 for 1.18.2 |
| Shader (alpha 5) | Complementary Unbound r5.9.3 + Euphoria Patches 1.10.5 |
| Patcher (optional) | EuphoriaPatcher 1.10.5-r5.9.3 for Forge |
| Previously tested shader | Complementary Reimagined r5.3 |
| Java | 21.0.9 runtime; Java 17 bytecode |

Other combinations are unverified. Java 21 compatibility depends on the entire
modpack. This release does not establish that Java 21 or ZGC improves performance.

## Installation

1. Back up or duplicate your instance. Install this on the client.
2. Remove or disable the existing Oculus JAR. Install the release JAR into `mods`.
3. Install the matching Embeddium and DH versions above from their official pages.
4. Disable Far Sight for the tested setup. Euphoria Patcher may remain enabled
   when using the matching r5.9.3 / 1.10.5 combination above on alpha 4 or newer.
   Do not combine with OptiFine, Iris, or a second Oculus JAR.
5. Obtain a DH-compatible shader separately and enable it in Video Settings.
6. Allow DH terrain to build. Test a disposable world before valuable saves.

For rollback, close the game and restore the original mod files/configuration.
This mod retains the `oculus` mod ID so existing integrations can recognize it.
The inherited `1.8.1` version prefix does not promise Oculus 1.8.1 feature parity.

## What is verified

- Shader-integrated DH terrain and water visible beyond a four-chunk vanilla
  distance in a local test world; normal world save and shutdown.
- Projection-depth and temporal-history helper checks.
- Successful compilation of the 1.18.2 integration.
- Alpha 4: Euphoria shader settings open, an option can be changed and saved,
  and applying settings reloads the shader successfully. Euphoria + DH terrain
  rendered in the disposable Overworld test with a four-chunk vanilla distance.
- Alpha 5: corrected terrain block lighting and ACT compute-shader compilation.
  Euphoria screen-space lighting, ACT at four chunks, and ACT + screen-space
  mode render colored illumination while DH draws distant terrain.
- Alpha 6 candidate: isolate block-selection outline color state, restoring
  Complementary's rainbow outline in first person with Botania in the tested pack.
  First/third person and ACT settings were checked with Euphoria and DH enabled.

Multiplayer, Vault runs, long sessions, broader reload scenarios and resize remain on the
[acceptance checklist](docs/TESTING.md). Optional entity sorting is disabled;
batching remains enabled. Larger ACT grids, modded light sources and other
untested settings remain unverified. ACT lights nearby terrain; this does not
extend color tracing across the full DH distance. The test is not a promise of
full modern Iris parity.
There is no broad shader compatibility or FPS claim.

## Source, licensing and credits

The combined release is **AGPL-3.0-only**, with inherited components retaining
their individual notices and license terms. See [NOTICE](NOTICE.md),
[third-party notices](THIRD_PARTY_NOTICES.md) and the [license audit](docs/LICENSE-AUDIT.md).
Share binaries with a link to the exact release's corresponding source, or use
the tester bundle which includes both. Source and build scripts are public.

Based on [Oculus](https://github.com/Asek3/Oculus), [Teqed's fork](https://github.com/Teqed/Oculus),
[Iris](https://github.com/IrisShaders/Iris), and [fangyu's DH bridge](https://github.com/fangyu-mc/oculus-dh3-compat).
Not affiliated with or endorsed by those projects or by Mojang/Microsoft.
Project initiated and maintained by **Fallen PumpKing**. Backport direction and
testing by Fallen PumpKing, with development assistance from OpenAI Codex.
Based on Iris/Oculus and the credited upstream contributors.
No platform submission has been made.
