package net.epoxide.elysian.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidHandler {
    
    public static Fluid fluidBreathable = new Fluid("breathableWater").setUnlocalizedName("breathableWater").setViscosity(750).setLuminosity(15).setDensity(750).setTemperature(295);
    
    public FluidHandler() {
    
        FluidRegistry.registerFluid(fluidBreathable);
    }
}
