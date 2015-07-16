package net.epoxide.elysian.world.biome;

import net.epoxide.elysian.handler.ConfigurationHandler;
import net.minecraft.init.Blocks;

public class BiomeElysianPlains extends BiomeGenElysian {
    
    public BiomeElysianPlains() {
    
        super(ConfigurationHandler.biomeTestID);
        
        this.setBiomeName("Elysian Plains");
        this.barrier = Blocks.sandstone;
        this.fillerBlock = Blocks.emerald_block;
        this.fluid = Blocks.water;
        this.topBlock = Blocks.gravel;
        this.waterColorMultiplier = 0xff0000;
        this.setColor(0xff0000);
    }
}
