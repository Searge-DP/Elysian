package net.epoxide.elysian.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemHandler {
    
    public static final Item bucket = new ItemElysianBucket();
    public static final Item runicDivingHelm = new ItemRunicDivingSuit(0);
    public static final Item runicDivingSuit = new ItemRunicDivingSuit(1);
    public static final Item runicDivingFloaters = new ItemRunicDivingSuit(2);
    public static final Item runicDivingBoots = new ItemRunicDivingSuit(3);
    
    public ItemHandler() {
    
        GameRegistry.registerItem(bucket, "bucket");
        GameRegistry.registerItem(runicDivingHelm, "runic_diving_helm");
        GameRegistry.registerItem(runicDivingSuit, "runic_diving_suit");
        GameRegistry.registerItem(runicDivingFloaters, "runic_diving_floaters");
        GameRegistry.registerItem(runicDivingBoots, "runic_diving_boots");
    }
}
