package net.epoxide.elysian.items;

import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.client.model.ModelHandler;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemTurtleArmor extends ItemArmor {
    
    private int defaultBreathTimer;
    private int breathingTimer;
    
    public ItemTurtleArmor(int armorID) {
    
        super(ArmorMaterial.IRON, 0, armorID);
        
        setCreativeTab(Elysian.tabElysian);
        
        switch (armorID) {
            case 0:
                setUnlocalizedName("turtleHelm");
                setTextureName("iron_helmet");
                break;
            case 1:
                setUnlocalizedName("turtleBody");
                setTextureName("iron_chestplate");
                defaultBreathTimer = breathingTimer = 5000;
                break;
            case 2:
                setUnlocalizedName("turtleLegs");
                setTextureName("iron_leggings");
                break;
            case 3:
                setUnlocalizedName("turtleBoots");
                setTextureName("iron_boots");
                break;
            
            default:
                break;
        }
    }
    
    @Override
    public String getArmorTexture (ItemStack stack, Entity entity, int slot, String type) {
    
        if (slot == 2)
            return "elysian:textures/items/armor/armor_1.png";
        else if (slot != 2)
            return "elysian:textures/items/armor/armor_0.png";
        
        return null;
    }
    
    @Override
    public ModelBiped getArmorModel (EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
    
        ModelBiped armorModel = null;
        
        if (itemStack != null && itemStack.getItem() instanceof ItemTurtleArmor) {
            
            armorModel = (armorSlot == 1 || armorSlot == 3) ? ModelHandler.runicScubaTop : ModelHandler.runicScubaBottom;
            ModelHandler.updateModelForArmor(armorModel, armorSlot);
            ModelHandler.updateModelToMatchEntity(armorModel, entityLiving);
        }
        
        return armorModel;
    }
    
    public int getBreathTimer () {
    
        return breathingTimer;
    }
    
    public void breath () {
    
        breathingTimer--;
    }
    
    public void resetTank () {
    
        breathingTimer = defaultBreathTimer;
    }
    
}
