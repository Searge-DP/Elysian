package net.epoxide.elysian.blocks;

import net.epoxide.elysian.dimensionStuff.TeleporterElysian;
import net.epoxide.elysian.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public class BlockElysianPort extends Block {

	protected BlockElysianPort(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer player,
			int meta, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {

		if(!world.isRemote)
			FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().
			transferPlayerToDimension(
					(EntityPlayerMP)player, 
					Constants.DIM_ID, 
					new TeleporterElysian(MinecraftServer.getServer().worldServerForDimension(Constants.DIM_ID)));

		return true;
	}

}
