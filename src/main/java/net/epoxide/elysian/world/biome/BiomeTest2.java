package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.init.Blocks;

public class BiomeTest2 extends BiomeGenElysian {
    
    public BiomeTest2() {
    
        super(ConfigurationHandler.biomeTestID2);
        
        this.setBiomeName("Elysian Plains 2");
        this.barrier = Blocks.clay;
        this.fillerBlock = BlockHandler.dirt;
        this.fluid = BlockHandler.water;
        this.topBlock = BlockHandler.grass;
        this.waterColorMultiplier = 0xffffff;
        this.setColor(0x66FFCC);
    }
}
