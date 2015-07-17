package net.epoxide.elysian.items;

import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.blocks.BlockHandler;
import net.minecraft.item.ItemBucket;

public class ItemElysianBucket extends ItemBucket {
    
    public ItemElysianBucket() {
    
        super(BlockHandler.water);
        setCreativeTab(Elysian.tabElysian);
        setUnlocalizedName("elysian.waterBucket");
        setTextureName("elysian:bucket_water");
    }
    
}
