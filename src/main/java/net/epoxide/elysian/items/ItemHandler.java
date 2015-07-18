package net.epoxide.elysian.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemHandler {

	public static final Item bucket = new ItemElysianBucket();

	public ItemHandler() {

		GameRegistry.registerItem(bucket, "bucket");

	}
}
