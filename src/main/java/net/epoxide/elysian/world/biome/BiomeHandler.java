package net.epoxide.elysian.world.biome;

import java.util.ArrayList;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeHandler {
    
    public static ArrayList<BiomeGenBase> elysianBiomes = new ArrayList<BiomeGenBase>();
    
    public static BiomeGenBase elysianTest1 = new BiomeTest1();
    public static BiomeGenBase elysianTest2 = new BiomeTest2();
    public static BiomeGenBase elysianTest3 = new BiomeTest3();
    public static BiomeGenBase elysianTest4 = new BiomeTest4();
    
    public BiomeHandler() {
    
        registerBiome(elysianTest1, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.DEAD);
        registerBiome(elysianTest2, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.MAGICAL);
        registerBiome(elysianTest3, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.DRY);
        registerBiome(elysianTest4, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WET);
        
    }
    
    public void registerBiome (BiomeGenBase biome, Type... types) {
    
        elysianBiomes.add(biome);
        BiomeDictionary.registerBiomeType(biome, types);
    }
}