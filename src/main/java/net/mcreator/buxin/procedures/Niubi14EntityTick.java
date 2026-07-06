package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModGameRules;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Niubi14EntityTick {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		double attack;
		entity.getPersistentData().putDouble("timer", (entity.getPersistentData().getDouble("timer") + 1));
		if (entity.getPersistentData().getDouble("timer") == 80) {
			entity.getPersistentData().putDouble("timer", 0);
			attack = Mth.nextInt(RandomSource.create(), 1, 6);
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.65) {
				if (!entity.getPersistentData().getBoolean("wood_door")) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(BuxinModItems.WOOR_DOOR.get());
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					entity.getPersistentData().putBoolean("wood_door", true);
					BuxinMod.queueServerWork(350, () -> {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get());
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
					});
				}
			} else {
				if (Math.random() > 0.8999) {
					if (!entity.getPersistentData().getBoolean("eatGoldenApple") && entity instanceof LivingEntity) {
						ItemStack oldItem = ((LivingEntity) entity).getMainHandItem();
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(Items.GOLDEN_APPLE);
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						entity.getPersistentData().putBoolean("eatGoldenApple", true);
						Method_114514.entity_use_command(entity, "/effect give @s epicfight:stun_immunity 12 5");
						if (world instanceof Level) {
							Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "entity.generic.eat");
						}
						if (!entity.level().isClientSide() && entity.getServer() != null) {
							Method_114514.entity_use_command(entity, "/execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10");
						}
						for (int i = 0; i < 6; i++) {
							BuxinMod.queueServerWork(4 * i, () -> {
								Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "entity.generic.eat");
								if (!entity.level().isClientSide() && entity.getServer() != null) {
									Method_114514.entity_use_command(entity, "/execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10");
								}
							});
						}

						BuxinMod.queueServerWork(28, () -> {
							if (!entity.level().isClientSide()) {
								entity.level().playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F);
							} else {
								entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
							}
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 2, false, true));
						});

						BuxinMod.queueServerWork(31, () -> {
							if (!entity.isRemoved()) {
								entity.setItemSlot(EquipmentSlot.MAINHAND, oldItem);
							}
							BuxinMod.queueServerWork(20, () -> {
								entity.getPersistentData().putBoolean("eatGoldenApple", false);
							});
						});
					}
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
				if (!entity.getPersistentData().getBoolean("zszr")) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_15.get());
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					entity.getPersistentData().putBoolean("zszr", true);
				}
			}

			if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DOMOBSUSEENDERPEARL)) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
							.toList();
					for (Entity entityiterator : _entfound) {
						if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
							if (attack == 1) {
								{
									if (entity instanceof LivingEntity entity1 && entityiterator != null) {
										if(Math.random() > 0.2){
											Method_114514.send_message_to_all_over_the_world(entity.level(), Component.translatable("chat.buxin.woopie").getString());
											float yaw = entity1.yRotO;
											float pitch = entity1.xRotO;
											double x_speed = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
											double y_speed = -Math.sin(Math.toRadians(pitch));
											double z_speed = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
											double speedMultiplier = 4;
											double highMultiplier = 2.5;
											x_speed *= speedMultiplier;
											y_speed *= highMultiplier;
											z_speed *= speedMultiplier;
											Level level = entity1.level();
											AnimationPlayer.playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
											double finalX_speed = x_speed;
											double finalY_speed = y_speed;
											double finalZ_speed = z_speed;
											entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.WOOPIE.get().getDefaultInstance());
											if (entity != entityiterator)
												entityiterator.hurt(level.damageSources().generic(), 7);
											BuxinMod.queueServerWork(5, () -> {
												if (level instanceof ServerLevel) {
													Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "epicfight:sfx.entity_move");
													entity1.setDeltaMovement(new Vec3(finalX_speed, finalY_speed, finalZ_speed));
												}
												if (VFXParticleConfig.VFXParticleConfig.get() && BuxinMod.isWindows()) {
													VFXTool.addVFXParticle(entity1.position(), BuxinMod.MODID, "woopie", entity1.level());
												}
												BuxinMod.queueServerWork(55, () -> {
													entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.NIUBI_2DANSHOU.get().getDefaultInstance());
												});
											});
										} else {
											Method_114514.execute_event(entity, entityiterator, BuxinAnimations.GREATSWORD_DIE, BuxinAnimations.PUTENTITYTODIEBEENATTACK, 0.2F, 49, 75, true, false, 0.07, 0.05, 0.07, 0.25, false);
										}
									}
								}
							}
						}
					}
				}
			}

		}
	}
}