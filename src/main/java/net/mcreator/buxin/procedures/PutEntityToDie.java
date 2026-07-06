package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.ChangdaohimEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.gameasset.PutEntityToDieAnimation;
import net.mcreator.buxin.gameasset.SpecialAnimation;
import net.mcreator.buxin.init.BuxinModGameRules;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
//import net.mcreator.buxin.utils.BlackShieldExplosionParticleEmitterInfo;
import net.mcreator.buxin.special_key.SpecialKeyPressEvent;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class PutEntityToDie {

    public static boolean cjl;

	//踢击/处决
	public static void execute(LevelAccessor world, Player entity) {

    cjl = false;

		if (entity == null || world.isClientSide())
			return;
		entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
			if (EntityPatch instanceof LivingEntityPatch<?> livingEntityPatch) {
				DynamicAnimation animation = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation();
				AtomicReference<Double> x = new AtomicReference<>(entity.getX());
				AtomicReference<Double> y = new AtomicReference<>(entity.getY());
				AtomicReference<Double> z = new AtomicReference<>(entity.getZ());
                if (!(animation instanceof LongHitAnimation) && !(animation instanceof HitAnimation) && !(animation instanceof PutEntityToDieAnimation) && !(animation instanceof SpecialAnimation)) {
                    {
                        //处决
                        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(Entitypatch -> {
                            if (Entitypatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                                WeaponCategory Maincategory = LivingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
                                WeaponCategory Offcategory = LivingEntityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory();
                                final Vec3 _center = new Vec3(x.get(), y.get(), z.get());
                                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                for (Entity entityiterator : _entfound) {
                                    entityiterator.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(e_Entitypatch -> {
                                        if (e_Entitypatch instanceof LivingEntityPatch<?> e_LivingEntityPatch && entity.distanceTo(entityiterator) > 0.45) {
                                            //entityiterator.lookAt(EntityAnchorArgument.Anchor.EYES,entity.getEyePosition());
                                            DynamicAnimation e_animation = e_LivingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                                            boolean CanPutToDeath = false;
                                            boolean littlekill = false;
                                            if (((!(entity.getPersistentData().getBoolean("isfuck"))) && !(entityiterator instanceof ItemEntity) && (!(entityiterator instanceof Projectile)) && ((!(entity == entityiterator)) && (((LivingEntity) entityiterator).getHealth() < ((LivingEntity) entityiterator).getMaxHealth() * 0.3)))) {
                                                CanPutToDeath = true;
                                            }
                                            if (entity.isCreative() && !(entityiterator instanceof ItemEntity) && (!(entityiterator instanceof Projectile)) && (!(entity.getPersistentData().getBoolean("isfuck"))) && ((!(entity == entityiterator)))) {
                                                CanPutToDeath = true;
                                            }
                                            //-------------------------------------------------------------//
                                            if (((!(entity.getPersistentData().getBoolean("isfuck"))) && !(entityiterator instanceof ItemEntity) && (!(entityiterator instanceof Projectile)) && ((!(entity == entityiterator)) && e_animation instanceof LongHitAnimation))) {
                                                littlekill = true;
                                            }



                                            if (entityiterator instanceof LivingEntity && entityiterator != entity && !(entityiterator.getPersistentData().getBoolean("isfuck"))) {



                                                //低于30%
                                                if (CanPutToDeath) {
                                                    if (Maincategory != CapabilityItem.WeaponCategories.FIST && entityiterator.isAlive()) {
                                                        entity.getPersistentData().putBoolean("isfuck", true);
                                                        entity.getPersistentData().putBoolean("YH", true);

                                                        cjl = true;

                                                        //entity.sendSystemMessage(Component.literal("§e处决进入冷却"));
                                                        BuxinMod.queueServerWork(20, () -> {
                                                            //entity.sendSystemMessage(Component.literal("§e5....."));
                                                            BuxinMod.queueServerWork(20, () -> {
                                                                //entity.sendSystemMessage(Component.literal("§e4..."));
                                                                BuxinMod.queueServerWork(20, () -> {
                                                                    //entity.sendSystemMessage(Component.literal("§e3.."));
                                                                    BuxinMod.queueServerWork(20, () -> {
                                                                        //entity.sendSystemMessage(Component.literal("§e2."));
                                                                        BuxinMod.queueServerWork(20, () -> {
                                                                            //entity.sendSystemMessage(Component.literal("§e1"));
                                                                        });
                                                                    });
                                                                });
                                                            });
                                                        });
                                                        BuxinMod.queueServerWork(135, () -> {
                                                            //entity.sendSystemMessage(Component.literal("§e处决冷却结束！可再次进行处决！"));
                                                            entity.getPersistentData().putBoolean("isfuck", false);
                                                            entity.getPersistentData().putBoolean("YH", false);
                                                        });
                                                       entityiterator.teleportTo(entity.getX() + 0.45, entity.getY(), entity.getZ() + 0.45);
                                                        entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entityiterator.getX(), entityiterator.getY() + 1.0, entityiterator.getZ()));
                                                        entityiterator.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0, entity.getZ()));
                                                        if ((((Maincategory == CapabilityItem.WeaponCategories.SWORD) && entityiterator.isAlive()) || (Maincategory == CapabilityItem.WeaponCategories.DAGGER && Offcategory == CapabilityItem.WeaponCategories.DAGGER)) && entityiterator.isAlive()) {
                                                            if (Offcategory == CapabilityItem.WeaponCategories.SWORD || Offcategory == CapabilityItem.WeaponCategories.DAGGER) {
                                                                BuxinMod.queueServerWork(32, () -> {
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 15));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                        Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
                                                                    }
                                                                    x.set(entityiterator.getX());
                                                                    y.set(entityiterator.getY());
                                                                    z.set(entityiterator.getZ());
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                                });
                                                                BuxinMod.queueServerWork(14, () -> {
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.1));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 5 4");

                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                    }
                                                                });
                                                                if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                    _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                }
                                                                BuxinMod.queueServerWork(10, () -> {
                                                                    if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                        entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                                entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                    }
                                                                });
                                                                Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                               entity.teleportTo(((LivingEntity) entityiterator).getX() - 1, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.51);
                                                                if (entity.onGround()) {
                                                                    entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                        if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                            if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.DUAL_SWORD_DIE, 0F);
                                                                            }
                                                                        }
                                                                    });
                                                                } else {
                                                                    Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/moonless_fullmoon\" 0 1");
                                                                }
                                                            } else {
                                                                BuxinMod.queueServerWork(32, () -> {
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 15));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                        Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
                                                                    }
                                                                    x.set(entityiterator.getX());
                                                                    y.set(entityiterator.getY());
                                                                    z.set(entityiterator.getZ());
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                                });
                                                                BuxinMod.queueServerWork(14, () -> {
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.1));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));

                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 3 2");

                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                    }
                                                                });
                                                                if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                    _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                }
                                                                BuxinMod.queueServerWork(10, () -> {
                                                                    if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                        entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                                entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                    }
                                                                });
                                                                Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                               entity.teleportTo(((LivingEntity) entityiterator).getX() + viewVec.x() * 1.51, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.51);
                                                                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                    if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                        if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                            fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.SWORD_DIE, 0F);
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        } else if (((LivingEntity) entity).getMainHandItem().getItem() == BuxinModItems.CHANGDAO.get() || ((LivingEntity) entity).getMainHandItem().getItem() == BuxinModItems.CHANGDAO_2.get()) {
                                                            if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                            if (!entity.level.isClientSide())
                                                                entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 3));
                                                            ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0, false, false));
                                                            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                            Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                            ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                            }
                                                            if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                        entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/combat/execute_greatsword_hit\" 0 1");

                                                            }
                                                            entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                    if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                        fLivingEntityPatch.playAnimationSynchronized(Animations.GREATSWORD_AUTO1, 0F);
                                                                    }
                                                                }
                                                            });
                                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                BuxinMod.queueServerWork(1, () -> {
                                                                    BuxinMod.queueServerWork(35, () -> {
                                                                        if (entityiterator.isAlive()) {
                                                                            entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                                if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                                    if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                        fLivingEntityPatch.playAnimationSynchronized(Animations.GREATSWORD_AUTO1, 0F);
                                                                                    }
                                                                                }
                                                                            });
                                                                        } else {
                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/weather clear");
                                                                        }
                                                                        BuxinMod.queueServerWork(20, () -> {
                                                                            if (entityiterator.isAlive()) {
                                                                                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                                    if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                                        if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                            fLivingEntityPatch.playAnimationSynchronized(Animations.GREATSWORD_AUTO1, 0F);
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/weather clear");
                                                                            }
                                                                        });
                                                                        BuxinMod.queueServerWork(20, () -> {
                                                                            if (entityiterator.isAlive()) {
                                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/indestructible @s play \"wom:biped/combat/moonless_auto_2\" 0 1");
                                                                            } else {
                                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/weather clear");
                                                                            }
                                                                            if (entityiterator.isAlive()) {
                                                                                if (world instanceof ServerLevel _level)
                                                                                    _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                                entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 15));
                                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));

                                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 60 4 3");
                                                                                x.set(entityiterator.getX());
                                                                                y.set(entityiterator.getY());
                                                                                z.set(entityiterator.getZ());
                                                                            }
                                                                        });
                                                                    });
                                                                });
                                                            }
                                                        } else if (((Maincategory == CapabilityItem.WeaponCategories.GREATSWORD) && entityiterator.isAlive()) && entityiterator.isAlive()) {
                                                            Method_114514.execute_event(entity, entityiterator, BuxinAnimations.GREATSWORD_DIE, BuxinAnimations.Special_greatsword_Been_Attack, 0.2F, 49, 75, true, false, 0.07, 0.05, 0.07, 0.25, true);
                                                            BuxinMod.queueServerWork(40, () -> {
                                                                AnimationPlayer.playAnimation(entity, Animations.GREATSWORD_AUTO1);
                                                            });
                                                        } else if (((Maincategory == CapabilityItem.WeaponCategories.TACHI) && entityiterator.isAlive()) || ((Maincategory == CapabilityItem.WeaponCategories.LONGSWORD) && entityiterator.isAlive())) {
                                                            if((Offcategory != CapabilityItem.WeaponCategories.TACHI)) {
                                                                BuxinMod.queueServerWork(10, () -> {
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.1));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 5 4");

                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                    }
                                                                });
                                                                Method_114514.execute_event(entity, entityiterator, BuxinAnimations.Tachi_DIE, BuxinAnimations.Tachi_BEEN_ATTACK, 0.1F, 24, 85, true, false, 0.07, 0.05, 0.07, 0.25, true);
                                                            } else {
                                                                BuxinMod.queueServerWork(32, () -> {
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 15));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {

                                                                    }
                                                                    Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
                                                                    x.set(entityiterator.getX());
                                                                    y.set(entityiterator.getY());
                                                                    z.set(entityiterator.getZ());
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                                });
                                                                BuxinMod.queueServerWork(14, () -> {
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.02));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 5 4");

                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                    }
                                                                });
                                                                if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                    _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                    BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                }
                                                                BuxinMod.queueServerWork(10, () -> {
                                                                    if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                        entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                                entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                    }
                                                                });
                                                                Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                               entity.teleportTo(((LivingEntity) entityiterator).getX() - 1, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.5);
                                                                if (entity.onGround()) {
                                                                    entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                        if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                            if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.DUAL_SWORD_DIE, 0F);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        } else {
                                                            BuxinMod.queueServerWork(32, () -> {
                                                                if (world instanceof ServerLevel _level)
                                                                    _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.215));
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                    Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");

                                                                }
                                                                x.set(entityiterator.getX());
                                                                y.set(entityiterator.getY());
                                                                z.set(entityiterator.getZ());
                                                                if (world instanceof ServerLevel _level)
                                                                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                            });
                                                            BuxinMod.queueServerWork(14, () -> {
                                                                entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.25));
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                            entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                    entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                            entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 3 2");

                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                }
                                                            });
                                                            if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                            ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                            Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                            ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                            }
                                                            BuxinMod.queueServerWork(10, () -> {
                                                                if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                    entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                            entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                }
                                                            });
                                                            Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                           entity.teleportTo(((LivingEntity) entityiterator).getX() + viewVec.x() * 1.51, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.51);
                                                            entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                    if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                        fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.SWORD_DIE, 0F);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                } else {
                                                    //破防
                                                    if (littlekill) {
                                                        //entity.sendSystemMessage(Component.literal("§e破防处决！"));
                                                        if (Maincategory != CapabilityItem.WeaponCategories.FIST && entityiterator.isAlive()) {

                                                            cjl = true;

                                                            entity.getPersistentData().putBoolean("isfuck", true);
                                                            entity.getPersistentData().putBoolean("YH", true);
                                                            WhatShitPhase2OnEntityUpdateTick.setMaxHealth((LivingEntity) entity, ((LivingEntity) entity).getMaxHealth() + 1);
                                                            //entity.sendSystemMessage(Component.literal("§e处决进入冷却"));
                                                            BuxinMod.queueServerWork(20, () -> {
                                                                //entity.sendSystemMessage(Component.literal("§e5....."));
                                                                BuxinMod.queueServerWork(20, () -> {
                                                                    //entity.sendSystemMessage(Component.literal("§e4..."));
                                                                    BuxinMod.queueServerWork(20, () -> {
                                                                        //entity.sendSystemMessage(Component.literal("§e3.."));
                                                                        BuxinMod.queueServerWork(20, () -> {
                                                                            //entity.sendSystemMessage(Component.literal("§e2."));
                                                                            BuxinMod.queueServerWork(20, () -> {
                                                                               //entity.sendSystemMessage(Component.literal("§e1"));
                                                                            });
                                                                        });
                                                                    });
                                                                });
                                                            });
                                                            BuxinMod.queueServerWork(135, () -> {
                                                                //entity.sendSystemMessage(Component.literal("§e处决冷却结束！可再次进行处决！"));
                                                                entity.getPersistentData().putBoolean("isfuck", false);
                                                                entity.getPersistentData().putBoolean("YH", false);
                                                            });
                                                            entityiterator.teleportTo(entity.getX() + 0.45, entity.getY(), entity.getZ() + 0.45);
                                                            entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entityiterator.getX(), entityiterator.getY() + 1.0, entityiterator.getZ()));
                                                            entityiterator.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0, entity.getZ()));
                                                            if ((((Maincategory == CapabilityItem.WeaponCategories.SWORD) && entityiterator.isAlive()) || (Maincategory == CapabilityItem.WeaponCategories.DAGGER && Offcategory == CapabilityItem.WeaponCategories.DAGGER)) && entityiterator.isAlive()) {
                                                                if (Offcategory == CapabilityItem.WeaponCategories.SWORD || Offcategory == CapabilityItem.WeaponCategories.DAGGER) {
                                                                    BuxinMod.queueServerWork(32, () -> {
                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                        entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                          //  NetWorkManger.sendToPlayer(new CameraShake(60, 7, 3), (ServerPlayer) entity);
                                                                        }
                                                                        Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
                                                                        x.set(entityiterator.getX());
                                                                        y.set(entityiterator.getY());
                                                                        z.set(entityiterator.getZ());
                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                                    });
                                                                    BuxinMod.queueServerWork(14, () -> {
                                                                        entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.02) + 8f);
                                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 5 4");

                                                                            if (world instanceof ServerLevel _level)
                                                                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                        }
                                                                    });
                                                                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                        _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                    ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                    Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                    }
                                                                    BuxinMod.queueServerWork(10, () -> {
                                                                        if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null) {
                                                                            entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                                    entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                        }
                                                                    });
                                                                    Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                                   entity.teleportTo(((LivingEntity) entityiterator).getX() - 1, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.5);
                                                                    if (entity.onGround()) {
                                                                        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                            if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                                if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                    fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.DUAL_SWORD_DIE, 0F);
                                                                                }
                                                                            }
                                                                        });
                                                                    } else {
                                                                        entityiterator.teleportTo(entity.getX() + 0.2,entity.getY() + 5,entity.getZ() + 0.2);
                                                                        Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/moonless_fullmoon\" 0 1");
                                                                    }
                                                                } else {
                                                                    BuxinMod.queueServerWork(32, () -> {
                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                        entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15) + 8f);
                                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                            BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                            Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");

                                                                            //NetWorkManger.sendToPlayer(new CameraShake(60, 7, 3), (ServerPlayer) entity);
                                                                        }
                                                                        x.set(entityiterator.getX());
                                                                        y.set(entityiterator.getY());
                                                                        z.set(entityiterator.getZ());
                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                                    });
                                                                    BuxinMod.queueServerWork(14, () -> {
                                                                        entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.05));
                                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 3 2");

                                                                            if (world instanceof ServerLevel _level)
                                                                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                        }
                                                                    });
                                                                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                        _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                    ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                    Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                    }
                                                                    BuxinMod.queueServerWork(10, () -> {
                                                                        if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                            entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                                    entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                        }
                                                                    });
                                                                    Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                                   entity.teleportTo(((LivingEntity) entityiterator).getX() + viewVec.x() * 1.51, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.5);
                                                                    entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                        if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                            if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.SWORD_DIE, 0F);
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            } else if (((LivingEntity) entity).getMainHandItem().getItem() == BuxinModItems.CHANGDAO.get() || ((LivingEntity) entity).getMainHandItem().getItem() == BuxinModItems.CHANGDAO_2.get()) {
                                                                if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                    _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                if (!entity.level.isClientSide())
                                                                    entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 3));
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0, false, false));
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                }
                                                                if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                    entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                            entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/combat/execute_greatsword_hit\" 0 1");

                                                                }
                                                                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                    if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                        if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                            fLivingEntityPatch.playAnimationSynchronized(Animations.GREATSWORD_AUTO1, 0F);
                                                                        }
                                                                    }
                                                                });
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    BuxinMod.queueServerWork(1, () -> {
                                                                        BuxinMod.queueServerWork(35, () -> {
                                                                            if (entityiterator.isAlive()) {
                                                                                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                                    if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                                        if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                            fLivingEntityPatch.playAnimationSynchronized(Animations.GREATSWORD_AUTO1, 0F);
                                                                                        }
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/weather clear");
                                                                            }
                                                                            BuxinMod.queueServerWork(20, () -> {
                                                                                if (entityiterator.isAlive()) {
                                                                                    entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                                        if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                                            if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                                fLivingEntityPatch.playAnimationSynchronized(Animations.GREATSWORD_AUTO1, 0F);
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                } else {
                                                                                    entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                            entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/weather clear");
                                                                                }
                                                                            });
                                                                            BuxinMod.queueServerWork(20, () -> {
                                                                                if (entityiterator.isAlive()) {
                                                                                    entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                            entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/indestructible @s play \"wom:biped/combat/moonless_auto_2\" 0 1");
                                                                                } else {
                                                                                    entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                            entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/weather clear");
                                                                                }
                                                                                if (entityiterator.isAlive()) {
                                                                                    if (world instanceof ServerLevel _level)
                                                                                        _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                                                                                    BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));

                                                                                    entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                            entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 60 7 3");
                                                                                    x.set(entityiterator.getX());
                                                                                    y.set(entityiterator.getY());
                                                                                    z.set(entityiterator.getZ());
                                                                                }
                                                                            });
                                                                        });
                                                                    });
                                                                }
                                                            } else if (((Maincategory == CapabilityItem.WeaponCategories.GREATSWORD) && entityiterator.isAlive()) || (entityiterator.isAlive())) {
                                                                Method_114514.execute_event(entity, entityiterator, BuxinAnimations.GREATSWORD_DIE, BuxinAnimations.Special_greatsword_Been_Attack, 0.2F, 49, 75, true, false, 0.07, 0.05, 0.07, 0.25, false);
                                                                BuxinMod.queueServerWork(40, () -> {
                                                                    AnimationPlayer.playAnimation(entity, Animations.GREATSWORD_AUTO1);
                                                                });
                                                            } else if (((Maincategory == CapabilityItem.WeaponCategories.TACHI) && entityiterator.isAlive()) || ((Maincategory == CapabilityItem.WeaponCategories.LONGSWORD) && entityiterator.isAlive())) {
                                                                if((Offcategory != CapabilityItem.WeaponCategories.TACHI)) {
                                                                    BuxinMod.queueServerWork(10, () -> {
                                                                        entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.1) + 8f);
                                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 5 4");

                                                                            if (world instanceof ServerLevel _level)
                                                                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                        }
                                                                    });
                                                                    Method_114514.execute_event(entity, entityiterator, BuxinAnimations.Tachi_DIE, BuxinAnimations.Tachi_BEEN_ATTACK, 0.1F, 24, 85, true, false, 0.07, 0.05, 0.07, 0.25, true);
                                                                } else {
                                                                    BuxinMod.queueServerWork(32, () -> {
                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                        entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15) + 8f);
                                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                            //NetWorkManger.sendToPlayer(new CameraShake(60, 7, 3), (ServerPlayer) entity);
                                                                        }
                                                                        Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
                                                                        x.set(entityiterator.getX());
                                                                        y.set(entityiterator.getY());
                                                                        z.set(entityiterator.getZ());
                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                                    });
                                                                    BuxinMod.queueServerWork(14, () -> {
                                                                        entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.02) + 8f);
                                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 5 4");

                                                                            if (world instanceof ServerLevel _level)
                                                                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                        }
                                                                    });
                                                                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                        _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                    ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                    Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                    ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                    }
                                                                    BuxinMod.queueServerWork(10, () -> {
                                                                        if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                            entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                                    entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                        }
                                                                    });
                                                                    Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                                 //   entity.teleportTo(((LivingEntity) entityiterator).getX() - 1, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.5);
                                                                    if (entity.onGround()) {
                                                                        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                            if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                                if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                                    fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.DUAL_SWORD_DIE, 0F);
                                                                                }
                                                                            }
                                                                        });
                                                                    } else {
                                                                        entityiterator.teleportTo(entity.getX(),entity.getY(),entity.getZ());
                                                                        Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/moonless_fullmoon\" 0 1");
                                                                    }
                                                                }
                                                            } else {
                                                                BuxinMod.queueServerWork(32, () -> {
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), x.get(), (y.get() + 1.3), z.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15) + 8f);
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                        Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");

                                                                        //NetWorkManger.sendToPlayer(new CameraShake(60, 7, 3), (ServerPlayer) entity);
                                                                    }
                                                                    x.set(entityiterator.getX());
                                                                    y.set(entityiterator.getY());
                                                                    z.set(entityiterator.getZ());
                                                                    if (world instanceof ServerLevel _level)
                                                                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (55), 0.07, 0.05, 0.07, 0.25);
                                                                });
                                                                BuxinMod.queueServerWork(14, () -> {
                                                                    entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.05) + 8f);
                                                                    if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @s");

                                                                        entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                                entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 14 3 2");

                                                                        if (world instanceof ServerLevel _level)
                                                                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x.get(), (y.get() + 1.3), z.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                                    }
                                                                });
                                                                if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                                    _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                                ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                                Method_114514.entity_use_command(entityiterator,"/effect give @s minecraft:slowness 5 255");
                                                                ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 130, 0, false, false));
                                                                if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                    ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/playsound buxin:bitch ambient @s");
                                                                BuxinMod.queueServerWork(20, () -> ChangdaohimEntity.playSoundToNearbyPlayers(entity, "/effect give @s epicfight:stun_immunity 6 1"));
                                                                }
                                                                BuxinMod.queueServerWork(10, () -> {
                                                                    if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                                        entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                                entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                                    }
                                                                });
                                                                Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
                                                                entity.teleportTo(((LivingEntity) entityiterator).getX() + viewVec.x() * 1.51, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.51);
                                                                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(FEntityPatch -> {
                                                                    if (FEntityPatch instanceof LivingEntityPatch<?> fLivingEntityPatch) {
                                                                        if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_ANIMATION_CLASH)) {
                                                                            fLivingEntityPatch.playAnimationSynchronized(BuxinAnimations.SWORD_DIE, 0F);
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    } else {

                                                       // GuardSkill
                                                      //  entity.getMainHandItem().use(entity.level(),entity,InteractionHand.MAIN_HAND);
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
		});

        if (cjl == false){
            double random_value = Math.random();

            //entity.sendSystemMessage(Component.literal(String.valueOf(random_value).toString()));

            if(random_value > 0.5) {
                KickAndShieldBash.execute(entity.level(), entity);
            } else {
                SpecialKeyPressEvent.event(entity);
            }
        }
	}
}