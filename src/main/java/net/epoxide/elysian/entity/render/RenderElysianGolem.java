package net.epoxide.elysian.entity.render;

import net.epoxide.elysian.entity.EntityRuneGolem;
import net.epoxide.elysian.entity.model.ModelElysianGolem;
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
    
    float f = 1.0f;
    
    ResourceLocation glow = new ResourceLocation("elysian:/textures/entity/rune_golem_glow.png");
    ResourceLocation golem = new ResourceLocation("elysian:/textures/entity/rune_golem.png");
    ModelElysianGolem modelPass = new ModelElysianGolem();
    
    public RenderElysianGolem(ModelBase par1ModelBase, float par2) {
    
        super(par1ModelBase, par2);
    }
    
    protected void preRenderCallback (EntityLiving pet, float par2) {
    
        GL11.glTranslatef(-0.5f, 1f, -1f);
    }
    
    public void renderGolem (EntityRuneGolem golem, double par2, double par4, double par6, float par8, float par9) {
    
        super.doRender(golem, par2, par4, par6, par8, par9);
    }
    
    public void doRenderLiving (EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
    
        this.renderGolem((EntityRuneGolem) par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    public void doRender (Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
    
        this.renderGolem((EntityRuneGolem) par1Entity, par2, par4, par6, par8, par9);
    }
    
    @Override
    protected int shouldRenderPass (EntityLivingBase par1EntityLiving, int par2, float par3) {
    
        return this.sheepTexturing((EntityRuneGolem) par1EntityLiving, par2, par3);
    }
    
    protected int sheepTexturing (EntityRuneGolem buddy, int par2, float par3) {
    
        if (par2 == 0) {
            this.setRenderPassModel(modelPass);
            bindTexture(glow);
            
            float time = (float) Math.cos(Utilities.getSysTimeF() / 20f);
            float angry = (float) Math.cos(Utilities.getSysTimeF() / 2f);
            
            if (buddy.isTamed()) {
                if (buddy.getHealth() < buddy.getMaxHealth() / 2f)
                    GL11.glColor4f((buddy.getHealth() / 100f) * buddy.getMaxHealth(), time < 0.5f ? 0.5f : time, time, 1f);
                else
                    GL11.glColor4f(time, 1f, time, 1f);
            }
            if (!buddy.isTamed()) {
                GL11.glColor4f(time, time, 1f, 1f);
            }
            if (buddy.isAngry()) {
                GL11.glColor4f(1f, angry, angry, 1f);
            }
            if (buddy.isInLove()) {
                GL11.glColor4f(1f, time, 0.7f, 1f);
            }
            if (buddy.isWet()) {
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
