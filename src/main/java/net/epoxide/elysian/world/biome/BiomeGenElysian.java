package net.epoxide.elysian.world.biome;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenElysian extends BiomeGenBase {
    
    public Block fluid = null;
    public Block barrier = null;
    
    public BiomeGenElysian(int biomeID) {
    
        super(biomeID);
        
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.setDisableRain();
    }
    
    @Override
    public List getSpawnableList (EnumCreatureType creatureType) {
    
        return creatureType == EnumCreatureType.monster ? this.spawnableMonsterList : (creatureType == EnumCreatureType.creature ? this.spawnableCreatureList : (creatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : (creatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : null)));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor (int p_150558_1_, int p_150558_2_, int p_150558_3_) {
    
        return this.color;
    }
}
