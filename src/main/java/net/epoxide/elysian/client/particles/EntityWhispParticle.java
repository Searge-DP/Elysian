package net.epoxide.elysian.client.particles;

import net.epoxide.elysian.entity.EntityHandler;
import net.epoxide.elysian.lib.ColorObject;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityWhispParticle extends EntityFX {
    
    public EntityWhispParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
    	super(world, x, y, z, motionX, motionY, motionZ);
    	init();
    }
    
    public EntityWhispParticle(World world, double x, double y, double z) {
        super(world, x, y, z);
        init();
    }
    
    private void init () {
    
        setParticleIcon(EntityHandler.whispParticle);
        
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        
        particleAlpha = 0.2f + rand.nextFloat();
        particleScale = 0.5f + rand.nextFloat();
        noClip = true;
        
        BiomeGenBase biome = worldObj.getBiomeGenForCoords((int) posX, (int) posZ);
        ColorObject color = new ColorObject(biome.color);
        this.particleRed = color.red;
        this.particleGreen = color.green;
        this.particleBlue = color.blue;
        particleMaxAge = 500;
        
    }
    
    @Override
    public void renderParticle (Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
    
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
        par1Tessellator.setBrightness(rand.nextInt(100) + 50);
        
    }
    
    @Override
    public void onUpdate () {
    
        super.onUpdate();
        motionY -= 0.003f;
        
    }
    
    @Override
    public int getFXLayer () {
    
        return 1;
    }
}
