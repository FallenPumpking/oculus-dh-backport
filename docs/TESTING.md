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

This covers basic settings and DH rendering, not every Euphoria option. Colored
lighting, advanced color tracing, custom images, storm appearance, dimension
transitions, Vault gameplay and multiplayer still need separate acceptance.

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
