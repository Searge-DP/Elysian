package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.init.Blocks;

public class BiomeTest2 extends BiomeGenElysian {
    
    public BiomeTest2() {
    
        super(ConfigurationHandler.biomeTestID2);
        
        this.setBiomeName("Elysian Plains 2");
        this.barrier = Blocks.clay;
        this.fillerBlock = Blocks.dirt;
        this.fluid = Blocks.water;
        this.topBlock = BlockHandler.grass;
        this.waterColorMultiplier = 0x66FFCC;
        this.setColor(0x66FFCC);
    }
}
