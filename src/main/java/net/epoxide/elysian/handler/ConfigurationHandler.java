package net.epoxide.elysian.handler;

import java.io.File;

import net.epoxide.elysian.lib.Constants;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    public static Configuration config;
    
    public ConfigurationHandler(File file) {
    
        config = new Configuration(file);
        
        FMLCommonHandler.instance().bus().register(this);
        syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (ConfigChangedEvent.OnConfigChangedEvent event) {
    
        if (event.modID.equals(Constants.MOD_ID))
            syncConfigData();
    }
    
    /**
     * Allows for the configuration data to be re-synced between the file on disk, and the
     * values in memory. For internal use only!
     */
    private void syncConfigData () {
    
        dimensionID = config.getInt("dimensionID", config.CATEGORY_GENERAL, dimensionID, 0, 10000, "The dimension ID for the Elysian dimension. If you have succesfully launched a world with the Elysian mod once, do not change this!");
        
        if (config.hasChanged())
            config.save();
    }
    
    public static int dimensionID = 8632;
}