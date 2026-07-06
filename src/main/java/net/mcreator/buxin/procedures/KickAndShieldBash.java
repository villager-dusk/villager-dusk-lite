package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.PutEntityToDieAnimation;
import net.mcreator.buxin.gameasset.SpecialAnimation;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
//import net.mcreator.buxin.utils.BlackShieldExplosionParticleEmitterInfo;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
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
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class KickAndShieldBash {
	//踢击/处决
	public static void execute(LevelAccessor world, Player entity) {
		if (entity == null || world.isClientSide())
			return;
		entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
            final Vec3 _center0 = new Vec3(entity.getX(),entity.getY(),entity.getZ());
            List<Entity> _entfound0 = world.getEntitiesOfClass(Entity.class, new AABB(_center0, _center0).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center0))).toList();
            for (Entity entityiterator : _entfound0) {
                entityiterator.getPersistentData().putBoolean("cj",true);
                BuxinMod.queueServerWork(80,() -> {
                    entityiterator.getPersistentData().putBoolean("cj",false);
                });
            }
			if (EntityPatch instanceof LivingEntityPatch<?> livingEntityPatch) {
				DynamicAnimation animation = livingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
				Random random = new Random();
				double normal_kick = random.nextInt(3);
				AtomicReference<Double> x = new AtomicReference<>(entity.getX());
				AtomicReference<Double> y = new AtomicReference<>(entity.getY());
				AtomicReference<Double> z = new AtomicReference<>(entity.getZ());
                var patch = AnimationPlayer.getlivingEntityPatch(entity);
                WeaponCategory offcategory = null;
                if (patch != null) {
                    offcategory = patch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
                }
                /*----------------------------------------------------------------------------*/
				boolean cankick = false;
				boolean candomainshieldkick = false;
				boolean candooffshieldkick = false;
                if (!(entity.getPersistentData().getBoolean("iskicking"))) {
                    cankick = true;
                }
                if (entity.getMainHandItem().getItem() == Items.SHIELD && !(entity.getPersistentData().getBoolean("iskicking"))) {
                    candomainshieldkick = true;
                }
                if (entity.getMainHandItem().getItem() == Items.SHIELD && !(entity.getPersistentData().getBoolean("iskicking"))) {
                    candooffshieldkick = true;
                }
                if(offcategory == CapabilityItem.WeaponCategories.SHIELD && !(entity.getPersistentData().getBoolean("iskicking"))){
                    candooffshieldkick = true;
                }
                if(ForgeRegistries.ITEMS.getKey(entity.getMainHandItem().getItem()).toString().contains("shield") && !(entity.getPersistentData().getBoolean("iskicking"))){
                    candooffshieldkick = true;
                }
                if(entity.getMainHandItem().getItem() instanceof ShieldItem && !(entity.getPersistentData().getBoolean("iskicking"))){
                    candooffshieldkick = true;
                }
                if (!(animation instanceof LongHitAnimation) && !(animation instanceof HitAnimation) && !(animation instanceof PutEntityToDieAnimation) && !(animation instanceof SpecialAnimation)) {
                    if (candooffshieldkick) {
                        {
                            if (entity.getMainHandItem().getItem() == BuxinModItems.BLACK_SHIELD_2.get()) {
                                Method_114514.entity_use_command(entity, "/impactful @s shake 30 4 4");
                                if (SystemMethod.isWindows()) {
                                    /*new BlackShieldExplosionParticleEmitterInfo(new ResourceLocation(BuxinMod.MODID, "ender_glaive_explosion")).fromTo(entity.position(), Method_114514.getPositionInFront(entity, 8), BlackShieldExplosionParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f, true).spawnInWorld(entity.level(), null);
                                    BuxinMod.queueServerWork(2, () -> {
                                        VFXTool.addVFXParticle(Method_114514.getPositionInFront(entity, 8), BuxinMod.MODID, "ender_explosion_small", entity.level());
                                    });

                                     */
                                }
                                Method_114514.play_sound(entity.level(), Method_114514.getPositionInFront(entity, 8), "entity.generic.explode", 2.5f, 1);
                                Method_114514.play_sound(entity, "epicfight:sfx.laser_blast", 1.5f, 1);
                                //Method_114514.entity_use_command(entity,"/particle buxin:ender ~ ~1 ~ 0 0 0 0.08 125");
                                BuxinMod.queueServerWork(4, () -> {
                                    if (entity.level() instanceof ServerLevel) {
                                        Vec3 explosionPos = Method_114514.getPositionInFront(entity, 8);
                                        //entity.level().explode(entity, explosionPos.x, explosionPos.y, explosionPos.z, 4, Explosion.BlockInteraction.NONE);
                                        final Vec3 _center02 = new Vec3(explosionPos.x, explosionPos.y, explosionPos.z);
                                        List<LivingEntity> _entfound02 = world.getEntitiesOfClass(LivingEntity.class, new AABB(_center02, _center02).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center02))).toList();
                                        for (LivingEntity entityiterator : _entfound02) {
                                            if (!(entityiterator == entity)) {
                                                if (entityiterator instanceof Player) {
                                                    Method_114514.entity_use_command(entityiterator, "/impactful @s shake 50 4 4");
                                                }
                                                if (!(AnimationPlayer.getAnimation(entityiterator) instanceof LongHitAnimation)) {
                                                    AnimationPlayer.playAnimation(entityiterator, Animations.BIPED_HIT_LONG);
                                                }
                                                entityiterator.hurt(entity.level().damageSources().mobAttack(entity), 10f + new Random().nextFloat(9f));
                                            }
                                        }
                                    }
                                });
                            }
                            //System.err.println(BuxinAnimations.PUTENTITYTODIEBEENATTACK.getNamespaceId());
                            final Vec3 _center = new Vec3(x.get(), y.get(), z.get());
                            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                            for (Entity entityiterator : _entfound) {
                                if (!(entity == entityiterator) && !(entityiterator instanceof Projectile) && !(entityiterator instanceof ItemEntity) && !(entityiterator.getPersistentData().getBoolean("isfuck"))) {
                                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                        _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 25, 1, false, false));
                                    entityiterator.playSound(EpicFightSounds.NEUTRALIZE_BOSSES.get());
                                    Method_114514.entity_use_command(entity, "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
                                    {
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 20 6 6");
                                        }
                                    }
                                    BuxinMod.queueServerWork(3, () -> {
                                        if (world instanceof ServerLevel _level)
                                            entityiterator.hurt(entity.level().damageSources().mobAttack(entity), (float) (2 + ((LivingEntity) entity).getSpeed() * 2.5 + normal_kick));
                                        Vec3 knockbackVec = entity.getLookAngle().normalize().scale(1.5).add(0, ((LivingEntity) entity).getSpeed() * 2.5, 0);
                                        entityiterator.setDeltaMovement(knockbackVec);
                                        entityiterator.hurtMarked = true;
                                        BuxinMod.queueServerWork(3, () -> {
                                            Method_114514.entity_use_command(entityiterator, "/indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 1");
                                        });
                                    });
                                }
                            }
                        }
                        entity.getPersistentData().putBoolean("iskicking", true);
                        Method_114514.entity_use_command(entity, "/indestructible @s play \"buxin:biped/combat/off_shield_kick\" 0 1");
                        BuxinMod.queueServerWork(60, () -> {
                            entity.getPersistentData().putBoolean("iskicking", false);//能踢
                        });
                    }
                    if (normal_kick == 0) {
                        if (cankick && !candomainshieldkick && !candooffshieldkick) {
                            {
                                final Vec3 _center = new Vec3(x.get(), y.get(), z.get());
                                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                                for (Entity entityiterator : _entfound) {
                                    if (!(entity == entityiterator) && !(entityiterator instanceof Arrow) && !(entityiterator instanceof ItemEntity) && !(entityiterator.getPersistentData().getBoolean("isfuck"))) {
                                        double dx = entityiterator.getX();
                                        double dy = entityiterator.getY();
                                        double dz = entityiterator.getZ();
                                        if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                            _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 25, 1, false, false));
                                        entityiterator.playSound(EpicFightSounds.BLUNT_HIT.get());
                                        BuxinMod.queueServerWork(7, () -> {
                                            if (world instanceof ServerLevel _level)
                                                _level.sendParticles((SimpleParticleType) (EpicFightParticles.HIT_BLUNT.get()), dx, (dy + 1.1), dz, 1, 1, 1, 1, 0.2);
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 20 6 6");
                                            }
                                            Method_114514.play_sound(entity,"epicfight:entity.hit.blunt_hard");
                                            entityiterator.hurt(entity.level().damageSources().mobAttack(entity), (float) (2 + ((LivingEntity) entity).getSpeed() * 2.5 + normal_kick));
                                            //int height = 0;
                                            if (!(entity.level().getDifficulty().equals(Difficulty.HARD))) {
                                                Vec3 knockbackVec = entity.getLookAngle()
                                                        .normalize()
                                                        .scale(3.0)
                                                        .add(0, ((LivingEntity) entity).getSpeed() * 2.5, 0);
                                                entityiterator.setDeltaMovement(knockbackVec);
                                                entityiterator.hurtMarked = true;
                                            }
                                        });
                                    }
                                }
                            }
                            entity.getPersistentData().putBoolean("iskicking", true);
                            if(entity.onGround()) {
                                if (entity.isSprinting()) {
                                    if(Math.random()>0.5)
                                        Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_auto_4\" 0 1");
                                    else
                                        Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_dash\" 0 1");
                                } else {
                                    Method_114514.entity_use_command(entity, "/indestructible @s play \"buxin:biped/combat/kick1\" 0 1");
                                }
                            } else {
                                Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_jumpkick\" 0 1");
                            }
                            BuxinMod.queueServerWork(20, () -> {
                                entity.getPersistentData().putBoolean("iskicking", false);
                            });
                        }
                    }
                    if (normal_kick == 1) {
                        if (cankick && !candomainshieldkick && !candooffshieldkick) {
                            entity.getPersistentData().putBoolean("iskicking", true);
                            {
                                final Vec3 _center = new Vec3(x.get(), y.get(), z.get());
                                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                                for (Entity entityiterator : _entfound) {
                                    if (!(entity == entityiterator) && !(entityiterator instanceof Arrow) && !(entityiterator instanceof ItemEntity) && !(entityiterator.getPersistentData().getBoolean("isfuck"))) {
                                        double dx = entityiterator.getX();
                                        double dy = entityiterator.getY();
                                        double dz = entityiterator.getZ();
                                        if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                            _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 200, 1, false, false));
                                        BuxinMod.queueServerWork(5, () -> {
                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 20 6 6");
                                            }
                                            Method_114514.play_sound(entity,"epicfight:entity.hit.blunt_hard");
                                        });
                                        BuxinMod.queueServerWork(7, () -> {
                                            if (world instanceof ServerLevel _level)
                                                _level.sendParticles((SimpleParticleType) (EpicFightParticles.HIT_BLUNT.get()), dx, (dy + 1.1), dz, 1, 1, 1, 1, 0.2);
                                            entityiterator.hurt(entity.level().damageSources().mobAttack(entity), (float) (2 + ((LivingEntity) entity).getSpeed() * 2.5 + normal_kick));
                                            if (!(entity.level().getDifficulty().equals(Difficulty.HARD))) {
                                                Vec3 knockbackVec = entity.getLookAngle()
                                                        .normalize()
                                                        .scale(3.0)
                                                        .add(0, ((LivingEntity) entity).getSpeed() * 2.5, 0);
                                                entityiterator.setDeltaMovement(knockbackVec);
                                                entityiterator.hurtMarked = true;
                                            }
                                        });
                                    }
                                }
                            }
                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                if(entity.onGround()) {
                                    if (entity.isSprinting()) {
                                        if(Math.random()>0.5)
                                            Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_auto_4\" 0 1");
                                        else
                                            Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_dash\" 0 1");
                                    } else {
                                        Method_114514.entity_use_command(entity, "/indestructible @s play \"buxin:biped/combat/kick3\" 0 1");
                                    }
                                } else {
                                    Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_jumpkick\" 0 1");
                                }
                            } else {
                                Method_114514.entity_use_command(entity,"/indestructible @s play \"wom:biped/combat/enderblaster_onehand_jumpkick\" 0 1");
                            }
                            BuxinMod.queueServerWork(20, () -> {
                                entity.getPersistentData().putBoolean("iskicking", false);
                            });
                        }
                    }
                    if (normal_kick == 2) {
                        if (cankick && !candomainshieldkick && !candooffshieldkick) {
                            entity.getPersistentData().putBoolean("iskicking", true);
                            {
                                final Vec3 _center = new Vec3(x.get(), y.get(), z.get());
                                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                                for (Entity entityiterator : _entfound) {
                                    if (!(entity == entityiterator) && !(entityiterator instanceof Arrow) && !(entityiterator instanceof ItemEntity) && !(entityiterator.getPersistentData().getBoolean("isfuck"))) {
                                        double dx = entityiterator.getX();
                                        double dy = entityiterator.getY();
                                        double dz = entityiterator.getZ();
                                        if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                            _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 25, 1, false, false));
                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/impactful @s shake 20 6 6");
                                        }
                                        Method_114514.play_sound(entity,"epicfight:entity.hit.blunt_hard");
                                        BuxinMod.queueServerWork(7, () -> {
                                            if (world instanceof ServerLevel _level)
                                                _level.sendParticles((SimpleParticleType) (EpicFightParticles.HIT_BLUNT.get()), dx, (dy + 1.1), dz, 1, 1, 1, 1, 0.2);
                                            entityiterator.hurt(entity.level().damageSources().mobAttack(entity), (float) (2 + ((LivingEntity) entity).getSpeed() * 2.5));
                                            if (!(entity.level().getDifficulty().equals(Difficulty.HARD))) {
                                                Vec3 knockbackVec = entity.getLookAngle()
                                                        .normalize()
                                                        .scale(3.0)
                                                        .add(0, ((LivingEntity) entity).getSpeed() * 2.5, 0);
                                                entityiterator.setDeltaMovement(knockbackVec);
                                                entityiterator.hurtMarked = true;
                                            }
                                        });
                                    }
                                }
                            }
                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                if(entity.onGround()) {
                                    if(entity.onGround()) {
                                        if (entity.isSprinting()) {
                                            if(Math.random()>0.5)
                                                Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_auto_4\" 0 1");
                                            else
                                                Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_dash\" 0 1");
                                        } else {
                                            Method_114514.entity_use_command(entity, "/indestructible @s play \"buxin:biped/combat/kick2\" 0 1");
                                        }
                                    } else {
                                        Method_114514.entity_use_command(entity, "/indestructible @s play \"wom:biped/combat/enderblaster_onehand_jumpkick\" 0 1");
                                    }
                                } else {
                                    Method_114514.entity_use_command(entity,"/indestructible @s play \"wom:biped/combat/enderblaster_onehand_jumpkick\" 0 1");
                                }
                            }
                            BuxinMod.queueServerWork(20, () -> {
                                entity.getPersistentData().putBoolean("iskicking", false);
                            });
                        }
                    }
                }
            }
		});
	}
}