package net.epoxide.elysian.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler {
    
    public static final Block transporter = new BlockElysianPortal();
    public static final Block grass = new BlockElysianGrass();
    public static final Block dirt = new BlockElysianDirt();
    
    public BlockHandler() {
    
        GameRegistry.registerBlock(transporter, "portal");
        GameRegistry.registerBlock(grass, "grass");
        GameRegistry.registerBlock(dirt, "dirt");

    }
}
