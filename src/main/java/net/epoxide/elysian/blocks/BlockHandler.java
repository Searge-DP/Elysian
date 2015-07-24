package net.epoxide.elysian.blocks;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler {
    
    public static Block transporter = new BlockElysianPortal();
    public static Block grass = new BlockElysianGrass();
    public static Block dirt = new BlockElysianDirt();
    public static Block breathableWater = new BlockBreathableWater();
    
    public BlockHandler() {
    
        GameRegistry.registerBlock(transporter, "portal");
        GameRegistry.registerBlock(grass, "grass");
        GameRegistry.registerBlock(dirt, "dirt");
        GameRegistry.registerBlock(breathableWater, "breathableWater");
    }
}