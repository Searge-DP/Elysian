package net.epoxide.elysian.handler;

import java.io.File;
import java.util.List;

import net.epoxide.elysian.lib.Constants;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    public static Configuration config;
    
    public static boolean allowConsoleOutput;
    public static boolean allowAutoTune;
    public static boolean allowPackLoading;
    public static boolean allowAdvancedNoteblock;
    public static boolean allowVillager;
    public static boolean allowRecordSounds;
    public static boolean allowCraftingAdvancedNoteblock;
    
    public static int villagerID;
    public static int autoTuneID;
    public static int noteBlockDistance;
    
    public static double autoTuneDropRate;
    
    public static List<String> bannedSounds;
    
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
    
        if (config.hasChanged())
            config.save();
    }
}