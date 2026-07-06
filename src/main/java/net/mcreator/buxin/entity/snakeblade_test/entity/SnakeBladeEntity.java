
package net.mcreator.buxin.entity.snakeblade_test.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.SnakeBladeConfig;
import net.mcreator.buxin.entity.SnakeBladeEntityFather;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SnakeBladeEntity extends SnakeBladeEntityFather {
    private final int MaxTargetValue = SnakeBladeConfig.SnakeBladeMaxTarget.get();
    private float curveStrength = 0.5f;
    private boolean hasCurveEffect = true;
    private boolean hasTwistEffect = false;

    public float getCurveStrength() {
        return curveStrength;
    }

    public boolean hasCurveEffect() {
        return hasCurveEffect;
    }
    public boolean hasTwistEffect() {
        return hasTwistEffect;
    }

    public SnakeBladeEntity(EntityType<SnakeBladeEntity> type, Level level) {
        super(type, level);
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(CREATOR_ID, Optional.empty());
        this.entityData.define(FROM_ID, -1);
        this.entityData.define(TARGET_COUNT, 0);
        this.entityData.define(CURRENT_TARGET_ID, -1);
        this.entityData.define(PROGRESS, 0F);
        this.entityData.define(DAMAGE, 3F);
        this.entityData.define(RETRACTING, false);
        this.entityData.define(HAS_BLADE, true);
        this.entityData.define(ENCHANTED, false);
    }
    @Override
    public void tick() {
        float progress = this.getProgress();
        this.prevProgress = progress;
        super.tick();
        Entity creator = getCreatorEntity();
        if(this.tickCount == 1) {
            BuxinMod.queueServerWork(125, this::discard);
            Method_114514.play_sound(this.level, this.getX(), this.getY(), this.getZ(), "buxin:obsidian_hit");
        }
        final Vec3 _center = new Vec3(this.getX(),this.getY(),this.getZ());
        List<Entity> _entfound = this.level().getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
        for (Entity entityiterator : _entfound) {
            if(entityiterator instanceof LivingEntity && Math.random() < 0.05) {
                if(((LivingEntity) entityiterator).getMainHandItem().getItem() != BuxinModItems.SUPER_SNAKE_SWORD.get()){
                    entityiterator.hurt(this.damageSources().generic(), (float) (10 + Math.random() * 4));
                    Method_114514.entity_use_command(entityiterator,"/particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1");
                }
            }
        }
        if (Math.random() < 0.001) {
            Method_114514.play_sound(this.level,this.getX(),this.getY(),this.getZ(),"buxin:dian_hu");
        }
        if (this.level() instanceof ServerLevel && Math.random() < 0.04)
            Method_114514.entity_use_command(this,"/particle buxin:niubi_22");
        if(!this.isRetracting() && progress < MAX_EXTEND_TIME){
            this.setProgress(progress + 1);
        }
        if(this.isRetracting() && progress > 0F){
            this.setProgress(progress - 1);
        }
        if(this.isRetracting() && progress == 0F) {
            Entity from = this.getFromEntity();
            if(from instanceof SnakeBladeEntity snakeBladeFragment){
                snakeBladeFragment.setRetracting(true);
            }
            Method_114514.play_sound(this.level, this.getX(), this.getY(), this.getZ(), "buxin:obsidian_hit");
            this.remove(Entity.RemovalReason.DISCARDED);
        }
        Vec3 vector3d = this.getDeltaMovement();
        if(!this.level().isClientSide){
            if(!hasChained){
                if(this.getTargetsHit() > MaxTargetValue){
                    this.setRetracting(true);
                } else if(creator instanceof LivingEntity && this.getProgress() >= MAX_EXTEND_TIME) {
                    Entity closestValid = null;
                    for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(SnakeBladeConfig.SnakeBladeMaxRange.get()))) {
                        if (!entity.equals(creator) && !previouslyTouched.contains(entity) && isValidTarget((LivingEntity) creator, entity) && this.hasLineOfSight(entity)) {
                            if (closestValid == null || this.distanceTo(entity) < this.distanceTo(closestValid)) {
                                closestValid = entity;
                            }
                        }
                    }
                    if(closestValid instanceof LivingEntity){
                        createChain(closestValid);
                        closestValid.hurt(this.damageSources().generic(),7);
                        if(((LivingEntity) closestValid).getMainHandItem().getItem() != BuxinModItems.SUPER_SNAKE_SWORD.get()) {
                            closestValid.hurt(this.damageSources().generic(), 7);
                            Method_114514.entity_use_command(closestValid, "/particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1");
                        }
                        hasChained = true;
                    } else {
                        this.setRetracting(true);
                    }
                }
            }
        }
        double x = this.getX() + vector3d.x;
        double y = this.getY() + vector3d.y;
        double z = this.getZ() + vector3d.z;
        this.setDeltaMovement(vector3d.scale(0.99F));
        this.setPos(x, y, z);
    }
}
