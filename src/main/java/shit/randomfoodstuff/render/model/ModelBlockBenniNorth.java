package shit.randomfoodstuff.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlockBenniNorth extends ModelBase {

	ModelRenderer Leg1;
	ModelRenderer Leg2;
	ModelRenderer BodyLittle;
	ModelRenderer BodyBig;
	ModelRenderer Neck;
	ModelRenderer Head;
	ModelRenderer Hair1;
	ModelRenderer Hair2;
	ModelRenderer Hair3;
	ModelRenderer Hair4;
	ModelRenderer Arm1;
	ModelRenderer Arm2;

	public ModelBlockBenniNorth() {
		textureWidth = 64;
		textureHeight = 64;

		Leg1 = new ModelRenderer(this, 0, 21);
		Leg1.addBox(0F, 0F, 0F, 2, 3, 2);
		Leg1.setRotationPoint(-3F, 21F, -1F);
		Leg1.setTextureSize(64, 64);
		Leg1.mirror = true;
		setRotation(Leg1, 0F, 0F, 0F);
		Leg2 = new ModelRenderer(this, 9, 21);
		Leg2.addBox(0F, 0F, 0F, 2, 3, 2);
		Leg2.setRotationPoint(1F, 21F, -1F);
		Leg2.setTextureSize(64, 64);
		Leg2.mirror = true;
		setRotation(Leg2, 0F, 0F, 0F);
		BodyLittle = new ModelRenderer(this, 0, 27);
		BodyLittle.addBox(0F, 0F, 0F, 8, 1, 4);
		BodyLittle.setRotationPoint(-4F, 20F, -2F);
		BodyLittle.setTextureSize(64, 64);
		BodyLittle.mirror = true;
		setRotation(BodyLittle, 0F, 0F, 0F);
		BodyBig = new ModelRenderer(this, 0, 0);
		BodyBig.addBox(0F, 0F, 0F, 8, 5, 6);
		BodyBig.setRotationPoint(4F, 15F, 3F);
		BodyBig.setTextureSize(64, 64);
		BodyBig.mirror = true;
		setRotation(BodyBig, 0F, 3.141593F, 0F);
		Neck = new ModelRenderer(this, 53, 29);
		Neck.addBox(0F, 0F, 0F, 2, 1, 2);
		Neck.setRotationPoint(-1F, 14F, -1F);
		Neck.setTextureSize(64, 64);
		Neck.mirror = true;
		setRotation(Neck, 0F, 0F, 0F);
		Head = new ModelRenderer(this, 48, 21);
		Head.addBox(0F, 0F, 0F, 4, 3, 4);
		Head.setRotationPoint(2F, 11F, 2F);
		Head.setTextureSize(64, 64);
		Head.mirror = true;
		setRotation(Head, 0F, 3.141593F, 0F);
		Hair1 = new ModelRenderer(this, 42, 0);
		Hair1.addBox(0F, 0F, 0F, 6, 1, 5);
		Hair1.setRotationPoint(-3F, 10F, -3F);
		Hair1.setTextureSize(64, 64);
		Hair1.mirror = true;
		setRotation(Hair1, 0F, 0F, 0F);
		Hair2 = new ModelRenderer(this, 50, 6);
		Hair2.addBox(0F, 0F, 0F, 6, 3, 1);
		Hair2.setRotationPoint(-3F, 11F, 2F);
		Hair2.setTextureSize(64, 64);
		Hair2.mirror = true;
		setRotation(Hair2, 0F, 0F, 0F);
		Hair3 = new ModelRenderer(this, 52, 10);
		Hair3.addBox(0F, 0F, 0F, 1, 3, 5);
		Hair3.setRotationPoint(2F, 11F, -3F);
		Hair3.setTextureSize(64, 64);
		Hair3.mirror = true;
		setRotation(Hair3, 0F, 0F, 0F);
		Hair4 = new ModelRenderer(this, 40, 10);
		Hair4.addBox(0F, 0F, 0F, 1, 3, 5);
		Hair4.setRotationPoint(-3F, 11F, -3F);
		Hair4.setTextureSize(64, 64);
		Hair4.mirror = true;
		setRotation(Hair4, 0F, 0F, 0F);
		Arm1 = new ModelRenderer(this, 0, 13);
		Arm1.addBox(0F, 0F, 0F, 1, 4, 2);
		Arm1.setRotationPoint(-5F, 15F, -1F);
		Arm1.setTextureSize(64, 64);
		Arm1.mirror = true;
		setRotation(Arm1, 0F, 0F, 0F);
		Arm2 = new ModelRenderer(this, 8, 13);
		Arm2.addBox(0F, 0F, 0F, 1, 4, 2);
		Arm2.setRotationPoint(4F, 15F, -1F);
		Arm2.setTextureSize(64, 64);
		Arm2.mirror = true;
		setRotation(Arm2, 0F, 0F, 0F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
