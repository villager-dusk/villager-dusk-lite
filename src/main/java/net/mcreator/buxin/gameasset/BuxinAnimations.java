package net.mcreator.buxin.gameasset;

import com.mojang.datafixers.util.Pair;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.CheckBooleanHandler;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.init.BuxinModSounds;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static net.mcreator.buxin.gameasset.BuxinAnimations.ReuseableEvents2.GROUNDSLAM_SMALL;

public class BuxinAnimations {
    public static final Collider WARDEN_ARM = new MultiOBBCollider(8, 0.8, 0.8, 0.8, 0, 0.3F, 0);

    public static StaticAnimation DRINK_OFFHAND;
    public static StaticAnimation TRIDENT;
    public static StaticAnimation SPECIAL_ATTACK;
    public static StaticAnimation HEROBRINE3_BORN;
    public static StaticAnimation SUMMONING;
    public static StaticAnimation LegendarySwordRun;
    public static StaticAnimation SWORD_DUAL_AUTO_1;
    public static StaticAnimation SWORD_DUAL_AUTO_1_BETTER;
    public static StaticAnimation SWORD_DUAL_AUTO_2_BETTER;
    public static StaticAnimation SWORD_DUAL_AUTO_3_BETTER;
    public static StaticAnimation SWORD_DUAL_AUTO_4_BETTER;
    public static StaticAnimation SWORD_DUAL_AUTO_2;
    public static StaticAnimation SWORD_DUAL_AUTO_3;
    public static StaticAnimation SWORD_DUAL_AUTO_4;
    public static StaticAnimation SWORD_DUAL_AUTO_5;
    public static StaticAnimation SWORD_DUAL_AUTO_6;
    public static StaticAnimation LEGENDARY_SWORD_AUTO_1;
    public static StaticAnimation LEGENDARY_SWORD_AUTO_2;
    public static StaticAnimation SNAKE_BLADE_SKILL;
    public static StaticAnimation BOW_SHOOT;
    public static StaticAnimation TRIDENT_HOLIDAY;
    public static StaticAnimation SingleSparkCanStartPrairieFire;
    public static StaticAnimation DUAL_SWORD_AUTO_1;
    public static StaticAnimation DUAL_SWORD_AUTO_2;
    public static StaticAnimation DUAL_SWORD_DASH;
    public static StaticAnimation DUAL_SWORD_RUN;
    public static StaticAnimation DIG;
    public static StaticAnimation SWORD_DIE;
    public static StaticAnimation GREATSWORD_DIE;
    public static StaticAnimation DUAL_SWORD_DIE;
    public static StaticAnimation PUTENTITYTODIEBEENATTACK;
    public static StaticAnimation KICK;
    public static StaticAnimation KICK2;
    public static StaticAnimation KICK3;
    public static StaticAnimation Attack1;
    public static StaticAnimation Attack2;
    public static StaticAnimation Attack3;
    public static StaticAnimation Attack4;
    public static StaticAnimation Special_greatsword_Been_Attack;
    public static StaticAnimation OFF_SHILED_KICK;
    public static final Collider LLL = new MultiOBBCollider(3, 0.4, 0.4, 1.5, 0.0, 0.0, -0.5);
    public static final Collider EXECUTE = new MultiOBBCollider(3, 0.4, 0.4, 1.5, 0.0, 0.0, -0.5);
    public static final Collider EXECUTE_SECOND = new MultiOBBCollider(2, 0.8, 0.8, 5.0, 0.0, 0.0, -0.66);
    public static StaticAnimation SIMPLE_SWORD_AUTO_2;
    public static StaticAnimation SIMPLE_SWORD_AUTO_3;
    public static StaticAnimation DEATH;
    public static StaticAnimation HB_getpower;
    public static StaticAnimation HB_sendpower;
    public static StaticAnimation HB_born;
    public static StaticAnimation Super_Whirlwind;
    public static StaticAnimation BlueDemonElectric;
    public static StaticAnimation BlueDemonElectricShort;
    public static StaticAnimation D3;
    public static StaticAnimation D4;
    public static StaticAnimation Tachi_DIE;
    public static StaticAnimation Tachi_BEEN_ATTACK;
    public static StaticAnimation Legendary_Sword_Heavy_Attack;
    public static StaticAnimation SWORD_DUAL_AUTO1;
    public static StaticAnimation SWORD_DUAL_AUTO2;
    public static StaticAnimation KILL_SELF;
    public BuxinAnimations() {
    }

    public static final Collider LegendarySword_Attack = new MultiOBBCollider(4, 0.55, 0.55, 1.6, 0.0, 0.0, -0.8);
    public static Vec3 getfloor(LivingEntityPatch<?> entitypatch, StaticAnimation self, Vec3f WeaponOffset, Joint joint) {
        OpenMatrix4f transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(1.0F), joint);
        transformMatrix.translate(WeaponOffset);
        OpenMatrix4f CORRECTION = (new OpenMatrix4f()).rotate(-((float)Math.toRadians((double)(((LivingEntity)entitypatch.getOriginal()).yRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);
        float dpx = transformMatrix.m30 + (float)((LivingEntity)entitypatch.getOriginal()).getX();
        float dpy = transformMatrix.m31 + (float)((LivingEntity)entitypatch.getOriginal()).getY();
        float dpz = transformMatrix.m32 + (float)((LivingEntity)entitypatch.getOriginal()).getZ();
        

        return new Vec3((double)dpx, (double)dpy, (double)dpz);
    }

    public static final AnimationEvent.AnimationEventConsumer TORMENT_GROUNDSLAM = (entitypatch, self, params) -> {
        Vec3 position = ((LivingEntity)entitypatch.getOriginal()).position();
        OpenMatrix4f modelTransform = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(1.0F), Armatures.BIPED.toolR).mulFront(OpenMatrix4f.createTranslation((float)position.x, (float)position.y, (float)position.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(entitypatch.getModelMatrix(1.0F))));
        Vec3 weaponEdge = OpenMatrix4f.transform(modelTransform, (new Vec3f(0.0F, -0.0F, -1.4F)).toDoubleVector());
        Level level = ((LivingEntity)entitypatch.getOriginal()).level();
        Vec3 floorPos = getfloor(entitypatch, self, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.toolR);
        Method_114514.play_sound(entitypatch.getOriginal().level(),entitypatch.getOriginal().getX(),entitypatch.getOriginal().getY(),entitypatch.getOriginal().getZ(),"epicfight:sfx.ground_slam");
        weaponEdge = new Vec3(weaponEdge.x, floorPos.y, weaponEdge.z);
        double x = entitypatch.getOriginal().getX(),y = entitypatch.getOriginal().getY(),z = entitypatch.getOriginal().getZ();
        Level world = entitypatch.getOriginal().level();
        final Vec3 _center = new Vec3(x, y, z);
        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))    .toList();
        for (Entity entityiterator : _entfound) {
            if(entityiterator != entitypatch.getOriginal() && entityiterator instanceof LivingEntity livingEntity && !world.isClientSide){
                livingEntity.hurt(livingEntity.level.damageSources().mobAttack(entitypatch.getOriginal()),livingEntity.getMaxHealth() + 40);
                BuxinMod.queueServerWork(5,() -> {
                    if(livingEntity.isAlive()) {
                        AnimationPlayer.playAnimation(livingEntity, Animations.BIPED_COMMON_NEUTRALIZED);
                    }
                });
                livingEntity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 10, 10, false, false));
            }
        }
        System.err.println("heavy");
        ((LivingEntity)entitypatch.getOriginal()).level().addParticle((ParticleOptions)EpicFightParticles.GROUND_SLAM.get(), floorPos.x, (double)((int)floorPos.y + 1), floorPos.z, 1.0, 50.0, 1.0);
        LevelUtil.circleSlamFracture((LivingEntity)entitypatch.getOriginal(), level, weaponEdge, 3.5, true, false);
    };
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put("buxin", BuxinAnimations::build);
    }
    public static void build() {
        HumanoidArmature humanoidarmature = Armatures.BIPED;
        HumanoidArmature biped = Armatures.BIPED;
        SWORD_DUAL_AUTO1 = (new NormalAnimation(0.05F, "biped/combat/sword_dual_auto1", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.1F, 0.35F, 0.45F, 0.4F, 0.5F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.0F, 0.6F, 0.7F, 0.8F, 0.9F, biped.toolR, (Collider)null)})).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        SWORD_DUAL_AUTO2 = (new NormalAnimation(0.05F, "biped/combat/sword_dual_auto2", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.1F, 0.35F, 0.5F, 0.4F, 0.5F, biped.toolR, (Collider)null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 0.9F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null)})).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD);
        Tachi_DIE = (new PutEntityToDieAnimation(0.01F, "biped/run/other_tachi", biped)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 4.0F})).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false);
        Tachi_BEEN_ATTACK = (new PutEntityToDieAnimation(0.01F, "biped/run/other_tachi_been_attack", biped)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 4.0F})).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false);
        DRINK_OFFHAND = new StaticAnimation(0.35F, true, "biped/other/drink_offhand", humanoidarmature);

        TRIDENT = (new BasicAttackAnimation(0.1F, "biped/buxin/blue_demon_trident_holiday", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 2.3F, 2.3F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 2.3F, 2.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));

        TRIDENT_HOLIDAY = (new PutEntityToDieAnimation(0.01F, "biped/skill/trident_holiday", biped)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 4.0F})).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false);

        KILL_SELF = (new PutEntityToDieAnimation(0.01F, "biped/skill/kill_self", biped)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 4.0F})).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false);

        HB_born = (new BasicAttackAnimation(0.1F, "biped/from_down_to_up", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 2.3F, 2.3F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 2.3F, 2.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        Super_Whirlwind = (new BasicAttackAnimation(0.41F, "biped/combat/super_whirlwind", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, biped.toolL, (Collider)null), (new AttackAnimation.Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, biped.toolL, (Collider)null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE)})).addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F);
        Legendary_Sword_Heavy_Attack = (new BasicAttackAnimation(0.05F, 0.5F, 0.7F, 1.2F, BuxinModColliders.TORMENT_BERSERK_AIRSLAM, biped.rootJoint, "biped/combat/legendary_sword_heavy_attack", biped)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create(new float[0]))).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F)).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.5F).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 0.3F})).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elapsedTime,e) -> {
            if (elapsedTime >= 0.3F && elapsedTime < 0.35F) {
                float dpx = (float)((LivingEntity)entitypatch.getOriginal()).getX();
                float dpy = (float)((LivingEntity)entitypatch.getOriginal()).getY();
                float dpz = (float)((LivingEntity)entitypatch.getOriginal()).getZ();

                for(BlockState block = ((LivingEntity)entitypatch.getOriginal()).level().getBlockState(new BlockPos((int)dpx, (int)dpy, (int)dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = ((LivingEntity)entitypatch.getOriginal()).level().getBlockState(new BlockPos((int)dpx, (int)dpy, (int)dpz))) {
                    --dpy;
                }

                float distanceToGround = (float)Math.max(Math.abs(((LivingEntity)entitypatch.getOriginal()).getY() - (double)dpy) - 1.0, 0.0);
                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
            } else {
                return 1.0F;
            }
        }).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.0F, (entitypatch, self, params) -> {
            if (entitypatch instanceof PlayerPatch) {
                ((PlayerPatch)entitypatch).setStamina(((PlayerPatch)entitypatch).getStamina() - 2.0F);
            }
            Method_114514.entity_use_command(entitypatch.getOriginal(),"/effect give @s minecraft:strength 10 20");
            LivingEntity entity = entitypatch.getOriginal();
            final Vec3 _center0 = new Vec3(entity.getX(),entity.getY(),entity.getZ());
            List<Entity> _entfound0 = entity.level().getEntitiesOfClass(Entity.class, new AABB(_center0, _center0).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center0))).toList();
            for (Entity entityiterator : _entfound0) {
                BuxinMod.queueServerWork(10,() -> {
                    if(entityiterator != entity){
                        if(entityiterator instanceof LivingEntity) {
                            entityiterator.hurt(entity.level.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.8 + 10));
                            BuxinMod.queueServerWork(5,() -> {
                                if (entityiterator.isAlive()) {
                                    AnimationPlayer.playAnimation(entityiterator, Animations.BIPED_COMMON_NEUTRALIZED);
                                }
                            });

                        }
                    }
                });
            }
        }, AnimationEvent.Side.SERVER), AnimationEvent.TimeStampedEvent.create(0.6F, TORMENT_GROUNDSLAM, AnimationEvent.Side.SERVER)});
        DEATH = new StaticAnimation(true, "biped/other/death_idle", humanoidarmature);

        D3 = (new BasicAttackAnimation(0.16F, "biped/combat/d3", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        D4 = (new BasicAttackAnimation(0.1F, "biped/combat/d4", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AttackPhaseProperty.SWING_SOUND, BuxinModSounds.SWORD_WHOOSH.get()).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG);
        HB_getpower = (new BasicAttackAnimation(0.1F, "biped/buxin/herobrine_get_power",  biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.1F, 0.3F, 0.6F, 0.6F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3F);
        HB_sendpower = (new BasicAttackAnimation(0.0F, "biped/buxin/herobrine_send_power",  biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3F);
        SWORD_DIE = (new PutEntityToDieAnimation(0.01F, "biped/run/other_attack", biped)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 4.0F})).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false);
        DUAL_SWORD_DIE = (new PutEntityToDieAnimation(0.01F, "biped/run/other_attack_dual", biped)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 4.0F})).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false);
        GREATSWORD_DIE = (new SpecialAnimation(0.1F, "biped/run/greatsword_die", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.25F, 0.25F, biped.toolR, (Collider)null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.05F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new AttackAnimation.Phase(0.25F, 0.85F, 1.05F, 1.9F, 1.9F, biped.toolR, EXECUTE)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.05F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new AttackAnimation.Phase(1.9F, 2.4F, 2.6F, 2.6F, Float.MAX_VALUE, Float.MAX_VALUE, biped.toolR, EXECUTE_SECOND)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F)).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))})).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true);
        PUTENTITYTODIEBEENATTACK = (new LongHitAnimation(0.1F, "biped/run/other_been_attack", biped)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_SWORD_DASH = (new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, (Collider)null, biped.toolR, "biped/combat/dual_sword_dash", biped)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);
        DUAL_SWORD_RUN = new MovementAnimation(true, "biped/combat/run_hold", biped);
        DUAL_SWORD_AUTO_1 = (new BasicAttackAnimation(0.1F, "biped/combat/dual_sword_auto__1", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_SWORD_AUTO_2 = (new BasicAttackAnimation(0.1F, "biped/combat/dual_sword_auto__2", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.05F, 0.4F, 0.8F, 1.167F, 2.5F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.1F, 1.2F, 1.3F, 1.5F, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.1F, 1.4F, 1.5F, 2.1F, biped.toolL, (Collider)null)})).addProperty(AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SIMPLE_SWORD_AUTO_2 = (new BasicAttackAnimation(0.1F, "biped/combat/simple_sword_auto_2", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.05F, 0.4F, 0.8F, 1.167F, 2.5F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.1F, 1.2F, 1.3F, 1.5F, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.1F, 1.4F, 1.5F, 2.1F, biped.toolL, (Collider)null)})).addProperty(AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SIMPLE_SWORD_AUTO_3 = (new BasicAttackAnimation(0.1F, "biped/combat/simple_sword_auto_3", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.05F, 0.4F, 0.8F, 1.167F, 2.5F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.1F, 1.2F, 1.3F, 1.5F, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.1F, 1.4F, 1.5F, 2.1F, biped.toolL, (Collider)null)})).addProperty(AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        KICK = (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, BuxinModColliders.KICK, biped.legL, "biped/combat/kick1", biped)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get()).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get()).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        KICK2 = (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, BuxinModColliders.KICK, biped.legL, "biped/combat/kick2", biped)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get()).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get()).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        KICK3 = (new KickAttackAnimation(0.05F, "biped/combat/kick3", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.4F, 0.45F, 0.49F, 0.49F, biped.legL, BuxinModColliders.KICK), new AttackAnimation.Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F, biped.legL, BuxinModColliders.KICK), new AttackAnimation.Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F, biped.legL, BuxinModColliders.KICK), new AttackAnimation.Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F, biped.legL, BuxinModColliders.KICK), new AttackAnimation.Phase(0.79F, 0.8F, 0.85F, 0.9F, Float.MAX_VALUE, biped.legL, BuxinModColliders.KICK)})).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 2).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 3).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 4).addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get()).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 1).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 1).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 1).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 1).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SingleSparkCanStartPrairieFire = (new BasicAttackAnimation(0.1F, "biped/skill/single_spark_can_start_prairie_fire", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 2.3F, 2.3F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 2.3F, 2.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        OFF_SHILED_KICK = (new BasicAttackAnimation(0.11F, 0.0F, 0.11F, 0.26F, InteractionHand.OFF_HAND, (Collider)null, biped.toolL, "biped/combat/off_shield_kick", biped)).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT);
        SPECIAL_ATTACK = new StaticAnimation(true, "biped/buxin/special_attack", biped);
        HEROBRINE3_BORN = new StaticAnimation(true, "biped/buxin/herobrine3_born", biped);
        SUMMONING = (new BasicAttackAnimation(0.1F, "biped/buxin/summon", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 2.3F, 2.3F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 2.3F, 2.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        LegendarySwordRun = new StaticAnimation(true, "biped/run/legendarysword_run", biped);
        SNAKE_BLADE_SKILL = (new BasicAttackAnimation(0.0F, "biped/skill/snake_blade_skill",  biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        SWORD_DUAL_AUTO_1 = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_1", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.967F, 0.967F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        DIG = (new BasicAttackAnimation(0.1F, "biped/combat/dig", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.967F, 0.967F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        BOW_SHOOT = new RangedAttackAnimation(0.11F, 0.1F, 0.45F, 0.49F, 1.25F, (Collider)null, biped.rootJoint, "biped/bow/bow_aim_mid", biped);
        SWORD_DUAL_AUTO_2 = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_2", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.967F, 0.967F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        BlueDemonElectric = (new BasicAttackAnimation(0.1F, "biped/skill/blue_demon_electric",  biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3F);
        BlueDemonElectricShort = (new BasicAttackAnimation(0.1F, "biped/skill/blue_demon_electric_short",  biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 3F);
        SWORD_DUAL_AUTO_1_BETTER = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_1_better",  biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.1F, 0.3F, 0.6F, 0.6F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);
        SWORD_DUAL_AUTO_2_BETTER = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_2_better",  biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.1F, 0.3F, 0.6F, 0.6F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
        SWORD_DUAL_AUTO_3_BETTER = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_3_better", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.1F, 0.3F, 0.6F, 0.6F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
        SWORD_DUAL_AUTO_4_BETTER = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_4_better", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.0F, 0.1F, 0.3F, 0.6F, 0.6F, biped.toolR, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
        SWORD_DUAL_AUTO_3 = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_3", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.967F, 0.967F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        SWORD_DUAL_AUTO_4 = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_4", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.967F, 0.967F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        SWORD_DUAL_AUTO_5 = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_5", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.967F, 0.967F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        SWORD_DUAL_AUTO_6 = (new BasicAttackAnimation(0.1F, "biped/combat/sword_dual_auto_6", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.967F, 0.967F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        Attack3 = (new BasicAttackAnimation(0.1F, "biped/combat/attack3", humanoidarmature, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.25F, 0.25F, humanoidarmature.toolR, (Collider)null)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.05F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new AttackAnimation.Phase(0.25F, 0.65F, 0.65F, 0.9F, 0.9F, humanoidarmature.toolR, LLL)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.05F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), (new AttackAnimation.Phase(1.9F, 2.4F, 2.6F, 2.6F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidarmature.toolR, EXECUTE_SECOND)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F)).addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))})).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.12F, ReuseableEvents2.GROUNDSLAM_SMALLEST, AnimationEvent.Side.CLIENT), AnimationEvent.TimeStampedEvent.create(2.78F, GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)}).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        Special_greatsword_Been_Attack = (new LongHitAnimation(0.1F, "biped/combat/execute_greatsword_hit", humanoidarmature)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false).addState(EntityState.MOVEMENT_LOCKED, false).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false);

        LEGENDARY_SWORD_AUTO_1= (new BuxinAttackAnimation(0.15F, 0.1F, 0.15F, 0.55F, 0.55F, LegendarySword_Attack, biped.toolR, "biped/combat/legendary_sword_auto_1", biped)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.2F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT).addProperty(AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLADE_RUSH_FINISHER.get()).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elapsedTime,sb) -> {
            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                LivingEntity livingentity = (LivingEntity)entitypatch.getOriginal();
                Level level = livingentity.level();
                float dpx = (float)livingentity.getX();
                float dpy = (float)livingentity.getY();
                float dpz = (float)livingentity.getZ();
                if (level instanceof ServerLevel) {
                    for(int x = -2; x <= 2; ++x) {
                        for(int z = -2; z <= 2; ++z) {
                            BlockPos blockPos_1 = new BlockPos((int) (dpx + (float)x), (int) dpy, (int) (dpz + (float)z));
                            BlockPos blockPos_2 = new BlockPos((int) (dpx + (float)x), (int) (dpy + 1.0F), (int) (dpz + (float)z));
                            BlockState blockState_1 = level.getBlockState(blockPos_1);
                            BlockState blockState_2 = level.getBlockState(blockPos_2);
                            if (CheckBooleanHandler.isDestructible(blockState_1)) {
                                level.destroyBlock(blockPos_1, true);
                            }

                            if (CheckBooleanHandler.isDestructible(blockState_2)) {
                                level.destroyBlock(blockPos_2, true);
                            }
                        }
                    }
                }
            }

            return 1.0F;
        });
        LEGENDARY_SWORD_AUTO_2= (new BuxinAttackAnimation(0.15F, 0.1F, 0.15F, 0.55F, 0.55F, LegendarySword_Attack, biped.toolR, "biped/combat/legendary_sword_auto_2", biped)).addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.2F)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT).addProperty(AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLADE_RUSH_FINISHER.get()).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elapsedTime,sb) -> {
            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                LivingEntity livingentity = (LivingEntity)entitypatch.getOriginal();
                Level level = livingentity.level();
                float dpx = (float)livingentity.getX();
                float dpy = (float)livingentity.getY();
                float dpz = (float)livingentity.getZ();
                if (level instanceof ServerLevel) {
                    for(int x = -2; x <= 2; ++x) {
                        for(int z = -2; z <= 2; ++z) {
                            BlockPos blockPos_1 = new BlockPos((int) (dpx + (float)x), (int) dpy, (int) (dpz + (float)z));
                            BlockPos blockPos_2 = new BlockPos((int) (dpx + (float)x), (int) (dpy + 1.0F), (int) (dpz + (float)z));
                            BlockState blockState_1 = level.getBlockState(blockPos_1);
                            BlockState blockState_2 = level.getBlockState(blockPos_2);
                            if (CheckBooleanHandler.isDestructible(blockState_1)) {
                                level.destroyBlock(blockPos_1, true);
                            }

                            if (CheckBooleanHandler.isDestructible(blockState_2)) {
                                level.destroyBlock(blockPos_2, true);
                            }
                        }
                    }
                }
            }

            return 1.0F;
        });
    }
    public static class ReuseableEvents2 {
        public static final AnimationEvent.AnimationEventConsumer FAST_SPINING = (livingentitypatch, staticanimation, aobject) -> {
            ((LivingEntity)livingentitypatch.getOriginal()).level().playSound((Player)livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), (SoundEvent)EpicFightSounds.WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 1.1F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
        };
        public static final AnimationEvent.AnimationEventConsumer LOOPED_FALLING_MOVE = (livingentitypatch, staticanimation, aobject) -> {
            float f = (float)((LivingEntity)livingentitypatch.getOriginal()).getX();
            float f1 = (float)((LivingEntity)livingentitypatch.getOriginal()).getY();
            float f2 = (float)((LivingEntity)livingentitypatch.getOriginal()).getZ();

            for(BlockState blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int) f, (int) f1, (int) f2))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int) f, (int) f1, (int) f2)))) {
                --f1;
            }

            f1 = (float)((int)f1);
            float f3 = 4.0F;
            float f4 = 0.01F;
            float f5 = (float)((double)f3 * (1.0 - Math.exp((double)(-f4) * (((LivingEntity)livingentitypatch.getOriginal()).getY() - (double)f1))));
            if (((LivingEntity)livingentitypatch.getOriginal()).getY() - (double)f1 > 4.0 && ((LivingEntity)livingentitypatch.getOriginal()).getDeltaMovement().y < -0.07999999821186066) {
                LivingEntity livingentity = (LivingEntity)livingentitypatch.getOriginal();
                Vec3f vec3f = new Vec3f(0.5F * f5, 0.0F, 0.0F);
                OpenMatrix4f openmatrix4f = (new OpenMatrix4f()).rotate(-((float)Math.toRadians((double)(((LivingEntity)livingentitypatch.getOriginal()).yBodyRotO + 90.0F))), new Vec3f(0.0F, 1.0F, 0.0F));
                OpenMatrix4f.transform3v(openmatrix4f, vec3f, vec3f);
                livingentity.move(MoverType.SELF, vec3f.toDoubleVector());
            }

        };
        public static final AnimationEvent.AnimationEventConsumer LOOPED_FALLING = (livingentitypatch, staticanimation, aobject) -> {
            float f = (float)((LivingEntity)livingentitypatch.getOriginal()).getX();
            float f1 = (float)((LivingEntity)livingentitypatch.getOriginal()).getY();
            float f2 = (float)((LivingEntity)livingentitypatch.getOriginal()).getZ();

            for(BlockState blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int)f, (int)f1, (int)f2))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int)f, (int)f1, (int)f2)))) {
                --f1;
            }

            f1 = (float)((int)f1);
            if (((LivingEntity)livingentitypatch.getOriginal()).getY() - (double)f1 > 4.0 && ((LivingEntity)livingentitypatch.getOriginal()).getDeltaMovement().y < -0.07999999821186066) {
                livingentitypatch.getAnimator().getPlayerFor(staticanimation).setElapsedTimeCurrent(livingentitypatch.getAnimator().getPlayerFor(staticanimation).getElapsedTime() - 0.2F);
            } else if (((LivingEntity)livingentitypatch.getOriginal()).getY() - (double)f1 > 1.0) {
                ((LivingEntity)livingentitypatch.getOriginal()).move(MoverType.SELF, new Vec3(0.0, -1.0, 0.0));
            }

        };
        public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM = (livingentitypatch, staticanimation, aobject) -> {
            Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).position();
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBindedTransformFor(livingentitypatch.getAnimator().getPose(1.0F), Armatures.BIPED.toolR).mulFront(OpenMatrix4f.createTranslation((float)vec3.x, (float)vec3.y, (float)vec3.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(livingentitypatch.getModelMatrix(1.0F))));
            Vec3 vec31 = OpenMatrix4f.transform(openmatrix4f, (new Vec3f(0.0F, -0.0F, -1.4F)).toDoubleVector());
            Level level = ((LivingEntity)livingentitypatch.getOriginal()).level();
            Vec3 vec32 = getfloor(livingentitypatch, staticanimation, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.toolR);
            BlockState blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int) vec32.x, (int) vec32.y, (int) vec32.z)));
            level.playLocalSound(vec32.x, vec32.y, vec32.z, blockstate.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : (SoundEvent)EpicFightSounds.GROUND_SLAM.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            vec31 = new Vec3(vec31.x, vec32.y, vec31.z);
            ((LivingEntity)livingentitypatch.getOriginal()).level().addParticle((ParticleOptions) EpicFightParticles.GROUND_SLAM.get(), vec32.x, (double)((int)vec32.y + 1), vec32.z, 1.0, 50.0, 1.0);
            LevelUtil.circleSlamFracture((LivingEntity)livingentitypatch.getOriginal(), level, vec31, 3.5, true, false);
        };
        public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM_SMALL = (livingentitypatch, staticanimation, aobject) -> {
            Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).position();
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBindedTransformFor(livingentitypatch.getAnimator().getPose(1.0F), Armatures.BIPED.toolR).mulFront(OpenMatrix4f.createTranslation((float)vec3.x, (float)vec3.y, (float)vec3.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(livingentitypatch.getModelMatrix(1.0F))));
            Vec3 vec31 = OpenMatrix4f.transform(openmatrix4f, (new Vec3f(0.0F, 0.0F, -1.4F)).toDoubleVector());
            Level level = ((LivingEntity)livingentitypatch.getOriginal()).level();
            Vec3 vec32 = getfloor(livingentitypatch, staticanimation, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.toolR);
            BlockState blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int) vec32.x, (int) vec32.y, (int) vec32.z)));
            level.playLocalSound(vec32.x, vec32.y, vec32.z, blockstate.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : (SoundEvent)EpicFightSounds.GROUND_SLAM.get(), SoundSource.BLOCKS, 1.5F, 1.5F - ((new Random()).nextFloat() - 0.5F) * 0.2F, false);
            vec31 = new Vec3(vec31.x, vec32.y, vec31.z);
            ((LivingEntity)livingentitypatch.getOriginal()).level().addParticle((ParticleOptions)EpicFightParticles.GROUND_SLAM.get(), vec32.x, (double)((int)vec32.y + 1), vec32.z, 0.7, 35.0, 0.7);
            LevelUtil.circleSlamFracture((LivingEntity)livingentitypatch.getOriginal(), level, vec31, 2.0, true, false);
        };

        public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM_SMALL_LEFT = (livingentitypatch, staticanimation, aobject) -> {
            Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).position();
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBindedTransformFor(livingentitypatch.getAnimator().getPose(1.0F), Armatures.BIPED.toolL).mulFront(OpenMatrix4f.createTranslation((float)vec3.x, (float)vec3.y, (float)vec3.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(livingentitypatch.getModelMatrix(1.0F))));
            Vec3 vec31 = OpenMatrix4f.transform(openmatrix4f, (new Vec3f(0.0F, 0.0F, 0.0F)).toDoubleVector());
            Level level = ((LivingEntity)livingentitypatch.getOriginal()).level();
            Vec3 vec32 = getfloor(livingentitypatch, staticanimation, new Vec3f(0.0F, 0.0F, 0.0F), Armatures.BIPED.toolL);
            BlockState blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int) vec32.x, (int) vec32.y, (int) vec32.z)));
            level.playLocalSound(vec32.x, vec32.y, vec32.z, blockstate.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : (SoundEvent)EpicFightSounds.GROUND_SLAM.get(), SoundSource.BLOCKS, 1.5F, 1.5F - ((new Random()).nextFloat() - 0.5F) * 0.2F, false);
            vec31 = new Vec3(vec31.x, vec32.y, vec31.z);
            ((LivingEntity)livingentitypatch.getOriginal()).level().addParticle((ParticleOptions)EpicFightParticles.GROUND_SLAM.get(), vec32.x, (double)((int)vec32.y + 1), vec32.z, 0.4, 30.0, 0.4);
            LevelUtil.circleSlamFracture((LivingEntity)livingentitypatch.getOriginal(), level, vec31, 1.5, true, false);
        };
        public static final AnimationEvent.AnimationEventConsumer GROUNDSLAM_SMALLEST = (livingentitypatch, staticanimation, aobject) -> {
            Vec3 vec3 = ((LivingEntity)livingentitypatch.getOriginal()).position();
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBindedTransformFor(livingentitypatch.getAnimator().getPose(1.0F), Armatures.BIPED.toolR).mulFront(OpenMatrix4f.createTranslation((float)vec3.x, (float)vec3.y, (float)vec3.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(livingentitypatch.getModelMatrix(1.0F))));
            Vec3 vec31 = OpenMatrix4f.transform(openmatrix4f, (new Vec3f(0.0F, 0.0F, -1.4F)).toDoubleVector());
            Level level = ((LivingEntity)livingentitypatch.getOriginal()).level();
            Vec3 vec32 = getfloor(livingentitypatch, staticanimation, new Vec3f(0.0F, 0.0F, -1.4F), Armatures.BIPED.toolR);
            BlockState blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int) vec32.x, (int) vec32.y, (int) vec32.z)));
            level.playLocalSound(vec32.x, vec32.y, vec32.z, blockstate.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : (SoundEvent)EpicFightSounds.GROUND_SLAM_SMALL.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            vec31 = new Vec3(vec31.x, vec32.y, vec31.z);
            ((LivingEntity)livingentitypatch.getOriginal()).level().addParticle((ParticleOptions)EpicFightParticles.GROUND_SLAM.get(), vec32.x, (double)((int)vec32.y + 1), vec32.z, 0.1, 1.2, 0.1);
            LevelUtil.circleSlamFracture((LivingEntity)livingentitypatch.getOriginal(), level, vec31, 1.0, true, false);
        };

        public ReuseableEvents2() {
        }

        public static Vec3 getfloor(LivingEntityPatch<?> livingentitypatch, StaticAnimation staticanimation, Vec3f vec3f, Joint joint) {
            OpenMatrix4f openmatrix4f = livingentitypatch.getArmature().getBindedTransformFor(livingentitypatch.getAnimator().getPose(1.0F), joint);
            openmatrix4f.translate(vec3f);
            OpenMatrix4f openmatrix4f1 = (new OpenMatrix4f()).rotate(-((float)Math.toRadians((double)(((LivingEntity)livingentitypatch.getOriginal()).yRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F));
            OpenMatrix4f.mul(openmatrix4f1, openmatrix4f, openmatrix4f);
            float f = openmatrix4f.m30 + (float)((LivingEntity)livingentitypatch.getOriginal()).getX();
            float f1 = openmatrix4f.m31 + (float)((LivingEntity)livingentitypatch.getOriginal()).getY();
            float f2 = openmatrix4f.m32 + (float)((LivingEntity)livingentitypatch.getOriginal()).getZ();

            for(BlockState blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int)f, (int)f1, (int)f2))); (blockstate.getBlock() instanceof BushBlock || blockstate.isAir()) && !blockstate.is(Blocks.VOID_AIR); blockstate = ((LivingEntity)livingentitypatch.getOriginal()).level().getBlockState(new BlockPos(new Vec3i((int)f, (int)f1, (int)f2)))) {
                --f1;
            }

            return new Vec3((int)f, (int)f1, (int)f2);
        }
    }
}