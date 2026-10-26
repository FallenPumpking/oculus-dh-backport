# Alpha acceptance checklist

Test a copy of your instance. Record mod version, DH/Embeddium/Forge/Java versions,
shader and settings, GPU/driver, OS, modpack version and reproduction steps.
Attach logs after removing tokens, private server addresses and personal paths.
Prefer before/after screenshots at the same camera position and settings.

| Check | Status |
| --- | --- |
| New single-player world, Complementary r5.3 + DH 3.3.3 | Passed on private prototype, 2026-10-25 |
| Distant terrain beyond vanilla distance | Passed with screenshot and DH buffer log evidence |
| Save, quit and DH database shutdown | Passed |
| Projection depth and history checks | Passed on prototype; automated in CI |
| Publication packaging build | Recorded per release/CI, not a gameplay test |
| Alpha 4: Unbound r5.9.3 + Euphoria 1.10.5 + DH 3.3.3 | Passed settings edit/apply, Overworld rendering, save and shutdown on 2026-10-25 |
| Alpha 4: shader-properties conditional regression | Passed; automated in CI |
| Alpha 5: terrain lighting and legacy/core attribute regression | Passed; regression automated in CI |
| Alpha 5: ACT four chunks + DH | Passed colored illumination, DH terrain, save and shutdown, 2026-10-25 |
| Alpha 5: screen-space colored lighting + DH | Passed colored illumination, DH terrain, save and shutdown, 2026-10-25 |
| Alpha 5: ACT + screen-space mode + DH | Passed both compiled paths, colored illumination, DH terrain, save and shutdown, 2026-10-25 |
| Cold start and repeat world load on packaged release | Pending tester confirmation |
| Multiplayer connect/disconnect/reconnect | Pending |
| Vault entry, combat, exit and dimension transitions | Pending |
| Nether/End, water/fog/shadows and weather | Pending |
| Shader disable/enable/reload and alternate shader | Pending |
| Fullscreen toggle, resizing and resource reload | Pending |
| At least one hour of normal gameplay | Pending |
| Compare frame times, memory and chunk-generation load | Pending; no benchmark claim |

## Alpha 4 Euphoria check

The copied Vault Hunters Paradise instance ran Forge 40.3.11, Embeddium
0.3.18+mc1.18.2, DH 3.3.3 and EuphoriaPatcher 1.10.5-r5.9.3 on Java 21.0.9.
The patcher generated `ComplementaryUnbound_r5.9.3 + EuphoriaPatches_1.10.5`
locally from the separately downloaded shader. No shader files are distributed.

Shader settings opened. Changing Epic Thunderstorm from Off to On and applying
saved `RAIN_ATMOSPHERE=true` and reloaded the shader successfully. The disposable
Overworld then rendered terrain and water beyond four vanilla chunks. Logs
confirmed shader GBuffer integration, terrain offsets beyond 256 blocks and a
135,744-byte DH vertex buffer. The world saved and DH databases closed normally.
The 60 FPS test cap was a load-control setting, not a benchmark result.

This alpha 4 check covers basic settings and DH rendering, not every Euphoria
option. See the alpha 5 lighting checks below for additional coverage. Storm
appearance, dimension transitions, Vault gameplay and multiplayer still need
separate acceptance.

## Alpha 5 Euphoria lighting checks

The same copied instance and shader versions were tested on Windows with an
NVIDIA GeForce RTX 5090 and driver 617.42, at night from a fixed
camera in a disposable Overworld. A gray concrete floor and wall surrounded
glowstone, a soul lantern and a redstone torch. Vanilla distance was four chunks,
DH remained enabled at 256 chunks, and real-time shadows were enabled
(`SHADOW_QUALITY=2`). The 60 FPS cap controls load; this is not a benchmark.

The baseline exposed corrupted block-light values caused by an integer terrain
attribute receiving Embeddium 1.18.2's float-converted vertex input. Correcting
the attribute restored smooth light falloff. A transformer regression check
covers both legacy and core shader paths.

ACT initially failed to compile `shadowcomp.csh`: the source preprocessor used
an environment snapshot without resolved optional feature macros. Forwarding
the updated environment retains ACT's custom-image samplers and color helpers.
The shader pack itself was not modified. ACT at four chunks
(`MCBL_MAIN_DEFINE=0`, `COLORED_LIGHTING=128`) then showed warm, cyan and red
illumination. Logs confirmed DH terrain beyond 256 blocks, one ACT flood-fill
compute source, no targeted shader/GL errors, normal world save and DH shutdown.

Screen-space lighting (`MCBL_MAIN_DEFINE=1`, `COLORED_LIGHTING=0`) also showed
colored spill with DH. The combined mode (`MCBL_MAIN_DEFINE=2`,
`COLORED_LIGHTING=128`) passed with both the ACT flood-fill compute path and 44
screen-space fragment sources active. DH logs reported terrain beyond 256 blocks
and a 372,672-byte vertex buffer in the combined run. Both modes saved and shut
down normally, with zero targeted shader compilation or GL errors.

ACT's tested range covers nearby voxel lighting. The shader disables its ACT
lighting path for DH terrain/water programs; this does not establish colored
light propagation across the full DH view distance. Larger ACT grids, the
dynamic-lighting compatibility mode (`MCBL_MAIN_DEFINE=3`), modded light sources,
Vault dimensions, multiplayer and long sessions remain unverified.

One earlier startup attempt failed in Vault Filters' Create ItemAttribute
registration. An unchanged retry passed; no fix for that separate modpack
startup issue is claimed here.

For visual acceptance temporarily use four vanilla chunks; ensure distant terrain
still renders with shaders. Restore your preferred distance afterward. Do not
interpret an FPS cap as a benchmark. Report crashes, missing geometry, seams,
flicker, broken transparency, shadow defects and memory growth separately.
The private test used Java 21.0.9, a 4–12 GiB heap, generational ZGC, DH 256 chunks
and seven worker threads. These are observations, not universal recommendations.

## Public packaging verification

The first local public-alpha build passed on 2026-10-25 with pinned Loom 1.2.377,
Gradle 8.5 and JDK 21.0.9. All 1,654 compiled `.class` entries matched the private
backport.1 prototype byte-for-byte. Packaging changes update notices and metadata;
this check does not replace launching the final artifact in the target instance.
