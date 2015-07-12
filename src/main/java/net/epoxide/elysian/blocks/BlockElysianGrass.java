package net.epoxide.elysian.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockElysianGrass extends Block {

	IIcon top;
	IIcon bottom;
	IIcon sides;
	
	protected BlockElysianGrass() {
		
		super(Material.grass);
		this.setCreativeTab(Elysian.tabElysian);
        this.setBlockName("elysianGrass");
        this.setBlockTextureName("elysian:elysian_grass_top");
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {

		return side == 1 ? top : side == 0 ? bottom : sides ;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = sides = reg.registerIcon(Constants.MOD_ID + ":elysian_grass_side");
		top = reg.registerIcon(Constants.MOD_ID + ":elysian_grass_top");
		bottom = reg.registerIcon(Constants.MOD_ID + ":elysian_grass_bottom");
	}

}
