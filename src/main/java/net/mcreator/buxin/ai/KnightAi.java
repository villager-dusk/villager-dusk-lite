
package net.mcreator.buxin.ai;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.mcreator.buxin.procedures.CunMinWeiBingShiTiChwuShiShengChengShiProcedure;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class KnightAi {
	public static void shootArrowAi(Entity entity){
		if(entity instanceof Mob && ((Mob) entity).getTarget() != null && Math.random() > 0.5 && AnimationPlayer.entity_getAnimation(entity) != null && !(AnimationPlayer.entity_getAnimation(entity) instanceof LongHitAnimation) && !(AnimationPlayer.entity_getAnimation(entity) instanceof HitAnimation) && ((Mob) entity).getTarget() != null){
			if(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6 || entity.isPassenger()){
				if (((Mob) entity).getMainHandItem().getItem() != Items.BOW) {
					entity.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
				}
				BuxinMod.queueServerWork(55,() -> {
					if(entity instanceof Mob mob && ((Mob) entity).getTarget() != null && !(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6) && !(mob.isPassenger())) {
						if (entity instanceof CunMinWeiBingEntity || entity instanceof PurpleVillagerCavalryEntity) {
							entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.QIBINJIAN.get().getDefaultInstance());
						} else if (entity instanceof GreenVillagerCavalryEntity || entity instanceof RedVillagerEntity) {
							entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.PALADINSWORD.get().getDefaultInstance());
						} else if (entity instanceof VillagerScoutEntity) {
							entity.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
						} else if (entity instanceof Niubi13Entity entity1) {
							if (Math.random() > 0.5) {
								entity1.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.WOOPIE.get().getDefaultInstance());
							} else {
								entity1.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
							}
						}
						mob.getMainHandItem().enchant(Enchantments.SHARPNESS, 1);
					}
				});
			}
		}
	}
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide() || entity.tickCount % 2 != 0)
			return;
		if(entity instanceof LivingEntity e){
			if(e.getMainHandItem().getItem() == Items.GOLDEN_APPLE && AnimationPlayer.getAnimation(entity) != Animations.BIPED_EAT){
				AnimationPlayer.playAnimation(entity,Animations.BIPED_EAT);
			}
		}
		if(entity.tickCount % 5 == 0 && entity instanceof Mob mob && mob.getTarget() != null){
			if(((Mob) entity).getTarget() != null && !(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) < 2.5) && !(mob.isPassenger())) {
				if (entity instanceof CunMinWeiBingEntity || entity instanceof PurpleVillagerCavalryEntity) {
					entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.QIBINJIAN.get().getDefaultInstance());
				} else if (entity instanceof GreenVillagerCavalryEntity || entity instanceof RedVillagerEntity) {
					entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.PALADINSWORD.get().getDefaultInstance());
				} else if (entity instanceof VillagerScoutEntity) {
					entity.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
				} else if (entity instanceof Niubi13Entity entity1) {
					if (Math.random() > 0.5) {
						entity1.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.WOOPIE.get().getDefaultInstance());
					} else {
						entity1.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
					}
				}
				mob.getMainHandItem().enchant(Enchantments.SHARPNESS, 1);
			}
			if(mob.isPassenger()){
				if(!(mob.getVehicle() instanceof PolarBear)) {
					mob.stopRiding();
				} else {
					if(((PolarBear) mob.getVehicle()).getTarget() != ((Mob) entity).getTarget()){
						((PolarBear) mob.getVehicle()).setTarget(((Mob) entity).getTarget());
					}
				}
			}
		}
		try {
            AtomicReference<Double> dx = new AtomicReference<>(entity.getX());
			AtomicReference<Double> dy = new AtomicReference<>(entity.getY());
			AtomicReference<Double> dz = new AtomicReference<>(entity.getZ());
			if (entity instanceof Mob m2) {
				if (m2.getTarget() instanceof VillagerScoutEntity || m2.getTarget() instanceof CunMinWeiBingEntity || m2.getTarget() instanceof GreenVillagerCavalryEntity || m2.getTarget() instanceof PurpleVillagerCavalryEntity || m2.getTarget() instanceof RedVillagerEntity) {
					m2.setTarget(null);
				}
			}
			shootArrowAi(entity);
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= 6) {
			} else {
				if (!entity.getPersistentData().getBoolean("eatGoldenApple") && entity instanceof Mob mob && mob.getHealth() > 3) {
					ItemStack oldItem = ((LivingEntity) entity).getMainHandItem();
                    ItemStack _setstack = new ItemStack(Items.GOLDEN_APPLE);
                    _setstack.setCount(1);
                    mob.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                    entity.getPersistentData().putBoolean("eatGoldenApple", true);
					AnimationPlayer.playAnimation(entity, Animations.BIPED_EAT);
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
						if (entity instanceof LivingEntity _entity && !_entity.level.isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 3, false, true));
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
            entity.getPersistentData().putDouble("timer", (entity.getPersistentData().getDouble("timer") + 1));
            if (entity.getPersistentData().getDouble("timer") == 35) {
                entity.getPersistentData().putDouble("timer", 0);
                if (Math.random() < 0.5 && !(entity.isPassenger())) {
                    {
                        CunMinWeiBingShiTiChwuShiShengChengShiProcedure event = new CunMinWeiBingShiTiChwuShiShengChengShiProcedure();
                        if (entity instanceof LivingEntity livingEntity) {
                            livingEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                                if (EntityPatch instanceof LivingEntityPatch<?>) {
                                    final Vec3 _center = new Vec3(x, y, z);
                                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                            .toList();
                                    for (Entity entityiterator : _entfound) {
                                        if (((!(entity.getPersistentData().getBoolean("isfuck"))) && !(entityiterator instanceof ItemEntity) && (!(entityiterator instanceof Projectile)) && ((!(entity == entityiterator))))) {
                                            event.CanPutToDeath = true;
                                        }
                                        if (entityiterator instanceof LivingEntity && entityiterator != entity && event.CanPutToDeath && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator && entity.isAlive() && ((LivingEntity) entityiterator).getHealth() < ((LivingEntity) entityiterator).getMaxHealth() * 0.7 || entityiterator instanceof LivingEntity && entityiterator != entity && event.CanPutToDeath && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator && entity.isAlive() && AnimationPlayer.entity_getAnimation(entityiterator) instanceof LongHitAnimation) {
                                            if(SystemMethod.isWindows()) {
                                                VFXTool.addVFXParticle(new Vec3(entityiterator.getX(),entityiterator.getY() + 2.2,entityiterator.getZ()),"buxin","danger",entity.level());
                                                Method_114514.play_sound(entityiterator,"buxin:danger");
                                            }
                                            BuxinMod.queueServerWork(10,() -> {
                                                entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ())));
                                                entityiterator.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0, entity.getZ()));
                                                Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                entity.teleportTo(((LivingEntity) entityiterator).getX() + viewVec.x() * 1.51, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.51);
                                                entity.getPersistentData().putBoolean("isfuck", true);
                                                event.CanPutToDeath = false;
                                                BuxinMod.queueServerWork(135, () -> {
                                                    entity.getPersistentData().putBoolean("isfuck", false);
                                                });
												BuxinMod.queueServerWork(32, () -> {
													if (world instanceof ServerLevel _level)
														_level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), entityiterator.getX(), entityiterator.getY() + 1.3, entityiterator.getZ(), 1, 0.25, 0.3, 0.25, 0.2);
													entityiterator.hurt(entity.damageSources().mobAttack((LivingEntity) entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.2) + 4);
													Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
													if (world instanceof ServerLevel _level)
														_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), entityiterator.getX(), (entityiterator.getY() + 1.3), entityiterator.getZ(), (55), 0.07, 0.05, 0.07, 0.25);
												});
												BuxinMod.queueServerWork(14, () -> {
													entityiterator.hurt(entity.damageSources().mobAttack((LivingEntity) entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.05));
													if (!entity.level().isClientSide() && entity.getServer() != null) {
														Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.blade");
														if (world instanceof ServerLevel _level)
															_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), dx.get(), (dy.get() + 1.3), dz.get(), (10), 0.03, 0.02, 0.03, 0.15);
													}
												});
                                                if (entity.level.getServer() != null && entityiterator.level.getServer() != null) {
                                                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                        _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                    ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                    ((LivingEntity) entityiterator).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45, 50, false, false));
                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) MobEffects.MOVEMENT_SLOWDOWN, 130, 0, false, false));
                                                    Method_114514.play_sound(entityiterator, "buxin:bitch");
                                                    Method_114514.entity_use_command(entityiterator, "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                }
                                                var LivingEntityPatch = AnimationPlayer.getlivingEntityPatch(entity);
                                                if (LivingEntityPatch != null) {
                                                    WeaponCategory Maincategory = LivingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
                                                    WeaponCategory Offcategory = LivingEntityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory();
                                                    BuxinMod.queueServerWork(2, () -> {
                                                        if (Maincategory == Offcategory) {
                                                            AnimationPlayer.playAnimation(entity, BuxinAnimations.DUAL_SWORD_DIE);
                                                        } else {
                                                            AnimationPlayer.playAnimation(entity, BuxinAnimations.SWORD_DIE);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception e){
			System.err.println(e);
		}
	}
}
