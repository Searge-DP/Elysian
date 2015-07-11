package net.epoxide.elysian.dimensionStuff;

import net.epoxide.elysian.lib.Constants;
import net.minecraftforge.common.DimensionManager;

public class Dimension {
    
    /**
     * Register dimensions.
     * 
     * @param register
     */
    public Dimension registerDimensions () {
    
        DimensionManager.registerDimension(Constants.DIM_ID, Constants.DIM_ID);
        return this;
    }
    
    /**
     * Regster dimension world providers with the dimension manager.
     */
    public Dimension registerWorldProvider () {
    
        DimensionManager.registerProviderType(Constants.DIM_ID, WorldProviderElysian.class, true);
        return this;
    }
    
}
