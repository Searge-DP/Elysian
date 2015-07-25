package net.epoxide.elysian.recipe;

import net.epoxide.elysian.items.ItemHandler;
import net.epoxide.elysian.items.ItemRunicDivingSuit;
import net.epoxide.elysian.lib.ColorObject.VanillaColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHandler {
    
    public RecipeHandler() {
    
        // Visor Recipes
        for (VanillaColor color : VanillaColor.values()) {
            
            ItemStack divingHelm = new ItemStack(ItemHandler.runicDivingHelm);
            ItemRunicDivingSuit.setVisorColor(divingHelm, color.colorObj);
            GameRegistry.addRecipe(new ShapedOreRecipe(divingHelm, new Object[] { "sss", "sxs", "   ", Character.valueOf('s'), Blocks.stone, Character.valueOf('x'), color.getGlassPaneName() }));
        }
    }
}