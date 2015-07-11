package net.epoxide.elysian.blocks;

import net.epoxide.elysian.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockElysianPort extends Block {

	protected BlockElysianPort(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer player,
			int meta, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {

		player.travelToDimension(Constants.DIM_ID);

		return true;
	}

}
