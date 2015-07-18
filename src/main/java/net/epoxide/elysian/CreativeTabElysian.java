package net.epoxide.elysian;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabElysian extends CreativeTabs {

	public CreativeTabElysian() {

		super("elysian");
		this.setBackgroundImageName("elysian.png");
	}

	@Override
	public Item getTabIconItem() {

		return Items.rotten_flesh;
	}

	@Override
	public boolean hasSearchBar() {

		return true;
	}

	@Override
	public void displayAllReleventItems(List list) {

		super.displayAllReleventItems(list);
	}
}