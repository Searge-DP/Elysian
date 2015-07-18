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

public class BlockElysianPortal extends Block {
    
    protected BlockElysianPortal() {
    
        super(Material.rock);
        this.setCreativeTab(Elysian.tabElysian);
        this.setBlockName("elysianPortal");
        this.setBlockTextureName("elysian:stone_portal");
    }
    
    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int meta, float offsetX, float offsetY, float offsetZ) {
    
        if (!world.isRemote) {
            
            int dimensionID = ConfigurationHandler.dimensionID;
            
            if (player.dimension != dimensionID)
                FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, dimensionID, new TeleporterElysian(MinecraftServer.getServer().worldServerForDimension(ConfigurationHandler.dimensionID)));
            
            else
                FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, 0, new TeleporterElysian(MinecraftServer.getServer().worldServerForDimension(0)));
        }
        
        return true;
    }
    
}
