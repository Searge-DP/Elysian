package net.epoxide.elysian.blocks;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler {
    
    public static final Block transporter = new BlockElysianPortal();
    
    public BlockHandler() {
    
        GameRegistry.registerBlock(transporter, "portal");
    }
}
