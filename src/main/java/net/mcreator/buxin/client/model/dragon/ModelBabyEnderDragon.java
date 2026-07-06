package net.mcreator.buxin.client.model.dragon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.client.animation.BabyEnderDragonAnimations;
import net.mcreator.buxin.entity.dragon.baby.BabyEnderDragonEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ModelBabyEnderDragon<T extends BabyEnderDragonEntity> extends HierarchicalModel<BabyEnderDragonEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BuxinMod.MODID, "modelbabyenderdragon"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart horn;
    private final ModelPart uppder_face;
    private final ModelPart lower_face;
    private final ModelPart left_wing;
    private final ModelPart left_wing_tip;
    private final ModelPart right_wing;
    private final ModelPart right_wing_tip;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart tail6;
    private final ModelPart tail7;
    private final ModelPart tail8;
    private final ModelPart tail9;
    private final ModelPart neck;
    private final ModelPart neck1;
    private final ModelPart neck2;
    private final ModelPart back_leg;
    private final ModelPart back_thigh;
    private final ModelPart back_lower_leg;
    private final ModelPart back_foot;
    private final ModelPart front_leg;
    private final ModelPart front_thigh;
    private final ModelPart front_lower_leg;
    private final ModelPart front_foot;

    public ModelBabyEnderDragon(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.horn = this.head.getChild("horn");
        this.uppder_face = this.head.getChild("uppder_face");
        this.lower_face = this.head.getChild("lower_face");
        this.left_wing = root.getChild("left_wing");
        this.left_wing_tip = root.getChild("left_wing_tip");
        this.right_wing = root.getChild("right_wing");
        this.right_wing_tip = root.getChild("right_wing_tip");
        this.tail = root.getChild("tail");
        this.tail2 = root.getChild("tail2");
        this.tail3 = root.getChild("tail3");
        this.tail4 = root.getChild("tail4");
        this.tail5 = root.getChild("tail5");
        this.tail6 = root.getChild("tail6");
        this.tail7 = root.getChild("tail7");
        this.tail8 = root.getChild("tail8");
        this.tail9 = root.getChild("tail9");
        this.neck = root.getChild("neck");
        this.neck1 = root.getChild("neck1");
        this.neck2 = root.getChild("neck2");
        this.back_leg = root.getChild("back_leg");
        this.back_thigh = this.back_leg.getChild("back_thigh");
        this.back_lower_leg = this.back_leg.getChild("back_lower_leg");
        this.back_foot = this.back_leg.getChild("back_foot");
        this.front_leg = root.getChild("front_leg");
        this.front_thigh = this.front_leg.getChild("front_thigh");
        this.front_lower_leg = this.front_leg.getChild("front_lower_leg");
        this.front_foot = this.front_leg.getChild("front_foot");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(48, 0).addBox(-2.5F, -4.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-1.0F, -1.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-1.0F, 2.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 44).addBox(-1.0F, -4.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.5F, 19.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 18).addBox(-2.5F, -16.0F, 3.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F))
                .texOffs(18, 2).addBox(-1.5F, -16.0F, 3.49F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.5F, 20.25F, -8.0F));

        PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(0, 45).addBox(2.0F, -4.75F, 13.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(-1.0F, -4.75F, 13.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -4.25F, -3.0F, 0.5672F, 0.0F, 0.0F));

        PartDefinition uppder_face = head.addOrReplaceChild("uppder_face", CubeListBuilder.create().texOffs(3, 5).addBox(0.5F, -14.25F, 1.75F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(3, 5).addBox(-1.5F, -14.25F, 1.75F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-2.0F, -14.0F, 1.25F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lower_face = head.addOrReplaceChild("lower_face", CubeListBuilder.create().texOffs(0, 15).addBox(-2.0F, -14.0F, 1.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, -1.0F, 9.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-1.0F, -2.0F, 9.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(2.5F, 17.0F, -8.0F));

        PartDefinition left_wing_tip = partdefinition.addOrReplaceChild("left_wing_tip", CubeListBuilder.create().texOffs(0, 46).addBox(1.0F, -2.0F, 9.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 56).addBox(1.0F, -1.0F, 9.5F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, 17.0F, -8.0F));

        PartDefinition right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 44).addBox(-7.0F, -2.0F, 9.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(0, 48).mirror().addBox(-7.0F, -1.0F, 9.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 17.0F, -8.0F));

        PartDefinition right_wing_tip = partdefinition.addOrReplaceChild("right_wing_tip", CubeListBuilder.create().texOffs(0, 46).addBox(-13.0F, -2.0F, 9.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 56).mirror().addBox(-13.0F, -1.0F, 9.5F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.5F, 17.0F, -8.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 7.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(0, 44).addBox(-1.0F, 7.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.5F, 17.0F, 1.0F));

        PartDefinition tail2 = partdefinition.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 8.5F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.08F))
                .texOffs(0, 44).addBox(-1.0F, 8.5F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.5F, 17.5F, 7.0F));

        PartDefinition tail3 = partdefinition.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 4.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.06F))
                .texOffs(0, 44).addBox(-0.5F, 4.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tail4 = partdefinition.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 12.0F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.04F))
                .texOffs(0, 44).addBox(-0.5F, 12.0F, -10.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 18.0F, 11.0F));

        PartDefinition tail5 = partdefinition.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 14.0F, -13.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(0, 44).addBox(-0.5F, 14.0F, -12.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 18.0F, 13.0F));

        PartDefinition tail6 = partdefinition.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 16.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-0.5F, 16.0F, -14.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 18.0F, 15.0F));

        PartDefinition tail7 = partdefinition.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 18.0F, -17.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.02F))
                .texOffs(0, 44).addBox(-0.5F, 18.0F, -16.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 18.0F, 17.0F));

        PartDefinition tail8 = partdefinition.addOrReplaceChild("tail8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 20.0F, -19.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.04F))
                .texOffs(0, 44).addBox(-0.5F, 20.0F, -18.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 18.0F, 19.0F));

        PartDefinition tail9 = partdefinition.addOrReplaceChild("tail9", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 22.0F, -21.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.06F))
                .texOffs(0, 44).addBox(-0.5F, 22.0F, -20.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 18.0F, 21.0F));

        PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -11.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(0, 44).addBox(-0.5F, -11.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition neck1 = partdefinition.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, 6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.09F))
                .texOffs(0, 44).addBox(-0.5F, -6.0F, 7.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 18.0F, -6.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition neck2 = partdefinition.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, 8.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.08F))
                .texOffs(0, 44).addBox(-0.5F, -8.0F, 9.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 18.0F, -8.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition back_leg = partdefinition.addOrReplaceChild("back_leg", CubeListBuilder.create(), PartPose.offset(-0.5F, 19.0F, 2.0F));

        PartDefinition back_thigh = back_leg.addOrReplaceChild("back_thigh", CubeListBuilder.create().texOffs(9, 10).addBox(1.5F, -2.0F, -6.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(9, 10).addBox(-2.5F, -2.0F, -6.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition back_lower_leg = back_leg.addOrReplaceChild("back_lower_leg", CubeListBuilder.create().texOffs(9, 10).addBox(0.5F, -4.0F, -7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F))
                .texOffs(9, 10).addBox(-2.5F, -4.0F, -7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.5F, 3.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition back_foot = back_leg.addOrReplaceChild("back_foot", CubeListBuilder.create().texOffs(8, 9).addBox(0.5F, -6.8F, -4.6F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(8, 9).addBox(-2.5F, -6.8F, -4.6F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.5F, 4.0F, -2.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition front_leg = partdefinition.addOrReplaceChild("front_leg", CubeListBuilder.create(), PartPose.offset(-0.5F, 20.0F, -3.0F));

        PartDefinition front_thigh = front_leg.addOrReplaceChild("front_thigh", CubeListBuilder.create().texOffs(8, 9).addBox(0.5F, -2.5195F, 2.1749F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(8, 9).addBox(-1.5F, -2.5195F, 2.1749F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition front_lower_leg = front_leg.addOrReplaceChild("front_lower_leg", CubeListBuilder.create().texOffs(8, 9).addBox(0.0F, -4.8446F, 0.6944F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F))
                .texOffs(8, 9).addBox(-2.0F, -4.8446F, 0.6944F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.5F, 0.8251F, -0.5195F, 0.3927F, 0.0F, 0.0F));

        PartDefinition front_foot = front_leg.addOrReplaceChild("front_foot", CubeListBuilder.create().texOffs(8, 9).addBox(-2.0F, -6.3446F, 3.0944F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(8, 9).addBox(0.0F, -6.3446F, 3.0944F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.5F, 1.8251F, -2.5195F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_wing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_wing_tip.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_wing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_wing_tip.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail9.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        neck1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        neck2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        back_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(BabyEnderDragonEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        root().getAllParts().forEach(ModelPart::resetPose);
        animate(pEntity.idleAnimationState, BabyEnderDragonAnimations.BABY_ENDER_DRAGON_IDLE, pAgeInTicks);
//        animateWalk(BabyEnderDragonAnimations.BABY_ENDER_DRAGON_IDLE, pLimbSwing, pLimbSwingAmount, 1.0F, 2.5F);
    }
}