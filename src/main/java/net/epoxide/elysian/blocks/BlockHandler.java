package net.epoxide.elysian.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockClay;
import net.minecraft.block.material.Material;

public class BlockHandler {
    
	public static final Block block = new BlockElysianPort(Material.ground).setBlockName("elyport").setBlockTextureName("minecraft:block_grass").setHardness(0.0f).setLightLevel(0.6f);
	
	public BlockHandler() {
		GameRegistry.registerBlock(block, "ELy Portal Block");
	}
}
