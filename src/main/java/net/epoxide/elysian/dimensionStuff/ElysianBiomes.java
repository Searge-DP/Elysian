package net.epoxide.elysian.dimensionStuff;

import net.minecraft.world.biome.BiomeGenBase;

//TODO make more biomes
public class ElysianBiomes {

  public static BiomeGenBase biomeElysian;

  public ElysianBiomes() {

      addBiomes();
  }

  private void addBiomes() {

      biomeElysian = new BiomeGenElysianPlains(41);
  }
}
