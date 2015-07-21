package net.epoxide.elysian.items;

import java.util.List;

import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.client.model.ModelHandler;
import net.epoxide.elysian.client.model.armor.ModelTurtleSuit;
import net.epoxide.elysian.lib.Utilities;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRunicDivingSuit extends ItemArmor {
    
    ResourceLocation visier_Overlay = new ResourceLocation("elysian:textures/items/armor/overlay_visier.png");
    ResourceLocation armor_Overlay = new ResourceLocation("elysian:textures/items/armor/armor_0_overlay.png");
    
    public ItemRunicDivingSuit(int armorType) {
    
        super(ArmorMaterial.IRON, 0, armorType);
        this.setCreativeTab(Elysian.tabElysian);
        this.setTextureName("elysian:runic_diving_" + Utilities.armorTypes[armorType]);
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
                
                player.motionX *= 1.10000000000000002D;
                player.motionZ *= 1.10000000000000002D;
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List tooltip, boolean isDebug) {
    
        if (stack != null && stack.getItem() instanceof ItemRunicDivingSuit && ((ItemRunicDivingSuit) stack.getItem()).armorType == 1)
            tooltip.add(StatCollector.translateToLocal("tooltip.elysian.airAmount") + ": " + getTankAmount(stack) + " / " + getTankCapacity(stack));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture (ItemStack stack, Entity entity, int slot, String type) {
    
        return (slot == 2) ? "elysian:textures/items/armor/armor_1.png" : "elysian:textures/items/armor/armor_0.png";
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
    
        if (item instanceof ItemRunicDivingSuit && ((ItemRunicDivingSuit) item).armorType == 1) {
            
            ItemStack stack = new ItemStack(item);
            setTankCapacity(stack, 10000);
            itemList.add(stack);
            itemList.add(setTankAmount(stack.copy(), 10000));
        }
        
        else
            super.getSubItems(item, tab, itemList);
    }
    
    @SubscribeEvent
    public void onFogColor (FogColors event) {
    
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
    
    ModelTurtleSuit suit = new ModelTurtleSuit(1.0f);
    
    @SubscribeEvent
    public void onPlayerRenderArmor (RenderPlayerEvent.SetArmorModel evt) {
    
        if (evt.entityPlayer != null) {
            EntityPlayer player = (EntityPlayer) evt.entityPlayer;
            
            ModelBiped main = evt.renderer.modelBipedMain;
            
            if (evt.stack != null && evt.stack.getItem().equals(ItemHandler.runicDivingHelm)) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                Minecraft.getMinecraft().renderEngine.bindTexture(visier_Overlay);
                GL11.glColor4f(1, 0, 1, 0.5f);
                suit.bipedHead.render(0.0625f);
                suit.bipedHead.rotateAngleX = main.bipedHead.rotateAngleX;
                suit.bipedHead.rotateAngleY = main.bipedHead.rotateAngleY;
                suit.bipedHead.rotationPointY = main.bipedHead.rotationPointY;
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
            
            if (evt.stack != null && evt.stack.getItem().equals(ItemHandler.runicDivingSuit)) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                Minecraft.getMinecraft().renderEngine.bindTexture(armor_Overlay);
                GL11.glColor4f(1, 0, 1, 1f);
                
                suit.bipedBody.render(0.0625f);
                suit.bipedBody.rotateAngleX = main.bipedBody.rotateAngleX;
                suit.bipedBody.rotateAngleY = main.bipedBody.rotateAngleY;
                suit.bipedBody.rotationPointY = main.bipedBody.rotationPointY;
                
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
            
            if (evt.stack != null && evt.stack.getItem().equals(ItemHandler.runicDivingBoots)) {
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                Minecraft.getMinecraft().renderEngine.bindTexture(armor_Overlay);
                GL11.glColor4f(1, 0, 1, 1f);
                
                suit.bipedRightLeg.render(0.0625f);
                suit.bipedLeftLeg.render(0.0625f);
                
                suit.bipedRightLeg.rotateAngleX = main.bipedRightLeg.rotateAngleX;
                suit.bipedRightLeg.rotateAngleY = main.bipedRightLeg.rotateAngleY;
                suit.bipedRightLeg.rotationPointY = main.bipedRightLeg.rotationPointY;
                suit.bipedRightLeg.rotationPointX = main.bipedRightLeg.rotationPointX;
                suit.bipedRightLeg.rotationPointZ = main.bipedRightLeg.rotationPointZ;
                
                suit.bipedLeftLeg.rotateAngleX = main.bipedLeftLeg.rotateAngleX;
                suit.bipedLeftLeg.rotateAngleY = main.bipedLeftLeg.rotateAngleY;
                suit.bipedLeftLeg.rotationPointY = main.bipedLeftLeg.rotationPointY;
                suit.bipedLeftLeg.rotationPointX = main.bipedLeftLeg.rotationPointX;
                suit.bipedLeftLeg.rotationPointZ = main.bipedLeftLeg.rotationPointZ;
                
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
    }
}
