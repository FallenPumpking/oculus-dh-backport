package net.coderbot.iris.compat.dh;

import io.github.douira.glsl_transformer.ast.transform.SingleASTTransformer;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.coderbot.iris.gl.blending.AlphaTest;
import net.coderbot.iris.pipeline.newshader.ShaderAttributeInputs;
import net.coderbot.iris.pipeline.transform.Patch;
import net.coderbot.iris.pipeline.transform.PatchShaderType;
import net.coderbot.iris.pipeline.transform.parameter.SodiumParameters;
import net.coderbot.iris.pipeline.transform.transformer.SodiumCoreTransformer;
import net.coderbot.iris.pipeline.transform.transformer.SodiumTransformer;

/** The 1.18.2 renderer supplies float-converted light attributes, including to core packs. */
public final class TerrainLightAttributeCheck {
    public static void main(String[] args) {
        var parameters = new SodiumParameters(Patch.SODIUM, new Object2ObjectOpenHashMap<>(),
                AlphaTest.ALWAYS, new ShaderAttributeInputs(true, true, false, true, true),
                32.0f / 65536.0f, -8.0f, 1.0f / 65536.0f);
        parameters.type = PatchShaderType.VERTEX;
        for (boolean core : new boolean[] {false, true}) {
            var transformer = new SingleASTTransformer<SodiumParameters>();
            transformer.setRootSupplier(RootSupplier.PREFIX_UNORDERED_ED_EXACT);
            transformer.setTransformation((tree, root) -> {
                if (core) SodiumCoreTransformer.transform(transformer, tree, root, parameters);
                else SodiumTransformer.injectVertInit(transformer, tree, root, parameters);
            });
            String source = core
                    ? "#version 330 core\nin ivec2 vaUV2; out vec2 light; void main(){light=vec2(vaUV2);gl_Position=vec4(0.0);}\n"
                    : "#version 330 compatibility\nout vec2 light; void main(){light=vec2(_vert_tex_light_coord);gl_Position=vec4(0.0);}\n";
            String result = transformer.transform(source).replaceAll("\\s+", " ");
            if (!result.contains("in vec2 a_LightCoord") || result.contains("in ivec2 a_LightCoord"))
                throw new AssertionError("Light attribute must match float binding: " + result);
            if (!result.contains("vec2 _vert_tex_light_coord") || result.contains("ivec2 _vert_tex_light_coord"))
                throw new AssertionError("Legacy light coordinates must remain numeric floats: " + result);
            if (core && !result.matches("(?s).*ivec2\\s*\\(\\s*a_LightCoord\\s*\\).*"))
                throw new AssertionError("Core light coordinates need an explicit integer conversion: " + result);
        }
        System.out.println("Terrain light attribute checks passed: float binding and core-profile integer conversion");
    }
}
