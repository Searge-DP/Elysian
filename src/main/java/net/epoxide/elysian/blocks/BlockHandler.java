package net.epoxide.elysian.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler {
    
    public static final Block block = new BlockElysianPort(Material.ground).setBlockName("elyport").setBlockTextureName("minecraft:block_grass").setHardness(0.0f).setLightLevel(0.6f);
    
    public BlockHandler() {
    
        GameRegistry.registerBlock(block, "ELy Portal Block");
    }
}
