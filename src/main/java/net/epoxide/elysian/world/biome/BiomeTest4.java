package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.init.Blocks;

public class BiomeTest4 extends BiomeGenElysian {
    
    public BiomeTest4() {
    
        super(ConfigurationHandler.biomeTestID4);
        
        this.setBiomeName("Test4");
        this.barrier = Blocks.glass;
        this.fillerBlock = BlockHandler.dirt;
        this.fluid = BlockHandler.breathableWater;
        this.topBlock = Blocks.packed_ice;
        this.waterColorMultiplier = 0x305d4f;
        this.setColor(0x305d4f);
    }
    
}
