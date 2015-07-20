package net.epoxide.elysian.items.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTurtleSuit extends ModelBiped
{
	//fields
	ModelRenderer HelmBase;
	ModelRenderer HelmMask;
	ModelRenderer Shoe;
	ModelRenderer Shoe2;
	ModelRenderer ShellTop;
	ModelRenderer ShellBase;
	ModelRenderer KneePad1;
	ModelRenderer KneePad2;

	public ModelTurtleSuit(float scale)
	{
		super(scale, 0, 128, 64);
		
		textureWidth = 128;
		textureHeight = 64;

		HelmBase = new ModelRenderer(this, 0, 52);
		HelmBase.addBox(-5F, -1.5F, -5F, 10, 2, 10);
		HelmBase.setRotationPoint(0F, 0F, 0F);
		HelmBase.setTextureSize(128, 64);
		HelmBase.mirror = true;
		setRotation(HelmBase, 0F, 0F, 0F);
		HelmMask = new ModelRenderer(this, 0, 32);
		HelmMask.addBox(-4.5F, -8.5F, -4.5F, 9, 8, 9);
		HelmMask.setRotationPoint(0F, 0F, 0F);
		HelmMask.setTextureSize(128, 64);
		HelmMask.mirror = true;
		setRotation(HelmMask, 0F, 0F, 0F);
		Shoe = new ModelRenderer(this, 40, 53);
		Shoe.addBox(-2.5F, 6F, -2.5F, 5, 6, 5);
		Shoe.setRotationPoint(0F, 0F, 0F);
		Shoe.setTextureSize(128, 64);
		Shoe.mirror = true;
		setRotation(Shoe, 0F, 0F, 0F);
		Shoe2 = new ModelRenderer(this, 40, 53);
		Shoe2.addBox(-2.5F, 6F, -2.5F, 5, 6, 5);
		Shoe2.setRotationPoint(0F, 0F, 0F);
		Shoe2.setTextureSize(128, 64);
		Shoe2.mirror = true;
		setRotation(Shoe2, 0F, 0F, 0F);
		ShellTop = new ModelRenderer(this, 56, 0);
		ShellTop.addBox(-5.5F, -2F, 2F, 11, 14, 4);
		ShellTop.setRotationPoint(0F, 0F, 0F);
		ShellTop.setTextureSize(128, 64);
		ShellTop.mirror = true;
		setRotation(ShellTop, 0F, 0F, 0F);
		ShellBase = new ModelRenderer(this, 56, 18);
		ShellBase.addBox(-6F, -2.5F, 2F, 12, 15, 1);
		ShellBase.setRotationPoint(0F, 0F, 0F);
		ShellBase.setTextureSize(128, 64);
		ShellBase.mirror = true;
		setRotation(ShellBase, 0F, 0F, 0F);
		KneePad1 = new ModelRenderer(this, 18, 32);
		KneePad1.addBox(-1.5F, 2F, -3F, 3, 3, 1);
		KneePad1.setRotationPoint(0F, 0F, 0F);
		KneePad1.setTextureSize(128, 64);
		KneePad1.mirror = true;
		setRotation(KneePad1, 0F, 0F, 0F);
		KneePad2 = new ModelRenderer(this, 18, 32);
		KneePad2.addBox(-1.5F, 2F, -3F, 3, 3, 1);
		KneePad2.setRotationPoint(0F, 0F, 0F);
		KneePad2.setTextureSize(128, 64);
		KneePad2.mirror = true;
		setRotation(KneePad2, 0F, 0F, 0F);
		
		bipedHead.addChild(HelmBase);
		bipedHead.addChild(HelmMask);
		
		bipedBody.addChild(ShellBase);
		bipedBody.addChild(ShellTop);
		
		bipedRightLeg.addChild(KneePad1);
		bipedLeftLeg.addChild(KneePad2);
		
		bipedRightLeg.addChild(Shoe);
		bipedLeftLeg.addChild(Shoe2);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
//		HelmBase.render(f5);
//		HelmMask.render(f5);
//		Shoe.render(f5);
//		Shoe2.render(f5);
//		ShellTop.render(f5);
//		ShellBase.render(f5);
//		KneePad1.render(f5);
//		KneePad2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
