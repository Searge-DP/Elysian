package net.epoxide.elysian.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemHandler {
    
    public static final Item bucket = new ItemElysianBucket();
    public static final Item turtleHelm = new ItemTurtleArmor(0);
    public static final Item turtleBody = new ItemTurtleArmor(1);
    public static final Item turtleLegs = new ItemTurtleArmor(2);
    public static final Item turtleBoots = new ItemTurtleArmor(3);
    
    
    public ItemHandler() {
    
        GameRegistry.registerItem(bucket, "bucket");
        GameRegistry.registerItem(turtleHelm, "turtleHelm");
        GameRegistry.registerItem(turtleBody, "turtleBody");
        GameRegistry.registerItem(turtleLegs, "turtleLegs");
        GameRegistry.registerItem(turtleBoots, "turtleBoots");
    }
}
