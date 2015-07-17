package net.epoxide.elysian.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler {
    
    public static final Material fluid = new ElysianBlockMaterial();
    
    public static final Block transporter = new BlockElysianPortal();
    public static final Block grass = new BlockElysianGrass();
    public static final Block dirt = new BlockElysianDirt();
    
    public static Fluid water_fluid_stub = new Fluid("elysian_water").setViscosity(750).setLuminosity(15).setDensity(750).setTemperature(295);
    public static Block water;
    
    public BlockHandler() {
    
        GameRegistry.registerBlock(transporter, "portal");
        GameRegistry.registerBlock(grass, "grass");
        GameRegistry.registerBlock(dirt, "dirt");
        
        // water -start
        FluidRegistry.registerFluid(water_fluid_stub);
        water = new BlockElysianWater();
        water_fluid_stub.setUnlocalizedName(water.getUnlocalizedName());
        
        GameRegistry.registerBlock(water, "elysianFluid");
        // water -end
    }
}
