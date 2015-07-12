package net.epoxide.elysian;

import java.util.List;

import net.epoxide.elysian.blocks.BlockHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabElysian extends CreativeTabs {
    
    public CreativeTabElysian() {
    
        super("elysian");
        this.setBackgroundImageName("elysian.png");
    }
    
    @Override
    public Item getTabIconItem () {
    
        return Items.rotten_flesh;
    }
    
    @Override
    public boolean hasSearchBar () {
    
        return true;
    }
    
    @Override
    public void displayAllReleventItems (List list) {
    
        list.add(new ItemStack(Items.nether_star));
        list.add(new ItemStack(Items.nether_wart));
        list.add(new ItemStack(Items.gold_nugget));
        list.add(new ItemStack(Items.golden_helmet));
        list.add(new ItemStack(Items.golden_apple));
        list.add(new ItemStack(Items.mushroom_stew));
        list.add(new ItemStack(BlockHandler.transporter));
        
    }
}