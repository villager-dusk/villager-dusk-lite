
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.color.BuxinWeaponColor;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.item.renderer.RainbowSnakeSwordItemRenderer;
import net.mcreator.buxin.procedures.RainbowSnakeSwordDangWuPinZaiShouShangMeiKeFaShengProcedure;
import net.mcreator.buxin.procedures.SherenDangShiTiHuiDongWuPinShiProcedure;
import net.mcreator.buxin.procedures.SherenDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class RainbowSnakeSwordItem extends CustomEnchantableItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    public static ItemDisplayContext transformType;

    public RainbowSnakeSwordItem() {
        super(new CustomEnchantableItem.Properties().durability(100).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new RainbowSnakeSwordItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    public void getTransformType(ItemDisplayContext type) {
        this.transformType = type;
    }

    protected void interpretFirstPersonInstructions(List<String> tokens, RainbowSnakeSwordItemRenderer renderer) {
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
        if (this.transformType != null) {
            if (this.animationprocedure.equals("empty")) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.hysrsh.new"));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <P extends Item & GeoItem> PlayState procedurePredicate(AnimationState<?> event) {
        if (this.transformType != null) {
            if (!this.animationprocedure.equals("empty") && event.getController().hasAnimationFinished()) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.hysrsh.new"));
                if (event.getController().hasAnimationFinished()) {
                    this.animationprocedure = "empty";
                    event.getController().forceAnimationReset();
                }
            }
        }
        return PlayState.CONTINUE;
    }

    public void setupAnimationState(RainbowSnakeSwordItemRenderer renderer, ItemStack stack, PoseStack matrixStack, float aimProgress) {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "procedureController", 0, null));
        controllers.add(new AnimationController<>(this, "idleController", 0, null));
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
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 22d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = ar.getObject();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        SherenDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure.execute(world, x, y, z, entity, itemstack);
        return ar;
    }

    @Override
    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
        boolean retval = super.onEntitySwing(itemstack, entity);
        SherenDangShiTiHuiDongWuPinShiProcedure.execute(entity.level, entity.getX(), entity.getY(), entity.getZ());
        return retval;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            RainbowSnakeSwordDangWuPinZaiShouShangMeiKeFaShengProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ());
    }
}
