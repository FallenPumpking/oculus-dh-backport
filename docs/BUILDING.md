# Building

Install a JDK 21 and Python 3.11+. Set JAVA_HOME to the JDK directory.
Clone this repository, then run from its root:

```sh
python scripts/fetch-dependencies.py
./gradlew build projectionChecks
python scripts/package-release.py
```

Windows: use `py -3` in place of `python` and `gradlew.bat` in place of `./gradlew`.
Outputs are in `build/libs` and `dist`. The mod compiles to Java 17 bytecode;
Gradle 8.5 runs on JDK 21. Architectury Loom is pinned to 1.2.377.
The dependency downloader checks SHA-256 before replacing a local file.
It downloads official DH and Embeddium artifacts; neither is bundled in releases.
Gradle downloads Minecraft, Forge and its toolchain dependencies separately.

`projectionChecks` runs two standalone regression programs; a pass is not a GPU
or full-game integration test. See TESTING.md for manual acceptance.
`build` also runs `dependencyMetadataCheck`, which reads the remapped release JAR
and checks its ranges against the actual dependency JARs with Maven version parsing.
The source archive includes the Gradle wrapper, build scripts, dependency lock
and all vendored source. Build timestamps/host differences may affect JAR hashes;
bit-for-bit reproducible builds are not claimed.
