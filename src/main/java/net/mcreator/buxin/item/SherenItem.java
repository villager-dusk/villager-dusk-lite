
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.item.renderer.SherenItemRenderer;
import net.mcreator.buxin.procedures.Niubi5DangWuPinZaiShouShangMeiKeFaShengProcedure;
import net.mcreator.buxin.procedures.SherenDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class SherenItem extends CustomEnchantableItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";

    public SherenItem() {
        super(new CustomEnchantableItem.Properties().fireResistant().rarity(Rarity.COMMON));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new SherenItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    protected void interpretFirstPersonInstructions(List<String> tokens, SherenItemRenderer renderer) {
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

    private <T extends GeoItem> PlayState idlePredicate(AnimationState<T> event) {
        if (this.animationprocedure.equals("empty")) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.hysrsh.new"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <T extends GeoItem> PlayState procedurePredicate(AnimationState<T> event) {
        if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
            }
        }
        return PlayState.CONTINUE;
    }

    public void setupAnimationState(SherenItemRenderer renderer, ItemStack stack, PoseStack matrixStack, float aimProgress) {
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
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 19d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<net.minecraft.network.chat.Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        //list.add(Component.literal("\u86C7\u5203(\u91CA\u653E)"));
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
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            Niubi5DangWuPinZaiShouShangMeiKeFaShengProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ());
    }
}
