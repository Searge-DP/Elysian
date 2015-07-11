package net.epoxide.elysian;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.common.ProxyCommon;
import net.epoxide.elysian.common.packet.PacketHandler;
import net.epoxide.elysian.dimensionStuff.Dimension;
import net.epoxide.elysian.dimensionStuff.WorldTypeElysian;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.epoxide.elysian.handler.GuiHandler;
import net.epoxide.elysian.items.ItemHandler;
import net.epoxide.elysian.lib.Constants;
import net.minecraft.creativetab.CreativeTabs;
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
        new ConfigurationHandler(pre.getModConfigurationDirectory());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        new PacketHandler();
        new BlockHandler();
        new ItemHandler();
        
        WorldTypeElysian.addCustomWorldTypes();
        new Dimension().registerWorldProvider().registerDimensions();
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
    
        proxy.init();
    }
    
    public void postInit (FMLPostInitializationEvent event) {
    
        proxy.postInit();
    }
}