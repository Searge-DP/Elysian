package net.epoxide.elysian.world.gen.layer;

import net.epoxide.elysian.world.biome.ElysianBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomesElysian extends GenLayer {
    
    protected BiomeGenBase[] allowedBiomes = { ElysianBiomes.biomeElysian, BiomeGenBase.forest, BiomeGenBase.hell };
    
    public GenLayerBiomesElysian(long seed, GenLayer genlayer) {
    
        super(seed);
        this.parent = genlayer;
    }
    
    public GenLayerBiomesElysian(long seed) {
    
        super(seed);
    }
    
    @Override
    public int[] getInts (int posX, int posZ, int width, int depth) {
    
        int[] positions = IntCache.getIntCache(width * depth);
        
        for (int distantZ = 0; distantZ < depth; distantZ++) {
            
            for (int distantX = 0; distantX < width; distantX++) {
                
                this.initChunkSeed(distantX + posX, distantZ + posZ);
                positions[(distantX + distantZ * width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
            }
        }
        
        return positions;
    }
}