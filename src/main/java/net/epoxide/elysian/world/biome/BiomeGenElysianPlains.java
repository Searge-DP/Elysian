package net.epoxide.elysian.world.biome;

import java.util.List;

import net.epoxide.elysian.blocks.BlockHandler;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenElysianPlains extends BiomeGenBase {
    
    public BiomeGenElysianPlains(int biomeID) {
    
        super(biomeID);
        
        this.setBiomeName("Elysian Plains");
        
        this.topBlock = BlockHandler.grass;
        this.fillerBlock = Blocks.emerald_block;
        this.setDisableRain();
        this.waterColorMultiplier = 0xff0000;
        this.setColor(0xff0000);
        
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }
    
    @Override
    public List getSpawnableList (EnumCreatureType creatureType) {
    
        return creatureType == EnumCreatureType.monster ? this.spawnableMonsterList : (creatureType == EnumCreatureType.creature ? this.spawnableCreatureList : (creatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : (creatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : null)));
    }
}
