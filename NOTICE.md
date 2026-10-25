# Notices and provenance

Oculus DH Backport is an independent, unofficial Minecraft 1.18.2 Forge fork.
It is not endorsed by Oculus, Iris, Distant Horizons, Mojang, or Microsoft.
Upstream copyrights remain with their respective authors. No shader packs,
Minecraft binaries, Forge binaries, Embeddium binaries, or DH binaries are bundled.

## Source origins

| Source | Snapshot | Contribution |
| --- | --- | --- |
| [Teqed/Oculus](https://github.com/Teqed/Oculus) | `f5259dbae072e77545c74c6ce2d5b368c3d32075` | Base renderer and embedded libraries |
| [Asek3/Oculus](https://github.com/Asek3/Oculus) | `c39e6c3c06d4f851bd1bf5eac3e634488fc3a10c` | Native 1.18.2 implementations |
| [fangyu-mc/oculus-dh3-compat](https://github.com/fangyu-mc/oculus-dh3-compat) | `7512883a5ae004d12cd01ee45428638c5a58da13` | DH 3 bridge, projection helpers and checks |
| [Iris Shaders](https://github.com/IrisShaders/Iris) | inherited through the above forks | Original shader renderer; coderbot, IMS212, Justsnoopy30, FoundationGames, Kroppeb and contributors |

The donor bridge credits [PlxelBuilder's groundwork](https://github.com/PlxelBuilder/Oculus-1.16.5-DH-Support/commit/00c58a2d6e571f01b3f8519c2dbb3c526e4800ae)
and [Iris commit a76a240](https://github.com/IrisShaders/Iris/commit/a76a240527e93780bbcba57c09bef377419d47a7).
Those credits are retained here.

## Distribution licensing

This combined distribution is supplied under AGPL-3.0-only because it includes
the AGPL glsl-transformer component. The upstream Oculus/Iris/DH-bridge portions
retain their LGPL-3.0-only notices and permissions; permissive components retain
their own licenses. The combined-work route follows LGPLv3 section 2(b), GPLv3
section 13 and AGPLv3 section 13. This does not claim ownership of upstream code
or revoke recipients' existing rights in separately licensed components.
See LICENSE, LICENSES/, THIRD_PARTY_NOTICES.md and docs/LICENSE-AUDIT.md.

## Local modifications, 2026-10-25

Maintained by FallenPumpking. Ported rendering, GUI, resource and mixin targets to
1.18.2; adapted DH 3.3.3 rendering hooks; added DH shader programs, uniforms,
projection/depth handling and framebuffer integration. Optional entity sorting
is disabled because its injection conflicts with the tested pack.
Build/release packaging, notices, tests and documentation were added.
These modifications and documentation were developed with OpenAI Codex assistance.
Existing source headers and copyright notices are preserved.
