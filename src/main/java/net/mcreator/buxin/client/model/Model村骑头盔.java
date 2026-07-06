package net.mcreator.buxin.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Model村骑头盔<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("buxin", "model_cun_qi_tou_kui"), "main");
	public final ModelPart Head;

	public Model村骑头盔(ModelPart root) {
		this.Head = root.getChild("Head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Head = partdefinition.addOrReplaceChild("Head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(-4.0F, -10.0F, -5.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(37, 0)
						.addBox(-1.0F, -10.5F, -5.0F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(15, 34).addBox(-0.5F, -12.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(43, 47)
						.addBox(-0.5F, -13.0F, -6.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(-0.5F, -14.0F, -6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(53, 42)
						.addBox(4.0F, -10.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(16, 49).addBox(-5.0F, -10.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(50, 0)
						.addBox(-5.0F, -9.0F, -5.0F, 10.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 2).addBox(-5.0F, -5.0F, -6.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 0)
						.addBox(-5.0F, -3.0F, -6.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, -2.0F, -7.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 6)
						.addBox(-1.0F, -2.5F, -6.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 29).addBox(-5.0F, -4.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 23)
						.addBox(4.0F, -4.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(38, 23).addBox(4.0F, -2.0F, -5.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(37, 10)
						.addBox(-5.0F, -2.0F, -5.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(38, 28).addBox(-5.0F, -2.0F, 3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 27)
						.addBox(4.0F, -2.0F, 3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 29).addBox(-4.0F, -2.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(28, 6)
						.addBox(3.0F, -2.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(28, 29).addBox(4.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 29)
						.addBox(-5.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(26, 23).addBox(-5.0F, -2.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 16)
						.addBox(4.0F, -2.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 21).addBox(-5.0F, -2.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(24, 6)
						.addBox(-5.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 24).addBox(4.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 20)
						.addBox(4.0F, -2.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(34, 56).addBox(-4.0F, -9.0F, 4.0F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 4)
						.addBox(-4.0F, -2.0F, 4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 27).addBox(-3.0F, -1.0F, 4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 24)
						.addBox(-1.0F, 0.0F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(27, 23).addBox(-5.0F, -9.0F, -4.0F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(26, 7)
						.addBox(4.0F, -9.0F, -4.0F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1047F, 0.0873F, 0.0F));
		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(52, 14).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(47, 26).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(46, 10).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.2094F, 0.0F, 0.0F));
		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.192F, 0.0F, 0.0349F));
		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(35, 39).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, -0.1745F, 0.0F, -0.0349F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull T t0, float f, float f1, float f2, float f3, float f4) {
		this.Head.yRot = f3 / 57.295776F;
		this.Head.xRot = f4 / 57.295776F;
	}

	public ModelPart root(){
		return Head;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
