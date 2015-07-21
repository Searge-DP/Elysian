package net.epoxide.elysian.entity.render;

import net.epoxide.elysian.entity.EntityEnvironementCreature;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderElysianEnvCreature extends RenderLiving {

	public RenderElysianEnvCreature() {

		super(new ModelBiped(), 0);
	}
	
	@Override
	protected void preRenderCallback(EntityLivingBase pet, float par2) {
		float f = 0.0002f;
		GL11.glScalef(f, f, f);
	}
	
	
	public void renderDragonFly(EntityEnvironementCreature entity, double par2, double par4, double par6, float par8, float par9) {

		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {

		this.renderDragonFly((EntityEnvironementCreature) par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {

		this.renderDragonFly((EntityEnvironementCreature) par1Entity, par2, par4, par6, par8, par9);
	}

	private static final ResourceLocation loc = new ResourceLocation("elysian:textures/blocks/particles/particleEnvironement.png");
	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return loc;
	}
}
