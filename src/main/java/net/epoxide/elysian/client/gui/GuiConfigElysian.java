package net.epoxide.elysian.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.epoxide.elysian.handler.ConfigurationHandler;
import net.epoxide.elysian.lib.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class GuiConfigElysian extends GuiConfig {
    
    static Configuration cfg = ConfigurationHandler.config;
    static ConfigurationHandler cfgh;
    
    public GuiConfigElysian(GuiScreen parent) {
    
        super(parent, generateConfigList(), Constants.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }
    
    /**
     * Generates a list of configuration options to be displayed in forge's configuration GUI.
     * 
     * @return List<IConfigElement>: A list of IConfigElement which are used to populate
     *         forge's configuration GUI.
     */
    public static List<IConfigElement> generateConfigList () {
    
        ArrayList<IConfigElement> elements = new ArrayList<IConfigElement>();
        
        for (String name : cfg.getCategoryNames())
            elements.add(new ConfigElement(cfg.getCategory(name)));
        
        return elements;
    }
}
