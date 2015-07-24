package net.epoxide.elysian.items;

import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.blocks.BlockBreathableWater;
import net.epoxide.elysian.blocks.BlockHandler;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemElysianBucket extends ItemBucket {
    
    public ItemElysianBucket() {
    
        super(BlockHandler.breathableWater);
        setCreativeTab(Elysian.tabElysian);
        setUnlocalizedName("elysian.waterBucket");
        setTextureName("elysian:bucket_water");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onBucketFill (FillBucketEvent event) {
    
        ItemStack result = fillCustomBucket(event.world, event.target);
        
        if (result == null)
            return;
        
        event.result = result;
        event.setResult(Result.ALLOW);
    }
    
    /**
     * Attempts to grab a bucket of this liquid from the provided position.
     * 
     * @param world: The world to grab the fluid from.
     * @param pos: The position to grab the fluid from.
     * @return ItemStack: If the block at the specified position is breathable water, a bucket
     *         of it will be returned. Otherwise null will be returned.
     */
    private ItemStack fillCustomBucket (World world, MovingObjectPosition pos) {
    
        if (world.getBlock(pos.blockX, pos.blockY, pos.blockZ) instanceof BlockBreathableWater && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
            
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(this);
        }
        
        else
            return null;
    }
}
