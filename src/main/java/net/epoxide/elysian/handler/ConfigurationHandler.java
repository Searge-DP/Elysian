package net.epoxide.elysian.handler;

import java.io.File;

import net.epoxide.elysian.lib.Constants;
import net.epoxide.elysian.lib.Utilities;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
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
    
        dimensionID = config.getInt("dimensionID", config.CATEGORY_GENERAL, DimensionManager.getNextFreeDimId(), -10000, 10000, "The dimension ID for the Elysian dimension. If you have succesfully launched a world with the Elysian mod once, do not change this!");
        biomeTestID = config.getInt("biomeTestID1", "Biomes", Utilities.getAvailableBiomeID(), 0, BiomeGenBase.getBiomeGenArray().length, "The biome ID for the test1 biome. If you have successfully launched a world with the Elysian mod once, do not change this!");
        biomeTestID2 = config.getInt("biomeTestID2", "Biomes", Utilities.getAvailableBiomeID(), 0, BiomeGenBase.getBiomeGenArray().length, "The biome ID for the test2 biome. If you have successfully launched a world with the Elysian mod once, do not change this!");
        biomeTestID3 = config.getInt("biomeTestID3", "Biomes", Utilities.getAvailableBiomeID(), 0, BiomeGenBase.getBiomeGenArray().length, "The biome ID for the test3 biome. If you have successfully launched a world with the Elysian mod once, do not change this!");
        biomeTestID4 = config.getInt("biomeTestID4", "Biomes", Utilities.getAvailableBiomeID(), 0, BiomeGenBase.getBiomeGenArray().length, "The biome ID for the test4 biome. If you have successfully launched a world with the Elysian mod once, do not change this!");

        if (config.hasChanged())
            config.save();
    }
    
    public static int dimensionID;
    
    public static int biomeTestID;
    public static int biomeTestID2;
    public static int biomeTestID3;
    public static int biomeTestID4;
}