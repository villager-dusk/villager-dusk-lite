//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.mcreator.buxin.ai;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.DelayedTask;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.CunMinWeiBingShiTiChwuShiShengChengShiProcedure;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class VillagerScoutTick {
    public static void execute(final LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity) {
        if (entity != null) {
            double attack = 0;
            Random random = new Random();
            double fuck = random.nextInt(3);
            AtomicReference<Double> dx = new AtomicReference<>(entity.getX());
            AtomicReference<Double> dy = new AtomicReference<>(entity.getY());
            AtomicReference<Double> dz = new AtomicReference<>(entity.getZ());
            ItemStack itemstack;
            if (entity.getVehicle() instanceof Animal) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;
                    itemstack = livingentity.getMainHandItem();
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() instanceof SwordItem) {
                    entity.stopRiding();
                }
            }

            Vec3 vec3 = new Vec3(d0, d1, d2);
            Vec3 finalVec = vec3;
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(20.0), (entity1x) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1x) -> {
                return entity1x.distanceToSqr(finalVec);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            label151:
            while(true) {
                while(true) {
                    LivingEntityPatch livingentitypatch;
                    do {
                        Entity entity1;
                        LivingEntity livingentity1;
                        do {
                            do {
                                Mob mob;
                                if (!iterator.hasNext()) {
                                    vec3 = new Vec3(d0, d1, d2);
                                    Vec3 finalVec1 = vec3;
                                    list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(2.5), (entity2) -> {
                                        return true;
                                    }).stream().sorted(Comparator.comparingDouble((entity2) -> {
                                        return entity2.distanceToSqr(finalVec1);
                                    })).collect(Collectors.toList());
                                    iterator = list.iterator();

                                    while(iterator.hasNext()) {
                                        entity1 = (Entity)iterator.next();
                                        if (entity instanceof Mob) {
                                            mob = (Mob)entity;
                                            livingentity1 = mob.getTarget();
                                        } else {
                                            livingentity1 = null;
                                        }

                                        if (entity1 == livingentity1) {
                                            if (entity instanceof LivingEntity) {
                                                LivingEntity livingentity9 = (LivingEntity)entity;
                                                ItemStack itemstack2 = new ItemStack(Items.IRON_SWORD);
                                                itemstack2.setCount(1);
                                                livingentity9.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                                if (livingentity9 instanceof Player) {
                                                    Player player1 = (Player)livingentity9;
                                                    player1.getInventory().setChanged();
                                                }
                                            }

                                            Entity nearestSheep = (Entity)levelaccessor.getEntitiesOfClass(Sheep.class, AABB.ofSize(new Vec3(d0, d1, d2), 3.0, 3.0, 3.0), (sheep) -> {
                                                return true;
                                            }).stream().sorted(Comparator.comparingDouble((e) -> {
                                                return e.distanceToSqr(d0, d1, d2);
                                            })).findFirst().orElse((Sheep) null);
                                            if (entity.getControllingPassenger() == nearestSheep) {
                                                entity.stopRiding();
                                            }
                                        }
                                    }
                                    break label151;
                                }

                                entity1 = (Entity)iterator.next();
                                if (entity instanceof Mob) {
                                    mob = (Mob)entity;
                                    livingentity1 = mob.getTarget();
                                } else {
                                    livingentity1 = null;
                                }
                            } while(entity1 != livingentity1);
                        } while(!entity.isAlive());

                        livingentitypatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    } while(livingentitypatch == null);

                    DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                    if (1==1) {
                    //if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
                        LivingEntity livingentity2;
                        if (entity instanceof Mob) {
                            Mob mob1 = (Mob)entity;
                            livingentity2 = mob1.getTarget();
                        } else {
                            livingentity2 = null;
                        }

                        LivingEntity livingentity3 = livingentity2;
                        if (livingentity3 instanceof LivingEntity) {
                            LivingEntity livingentity4 = livingentity3;
                            itemstack = livingentity4.getMainHandItem();
                        } else {
                            itemstack = ItemStack.EMPTY;
                        }

                        DelayedTask var10001;
                        Vec3 vec31;
                        Mob mob2;
                        LivingEntity livingentity5;
                        double d3;
                        LivingEntity livingentity6;
                        double d4;
                        LivingEntity livingentity7;
                        ItemStack itemstack1;
                        Player player;
                        EntityAnchorArgument.Anchor anchor;
                        if (itemstack.getItem() instanceof BowItem) {
                            anchor = Anchor.EYES;
                            if (entity instanceof Mob) {
                                mob2 = (Mob)entity;
                                livingentity5 = mob2.getTarget();
                            } else {
                                livingentity5 = null;
                            }

                            d3 = livingentity5.getX();
                            if (entity instanceof Mob) {
                                mob2 = (Mob)entity;
                                livingentity6 = mob2.getTarget();
                            } else {
                                livingentity6 = null;
                            }

                            d4 = livingentity6.getY();
                            if (entity instanceof Mob) {
                                mob2 = (Mob)entity;
                                livingentity7 = mob2.getTarget();
                            } else {
                                livingentity7 = null;
                            }

                            vec31 = new Vec3(d3, d4, livingentity7.getZ());
                            entity.lookAt(anchor, vec31);
                            if (entity instanceof LivingEntity) {
                                livingentity3 = (LivingEntity)entity;
                                itemstack1 = new ItemStack(Items.BOW);
                                itemstack1.setCount(1);
                                livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                if (livingentity3 instanceof Player) {
                                    player = (Player)livingentity3;
                                    player.getInventory().setChanged();
                                }
                            }

                            if (Math.random() <= 0.05) {
                                var10001 = new DelayedTask(Mth.nextInt(BuxinMod.randomSource, 1, 10)) {
                                    public void run() {
                                        Entity entity2 = entity;
                                        Method_114514.entity_use_command(entity,"/indestructible @s play \"epicfight:biped/combat/bow_shot_mid\" 0 1");

                                        entity2 = entity;
                                        Level level = entity2.level;
                                        if (!level.isClientSide()) {
                                            Projectile projectile = new Arrow(EntityType.ARROW, level);
                                            projectile.setOwner(entity2);
                                            ((Arrow)projectile).setBaseDamage(4.0);
                                            ((Arrow)projectile).setKnockback(0);
                                            ((Arrow)projectile).setPierceLevel((byte)1);
                                            projectile.setPos(entity2.getX(), entity2.getEyeY() - 0.1, entity2.getZ());
                                            projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 3.0F, 0.0F);
                                            level.addFreshEntity(projectile);
                                        }

                                        LevelAccessor levelaccessor1 = levelaccessor;
                                        if (levelaccessor1 instanceof Level level1) {
                                            if (!level1.isClientSide()) {
                                                level1.playSound((Player)null, new BlockPos((int) d0,(int) d1,(int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                            } else {
                                                level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                            }
                                        }

                                    }
                                };
                            }
                            if(Math.random() < 0.05){
                                {
                                    if (Math.random() > 0.5) {
                                        CunMinWeiBingShiTiChwuShiShengChengShiProcedure event = new CunMinWeiBingShiTiChwuShiShengChengShiProcedure();
                                        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                                            if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                                                final Vec3 _center = new Vec3(d0, d1, d2);
                                                List<Entity> _entfound = levelaccessor.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                                        .collect(Collectors.toList());
                                                for (Entity entityiterator : _entfound) {
                                                    if (((!(entity.getPersistentData().getBoolean("isfuck"))) && !(entityiterator instanceof ItemEntity) && (!(entityiterator instanceof Projectile)) && ((!(entity == entityiterator))))) {
                                                        event.CanPutToDeath = true;
                                                    }
                                                    if (entityiterator instanceof LivingEntity && entityiterator != entity && event.CanPutToDeath && !(entityiterator instanceof Sheep) && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator && entity.isAlive() && ((LivingEntity) entityiterator).getHealth() < ((LivingEntity) entityiterator).getMaxHealth() * 0.5) {
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
                                                            if (levelaccessor instanceof ServerLevel _level)
                                                                _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), dx.get(), (dy.get() + 1.3), dz.get(), 1, 0.25, 0.3, 0.25, 0.2);
                                                            entityiterator.hurt(entityiterator.level.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.2) + 4);
                                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.eviscerate voice @p");
                                                            }
                                                            if (levelaccessor instanceof ServerLevel _level)
                                                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), entityiterator.getX(), (entityiterator.getY() + 1.3), entityiterator.getZ(), (55), 0.07, 0.05, 0.07, 0.25);
                                                        });
                                                        BuxinMod.queueServerWork(14, () -> {
                                                            entityiterator.hurt(entityiterator.level.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.05));
                                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound epicfight:entity.hit.blade voice @p");

                                                                if (levelaccessor instanceof ServerLevel _level)
                                                                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), dx.get(), (dy.get() + 1.3), dz.get(), (10), 0.03, 0.02, 0.03, 0.15);
                                                            }
                                                        });
                                                        if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                            _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
                                                        ((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                                                        ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                                                        ((LivingEntity) entityiterator).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45, 50, false, false));
                                                        ((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) MobEffects.MOVEMENT_SLOWDOWN, 130, 0, false, false));
                                                        if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                    entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/playsound buxin:bitch ambient @p");//
                                                        }
                                                        //BuxinMod.queueServerWork(10, () -> {
                                                        if (!entityiterator.level.isClientSide() && entityiterator.getServer() != null && (!(entity == entityiterator))) {
                                                            entityiterator.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entityiterator.position(), entityiterator.getRotationVector(), entityiterator.level instanceof ServerLevel ? (ServerLevel) entityiterator.level : null, 6,
                                                                    entityiterator.getName().getString(), entityiterator.getDisplayName(), entityiterator.level.getServer(), entityiterator), "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
                                                        }
                                                        //});
                                                        BuxinMod.queueServerWork(2, () -> {
                                                            if (!entity.level.isClientSide() && entity.getServer() != null) {
                                                                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 6,
                                                                        entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), "/indestructible @s play \"buxin:biped/run/other_attack\" 0 1");
                                                            }
                                                        });
                                                    }

                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        } else {
                            if (entity instanceof Mob) {
                                Mob mob3 = (Mob)entity;
                                livingentity2 = mob3.getTarget();
                            } else {
                                livingentity2 = null;
                            }

                            livingentity3 = livingentity2;
                            if (livingentity3 instanceof LivingEntity) {
                                LivingEntity livingentity8 = livingentity3;
                                itemstack = livingentity8.getMainHandItem();
                            } else {
                                itemstack = ItemStack.EMPTY;
                            }

                            if (itemstack.getItem() instanceof CrossbowItem) {
                                anchor = Anchor.EYES;
                                if (entity instanceof Mob) {
                                    mob2 = (Mob)entity;
                                    livingentity5 = mob2.getTarget();
                                } else {
                                    livingentity5 = null;
                                }

                                d3 = livingentity5.getX();
                                if (entity instanceof Mob) {
                                    mob2 = (Mob)entity;
                                    livingentity6 = mob2.getTarget();
                                } else {
                                    livingentity6 = null;
                                }

                                d4 = livingentity6.getY();
                                if (entity instanceof Mob) {
                                    mob2 = (Mob)entity;
                                    livingentity7 = mob2.getTarget();
                                } else {
                                    livingentity7 = null;
                                }

                                vec31 = new Vec3(d3, d4, livingentity7.getZ());
                                entity.lookAt(anchor, vec31);
                                if (entity instanceof LivingEntity) {
                                    livingentity3 = (LivingEntity)entity;
                                    itemstack1 = new ItemStack(Items.BOW);
                                    itemstack1.setCount(1);
                                    livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                                    if (livingentity3 instanceof Player) {
                                        player = (Player)livingentity3;
                                        player.getInventory().setChanged();
                                    }
                                }

                                if (Math.random() <= 0.05) {
                                    var10001 = new DelayedTask(Mth.nextInt(BuxinMod.randomSource, 1, 10)) {
                                        public void run() {
                                            Entity entity2 = entity;
                                            if (!entity2.level.isClientSide() && entity2.getServer() != null) {
                                                try {
                                                    entity2.getServer().getCommands().getDispatcher().execute("indestructible @s play \"epicfight:biped/combat/bow_shot_mid\" 0 1", entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException var5) {
                                                }
                                            }

                                            entity2 = entity;
                                            Level level = entity2.level;
                                            if (!level.isClientSide()) {
                                                Projectile projectile = new Arrow(EntityType.ARROW, level);
                                                projectile.setOwner(entity2);
                                                ((Arrow)projectile).setBaseDamage(4.0);
                                                ((Arrow)projectile).setKnockback(0);
                                                ((Arrow)projectile).setPierceLevel((byte)1);
                                                projectile.setPos(entity2.getX(), entity2.getEyeY() - 0.1, entity2.getZ());
                                                projectile.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 3.0F, 0.0F);
                                                level.addFreshEntity(projectile);
                                            }

                                            LevelAccessor levelaccessor1 = levelaccessor;
                                            if (levelaccessor1 instanceof Level level1) {
                                                if (!level1.isClientSide()) {
                                                    level1.playSound((Player)null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                                } else {
                                                    level1.playLocalSound(d0, d1, d2, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                                                }
                                            }

                                        }
                                    };
                                }
                            }
                        }
                    } else {
                        entity.clearFire();
                    }
                }
            }
        }

    }
}
