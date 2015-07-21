package net.epoxide.elysian.blocks;

import net.epoxide.elysian.Elysian;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockElysianDirt extends Block {
    
    protected BlockElysianDirt() {
    
        super(Material.grass);
        this.setCreativeTab(Elysian.tabElysian);
        this.setBlockName("elysianDirt");
        this.setBlockTextureName("elysian:elysian_dirt");
        this.setHardness(0.4f);
        
    }
}