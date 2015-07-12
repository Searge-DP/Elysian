package net.epoxide.elysian.blocks;

import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.handler.ConfigurationHandler;
import net.epoxide.elysian.world.TeleporterElysian;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public class BlockElysianPort extends Block {
    
    protected BlockElysianPort() {
    
        super(Material.rock);
        this.setCreativeTab(Elysian.tabElysian);
        this.setBlockName("elysianPortal");
        this.setBlockTextureName("elysian:portal");
    }
    
    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int meta, float offsetX, float offsetY, float offsetZ) {
    
        if (!world.isRemote)
            FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, ConfigurationHandler.dimensionID, new TeleporterElysian(MinecraftServer.getServer().worldServerForDimension(ConfigurationHandler.dimensionID)));
        
        return true;
    }
    
}
