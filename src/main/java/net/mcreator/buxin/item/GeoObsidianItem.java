package net.mcreator.buxin.item;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Item;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

import net.mcreator.buxin.item.renderer.GeoObsidianItemRenderer;

import java.util.function.Consumer;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;

public class GeoObsidianItem extends CustomEnchantableItem implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public String animationprocedure = "empty";
	public static ItemDisplayContext transformType;

	public GeoObsidianItem() {
		super(new CustomEnchantableItem.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new GeoObsidianItemRenderer();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	public void getTransformType(ItemDisplayContext type) {
		this.transformType = type;
	}

	protected void interpretFirstPersonInstructions(List<String> tokens, GeoObsidianItemRenderer renderer) {
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

	private PlayState idlePredicate(AnimationState<GeoObsidianItem> event) {
		if (this.transformType != null) {
			if (this.animationprocedure.equals("empty")) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.8.new"));
				return PlayState.CONTINUE;
			}
		}
		return PlayState.STOP;
	}

	private PlayState procedurePredicate(AnimationState<GeoObsidianItem> event) {
		if (this.transformType != null) {
			if (!this.animationprocedure.equals("empty")) {
				if (event.getController().hasAnimationFinished()) {
					event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
					this.animationprocedure = "empty";
				}
			}
		}
		return PlayState.CONTINUE;
	}

	public void setupAnimationState(GeoObsidianItemRenderer renderer, ItemStack stack, PoseStack matrixStack, float aimProgress) {
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
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 4d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
	}
}