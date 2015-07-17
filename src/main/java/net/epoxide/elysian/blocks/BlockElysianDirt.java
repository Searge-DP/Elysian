package net.epoxide.elysian.blocks;

import net.epoxide.elysian.Elysian;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElysianDirt extends Block {
    
    protected BlockElysianDirt() {
    
        super(Material.grass);
        this.setCreativeTab(Elysian.tabElysian);
        this.setBlockName("elysianDirt");
        this.setBlockTextureName("elysian:elysian_dirt");
        
    }
}