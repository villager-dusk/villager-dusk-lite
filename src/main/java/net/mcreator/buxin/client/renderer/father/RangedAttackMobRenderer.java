
package net.mcreator.buxin.client.renderer.father;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

public class RangedAttackMobRenderer <T extends Mob, M extends HumanoidModel<T>> extends MobRenderer<T, M> {
    private static final ResourceLocation DEFAULT_LOCATION = new ResourceLocation("textures/entity/steve.png");
    
    public RangedAttackMobRenderer(EntityRendererProvider.Context p_174169_, M p_174170_, float p_174171_) {
        this(p_174169_, p_174170_, p_174171_, 1.0F, 1.0F, 1.0F);
    }

    public RangedAttackMobRenderer(EntityRendererProvider.Context p_174173_, M p_174174_, float p_174175_, float p_174176_, float p_174177_, float p_174178_) {
        super(p_174173_, p_174174_, p_174175_);
        this.addLayer(new CustomHeadLayer(this, p_174173_.getModelSet(), p_174176_, p_174177_, p_174178_, p_174173_.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, p_174173_.getModelSet()));
        this.addLayer(new ItemInHandLayer(this, p_174173_.getItemInHandRenderer()));
    }
    
    public ResourceLocation getTextureLocation(T t) {
        return DEFAULT_LOCATION;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        model.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        model.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack stack = pEntity.getMainHandItem();
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof CrossbowItem) {
                if (CrossbowItem.isCharged(pEntity.getMainHandItem()))
                    setHandPose(pEntity, HumanoidModel.ArmPose.CROSSBOW_CHARGE);
                else
                    setHandPose(pEntity, HumanoidModel.ArmPose.CROSSBOW_HOLD);
            } else if (stack.getItem() instanceof BowItem && pEntity.isAggressive())
                setHandPose(pEntity, HumanoidModel.ArmPose.BOW_AND_ARROW);
            else
                setHandPose(pEntity, HumanoidModel.ArmPose.ITEM);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    private void setHandPose(T entity, HumanoidModel.ArmPose pose) {
        if (entity.getMainArm() == HumanoidArm.RIGHT) {
            model.rightArmPose = pose;
        } else {
            model.leftArmPose = pose;
        }
    }
}
