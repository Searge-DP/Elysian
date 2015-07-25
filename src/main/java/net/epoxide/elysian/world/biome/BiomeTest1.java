package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.init.Blocks;

public class BiomeTest1 extends BiomeGenElysian {
    
    public BiomeTest1() {
    
        super(ConfigurationHandler.biomeTestID);
        
        this.setBiomeName("Test1");
        this.barrier = Blocks.sandstone;
        this.fillerBlock = BlockHandler.dirt;
        this.fluid = Blocks.water;
        this.topBlock = Blocks.hay_block;
        this.waterColorMultiplier = 0x02f87a;
        this.setColor(0x02f87a);
    }
}
