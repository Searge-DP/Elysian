package net.epoxide.elysian.dimensionStuff;


import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenElysianPlains extends BiomeGenBase {

	public BiomeGenElysianPlains(int par1) {

		super(par1);

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();

		this.topBlock = Blocks.hay_block; //TODO custom
		this.fillerBlock = Blocks.emerald_block; //TODO custom
		this.setBiomeName("Elysian Plains");
		this.setDisableRain();
		this.waterColorMultiplier = 0xE42D17;
		setColor(0xE42D17);
		setEnableSnow();
		
		//TODO add spawnable, custom, creatures
		
//		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityGreenDragon.class, 1, 1, 1));
//		this.spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityElysianDragonfly.class, 1, 1, 1));
//		this.spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityEnvironementCreature.class, 5, 5, 10));
		
		//wanted to make this bunnies, but bunnies only in 1.8 :/
		this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 2, 5, 20));
		
		this.spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 2, 20, 50));

		
	}
	
	
	@Override
	public List getSpawnableList(EnumCreatureType par1EnumCreatureType)
    {
        return par1EnumCreatureType == EnumCreatureType.monster ? this.spawnableMonsterList : (par1EnumCreatureType == EnumCreatureType.creature ? this.spawnableCreatureList : (par1EnumCreatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : (par1EnumCreatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : null)));
    }
}
