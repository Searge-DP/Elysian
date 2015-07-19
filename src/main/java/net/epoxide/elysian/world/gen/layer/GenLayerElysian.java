package net.epoxide.elysian.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerElysian extends GenLayer {
    
    public GenLayerElysian(long seed) {
    
        super(seed);
    }
    
    /**
     * Create a biome map for an Elysian world.
     * 
     * @param seed: The seed which is used for the world.
     * @return GenLayer[]: A GenLayer[] array for the new Elysian world.
     */
    public static GenLayer[] makeTheWorld (long seed) {
    
        GenLayer biomes = new GenLayerBiomesElysian(1L);
        biomes = new GenLayerZoom(1000L, biomes);
        biomes = new GenLayerZoom(1001L, biomes);
        biomes = new GenLayerZoom(1002L, biomes);
        biomes = new GenLayerZoom(1003L, biomes);
        biomes = new GenLayerZoom(1004L, biomes);
        biomes = new GenLayerZoom(1005L, biomes);
        GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);
        biomes.initWorldGenSeed(seed);
        genlayervoronoizoom.initWorldGenSeed(seed);
        return new GenLayer[] { biomes, genlayervoronoizoom };
    }
    
    @Override
    public int[] getInts (int posX, int posZ, int width, int depth) {
    
        return null;
    }
}