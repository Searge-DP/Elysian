package net.epoxide.elysian;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.common.ProxyCommon;
import net.epoxide.elysian.common.packet.PacketHandler;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.epoxide.elysian.handler.ElysianEventHandler;
import net.epoxide.elysian.handler.GuiHandler;
import net.epoxide.elysian.items.ItemHandler;
import net.epoxide.elysian.lib.Constants;
import net.epoxide.elysian.world.WorldProviderElysian;
import net.epoxide.elysian.world.biome.BiomeHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, guiFactory = Constants.FACTORY)
public class Elysian {
    
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static ProxyCommon proxy;
    
    @Instance(Constants.MOD_ID)
    public static Elysian instance;
    
    public static CreativeTabs tabElysian = new CreativeTabElysian();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent pre) {
    
        proxy.preInit();
        new ConfigurationHandler(pre.getSuggestedConfigurationFile());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        new PacketHandler();
        new BlockHandler();
        new ItemHandler();
        new BiomeHandler();
        
        DimensionManager.registerProviderType(ConfigurationHandler.dimensionID, WorldProviderElysian.class, true);
        DimensionManager.registerDimension(ConfigurationHandler.dimensionID, ConfigurationHandler.dimensionID);
        
        new ElysianEventHandler();
        
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
    
        proxy.init();
    }
    
    public void postInit (FMLPostInitializationEvent event) {
    
        proxy.postInit();
    }
}