package net.epoxide.elysian.world.biome;

import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenElysianPlains extends BiomeGenBase {
    
    public BiomeGenElysianPlains(int biomeID) {
    
        super(biomeID);
        
        this.setBiomeName("Elysian Plains");
        
        // TODO Top and Fillers don't actually work
        this.topBlock = Blocks.hay_block;
        this.fillerBlock = Blocks.emerald_block;
        this.setDisableRain();
        this.waterColorMultiplier = 0xE42D17;
        this.setColor(0xE42D17);
        
        // TODO replace with our own lists. This is not too hard actually.
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }
    
    // TODO switch to work off of custom spawning lists.
    @Override
    public List getSpawnableList (EnumCreatureType creatureType) {
    
        return creatureType == EnumCreatureType.monster ? this.spawnableMonsterList : (creatureType == EnumCreatureType.creature ? this.spawnableCreatureList : (creatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : (creatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : null)));
    }
}
