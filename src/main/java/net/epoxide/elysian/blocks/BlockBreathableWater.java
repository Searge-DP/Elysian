package net.epoxide.elysian.blocks;

import net.epoxide.elysian.fluids.FluidHandler;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBreathableWater extends BlockFluidClassic {
    
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
    
    public BlockBreathableWater() {
    
        super(FluidHandler.fluidBreathable, Material.water);
        setBlockName("breathableWater");
        setLightLevel(1.0f);
        this.setDensity(1500);
    }
    
    @Override
    public int colorMultiplier (IBlockAccess world, int x, int y, int z) {
    
        return 0xff4065;
    }
    
    @Override
    public boolean canDisplace (IBlockAccess world, int x, int y, int z) {
    
        if (world.getBlock(x, y, z).getMaterial().isLiquid())
            return false;
        
        return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible (World world, int x, int y, int z) {
    
        if (world.getBlock(x, y, z).getMaterial().isLiquid())
            return false;
        
        return super.displaceIfPossible(world, x, y, z);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
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
}
