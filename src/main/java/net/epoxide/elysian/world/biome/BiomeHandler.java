package net.epoxide.elysian.world.biome;

import java.util.ArrayList;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeHandler {
    
    public static ArrayList<BiomeGenBase> elysianBiomes = new ArrayList<BiomeGenBase>();
    
    public static BiomeGenBase elysianTest1 = new BiomeElysianPlains();
    public static BiomeGenBase elysianTest2 = new BiomeTest2();
    
    public BiomeHandler() {
    
        registerBiome(elysianTest1, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.DEAD);
        registerBiome(elysianTest2, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.MAGICAL);
        BiomeDictionary.registerAllBiomes();
    }
    
    public void registerBiome (BiomeGenBase biome, Type... types) {
    
        elysianBiomes.add(biome);
        BiomeDictionary.registerBiomeType(biome, types);
       
    }
}
