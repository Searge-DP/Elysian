package net.epoxide.elysian.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemHandler {
    
	public static final Item bucket = new ItemElysianBucket();
	
	public ItemHandler() {

		GameRegistry.registerItem(bucket, "bucket");
		
	}
}
