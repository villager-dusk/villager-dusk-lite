
package net.mcreator.buxin.ai;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Math114514;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;

import java.util.Random;

public class ObsidianUseGoal extends Goal {
	private final Mob user;
	private LivingEntity targetLivingEntity;
	private boolean offhand;
	private boolean mainhand;
	private ItemStack mainhanditem;
	private ItemStack offhanditem;
	private ItemStack defaultmainhanditem;
	private ItemStack defaultoffhanditem;
	private int cooldown = reducedTickDelay(50);

	public ObsidianUseGoal(Mob user, boolean offhand, boolean mainhand, ItemStack mainhanditem, ItemStack offhanditem, ItemStack defaultmainhanditem, ItemStack defaultoffhanditem) {
		this.user = user;
		this.offhand = offhand;
		this.mainhand = mainhand;
		this.mainhanditem = mainhanditem;
		this.offhanditem = offhanditem;
		this.defaultmainhanditem = defaultmainhanditem;
		this.defaultoffhanditem = defaultoffhanditem;
	}

	public boolean canUse() {
		LivingEntity target = this.user.getTarget();
		if (!(target instanceof LivingEntity))
			return false;

		if (--this.cooldown > 0)
			return false;

		if (Math114514.entity1_distance_to_entity2_xyz(user, target) > 3.5) {
			return false;
		}

		return !this.user.getMainHandItem().isEmpty();
	}

	public void start() {
		this.targetLivingEntity = this.user.getTarget();
		Random random = new Random();
		int val = random.nextInt(3);
		if (val == 0) {
			AnimationPlayer.playAnimation(user, Animations.FIST_AUTO1);
		} else if (val == 1) {
			AnimationPlayer.playAnimation(user, Animations.FIST_AUTO2);
		} else {
			AnimationPlayer.playAnimation(user, Animations.FIST_AUTO3);
		}
		if (mainhand) {
			user.setItemSlot(EquipmentSlot.MAINHAND, mainhanditem);
		}
		if (offhand) {
			user.setItemSlot(EquipmentSlot.OFFHAND, offhanditem);
		}
		if (!user.level().isClientSide()) {
			SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair"));
			if (soundEvent != null) {
				user.level().playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
			}
		}
		if (targetLivingEntity != user) {
			targetLivingEntity.hurt(targetLivingEntity.damageSources().mobAttack(user), 4f + new Random().nextFloat(3f));
		}
		BuxinMod.queueServerWork(15, () -> {
			if (mainhand) {
				user.setItemSlot(EquipmentSlot.MAINHAND, defaultmainhanditem);
			}
			if (offhand) {
				user.setItemSlot(EquipmentSlot.OFFHAND, defaultoffhanditem);
			}
		});
		this.cooldown = reducedTickDelay(45);
	}

	public void stop() {
		this.targetLivingEntity = null;
		this.user.getNavigation().stop();
	}
}
