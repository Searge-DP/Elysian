package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;

public class BiomeTest4 extends BiomeGenElysian {

	public BiomeTest4() {
		super(ConfigurationHandler.biomeTestID4);

		this.setBiomeName("Elysian test 4 Biome");
		this.barrier = Blocks.glass;
		this.fillerBlock = BlockHandler.dirt;
		this.fluid = BlockHandler.water;
		this.topBlock = Blocks.packed_ice;
		this.waterColorMultiplier = 0xffffff;
		this.setColor(0x66FFCC);
	}

}
