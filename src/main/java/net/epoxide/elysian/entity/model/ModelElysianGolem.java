package net.epoxide.elysian.entity.model;

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
public class ModelElysianGolem extends ModelBase
{
	public ModelRenderer Torso;
	public ModelRenderer Torso2;
	public ModelRenderer Torso3;
	public ModelRenderer Hip;
	public ModelRenderer Hip2;
	public ModelRenderer Hip3;
	public ModelRenderer Hip4;
	public ModelRenderer Head;
	public ModelRenderer LegLeft;
	public ModelRenderer LegRight;
	public ModelRenderer ArmLeft;
	public ModelRenderer ArmRight;
	public ModelRenderer ArmPieceRight;
	public ModelRenderer ArmPieceLeft;

	public ModelElysianGolem(){
		this(0f);
	}
	
	public ModelElysianGolem(float f)
	{
		textureWidth = 64;
		textureHeight = 32;

		Torso = new ModelRenderer(this, 0, 12);
		Torso.addBox(-5F, 0F, -2.5F, 10, 4, 5, f);
		Torso.setRotationPoint(0F, 9F, 0F);
		Torso.setTextureSize(64, 32);
		Torso.mirror = true;
		setRotation(Torso, 0F, 0F, 0F);
		Torso2 = new ModelRenderer(this, 0, 21);
		Torso2.addBox(-3F, 4F, -2F, 6, 1, 4, f);
		Torso2.setRotationPoint(0F, 0F, 0F);
		Torso2.setTextureSize(64, 32);
		Torso2.mirror = true;
		setRotation(Torso2, 0F, 0F, 0F);
		Torso3 = new ModelRenderer(this, 20, 21);
		Torso3.addBox(-2F, 5F, -1F, 4, 2, 2, f);
		Torso3.setRotationPoint(0F, 0F, 0F);
		Torso3.setTextureSize(64, 32);
		Torso3.mirror = true;
		setRotation(Torso3, 0F, 0F, 0F);
		Hip = new ModelRenderer(this, 0, 26);
		Hip.addBox(-1F, 0F, -1F, 2, 1, 2, f);
		Hip.setRotationPoint(0F, 16F, 0F);
		Hip.setTextureSize(64, 32);
		Hip.mirror = true;
		setRotation(Hip, 0F, 0F, 0F);
		Hip2 = new ModelRenderer(this, 33, 0);
		Hip2.addBox(-2.5F, 1F, -1F, 5, 1, 2, f);
		Hip2.setRotationPoint(0F, 0F, 0F);
		Hip2.setTextureSize(64, 32);
		Hip2.mirror = true;
		setRotation(Hip2, 0F, 0F, 0F);
		Hip3 = new ModelRenderer(this, 35, 7);
		Hip3.addBox(-1.5F, 1F, -1F, 3, 1, 2, f);
		Hip3.setRotationPoint(0F, 1F, 0F);
		Hip3.setTextureSize(64, 32);
		Hip3.mirror = true;
		setRotation(Hip3, 0F, 0F, 0F);
		Hip4 = new ModelRenderer(this, 35, 3);
		Hip4.addBox(-1F, 1.5F, -1.5F, 2, 1, 3, f);
		Hip4.setRotationPoint(0F, 0F, 0F);
		Hip4.setTextureSize(64, 32);
		Hip4.mirror = true;
		setRotation(Hip4, 0F, 0F, 0F);
		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-3F, -6F, -3F, 6, 6, 6, f);
		Head.setRotationPoint(0F, 9F, 0F);
		Head.setTextureSize(64, 32);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		LegLeft = new ModelRenderer(this, 52, 23);
		LegLeft.addBox(-0.5F, -1.5F, -1.5F, 3, 6, 3, f);
		LegLeft.setRotationPoint(1F, 19F, 0F);
		LegLeft.setTextureSize(64, 32);
		LegLeft.mirror = true;
		setRotation(LegLeft, 0F, 0F, 0F);
		LegRight = new ModelRenderer(this, 40, 23);
		LegRight.addBox(-2.5F, -1.5F, -1.5F, 3, 6, 3, f);
		LegRight.setRotationPoint(-1F, 19F, 0F);
		LegRight.setTextureSize(64, 32);
		LegRight.mirror = true;
		setRotation(LegRight, 0F, 0F, 0F);
		ArmLeft = new ModelRenderer(this, 56, 13);
		ArmLeft.addBox(0F, -1F, -1F, 2, 8, 2, f);
		ArmLeft.setRotationPoint(5F, 10F, 0F);
		ArmLeft.setTextureSize(64, 32);
		ArmLeft.mirror = true;
		setRotation(ArmLeft, 0F, 0F, 0F);
		ArmRight = new ModelRenderer(this, 48, 13);
		ArmRight.addBox(-2F, -1F, -1F, 2, 8, 2, f);
		ArmRight.setRotationPoint(-5F, 10F, 0F);
		ArmRight.setTextureSize(64, 32);
		ArmRight.mirror = true;
		setRotation(ArmRight, 0F, 0F, 0F);
		ArmPieceRight = new ModelRenderer(this, 54, 0);
		ArmPieceRight.addBox(-1F, -2F, -2F, 1, 4, 4, f);
		ArmPieceRight.setRotationPoint(0F, 0F, 0F);
		ArmPieceRight.setTextureSize(64, 32);
		ArmPieceRight.mirror = true;
		setRotation(ArmPieceRight, 0F, 0F, 0F);
		ArmPieceLeft = new ModelRenderer(this, 54, 0);
		ArmPieceLeft.addBox(0F, -2F, -2F, 1, 4, 4, f);
		ArmPieceLeft.setRotationPoint(0F, 0F, 0F);
		ArmPieceLeft.setTextureSize(64, 32);
		ArmPieceLeft.mirror = true;
		setRotation(ArmPieceLeft, 0F, 0F, 0F);

		Torso.addChild(Torso2);
		Torso.addChild(Torso3);

		ArmLeft.addChild(ArmPieceLeft);
		ArmRight.addChild(ArmPieceRight);

		Hip.addChild(Hip2);
		Hip.addChild(Hip3);
		Hip.addChild(Hip4);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		if (this.isChild)
		{
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 7.0F * par7, 0.0F * par7);
			this.Head.renderWithRotation(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			this.Torso.render(par7);
			this.LegLeft.render(par7);
			this.LegRight.render(par7);
			this.ArmRight.render(par7);
			this.ArmLeft.render(par7);
			this.Hip.render(par7);
			GL11.glPopMatrix();
		}
		else
		{
			this.Head.renderWithRotation(par7);
			this.Torso.render(par7);
			this.LegLeft.render(par7);
			this.LegRight.render(par7);
			this.ArmRight.render(par7);
			this.ArmLeft.render(par7);
			this.Hip.render(par7);
		}
	}
	
	public void render(float par7){
		this.Head.render(par7);
		this.Torso.render(par7);
		this.LegLeft.render(par7);
		this.LegRight.render(par7);
		this.ArmRight.render(par7);
		this.ArmLeft.render(par7);
		this.Hip.render(par7);
	}

	public void renderForRocks(){
		float par7 = 0.0625f;
		this.Head.renderWithRotation(par7);
		this.Torso.render(par7);
		this.LegLeft.render(par7);
		this.LegRight.render(par7);
		this.ArmRight.render(par7);
		this.ArmLeft.render(par7);
		this.Hip.render(par7);

		this.Torso.setRotationPoint(0F, 9F, 0F);
		this.Hip.setRotationPoint(0f,16f,0f);
		this.Head.setRotationPoint(0, 9, 0);
		ArmLeft.setRotationPoint(5F, 10F, 0F);
		ArmRight.setRotationPoint(-5F, 10F, 0F);
		this.LegLeft.setRotationPoint(1F, 19.0F, 0F);
		this.LegRight.setRotationPoint(-1F, 19.0F, 0F);
		LegLeft.rotateAngleX = LegRight.rotateAngleX = 0.0f;
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		EntityRuneGolem golem = (EntityRuneGolem)par1EntityLivingBase;

		if (golem.isSitting())
		{
			this.LegLeft.setRotationPoint(1F, 22.0F, 0F);
			this.LegLeft.rotateAngleX = ((float)Math.PI * 3F / 2F);
			this.LegRight.setRotationPoint(-1F, 22.0F, 0F);
			this.LegRight.rotateAngleX = ((float)Math.PI * 3F / 2F);
			this.Torso.setRotationPoint(0f,12f,0f);
			this.Hip.setRotationPoint(0f,19f,0f);
			this.Head.setRotationPoint(0, 12, 0);
			ArmLeft.setRotationPoint(5F, 13F, 0F);
			ArmRight.setRotationPoint(-5F, 13F, 0F);
			ArmLeft.rotateAngleX = 0f;
			ArmRight.rotateAngleX = 0f;

		}
		else
		{
			this.Torso.setRotationPoint(0F, 9F, 0F);
			this.Hip.setRotationPoint(0f,16f,0f);
			this.Head.setRotationPoint(0, 9, 0);
			ArmLeft.setRotationPoint(5F, 10F, 0F);
			ArmRight.setRotationPoint(-5F, 10F, 0F);
			this.LegLeft.setRotationPoint(1F, 19.0F, 0F);
			this.LegRight.setRotationPoint(-1F, 19.0F, 0F);
			this.LegLeft.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
			this.LegRight.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
			this.ArmRight.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
			this.ArmLeft.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
		}
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		this.Head.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.Head.rotateAngleY = par4 / (180F / (float)Math.PI);
	}


	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
