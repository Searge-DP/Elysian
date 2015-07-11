package net.epoxide.elysian.dimensionStuff;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderElysian extends WorldProvider {
    
    @Override
    public String getDimensionName () {
    
        return "Elysian";
    }
    
    public IChunkProvider createChunkGenerator () {
    
        return new ChunkProviderElysian(this.worldObj, this.worldObj.getSeed());
    }
    
    public boolean renderStars () {
    
        return false;
    }
    
    public boolean renderClouds () {
    
        return true;
    }
    
    /* ===Sun and mon sizes. could be fun. don't ee the point of it though== */
    public float setSunSize () {
    
        return 4.0F;
    }
    
    public float setMoonSize () {
    
        return 15.0F;
    }
    
    // enable to respawn in this dimension ? would allow for people to live in our wonderous
    // world
    public boolean canRespawnHere () {
    
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public float getCloudHeight () {
    
        return 128.0F;
    }
    
    public boolean canCoordinateBeSpawn (int par1, int par2) {
    
        return true;
    }
    
    protected void generateLightBrightnessTable () {
    
        float f = 12.0F;
        for (int i = 0; i <= 15; i++) {
            float f1 = 12.0F - i / 15.0F;
            this.lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public String getWelcomeMessage () {
    
        if ((this instanceof WorldProviderElysian)) {
            return "Entering Elysian, Runic Dimension";
        }
        return null;
    }
    
}
