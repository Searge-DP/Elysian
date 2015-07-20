package net.epoxide.elysian.blocks;

import net.epoxide.elysian.Elysian;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElysianWater extends BlockFluidClassic {
    
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
    
    public BlockElysianWater() {
    
        super(BlockHandler.water_fluid_stub, BlockHandler.fluid);
        setCreativeTab(Elysian.tabElysian);
        setBlockName("elysianWater");
        setLightLevel(1.0f);
        this.setDensity(1500);
    }
    
    @Override
    public IIcon getIcon (int side, int meta) {
    
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister register) {
    
        super.registerBlockIcons(register);
        stillIcon = register.registerIcon("elysian:elysian_water_still");
        flowingIcon = register.registerIcon("elysian:elysian_water_flow");
    }
    
    @Override
	public int colorMultiplier (IBlockAccess world, int x, int y, int z) {
    
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        
        for (int k1 = -1; k1 <= 1; ++k1) {
            for (int l1 = -1; l1 <= 1; ++l1) {
                int i2 = world.getBiomeGenForCoords(x + l1, z + k1).getWaterColorMultiplier();
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }
        // return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
        // return 0xE42D17;
        // return 0xff0320;
        return 0xff4065;
    }
    
    // @Override
    // public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
    // // if (world.getBlock(x, y, z).getMaterial().isLiquid())
    // // return false;
    // return super.canDisplace(world, x, y, z);
    // }
    //
    // @Override
    // public boolean displaceIfPossible(World world, int x, int y, int z) {
    // // if (world.getBlock(x, y, z).getMaterial().isLiquid())
    // // return false;
    //
    // return super.displaceIfPossible(world, x, y, z);
    // }
    
}
