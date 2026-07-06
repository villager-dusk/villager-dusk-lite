
package net.mcreator.buxin.entity.dragon.baby;

import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.PlayMessages;

import java.util.UUID;

public class BabyEnderDragonEntity extends FlyingMob {
    private UUID followTargetUUID;
    private Entity followTarget;
    private int breathCooldown = 0;
    private int lifeLeft = 600;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public void setFollowTarget(Entity followTarget) {
        this.followTarget = followTarget;
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public UUID getFollowTargetUUID() {
        return followTargetUUID;
    }

    public BabyEnderDragonEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
        this(BuxinModEntities.BABY_ENDER_DRAGON.get(), level);
    }

    public BabyEnderDragonEntity(EntityType<BabyEnderDragonEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setNoGravity(true);
        this.setPersistenceRequired();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanPassDoors(true);
        nav.setCanFloat(true);
        return nav;
    }
    
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }
    public static void init() {
        DungeonHooks.addDungeonMob(BuxinModEntities.BABY_ENDER_DRAGON.get(), 180);
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive();
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    copyOwnerLook(followTarget);
                    Vec3 posBehind3D = posBehind3D(followTarget, 1.0D, 2.0D, 1.0D);
                    getNavigation().moveTo(posBehind3D.x, posBehind3D.y, posBehind3D.z, 9999.0D);
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 1.0D;
            }
        });
    }

    @Override
    public boolean hurt(DamageSource damagesource, float f) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return false;
    }

    public static Vec3 posBehind3D(Entity p, double back, double upFromGround, double right) {
        Vec3 look = p.getLookAngle().normalize();

        Vec3 forwardXZ = new Vec3(look.x, 0, look.z);
        if (forwardXZ.lengthSqr() < 1e-6) {
            float yaw = p.getYRot() * ((float)Math.PI/180F);
            forwardXZ = new Vec3(-Mth.sin(yaw), 0, Mth.cos(yaw));
        }
        forwardXZ = forwardXZ.normalize();

        Vec3 rightVec = new Vec3(-forwardXZ.z, 0, forwardXZ.x);

        double tx = p.getX() - forwardXZ.x * back + rightVec.x * right;
        double tz = p.getZ() - forwardXZ.z * back + rightVec.z * right;

        int blockY = Mth.floor(p.getY());
        double ty = blockY + upFromGround;

        return new Vec3(tx, ty, tz);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BodyRotationControl(this) {
            @Override
            public void clientTick() {
                BabyEnderDragonEntity self = BabyEnderDragonEntity.this;
                self.yHeadRot = self.getYRot();
                self.yBodyRot = self.getYRot();
            }
        };
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.followTarget != null && this.followTarget.isAlive()) {
            copyOwnerLook(this.followTarget);
        }
    }

    public static LivingEntity getNearestLivingEntity(Level level, Entity sourceEntity, double range) {
        AABB searchBox = sourceEntity.getBoundingBox().inflate(range);

        return level.getNearestEntity(
                level.getEntitiesOfClass(LivingEntity.class, searchBox, e -> e != sourceEntity && !(e instanceof BabyEnderDragonEntity) && e.isAlive()),
                TargetingConditions.DEFAULT,
                (LivingEntity) sourceEntity,
                sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ()
        );
    }

    private void copyOwnerLook(Entity owner) {
        float targetYaw   = owner.getYRot();
        float targetPitch = owner.getXRot() * 0.35F;

        this.setYRot(Mth.approachDegrees(this.getYRot(), targetYaw, 10.0F));
        this.setXRot(Mth.approachDegrees(this.getXRot(), targetPitch, 8.0F));

        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            this.setupAnimationStates();
        }
        if(this.tickCount == 1){
            //Method_114514.entity_use_command(this,"/impactful @p shake 400 5 5");
        }
        /*
        if(this.tickCount == 200){
            Method_114514.entity_use_command(this,"/particle epicfight:force_field_end");
            VFXTool.addVFXParticle(this.position(), BuxinMod.MODID,"herobrine_portal",this.level());
            Method_114514.play_sound(this.level(),this.getX(),this.getY(),this.getZ(),"epicfight:entity.enderdragon.dragon_breath");
            BuxinMod.queueServerWork(50, this::discard);
        }

         */
        if (!level().isClientSide()) {
            if (--lifeLeft <= 0) {
                this.discard();
                return;
            }
            if (followTarget == null && followTargetUUID != null) {
                if (!(this.level() instanceof ServerLevel serverLevel)) return;
                var server = serverLevel.getServer();
                var serverPlayer = server.getPlayerList().getPlayer(this.followTargetUUID);

                if (serverPlayer != null) {
                    this.followTarget = serverPlayer;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                followTarget = null;
                followTargetUUID = null;
                this.discard();
            }
            if (followTarget != null && followTarget.isAlive()) {
                double distanceSq = this.distanceToSqr(followTarget);

                if (distanceSq > 4.0D) {
                    Vec3 posBehind3D = posBehind3D(followTarget, 1.0D, 2.0D, 1.0D);
                    this.teleportTo(
                            posBehind3D.x,
                            posBehind3D.y,
                            posBehind3D.z
                    );
                }

                if (breathCooldown <= 0 && followTarget instanceof LivingEntity) {
                    LivingEntity target;
                    target = ((LivingEntity) followTarget).getLastHurtMob();
                    if (target == null || !target.isAlive()) {
                        if(followTarget instanceof LivingEntity) {
                            target = ((LivingEntity) followTarget).getLastHurtByMob();
                        }
                    }
                    if (target == null || !target.isAlive()) {
                        target = getNearestLivingEntity(followTarget.level(), followTarget, 30.0D);
                    }
                    if (target != null) {
                        Vec3 look = this.getLookAngle().normalize();
                        double dist = 0.5;
                        double sx = this.getX() + look.x * dist;
                        double sy = this.getEyeY();
                        double sz = this.getZ() + look.z * dist;

                        BabyDragonBeamEntity beam = new BabyDragonBeamEntity(
                                BuxinModEntities.BABY_DRAGON_BEAM.get(), level(),
                                this,
                                target,
                                sx, sy, sz,
                                100, 2
                        );
                        level().addFreshEntity(beam);
                        breathCooldown = 120;
                    } else {
                    }
                }
            }
            if (breathCooldown > 0) breathCooldown--;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (followTargetUUID != null) {
            tag.putUUID("FollowTarget", followTargetUUID);
            tag.putInt("BreathCooldown", breathCooldown);
            tag.putInt("LifeLeft", lifeLeft);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("FollowTarget")) {
            followTargetUUID = tag.getUUID("FollowTarget");
        }
        breathCooldown = tag.getInt("BreathCooldown");
        lifeLeft = tag.contains("LifeLeft") ? tag.getInt("LifeLeft") : 600;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MAX_HEALTH, 150.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 3.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
        return builder;
    }
}
