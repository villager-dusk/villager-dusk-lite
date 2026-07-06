
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.item.renderer.Wp2ItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
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

public class Wp2Item extends CustomEnchantableItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    public static ItemDisplayContext transformType;

    public Wp2Item() {
        super(new CustomEnchantableItem.Properties().durability(100).fireResistant().rarity(Rarity.COMMON));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new Wp2ItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    public void getTransformType(ItemDisplayContext type) {
        this.transformType = type;
    }

    protected void interpretFirstPersonInstructions(List<String> tokens, Wp2ItemRenderer renderer) {
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
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.obs_stick_2.new"));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <P extends Item & GeoItem> PlayState procedurePredicate(AnimationState<?> event) {
        if (this.transformType != null) {
            if (!this.animationprocedure.equals("empty") && event.getController().hasAnimationFinished()) {
                event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold(this.animationprocedure));
                if (event.getController().hasAnimationFinished()) {
                    this.animationprocedure = "empty";
                }
            }
        }
        return PlayState.CONTINUE;
    }

    public void setupAnimationState(Wp2ItemRenderer renderer, ItemStack stack, PoseStack matrixStack, float aimProgress) {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "procedureController", 0, null));
        controllers.add(new AnimationController(this, "idleController", 0, null));
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
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 0d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("long obs"));
    }
}
