package io.github.elizayami.galaxia.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

public class ModelGalaxiumArmor extends BipedModel<LivingEntity>
{
	private final ModelRenderer LeftLeg;
	private final ModelRenderer RightLeg;
	private final ModelRenderer Body;
	private final ModelRenderer Galaxy;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer Head;
	private final ModelRenderer LeftHorn;
	private final ModelRenderer RightHorn;
	private final ModelRenderer Gem;
	private final ModelRenderer Glass;

	public ModelGalaxiumArmor(float modelSize, boolean legs) 
	{
        super(modelSize, 0, 64, 64);
		textureWidth = 64;
		textureHeight = 64;

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		LeftLeg.setTextureOffset(0, 16).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		
		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		RightLeg.setTextureOffset(0, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		Galaxy = new ModelRenderer(this);
		Galaxy.setRotationPoint(0.0F, 5.0F, -0.5F);
		Galaxy.setTextureOffset(0, 34).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
		Galaxy.setTextureOffset(11, 34).addBox(-3.0F, -1.0F, -3.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		Galaxy.setTextureOffset(11, 34).addBox(2.0F, 0.0F, -3.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		Galaxy.setTextureOffset(10, 39).addBox(-2.0F, -2.0F, -3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		Galaxy.setTextureOffset(10, 42).addBox(-1.0F, 3.0F, -3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		Galaxy.setTextureOffset(16, 36).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		Galaxy.setTextureOffset(16, 36).addBox(-1.0F, 4.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		RightArm.setTextureOffset(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		LeftArm.setTextureOffset(40, 36).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 3.0F, 0.0F);
		Head.setTextureOffset(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		LeftHorn = new ModelRenderer(this);
		LeftHorn.setRotationPoint(0.0F, 3.0F, 1.0F);
		LeftHorn.setTextureOffset(19, 39).addBox(4.0F, -12.0F, -1.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		LeftHorn.setTextureOffset(9, 45).addBox(4.0F, -11.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

		RightHorn = new ModelRenderer(this);
		RightHorn.setRotationPoint(-1.0F, 3.0F, 0.0F);
		RightHorn.setTextureOffset(19, 39).addBox(-5.0F, -12.0F, -1.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		RightHorn.setTextureOffset(9, 45).addBox(-5.0F, -11.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

		Gem = new ModelRenderer(this);
		Gem.setRotationPoint(0.0F, 2.5F, -1.0F);
		Gem.setTextureOffset(28, 40).addBox(-1.0F, -11.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

		Glass = new ModelRenderer(this);
		Glass.setRotationPoint(0.0F, 3.0F, -0.5F);
		Glass.setTextureOffset(32, 48).addBox(-4.0F, -11.0F, -3.5F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		bipedHead.addChild(RightHorn);
		bipedHead.addChild(LeftHorn);
		bipedHead.addChild(Gem);
		bipedHead.addChild(Glass);
		bipedBody.addChild(Galaxy);
	}

	
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn instanceof ArmorStandEntity) {
            ArmorStandEntity entityarmorstand = (ArmorStandEntity) entityIn;
            this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            this.bipedHeadwear.copyModelAngles(this.bipedHead);
        } else {
            super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		Head.copyModelAngles(bipedHead);
		Body.copyModelAngles(bipedBody);
		LeftArm.copyModelAngles(bipedLeftArm);
		RightArm.copyModelAngles(bipedRightArm);
		LeftLeg.copyModelAngles(bipedLeftLeg);
		RightLeg.copyModelAngles(bipedRightLeg);
		super.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}