package net.epoxide.elysian.items;

import java.util.List;

import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.client.model.ModelHandler;
import net.epoxide.elysian.lib.ColorObject;
import net.epoxide.elysian.lib.ColorObject.VanillaColor;
import net.epoxide.elysian.lib.Utilities;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRunicDivingSuit extends ItemArmor {
    
    public ItemRunicDivingSuit(int armorType) {
    
        super(ArmorMaterial.IRON, 0, armorType);
        this.setCreativeTab(Elysian.tabElysian);
        this.setUnlocalizedName("elysian.runic_diving_" + Utilities.armorTypes[armorType]);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    /**
     * Sets the amount of breathable air within an ItemStack.
     * 
     * @param stack: The ItemStack which is going to hold this breathable air.
     * @param amount: The amount of air for this ItemStack to hold.
     * @return ItemStack: The same ItemStack that was passed to this method.
     */
    public static ItemStack setTankAmount (ItemStack stack, int amount) {
    
        Utilities.prepareStackTag(stack);
        stack.getTagCompound().setInteger("ContainedAmount", amount);
        return stack;
    }
    
    /**
     * Retrieves the amount of breathable air within an ItemStack.
     * 
     * @param stack: The ItemStack which is holding the breathable air.
     * @return int: The amount of breathable air contained within the stack.
     */
    public static int getTankAmount (ItemStack stack) {
    
        Utilities.prepareStackTag(stack);
        return stack.getTagCompound().getInteger("ContainedAmount");
    }
    
    /**
     * Sets the maximum amount of breathable air that can be held by an ItemStack.
     * 
     * @param stack: The ItemStack which is going to be holding air.
     * @param amount: The maximum amount of air that this ItemStack should be able to hold.
     * @return ItemStack: The same ItemStack that was passed to this method.
     */
    public static ItemStack setTankCapacity (ItemStack stack, int amount) {
    
        Utilities.prepareStackTag(stack);
        stack.getTagCompound().setInteger("AirCapacity", amount);
        return stack;
    }
    
    /**
     * Retrieves the maximum amount of breathable air that can be held by an ItemStack.
     * 
     * @param stack: The ItemStack which is holding the breathable air.
     * @return int: The maximum amount of air which can by this ItemStack.
     */
    public static int getTankCapacity (ItemStack stack) {
    
        Utilities.prepareStackTag(stack);
        return stack.getTagCompound().getInteger("AirCapacity");
    }
    
    /**
     * Sets the color of the visor for the Runic Diving Helmet. This changes the texture on the
     * item, and the fog and water(fog) color.
     * 
     * @param stack: The ItemStack which is going to have a color written to it.
     * @param color: The color to write to this ItemStack.
     * @return ItemStack: The same ItemStack that was passed to this method.
     */
    public static ItemStack setVisorColor (ItemStack stack, ColorObject color) {
    
        Utilities.prepareStackTag(stack);
        color.writeToTag(stack.getTagCompound());
        return stack;
    }
    
    /**
     * Retrieves the ColorObject used for the color of visor on the diving helmet.
     * 
     * @param stack: An ItemStack which represents a diving helmet.
     * @return ColorObject: A ColorObject which represents the color of the visor. If no color
     *         is available, it will be black.
     */
    public static ColorObject getVisorColor (ItemStack stack) {
    
        Utilities.prepareStackTag(stack);
        return new ColorObject(stack.getTagCompound());
    }
    
    @Override
    public void onArmorTick (World world, EntityPlayer player, ItemStack itemStack) {
    
        if (player.isInsideOfMaterial(Material.water) && itemStack.getItem() instanceof ItemRunicDivingSuit) {
            
            ItemRunicDivingSuit armor = (ItemRunicDivingSuit) itemStack.getItem();
            
            if (armor.armorType == 3 && !player.onGround)
                player.motionY += player.motionY * 0.15;
            
            if (armor.armorType == 1 && getTankAmount(itemStack) > 0) {
                
                setTankAmount(itemStack, getTankAmount(itemStack) - 1);
                player.setAir(300);
            }
            
            if (armor.armorType == 2) {
                if (player.motionX < 0.25f && player.motionZ < 0.25f) {
                    
                    player.motionX *= 1.05;
                    player.motionZ *= 1.05;
                }
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List tooltip, boolean isDebug) {
    
        if (armorType == 0)
            tooltip.add(StatCollector.translateToLocal("tooltip.elysian.divingtip0"));
        
        else if (armorType == 1) {
            
            tooltip.add(StatCollector.translateToLocal("tooltip.elysian.airAmount") + ": " + getTankAmount(stack) + " / " + getTankCapacity(stack));
            tooltip.add(StatCollector.translateToLocal("tooltip.elysian.divingtip1"));
        }
        
        else if (armorType == 2)
            tooltip.add(StatCollector.translateToLocal("tooltip.elysian.divingtip2"));
        
        else if (armorType == 3)
            tooltip.add(StatCollector.translateToLocal("tooltip.elysian.divingtip3"));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture (ItemStack stack, Entity entity, int slot, String type) {
    
        return (slot == 2) ? "elysian:textures/items/armor/armor_1.png" : "elysian:textures/items/armor/armor_0.png";
    }
    
    @SideOnly(Side.CLIENT)
    public static IIcon visorIcon = Items.apple.getIconFromDamage(0);
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister) {
    
        this.itemIcon = iconRegister.registerIcon("elysian:runic_diving_" + Utilities.armorTypes[armorType]);
        
        if (armorType == 0)
            this.visorIcon = iconRegister.registerIcon("elysian:runic_diving_visor");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack (ItemStack stack, int pass) {
    
        if (armorType == 0 && pass == 0 && stack.hasTagCompound())
            return getVisorColor(stack).getDecimalFromColor();
        
        return VanillaColor.WHITE.colorObj.getDecimalFromColor();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses () {
    
        return armorType == 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass (int damage, int pass) {
    
        return pass > 0 ? this.itemIcon : this.visorIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel (EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
    
        ModelBiped armorModel = null;
        
        if (itemStack != null && itemStack.getItem() instanceof ItemRunicDivingSuit) {
            
            armorModel = (armorSlot == 1 || armorSlot == 3) ? ModelHandler.runicScubaTop : ModelHandler.runicScubaBottom;
            ModelHandler.updateModelForArmor(armorModel, armorSlot);
            ModelHandler.updateModelToMatchEntity(armorModel, entityLiving);
        }
        
        return armorModel;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List itemList) {
    
        if (item instanceof ItemRunicDivingSuit) {
            
            if (armorType == 0)
                for (VanillaColor color : VanillaColor.values())
                    itemList.add(setVisorColor(new ItemStack(item), color.colorObj));
            
            else if (armorType == 1) {
                
                ItemStack stack = new ItemStack(item);
                setTankCapacity(stack, 10000);
                itemList.add(stack);
                itemList.add(setTankAmount(stack.copy(), 10000));
            }
            
            else
                super.getSubItems(item, tab, itemList);
        }
    }
    
    @SubscribeEvent
    public void onFogColor (FogColors event) {
    
        if (event.block.getMaterial() == Material.water && event.entity instanceof EntityPlayer) {
            
            EntityPlayer player = (EntityPlayer) event.entity;
            ItemStack helmet = player.inventory.armorItemInSlot(3);
            
            if (helmet != null && helmet.getItem() instanceof ItemRunicDivingSuit) {
                
                ColorObject color = getVisorColor(helmet);
                event.red = color.red;
                event.green = color.green;
                event.blue = color.blue;
            }
        }
    }
    
    @SubscribeEvent
    public void onFogDensity (FogDensity event) {
    
        if (event.block != null && event.block.getMaterial() == Material.water && event.entity instanceof EntityPlayer) {
            
            EntityPlayer player = (EntityPlayer) event.entity;
            ItemStack helmet = player.inventory.armorItemInSlot(3);
            
            if (helmet != null && helmet.getItem() instanceof ItemRunicDivingSuit) {
                
                event.setCanceled(true);
                event.density = 0.002f;
                GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                GL11.glFogf(GL11.GL_FOG_DENSITY, 1.0f + (0.1F - (float) EnchantmentHelper.getRespiration(player) * 0.03F));
            }
        }
    }
}
