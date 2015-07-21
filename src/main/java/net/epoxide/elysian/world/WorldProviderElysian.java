package net.epoxide.elysian.world;

import net.epoxide.elysian.handler.ConfigurationHandler;
import net.epoxide.elysian.world.biome.WorldChunkManagerElysian;
import net.epoxide.elysian.world.chunk.ChunkProviderElysian;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderElysian extends WorldProvider {
    
    @Override
    public void registerWorldChunkManager () {
    
        this.worldChunkMgr = new WorldChunkManagerElysian(this.worldObj.getSeed(), this.terrainType);
        this.hasNoSky = true;
        this.dimensionId = ConfigurationHandler.dimensionID;
    }
    
    @Override
    public String getDimensionName () {
    
        return "Elysian";
    }
    
    @Override
    public IChunkProvider createChunkGenerator () {
    
        return new ChunkProviderElysian(this.worldObj, this.worldObj.getSeed());
    }
    
    @Override
    public boolean canRespawnHere () {
    
        return false;
    }
    
    @Override
    public boolean canCoordinateBeSpawn (int posX, int posY) {
    
        return false;
    }
    
    @Override
    protected void generateLightBrightnessTable () {
    
        float f = 0.1F;
        
        for (int i = 0; i <= 15; ++i) {
            
            float f1 = 1.0F - i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }
    
    @Override
    public boolean canDoLightning (Chunk chunk) {
    
        return false;
    }
    
    @Override
    public boolean canDoRainSnowIce (Chunk chunk) {
    
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight () {
    
        return 270.0F;
    }
}
