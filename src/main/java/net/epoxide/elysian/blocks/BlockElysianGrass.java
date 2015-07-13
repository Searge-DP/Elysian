package net.epoxide.elysian.blocks;

import net.epoxide.elysian.Elysian;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElysianGrass extends Block {
    
    IIcon top;
    IIcon bottom;
    IIcon sides;
    
    protected BlockElysianGrass() {
    
        super(Material.grass);
        this.setCreativeTab(Elysian.tabElysian);
        this.setBlockName("elysianGrass");
        this.setBlockTextureName("elysian:grass_top");
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta) {
    
        return side == 1 ? top : side == 0 ? bottom : sides;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister reg) {
    
        this.blockIcon = sides = reg.registerIcon("elysian:grass_side");
        top = reg.registerIcon("elysian:grass_top");
        bottom = reg.registerIcon("elysian:grass_bottom");
    }
}