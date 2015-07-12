package net.epoxide.elysian.world.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class ElysianBiomes {
    
    public static BiomeGenBase biomeElysian;
    
    public ElysianBiomes() {
    
        addBiomes();
    }
    
    private void addBiomes () {
    
        biomeElysian = new BiomeGenElysianPlains(41);
    }
}
