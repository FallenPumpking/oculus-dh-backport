# License audit — 2026-10-25

The private prototype's LGPL-only label was incomplete. The bundled GLSL
transformer is not the LGPL 1.0.1 used by the DH donor's separate build.
Comparison against Maven Central source archives found 226 of 228 Java files
matching glsl-transformer 2.0.0 after whitespace and package-relocation
normalization. The remaining changes are a comment encoding difference and
covariant collection/stream generic parameters in TranslationUnit.java.
The same match occurs against 2.0.0-pre13, whose POM lists GPL with exceptions;
the current upstream tag license is AGPL. We do not rely on that ambiguous
pre-release metadata to assert an LGPL-only distribution.

This distribution uses the documented AGPL 2.0.0 licensing basis, retains the
modified source, and supplies all source and build scripts alongside binaries.
No later relicensing claim is used to remove historical upstream rights.

Evidence:
- [2.0.0 POM](https://repo.maven.apache.org/maven2/io/github/douira/glsl-transformer/2.0.0/glsl-transformer-2.0.0.pom)
- [2.0.0 sources](https://repo.maven.apache.org/maven2/io/github/douira/glsl-transformer/2.0.0/glsl-transformer-2.0.0-sources.jar)
- [Pinned upstream license](https://github.com/IrisShaders/glsl-transformer/blob/0ea0e35e68c59e5348340cdc659e2f3558cfe5ea/LICENSE)
- [LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.html), [GPLv3](https://www.gnu.org/licenses/gpl-3.0.html), [AGPLv3](https://www.gnu.org/licenses/agpl-3.0.html)

The main code provenance is recorded in NOTICE.md. Source headers, Apache NOTICE,
ANTLR BSD notice and JOML copyright notices are retained. No claim is made to
upstream trademarks. The original Oculus logo is retained only as an inherited
in-game resource; it is not the publishing avatar. Supply an original human-made
avatar before platform submission. Platform approval remains a separate review.
