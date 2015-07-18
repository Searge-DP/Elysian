package net.epoxide.elysian.handler;

import java.util.HashMap;
import java.util.Map;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.items.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ElysianEventHandler {

	public static Map<Block, Item> buckets = new HashMap<Block, Item>();

	public ElysianEventHandler() {

		MinecraftForge.EVENT_BUS.register(this);
		buckets.put(BlockHandler.water, ItemHandler.bucket);
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent evt) {

		ItemStack result = fillCustomBucket(evt.world, evt.target);

		if (result == null)
			return;
		evt.result = result;
		evt.setResult(Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

		Item bucket = buckets.get(block);

		if (bucket != null
				&& world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return new ItemStack(bucket);
		} else
			return null;
	}
}
