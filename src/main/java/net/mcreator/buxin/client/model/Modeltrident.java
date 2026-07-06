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

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modeltrident<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("buxin", "modeltrident"), "main");
	public final ModelPart trident;

	public Modeltrident(ModelPart root) {
		this.trident = root.getChild("trident");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition trident = partdefinition.addOrReplaceChild("trident",
				CubeListBuilder.create().texOffs(0, 0).addBox(-8.51F, -4.01F, 7.49F, 1.02F, 31.02F, 1.02F, new CubeDeformation(0.0F)).texOffs(4, 0).addBox(-9.5F, 0.0F, 7.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 3)
						.addBox(-10.5F, -3.0F, 7.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 3).addBox(-6.5F, -3.0F, 7.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, 0.0F, -8.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		trident.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
