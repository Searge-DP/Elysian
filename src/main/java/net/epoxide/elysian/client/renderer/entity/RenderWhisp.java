package net.epoxide.elysian.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderWhisp extends RenderLiving {

	private static final ResourceLocation loc = new ResourceLocation("elysian:textures/blocks/particles/particleEnvironement.png");

	public RenderWhisp() {

		super(new ModelBiped(), 0);
	}

	@Override
	protected void preRenderCallback (EntityLivingBase pet, float par2) {
		float f = 0.0002f;
		GL11.glScalef(f, f, f);
	}

	@Override
	public void doRender (Entity entity, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture (Entity var1) {
		return loc;
	}
}
