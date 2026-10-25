package net.coderbot.iris.compat.dh;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.toml.TomlParser;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.VersionRange;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

/** Validate the remapped release artifact against the actual downloaded mod metadata. */
public final class DependencyMetadataCheck {
    private static Config readMetadata(JarFile jar) throws Exception {
        var entry = jar.getJarEntry("META-INF/mods.toml");
        if (entry == null) throw new AssertionError("Missing Forge metadata: " + jar.getName());
        try (var reader = new InputStreamReader(jar.getInputStream(entry), StandardCharsets.UTF_8)) {
            return new TomlParser().parse(reader);
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> installed = new HashMap<>();
        installed.put("minecraft", args[2]);
        installed.put("forge", args[3]);
        try (var files = Files.list(Path.of(args[0], "deps"))) {
            for (Path file : files.filter(p -> p.toString().endsWith(".jar")).toList()) {
                try (var jar = new JarFile(file.toFile())) {
                    List<Config> mods = readMetadata(jar).get("mods");
                    for (Config mod : mods) {
                        String version = mod.get("version");
                        if ("${file.jarVersion}".equals(version)) {
                            version = jar.getManifest().getMainAttributes().getValue("Implementation-Version");
                        }
                        if (version == null || version.contains("${")) {
                            throw new AssertionError("Unresolved version in " + file);
                        }
                        installed.put(mod.get("modId"), version);
                    }
                }
            }
        }
        try (var jar = new JarFile(args[1])) {
            List<Config> dependencies = readMetadata(jar).get("dependencies.oculus");
            for (Config dependency : dependencies) {
                String id = dependency.get("modId");
                String actual = installed.get(id);
                if (actual == null) throw new AssertionError("Missing test fixture for dependency " + id);
                String spec = dependency.get("versionRange");
                VersionRange range = VersionRange.createFromVersionSpec(spec);
                if (!range.containsVersion(new DefaultArtifactVersion(actual))) {
                    throw new AssertionError(id + " " + actual + " rejected by packaged range " + spec);
                }
                if ("embeddium".equals(id)) {
                    for (String unsupported : List.of("0.3.17+mc1.18.2", "0.3.19+mc1.18.2", "0.3.18+mc1.20.1")) {
                        if (range.containsVersion(new DefaultArtifactVersion(unsupported))) {
                            throw new AssertionError("Unexpectedly accepts untested Embeddium " + unsupported);
                        }
                    }
                }
                System.out.println("Accepted packaged dependency: " + id + " " + actual + " in " + spec);
            }
        }
    }
}
