
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.buxin.entity.dragon.baby.BabyEnderDragonEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Random;

import static net.mcreator.buxin.entity.dragon.baby.BabyEnderDragonEntity.posBehind3D;

public class Niubi8Item extends CustomEnchantableItem {
	public Niubi8Item() {
		super(new CustomEnchantableItem.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
	@Override
	public boolean hasCraftingRemainingItem() {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		return new ItemStack(this);
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 9999;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 12d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
	}

	public static void spawnBabyEnderDragon(ItemStack itemstack, Player player) {
		if (player.level() instanceof ServerLevel serverlevel) {
			BabyEnderDragonEntity babyEnderDragonEntity = new BabyEnderDragonEntity((EntityType<BabyEnderDragonEntity>) BuxinModEntities.BABY_ENDER_DRAGON.get(), serverlevel);
			Vec3 posBehind3D = posBehind3D(player, 1.0D, 2.0D, 1.0D);
			babyEnderDragonEntity.moveTo(
					posBehind3D.x(),
					posBehind3D.y(),
					posBehind3D.z()
			);
			babyEnderDragonEntity.setFollowTarget(player);
			babyEnderDragonEntity.setFollowTargetUUID(player.getUUID());
			babyEnderDragonEntity.finalizeSpawn(serverlevel, serverlevel.getCurrentDifficultyAt(player.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			serverlevel.addFreshEntity(babyEnderDragonEntity);
			if (itemstack.getTag() == null) {
				itemstack.setTag(new CompoundTag());
			}
			itemstack.getTag().putUUID("DragonSummon", babyEnderDragonEntity.getUUID());
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		
		return retval;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected && entity instanceof LivingEntity livingEntity) {
			livingEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entitypatch -> {
				if(entitypatch instanceof LivingEntityPatch<?> livingEntityPatch){
					DynamicAnimation animation = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation();
					int value = 2;
					float min = 0.5F;
					float max = 2.4F;
					float rz;
					OpenMatrix4f transformMatrixx;
					byte nL;
					for(int i = 0; i < value; ++i) {
						rz = min + (max - min) * (new Random()).nextFloat();
						transformMatrixx = ((LivingEntityPatch<?>) entitypatch).getArmature().getBindedTransformFor(((LivingEntityPatch<?>) entitypatch).getAnimator().getPose(0.0F), Armatures.BIPED.toolR);
						transformMatrixx.translate(new Vec3f(0.0F, 0.0F, -rz));
						OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(((LivingEntity) entitypatch.getOriginal()).getYRot() + 180.0F)), new Vec3f(0.0F, 1.0031415927F, 0.0F)), transformMatrixx, transformMatrixx);
						nL = 2;
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
