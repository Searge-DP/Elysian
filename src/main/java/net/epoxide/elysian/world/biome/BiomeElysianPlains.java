package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.init.Blocks;

public class BiomeElysianPlains extends BiomeGenElysian {

	public BiomeElysianPlains() {

		super(ConfigurationHandler.biomeTestID);

		this.setBiomeName("Elysian Plains");
		this.barrier = Blocks.sandstone;
		this.fillerBlock = BlockHandler.dirt;
		this.fluid = Blocks.water;
		this.topBlock = Blocks.hay_block;
		this.waterColorMultiplier = 0xffffff;
		this.setColor(0xff0000);
	}
}
