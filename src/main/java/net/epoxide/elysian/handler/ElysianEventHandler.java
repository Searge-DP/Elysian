package net.epoxide.elysian.handler;

import java.util.HashMap;
import java.util.Map;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.items.ItemHandler;
import net.epoxide.elysian.items.ItemTurtleArmor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ElysianEventHandler {
    
    public static Map<Block, Item> buckets = new HashMap<Block, Item>();
    
    public ElysianEventHandler() {
    
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        buckets.put(BlockHandler.water, ItemHandler.bucket);
    }
    
    @SubscribeEvent
    public void onBucketFill (FillBucketEvent evt) {
    
        ItemStack result = fillCustomBucket(evt.world, evt.target);
        
        if (result == null)
            return;
        evt.result = result;
        evt.setResult(Result.ALLOW);
    }
    
    private ItemStack fillCustomBucket (World world, MovingObjectPosition pos) {
    
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        
        Item bucket = buckets.get(block);
        
        if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(bucket);
        }
        else
            return null;
    }
    
    @SubscribeEvent
    public void stuff(PlayerTickEvent evt){
    	EntityPlayer player = evt.player;
    	
    	if(isWearingFullTurtleSuite(player)){
    		if(player.isInsideOfMaterial(Material.water)){
    			//TODO add iff check to check if tank is full
    			player.setAir(300);
    			//TODO if tank is full, decrease tank air time
    		}
    	}
    }
    
    private boolean isWearingFullTurtleSuite(EntityPlayer player){
    	
    	boolean set[] = new boolean[4];
    	
    	for(int index = 0; index <4; index++){
    		if(player.inventory.armorItemInSlot(index) != null){
    			Item item = player.inventory.armorItemInSlot(index).getItem();
    			if(item != null && item instanceof ItemTurtleArmor)
    				set[index] = true;
    		}
    	}
    	
    	return set[0] && set[1] && set[2] && set[3] ? true : false;
    }
}
