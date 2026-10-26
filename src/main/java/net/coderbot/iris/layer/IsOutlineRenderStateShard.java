package net.coderbot.iris.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.coderbot.iris.Iris;
import net.coderbot.iris.pipeline.newshader.CoreWorldRenderingPipeline;
import net.minecraft.client.renderer.RenderStateShard;

public class IsOutlineRenderStateShard extends RenderStateShard {
	public static final IsOutlineRenderStateShard INSTANCE = new IsOutlineRenderStateShard();
	private static final float[] savedColor = new float[4];
	private static boolean restoreColor;

	private IsOutlineRenderStateShard() {
		super("iris:is_outline", IsOutlineRenderStateShard::begin, IsOutlineRenderStateShard::end);
	}

	private static void begin() {
		GbufferPrograms.beginOutline();
		var pipeline = Iris.getPipelineManager().getPipelineNullable();
		restoreColor = pipeline instanceof CoreWorldRenderingPipeline core && core.shouldOverrideShaders();
		if (restoreColor) {
			// The vanilla outline already carries its color and alpha in the vertices.
			// Batched draws must not inherit a tint left by an unrelated earlier draw.
			System.arraycopy(RenderSystem.getShaderColor(), 0, savedColor, 0, savedColor.length);
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	private static void end() {
		if (restoreColor) {
			RenderSystem.setShaderColor(savedColor[0], savedColor[1], savedColor[2], savedColor[3]);
			restoreColor = false;
		}
		GbufferPrograms.endOutline();
	}
}
