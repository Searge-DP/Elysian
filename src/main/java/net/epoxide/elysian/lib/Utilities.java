package net.epoxide.elysian.lib;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;

public class Utilities {
    
    /**
     * Prepares an ItemStack with a NBTTagCompound if it does not already have one.
     * 
     * @param stack: The ItemStack to prepare for NBT work.
     * @return ItemStack: The exact same ItemStack which was passed to this method.
     */
    public static ItemStack prepareStackTag (ItemStack stack) {
    
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        
        return stack;
    }
    
    /**
     * A list of all the different armor types. Useful for generating translation keys and
     * texture names.
     */
    public static String[] armorTypes = new String[] { "helmet", "chestplate", "leggings", "boots" };
    
    /**
     * Checks to see if a player is wearing a full set of a given armor type.
     * 
     * @param player: The player to check the armor of.
     * @param armorItem: The class of the armor type you are searching for.
     * @return boolean: True if all items share the same class, false if they do not.
     */
    public static boolean isWearingFullSet (EntityPlayer player, Class armorItem) {
    
        for (int armorSlot = 0; armorSlot < 4; armorSlot++) {
            
            ItemStack armor = player.inventory.armorItemInSlot(armorSlot);
            
            if (armor == null || !armor.getItem().getClass().equals(armorItem))
                return false;
        }
        
        return true;
    }
    
    /**
     * A list of all biome ids which have been found by the getAvailableBiomeID method. This
     * list is used to help keep track of biome IDs which have already been found, and prevents
     * duplicate IDs from being presented.
     */
    private static ArrayList<Integer> foundBiomes = new ArrayList();
    
    /**
     * Attempts to find a vacant biome ID. Does not guarantee that other mods won't use this
     * same ID afterwards.
     * 
     * @return int: A biome id which is currently available.
     */
    public static int getAvailableBiomeID () {
    
        for (int possibleID = 0; possibleID < BiomeGenBase.getBiomeGenArray().length; possibleID++)
            
            if (BiomeGenBase.getBiome(possibleID) == null && !foundBiomes.contains(possibleID)) {
                
                foundBiomes.add(possibleID);
                return possibleID;
            }
        
        throw new OutOfBiomesException();
    }
    
    public static class OutOfBiomesException extends RuntimeException {
        
        /**
         * A new exceptions which is thrown when all possible ids within the biomeList are
         * occupied.
         */
        public OutOfBiomesException() {
        
            super("An attempt to find an available biome ID was made, however no IDs are available.");
        }
    }
}
