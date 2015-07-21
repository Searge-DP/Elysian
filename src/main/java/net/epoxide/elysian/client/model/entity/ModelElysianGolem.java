package net.epoxide.elysian.client.model.entity;

import net.epoxide.elysian.entity.EntityRuneGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelElysianGolem extends ModelBase {
    
	public ModelRenderer torso;
    public ModelRenderer torso_mid;
    public ModelRenderer torso_bot;
    public ModelRenderer hip;
    public ModelRenderer hip_left;
    public ModelRenderer hip_right;
    public ModelRenderer hip_bot;
    public ModelRenderer head;
    public ModelRenderer leg_left;
    public ModelRenderer leg_right;
    public ModelRenderer arm_left;
    public ModelRenderer arm_right;
    public ModelRenderer arm_right_top;
    public ModelRenderer arm_left_top;
    
    public ModelElysianGolem() {
        this(0f);
    }
    
    public ModelElysianGolem(float f) {
    
        textureWidth = 64;
        textureHeight = 32;
        
        torso = new ModelRenderer(this, 0, 12);
        torso.addBox(-5F, 0F, -2.5F, 10, 4, 5, f);
        torso.setRotationPoint(0F, 9F, 0F);
        torso.setTextureSize(64, 32);
       
        torso_mid = new ModelRenderer(this, 0, 21);
        torso_mid.addBox(-3F, 4F, -2F, 6, 1, 4, f);
        torso_mid.setTextureSize(64, 32);

        torso_bot = new ModelRenderer(this, 20, 21);
        torso_bot.addBox(-2F, 5F, -1F, 4, 2, 2, f);
        torso_bot.setTextureSize(64, 32);
        
        hip = new ModelRenderer(this, 0, 26);
        hip.addBox(-1F, 0F, -1F, 2, 1, 2, f);
        hip.setRotationPoint(0F, 16F, 0F);
        hip.setTextureSize(64, 32);

        hip_left = new ModelRenderer(this, 33, 0);
        hip_left.addBox(-2.5F, 1F, -1F, 5, 1, 2, f);
        hip_left.setTextureSize(64, 32);
        
        hip_right = new ModelRenderer(this, 35, 7);
        hip_right.addBox(-1.5F, 1F, -1F, 3, 1, 2, f);
        hip_right.setRotationPoint(0F, 1F, 0F);
        hip_right.setTextureSize(64, 32);

        hip_bot = new ModelRenderer(this, 35, 3);
        hip_bot.addBox(-1F, 1.5F, -1.5F, 2, 1, 3, f);
        hip_bot.setTextureSize(64, 32);

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-3F, -6F, -3F, 6, 6, 6, f);
        head.setRotationPoint(0F, 9F, 0F);
        head.setTextureSize(64, 32);

        leg_left = new ModelRenderer(this, 52, 23);
        leg_left.addBox(-0.5F, -1.5F, -1.5F, 3, 6, 3, f);
        leg_left.setRotationPoint(1F, 19F, 0F);
        leg_left.setTextureSize(64, 32);

        leg_right = new ModelRenderer(this, 40, 23);
        leg_right.addBox(-2.5F, -1.5F, -1.5F, 3, 6, 3, f);
        leg_right.setRotationPoint(-1F, 19F, 0F);
        leg_right.setTextureSize(64, 32);

        arm_left = new ModelRenderer(this, 56, 13);
        arm_left.addBox(0F, -1F, -1F, 2, 8, 2, f);
        arm_left.setRotationPoint(5F, 10F, 0F);
        arm_left.setTextureSize(64, 32);

        arm_right = new ModelRenderer(this, 48, 13);
        arm_right.addBox(-2F, -1F, -1F, 2, 8, 2, f);
        arm_right.setRotationPoint(-5F, 10F, 0F);
        arm_right.setTextureSize(64, 32);

        arm_right_top = new ModelRenderer(this, 54, 0);
        arm_right_top.addBox(-1F, -2F, -2F, 1, 4, 4, f);
        arm_right_top.setTextureSize(64, 32);

        arm_left_top = new ModelRenderer(this, 54, 0);
        arm_left_top.addBox(0F, -2F, -2F, 1, 4, 4, f);
        arm_left_top.setTextureSize(64, 32);
        
        torso.addChild(torso_mid);
        torso.addChild(torso_bot);
        
        arm_left.addChild(arm_left_top);
        arm_right.addChild(arm_right_top);
        
        hip.addChild(hip_left);
        hip.addChild(hip_right);
        hip.addChild(hip_bot);
    }
    
    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render (Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        
        if (this.isChild) {
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0F, 7.0F * par7, 0.0F * par7);
            this.head.renderWithRotation(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
            this.torso.render(par7);
            this.leg_left.render(par7);
            this.leg_right.render(par7);
            this.arm_right.render(par7);
            this.arm_left.render(par7);
            this.hip.render(par7);
            GL11.glPopMatrix();
        }
        else {
            this.head.renderWithRotation(par7);
            this.torso.render(par7);
            this.leg_left.render(par7);
            this.leg_right.render(par7);
            this.arm_right.render(par7);
            this.arm_left.render(par7);
            this.hip.render(par7);
        }
    }
    
    /**to render parts without movement*/
    public void render (float scale) {
        this.head.render(scale);
        this.torso.render(scale);
        this.leg_left.render(scale);
        this.leg_right.render(scale);
        this.arm_right.render(scale);
        this.arm_left.render(scale);
        this.hip.render(scale);
    }
    
    /**apperently, once upon a time, I wanted to implement the golem turning to a rock.
     * i'll leave this code here in the meanwhile*/
    public void renderForRocks () {
    
        float scale = 0.0625f;
        this.head.renderWithRotation(scale);
        this.torso.render(scale);
        this.leg_left.render(scale);
        this.leg_right.render(scale);
        this.arm_right.render(scale);
        this.arm_left.render(scale);
        this.hip.render(scale);
        
        this.torso.setRotationPoint(0F, 9F, 0F);
        this.hip.setRotationPoint(0f, 16f, 0f);
        this.head.setRotationPoint(0, 9, 0);
        arm_left.setRotationPoint(5F, 10F, 0F);
        arm_right.setRotationPoint(-5F, 10F, 0F);
        this.leg_left.setRotationPoint(1F, 19.0F, 0F);
        this.leg_right.setRotationPoint(-1F, 19.0F, 0F);
        leg_left.rotateAngleX = leg_right.rotateAngleX = 0.0f;
    }
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params
     * here are the same second and third as in the setRotationAngles method.
     */
    public void setLivingAnimations (EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
    
        EntityRuneGolem golem = (EntityRuneGolem) par1EntityLivingBase;
        
        if (golem.isSitting()) {
            this.leg_left.setRotationPoint(1F, 22.0F, 0F);
            this.leg_left.rotateAngleX = ((float) Math.PI * 3F / 2F);
            this.leg_right.setRotationPoint(-1F, 22.0F, 0F);
            this.leg_right.rotateAngleX = ((float) Math.PI * 3F / 2F);
            this.torso.setRotationPoint(0f, 12f, 0f);
            this.hip.setRotationPoint(0f, 19f, 0f);
            this.head.setRotationPoint(0, 12, 0);
            arm_left.setRotationPoint(5F, 13F, 0F);
            arm_right.setRotationPoint(-5F, 13F, 0F);
            arm_left.rotateAngleX = 0f;
            arm_right.rotateAngleX = 0f;
            
        }
        else {
            this.torso.setRotationPoint(0F, 9F, 0F);
            this.hip.setRotationPoint(0f, 16f, 0f);
            this.head.setRotationPoint(0, 9, 0);
            arm_left.setRotationPoint(5F, 10F, 0F);
            arm_right.setRotationPoint(-5F, 10F, 0F);
            this.leg_left.setRotationPoint(1F, 19.0F, 0F);
            this.leg_right.setRotationPoint(-1F, 19.0F, 0F);
            this.leg_left.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
            this.leg_right.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float) Math.PI) * 1.4F * par3;
            this.arm_right.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
            this.arm_left.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float) Math.PI) * 1.4F * par3;
        }
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for
     * animating the movement of arms and legs, where par1 represents the time(so that arms and
     * legs swing back and forth) and par2 represents how "far" arms and legs can swing at
     * most.
     */
    public void setRotationAngles (float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.head.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.head.rotateAngleY = par4 / (180F / (float) Math.PI);
    }
    
    private void setRotation (ModelRenderer model, float x, float y, float z) {
    
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
