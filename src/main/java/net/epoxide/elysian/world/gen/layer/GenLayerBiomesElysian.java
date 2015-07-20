package net.epoxide.elysian.world.gen.layer;

import net.epoxide.elysian.world.biome.BiomeHandler;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomesElysian extends GenLayer {
    
    /**
     * An array of all possible biomes which should be allowed to spawn within the elysian
     * dimension.
     */
    protected Object[] allowedBiomes = BiomeHandler.elysianBiomes.toArray();
    
    public GenLayerBiomesElysian(long seed) {
    
        super(seed);
    }
    
    @Override
    public int[] getInts (int posX, int posZ, int width, int depth) {
    
        int[] positions = IntCache.getIntCache(width * depth);
        
        for (int distantZ = 0; distantZ < depth; distantZ++) {
            
            for (int distantX = 0; distantX < width; distantX++) {
                
                this.initChunkSeed(distantX + posX, distantZ + posZ);
                BiomeGenBase biome = (BiomeGenBase) BiomeHandler.elysianBiomes.toArray()[nextInt(this.allowedBiomes.length)];
                positions[(distantX + distantZ * width)] = biome.biomeID;
            }
        }
        
        return positions;
    }
}