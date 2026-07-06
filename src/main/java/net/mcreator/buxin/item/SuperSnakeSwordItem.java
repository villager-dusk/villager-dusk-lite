package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.item.renderer.SuperSnakeSwordItemRenderer;
import net.mcreator.buxin.procedures.SherenDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
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
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class SuperSnakeSwordItem extends CustomEnchantableItem implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public String animationprocedure = "empty";
	public static ItemDisplayContext transformType;

	public SuperSnakeSwordItem() {
		super(new CustomEnchantableItem.Properties().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new SuperSnakeSwordItemRenderer();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	public void getTransformType(ItemDisplayContext type) {
		this.transformType = type;
	}

	protected void interpretFirstPersonInstructions(List<String> tokens, SuperSnakeSwordItemRenderer renderer) {
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

	private PlayState idlePredicate(AnimationState<SuperSnakeSwordItem> event) {
		if (this.transformType != null) {
			if (this.animationprocedure.equals("empty")) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("super_snake_sword.animation.idle"));
				return PlayState.CONTINUE;
			}
		}
		return PlayState.STOP;
	}

	private PlayState procedurePredicate(AnimationState<SuperSnakeSwordItem> event) {
		if (this.transformType != null) {
			if (!this.animationprocedure.equals("empty") && event.getController().hasAnimationFinished()) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
				if (event.getController().hasAnimationFinished()) {
					this.animationprocedure = "empty";
					event.getController().forceAnimationReset();
				}
			}
		}
		return PlayState.CONTINUE;
	}

	public void setupAnimationState(SuperSnakeSwordItemRenderer renderer, ItemStack stack, PoseStack matrixStack, float aimProgress) {
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
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 43d, AttributeModifier.Operation.ADDITION));
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

		SherenDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure.execute(world, x, y, z, entity, itemstack);
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected) {
			entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entitypatch -> {
				if(entitypatch instanceof LivingEntityPatch<?> livingEntityPatch){
					int value = 2;
					float min = 0.5F;
					float max = 2.9F;
					float rz;
					OpenMatrix4f transformMatrixx;
					byte nL;
					for(int i = 0; i < value; ++i) {
						rz = min + (max - min) * (new Random()).nextFloat();
						transformMatrixx = ((LivingEntityPatch<?>) entitypatch).getArmature().getBindedTransformFor(((LivingEntityPatch<?>) entitypatch).getAnimator().getPose(0.0F), Armatures.BIPED.toolR);
						transformMatrixx.translate(new Vec3f(0.0F, 0.0F, -rz));
						OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0031415927F, 0.0F)), transformMatrixx, transformMatrixx);
						nL = 3;
						for (int j = 0; j < nL; ++j) {
							if(Math.random() > 0.75) {
								entitypatch.getOriginal().level().addParticle(BuxinModParticleTypes.NIUBI_22.get(), (double) transformMatrixx.m30 + entitypatch.getOriginal().getX(), (double) transformMatrixx.m31 + entitypatch.getOriginal().getY(), (double) transformMatrixx.m32 + entitypatch.getOriginal().getZ(), 0.0, -0.009999999776482582, 0.0);
							}
						}
						for (int j = 0; j < 2; ++j) {
							entitypatch.getOriginal().level().addParticle(ParticleTypes.PORTAL, (double) transformMatrixx.m30 + entitypatch.getOriginal().getX(), (double) transformMatrixx.m31 + entitypatch.getOriginal().getY(), (double) transformMatrixx.m32 + entitypatch.getOriginal().getZ(), 0.0, -0.009999999776482582, 0.0);
						}
					}
				}
			});
		}
	}
}