
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.init.BuxinModTabs;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.item.renderer.WhatItemRenderer;
import net.mcreator.buxin.procedures.DarkGeckWeaponDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class WhatItem extends CustomEnchantableItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    public static BakedModel transformType;

    public WhatItem() {
        super(new CustomEnchantableItem.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new WhatItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    public void getTransformType(BakedModel type) {
        this.transformType = type;
    }

    protected void interpretFirstPersonInstructions(List<String> tokens, WhatItemRenderer renderer) {
        String firstTok = tokens.get(0);
        if (tokens.size() < 2)
            return;
        String boneName = tokens.get(1);
        if (firstTok.equals("set_hidden")) {
            boolean hidden = Boolean.valueOf(tokens.get(2));
            renderer.hideBone(boneName, hidden);
        } else if (firstTok.equals("move")) {
            float x = Float.valueOf(tokens.get(2));
            float y = Float.valueOf(tokens.get(3));
            float z = Float.valueOf(tokens.get(4));
            renderer.setBonePosition(boneName, x, y, z);
        } else if (firstTok.equals("rotate")) {
            float x = Float.valueOf(tokens.get(2));
            float y = Float.valueOf(tokens.get(3));
            float z = Float.valueOf(tokens.get(4));
            renderer.setBoneRotation(boneName, x, y, z);
        } else if (firstTok.equals("suppress_mod")) {
            renderer.suppressModification(boneName);
        } else if (firstTok.equals("allow_mod")) {
            renderer.allowModification(boneName);
        }
    }

    private <P extends Item & GeoItem> PlayState idlePredicate(AnimationState<?> event) {
        if (this.transformType != null ? true : false) {
            if (this.animationprocedure.equals("empty")) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <P extends Item & GeoItem> PlayState procedurePredicate(AnimationState<?> event) {
        if (this.transformType != null ? true : false) {
            if (!(this.animationprocedure.equals("empty"))) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
                if (false) {
                    this.animationprocedure = "empty";
                    event.getController().forceAnimationReset();
                }
            }
        }
        return PlayState.CONTINUE;
    }

    public void setupAnimationState(WhatItemRenderer renderer, ItemStack stack, PoseStack matrixStack, float aimProgress) {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "idle", 0, null));
        data.add(new AnimationController<>(this, "procedure", 0, null));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 15d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = ar.getObject();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        DarkGeckWeaponDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure.execute();
        return ar;
    }
}
