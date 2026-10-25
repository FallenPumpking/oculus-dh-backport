package net.coderbot.iris.compat.dh;

import com.google.common.collect.ImmutableList;
import net.coderbot.iris.shaderpack.StringPair;
import net.coderbot.iris.shaderpack.include.IncludeGraph;
import net.coderbot.iris.shaderpack.option.ShaderPackOptions;
import net.coderbot.iris.shaderpack.preprocessor.PropertiesPreprocessor;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public final class PropertiesConditionCheck {
    public static void main(String[] args) throws Exception {
        var root = Files.createTempDirectory("oculus-properties-check");
        try {
            var options = new ShaderPackOptions(new IncludeGraph(root, ImmutableList.of()), Map.of());
            var defines = List.of(new StringPair("IS_IRIS", ""), new StringPair("MC_VERSION", "11802"));
            var replacements = List.of(new StringPair("IS_IRIS", ""), new StringPair("MC_VERSION", "11802"),
                    new StringPair("BIOME_FOREST", "42"), new StringPair("BIOME_FOREST_HILLS", "43"));
            String source = """
                    #ifndef IS_IRIS
                    wrong.branch=true
                    #endif
                    #if defined PHOTONICS && PHOTONICS_VERSION >= 301 && defined IS_IRIS
                    #if PHOTONICS_LIGHTING_MODE == 1
                    ignored.mode=true
                    #endif
                    size.buffer.colortex9=PHOTONICS_RENDER_SCALE PHOTONICS_RENDER_SCALE
                    #endif
                    #if defined(IS_IRIS) && MC_VERSION >= 11800
                    supported.branch=true
                    #endif
                    #if !defined IS_IRIS || defined MC_OS_MAC
                    wrong.platform=true
                    #endif
                    uniform.int.forest=BIOME_FOREST
                    uniform.int.hills=BIOME_FOREST_HILLS
                    screen=IS_IRIS_SOMETHING MY_BIOME_FOREST
                    """;
            String result = PropertiesPreprocessor.preprocessSource(source, options, defines, replacements);
            if (result.contains("wrong.") || result.contains("size.buffer.") || result.contains("ignored.mode"))
                throw new AssertionError("Inactive branches leaked: " + result);
            for (String expected : List.of("supported.branch=true", "forest=42", "hills=43", "IS_IRIS_SOMETHING MY_BIOME_FOREST"))
                if (!result.contains(expected)) throw new AssertionError("Missing " + expected + ": " + result);
            System.out.println("Properties conditional checks passed: empty defines, nested inactive branches, full identifiers and constants");
        } finally {
            Files.delete(root);
        }
    }
}
