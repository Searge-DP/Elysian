package net.epoxide.elysian.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelRunicDivingSuit extends ModelBiped {
    private ModelRenderer HelmBase;
    private ModelRenderer HelmMask;
    private ModelRenderer Shoe;
    private ModelRenderer Shoe2;
    private ModelRenderer ShellTop;
    private ModelRenderer ShellBase;
    private ModelRenderer KneePad1;
    private ModelRenderer KneePad2;
    
    /**
     * defining every modelpart in here : size, texture place, rotation point, adding child
     * parts, ...
     */
    public ModelRunicDivingSuit(float scale) {
    
        super(scale, 0, 128, 64);
        
        HelmBase = new ModelRenderer(this, 0, 52);
        HelmBase.addBox(-5F, -1.5F, -5F, 10, 2, 10);
        HelmBase.setTextureSize(128, 64);
        
        HelmMask = new ModelRenderer(this, 0, 32);
        HelmMask.addBox(-4.5F, -8.5F, -4.5F, 9, 8, 9);
        HelmMask.setTextureSize(128, 64);
        
        Shoe = new ModelRenderer(this, 40, 53);
        Shoe.addBox(-2.5F, 6F, -2.5F, 5, 6, 5);
        Shoe.setTextureSize(128, 64);
        
        Shoe2 = new ModelRenderer(this, 40, 53);
        Shoe2.addBox(-2.5F, 6F, -2.5F, 5, 6, 5);
        Shoe2.setTextureSize(128, 64);
        
        ShellTop = new ModelRenderer(this, 56, 0);
        ShellTop.addBox(-5.5F, -2F, 2F, 11, 14, 4);
        ShellTop.setTextureSize(128, 64);
        
        ShellBase = new ModelRenderer(this, 56, 18);
        ShellBase.addBox(-6F, -2.5F, 2F, 12, 15, 1);
        ShellBase.setTextureSize(128, 64);
        
        KneePad1 = new ModelRenderer(this, 18, 32);
        KneePad1.addBox(-1.5F, 2F, -3F, 3, 3, 1);
        KneePad1.setTextureSize(128, 64);
        
        KneePad2 = new ModelRenderer(this, 18, 32);
        KneePad2.addBox(-1.5F, 2F, -3F, 3, 3, 1);
        KneePad2.setTextureSize(128, 64);
        
        bipedHead.addChild(HelmBase);
        bipedHead.addChild(HelmMask);
        
        bipedBody.addChild(ShellBase);
        bipedBody.addChild(ShellTop);
        
        bipedRightLeg.addChild(KneePad1);
        bipedLeftLeg.addChild(KneePad2);
        
        bipedRightLeg.addChild(Shoe);
        bipedLeftLeg.addChild(Shoe2);
    }
    
    @Override
    public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
    
    private void setRotation (ModelRenderer model, float x, float y, float z) {
    
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
