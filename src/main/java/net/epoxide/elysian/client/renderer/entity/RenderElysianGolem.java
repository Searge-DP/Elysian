package net.epoxide.elysian.client.renderer.entity;

import net.epoxide.elysian.client.model.entity.ModelElysianGolem;
import net.epoxide.elysian.entity.EntityRuneGolem;
import net.epoxide.elysian.lib.Utilities;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderElysianGolem extends RenderLiving {
    
    ResourceLocation glow = new ResourceLocation("elysian:/textures/entity/rune_golem_glow.png");
    ResourceLocation golem = new ResourceLocation("elysian:/textures/entity/rune_golem.png");
    ModelElysianGolem modelPass = new ModelElysianGolem();
    
    public RenderElysianGolem(ModelBase model, float scale) {
    
        super(model, scale);
    }
    
    protected void preRenderCallback (EntityLiving el, float scale) {
    
        GL11.glTranslatef(-0.5f, 1f, -1f);
    }
    
    public void doRender (Entity e, double par2, double par4, double par6, float par8, float par9) {
    
        super.doRender(e, par2, par4, par6, par8, par9);
    }
    
    @Override
    protected int shouldRenderPass (EntityLivingBase el, int pass, float partialTick) {
    
        return this.renderPass((EntityRuneGolem) el, pass, partialTick);
    }
    
    protected int renderPass (EntityRuneGolem golem, int renderPass, float par3) {
    
        if (renderPass == 0) {
            this.setRenderPassModel(modelPass);
            bindTexture(glow);
            
            float time = (float) Math.cos(Utilities.getSysTimeF() / 20f);
            float angry = (float) Math.cos(Utilities.getSysTimeF() / 2f);
            
            if (golem.isTamed()) {
                if (golem.getHealth() < golem.getMaxHealth() / 2f)
                    GL11.glColor4f((golem.getHealth() / 100f) * golem.getMaxHealth(), time < 0.5f ? 0.5f : time, time, 1f);
                else
                    GL11.glColor4f(time, 1f, time, 1f);
            }
            if (!golem.isTamed()) {
                GL11.glColor4f(time, time, 1f, 1f);
            }
            if (golem.isAngry()) {
                GL11.glColor4f(1f, angry, angry, 1f);
            }
            if (golem.isInLove()) {
                GL11.glColor4f(1f, time, 0.7f, 1f);
            }
            if (golem.isWet()) {
                GL11.glColor4f(1f, 1f, angry, 1f);
            }
            return 1;
        }
        return -1;
    }
    
    @Override
    protected ResourceLocation getEntityTexture (Entity var1) {
    
        return golem;
    }
}
