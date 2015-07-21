package net.epoxide.elysian.entity.effects;

import net.epoxide.elysian.entity.EntityHandler;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityElysianEnvironementEffect extends EntityFX{

	public EntityElysianEnvironementEffect(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		init();
	}

	public EntityElysianEnvironementEffect(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		init();
	}

	private void init(){
		setParticleIcon(EntityHandler.entity);

		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;

		particleAlpha = 0.2f + rand.nextFloat();
		particleScale = 0.5f + rand.nextFloat();
		noClip = true;

		BiomeGenBase biome = worldObj.getBiomeGenForCoords((int)posX, (int)posZ);
		int color = biome.color;
		
		int r = (color)&0xFF;
		int g = (color>>8)&0xFF;
		int b = (color>>16)&0xFF;
		
		particleRed = (float)r / 255f;
		particleGreen = (float)g / 255f;
		particleBlue = (float)b / 255f;

		particleMaxAge = 500;

	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2,
			float par3, float par4, float par5, float par6, float par7) {

		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
		par1Tessellator.setBrightness(rand.nextInt(100) + 50);

	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		motionY -= 0.003f;

	}

	@Override
	public int getFXLayer() {
		return 1;
	}
}
