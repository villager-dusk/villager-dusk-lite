
package net.mcreator.buxin.ai;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.BlackShieldHerobrineEntity;
import net.mcreator.buxin.entity.father.BattleVillagerEntity;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Comparator;
import java.util.List;

public class BlackShieldBashUseGoal extends Goal {
	private final Mob user;
	private LivingEntity targetLivingEntity;
	private int cooldown = reducedTickDelay(50);

	public BlackShieldBashUseGoal(Mob user){
		this.user = user;
	}

	public boolean canUse() {
		LivingEntity target = this.user.getTarget();
		if (!(target instanceof LivingEntity))
			return false;

		if (--this.cooldown > 0)
			return false;
		var patch = AnimationPlayer.getlivingEntityPatch(user);
		WeaponCategory offcategory = null;
		if (patch != null) {
			offcategory = patch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory();
		}
		if(offcategory != CapabilityItem.WeaponCategories.SHIELD || !(user.getOffhandItem().getItem() instanceof ShieldItem)){
			return false;
		}

		if(user.getMainHandItem().getItem() == Items.BOW){
			return false;
		}

		return !this.user.getMainHandItem().isEmpty();
	}

	public void start() {
		this.targetLivingEntity = this.user.getTarget();
		if(targetLivingEntity == null){
			return;
		}

		if(user.level() instanceof ServerLevel) {
			{
				Entity entityiterator = user.getTarget();
				if (entityiterator instanceof LivingEntity && !(user == entityiterator) && !(entityiterator instanceof BattleVillagerEntity) && !entityiterator.getPersistentData().getBoolean("isfuck") && entityiterator instanceof LivingEntity) {
					if(Math.random() > 0.7) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 5, 1, false, false));
						user.lookAt(EntityAnchorArgument.Anchor.EYES, entityiterator.getEyePosition());
						Method_114514.play_sound(entityiterator, "epicfight:sfx.neutralize_bosses");
						Method_114514.entity_use_command(entityiterator, "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
						if (entityiterator instanceof Player) {
							Method_114514.entity_use_command(entityiterator, "/impactful @s shake 50 6 6");
							if (AnimationPlayer.getlivingEntityPatch(entityiterator) instanceof PlayerPatch<?> playerPatch) {
								playerPatch.setStamina(playerPatch.getStamina() - playerPatch.getMaxStamina() * 0.2f);
							}
						}
						Method_114514.entity_use_command(user, "/indestructible @s play \"wom:biped/skill/gezets_auto_1\" 0 1");
						BuxinMod.queueServerWork(3, () -> {
							if (user.level() instanceof ServerLevel) {
								entityiterator.hurt(user.damageSources().mobAttack(user), 9f);
							}
							BuxinMod.queueServerWork(3, () -> {
								Method_114514.entity_use_command(entityiterator, "/indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 1");
							});
						});
					} else {
						if(SystemMethod.isWindows()) {
						}
						Method_114514.play_sound(user,"epicfight:sfx.laser_blast",1.5f,1f);
						Method_114514.entity_use_command(user, "/indestructible @s play \"wom:biped/skill/gezets_auto_1\" 0 1");
						BuxinMod.queueServerWork(4, () -> {
							Method_114514.play_sound(user.level(),entityiterator.position(),"entity.generic.explode",2.5f,1f);
							if (entityiterator instanceof Player) {
								Method_114514.entity_use_command(entityiterator, "/impactful @s shake 50 4 4");
							}
							if (user.level() instanceof ServerLevel) {
								if(!(AnimationPlayer.getAnimation(entityiterator) instanceof LongHitAnimation)){
									AnimationPlayer.playAnimation(entityiterator, Animations.BIPED_HIT_LONG);
								}
								entityiterator.hurt(user.damageSources().mobAttack(user), 20f + ((LivingEntity) entityiterator).getMaxHealth() * 0.1f);
							}
						});
					}
				}
			}
		}
		this.cooldown = reducedTickDelay(60);
	}

	public void stop() {
		this.targetLivingEntity = null;
		this.user.getNavigation().stop();
	}
}
