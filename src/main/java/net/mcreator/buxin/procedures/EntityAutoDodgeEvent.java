package net.mcreator.buxin.procedures;

import net.mcreator.buxin.config.common.DodgePercentConfig;
import net.mcreator.buxin.entity.LanemobentiEntity;
import net.mcreator.buxin.entity.ZhanshenzhirenemoEntity;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

@Mod.EventBusSubscriber
public class EntityAutoDodgeEvent {
    @SubscribeEvent
    public static void event(LivingHurtEvent event){
        if(!(event.getEntity().level().isClientSide)) {
            Entity entity = event.getEntity();
            Random random = new Random();
            int b = random.nextInt(8);
            int percentage = DodgePercentConfig.EntityDodgePercentage.get();
            try {
                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                    if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch && !(EntityPatch.getOriginal().isPassenger())) {
                        if (Math.random() * 100 > 100 - percentage && !(event.getSource() == entity.damageSources().magic()) && !(entity.isOnFire()) && !entity.getPersistentData().getBoolean("isfuck") && !entity.getPersistentData().getBoolean("YH")) {
                            DynamicAnimation animation = LivingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                            if (!(animation instanceof LongHitAnimation) && !event.getEntity().getPersistentData().getBoolean("isfuck") && !event.getEntity().getPersistentData().getBoolean("YH") && entity.getPersistentData().getBoolean("isbuxinentity") || !(animation instanceof LongHitAnimation) && entity instanceof Monster l && !event.getEntity().getPersistentData().getBoolean("isfuck") && !event.getEntity().getPersistentData().getBoolean("YH") && !(l.getMainHandItem().getItem() == BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get() && !(entity.getPersistentData().getBoolean("hbb"))) && !(entity.getPersistentData().getBoolean("cj"))) {
                                if (b == 0)
                                    AnimationPlayer.playAnimation(entity, Animations.BIPED_ROLL_BACKWARD);
                                else
                                if (b == 1) {
                                    if(entity instanceof LanemobentiEntity || entity instanceof ZhanshenzhirenemoEntity){
                                        if(entity.level() instanceof ServerLevel _level) {
                                            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
                                            entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entity.getX(), (int)entity.getY(), (int)entity.getZ())));
                                            entityToSpawn.setVisualOnly(false);
                                            entityToSpawn.setCustomName(Component.literal("b"));
                                            _level.addFreshEntity(entityToSpawn);
                                        }
                                    }
                                    if(!(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("him") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine")) {
                                        AnimationPlayer.playAnimation(entity,Animations.BIPED_STEP_BACKWARD);
                                    } else {
                                        AnimationPlayer.playAnimation(entity,Animations.BIPED_STEP_BACKWARD);
                                    }
                                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 0.8F, 0.8F);
                                }
                                else
                                if (b == 2) {
                                    if(entity instanceof LanemobentiEntity || entity instanceof ZhanshenzhirenemoEntity){
                                        if(entity.level() instanceof ServerLevel _level) {
                                            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
                                            entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entity.getX(), (int)entity.getY(), (int)entity.getZ())));
                                            entityToSpawn.setVisualOnly(false);
                                            entityToSpawn.setCustomName(Component.literal("b"));
                                            _level.addFreshEntity(entityToSpawn);
                                        }
                                    }
                                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 0.8F, 0.8F);
                                    if(!(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("him") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine")) {
                                        AnimationPlayer.playAnimation(entity, Animations.BIPED_STEP_BACKWARD);
                                    } else {
                                        AnimationPlayer.playAnimation(entity,Animations.BIPED_STEP_RIGHT);
                                    }
                                }
                                else
                                if (b == 3)
                                    AnimationPlayer.playAnimation(entity, Animations.BIPED_ROLL_FORWARD);
                                else
                                if (b == 4) {
                                    if(entity instanceof LanemobentiEntity || entity instanceof ZhanshenzhirenemoEntity){
                                        if(entity.level() instanceof ServerLevel _level) {
                                            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
                                            entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entity.getX(), (int)entity.getY(), (int)entity.getZ())));
                                            entityToSpawn.setVisualOnly(false);
                                            entityToSpawn.setCustomName(Component.literal("b"));
                                            _level.addFreshEntity(entityToSpawn);
                                        }
                                    }
                                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 0.8F, 0.8F);
                                    if(!(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("him") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine")) {
                                        AnimationPlayer.playAnimation(entity, Animations.BIPED_STEP_BACKWARD);
                                    } else {
                                        AnimationPlayer.playAnimation(entity,Animations.BIPED_STEP_LEFT);
                                    }
                                }
                                else
                                if(b == 5)
                                    AnimationPlayer.playAnimation(entity, Animations.BIPED_ROLL_BACKWARD);
                                else
                                if(b == 6)
                                    AnimationPlayer.playAnimation(entity, Animations.BIPED_ROLL_FORWARD);
                                else {
                                    if(entity instanceof LanemobentiEntity || entity instanceof ZhanshenzhirenemoEntity){
                                        if(entity.level() instanceof ServerLevel _level) {
                                            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
                                            entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entity.getX(), (int)entity.getY(), (int)entity.getZ())));
                                            entityToSpawn.setVisualOnly(false);
                                            entityToSpawn.setCustomName(Component.literal("b"));
                                            _level.addFreshEntity(entityToSpawn);
                                        }
                                    }
                                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 0.8F, 0.8F);
                                    if(!(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("him") && !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine")) {
                                        AnimationPlayer.playAnimation(entity, Animations.BIPED_STEP_BACKWARD);
                                    } else {
                                        AnimationPlayer.playAnimation(entity,Animations.BIPED_STEP_FORWARD);
                                    }
                                }
                            }
                        }
                    }
                });
            } catch (Exception e){
            }
        }
    }
}