package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.init.Blocks;

public class BiomeTest3 extends BiomeGenElysian {
    
    public BiomeTest3() {
    
        super(ConfigurationHandler.biomeTestID3);
        
        this.setBiomeName("Elysian test 3 Biome");
        this.barrier = Blocks.planks;
        this.fillerBlock = BlockHandler.dirt;
        this.fluid = BlockHandler.breathableWater;
        this.topBlock = Blocks.gravel;
        this.waterColorMultiplier = 0x64441c;
        this.setColor(0x64441c);
    }
}