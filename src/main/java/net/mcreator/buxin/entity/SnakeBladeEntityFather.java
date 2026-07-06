
package net.mcreator.buxin.entity;

import net.mcreator.buxin.entity.snakeblade_test.entity.SnakeBladeEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;

public class SnakeBladeEntityFather extends Entity {
    protected static final EntityDataAccessor<Optional<UUID>> CREATOR_ID = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.OPTIONAL_UUID);
    protected static final EntityDataAccessor<Integer> FROM_ID = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> TARGET_COUNT = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> CURRENT_TARGET_ID = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> PROGRESS = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Boolean> RETRACTING = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> HAS_BLADE = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ENCHANTED = SynchedEntityData.defineId(SnakeBladeEntityFather.class, EntityDataSerializers.BOOLEAN);
    protected List<Entity> previouslyTouched = new ArrayList<>();
    protected boolean hasChained = false;
    public float prevProgress = 0;
    public static Random random = new Random();
    public static final float MAX_EXTEND_TIME = 5f;

    public SnakeBladeEntityFather(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(CREATOR_ID, Optional.empty());
        this.entityData.define(FROM_ID, -1);
        this.entityData.define(TARGET_COUNT, 0);
        this.entityData.define(CURRENT_TARGET_ID, -1);
        this.entityData.define(PROGRESS, 0.0f);
        this.entityData.define(DAMAGE, 0.0f);
        this.entityData.define(RETRACTING, false);
        this.entityData.define(HAS_BLADE, true);
        this.entityData.define(ENCHANTED, false);
    }

    public void setEnchanted(boolean enchanted) {
        this.entityData.set(ENCHANTED, enchanted);
    }

    public boolean isEnchanted() {
        return this.entityData.get(ENCHANTED);
    }
    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("CreatorUUID")) {
            this.entityData.set(CREATOR_ID, Optional.of(compoundTag.getUUID("CreatorUUID")));
        }
        if (compoundTag.contains("FromID")) {
            this.entityData.set(FROM_ID, compoundTag.getInt("FromID"));
        }
        if (compoundTag.contains("TargetCount")) {
            this.entityData.set(TARGET_COUNT, compoundTag.getInt("TargetCount"));
        }
        if (compoundTag.contains("CurrentTargetID")) {
            this.entityData.set(CURRENT_TARGET_ID, compoundTag.getInt("CurrentTargetID"));
        }
        if (compoundTag.contains("Progress")) {
            this.entityData.set(PROGRESS, compoundTag.getFloat("Progress"));
        }
        if (compoundTag.contains("Damage")) {
            this.entityData.set(DAMAGE, compoundTag.getFloat("Damage"));
        }
        if (compoundTag.contains("Retracting")) {
            this.entityData.set(RETRACTING, compoundTag.getBoolean("Retracting"));
        }
        if (compoundTag.contains("HasBlade")) {
            this.entityData.set(HAS_BLADE, compoundTag.getBoolean("HasBlade"));
        }
        if (compoundTag.contains("Enchanted")) {
            this.entityData.set(ENCHANTED, compoundTag.getBoolean("Enchanted"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        Optional<UUID> creatorUUID = this.entityData.get(CREATOR_ID);
        if (creatorUUID.isPresent()) {
            compoundTag.putUUID("CreatorUUID", creatorUUID.get());
        }
        compoundTag.putInt("FromID", this.entityData.get(FROM_ID));
        compoundTag.putInt("TargetCount", this.entityData.get(TARGET_COUNT));
        compoundTag.putInt("CurrentTargetID", this.entityData.get(CURRENT_TARGET_ID));
        compoundTag.putFloat("Progress", this.entityData.get(PROGRESS));
        compoundTag.putFloat("Damage", this.entityData.get(DAMAGE));
        compoundTag.putBoolean("Retracting", this.entityData.get(RETRACTING));
        compoundTag.putBoolean("HasBlade", this.entityData.get(HAS_BLADE));
        compoundTag.putBoolean("Enchanted", this.entityData.get(ENCHANTED));
    }

    protected boolean isValidTarget(LivingEntity creator, Entity entity) {
        if(!creator.isAlliedTo(entity) && !entity.isAlliedTo(creator) && entity instanceof Mob){
            return true;
        }
        if(creator instanceof ShifangEntity && !Method_114514.isHerobrine(entity)){
            return true;
        }
        return creator.getLastHurtMob() != null && creator.getLastHurtMob().getUUID().equals(entity.getUUID()) || creator.getLastHurtByMob() != null && creator.getLastHurtByMob().getUUID().equals(entity.getUUID());
    }

    protected boolean hasLineOfSight(Entity entity) {
        if (entity.level() != this.level()) {
            return false;
        } else {
            Vec3 vec3 = new Vec3(this.getX(), this.getEyeY(), this.getZ());
            Vec3 vec31 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
            if (vec31.distanceTo(vec3) > 128.0D) {
                return false;
            } else {
                return this.level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
            }
        }
    }

    protected void createChain(Entity closestValid) {
        this.entityData.set(HAS_BLADE, false);
        SnakeBladeEntity child = BuxinModEntities.SNAKE_BLADE.get().create(this.level());
        if(child != null) {
            if (this.isEnchanted()) {
                child.setEnchanted(true);
            }
            child.previouslyTouched = new ArrayList<>(previouslyTouched);
            child.previouslyTouched.add(closestValid);
            child.setCreatorEntityUUID(this.getCreatorEntityUUID());
            child.setFromEntityID(this.getId());
            child.setToEntityID(closestValid.getId());
            child.setPos(closestValid.getX(), closestValid.getEyeY(), closestValid.getZ());
            child.setTargetsHit(this.getTargetsHit() + 1);
            this.level().addFreshEntity(child);
        }
    }

    public UUID getCreatorEntityUUID() {
        return this.entityData.get(CREATOR_ID).orElse(null);
    }

    public void setCreatorEntityUUID(UUID id) {
        this.entityData.set(CREATOR_ID, Optional.ofNullable(id));
    }

    public Entity getCreatorEntity() {
        UUID uuid = getCreatorEntityUUID();
        if(uuid != null && !this.level().isClientSide){
            return ((ServerLevel) level()).getEntity(uuid);
        }
        return null;
    }

    public int getFromEntityID() {
        return this.entityData.get(FROM_ID);
    }

    public void setFromEntityID(int id) {
        this.entityData.set(FROM_ID, id);
    }

    public Entity getFromEntity() {
        return getFromEntityID() == -1 ? null : this.level().getEntity(getFromEntityID());
    }

    public void setToEntityID(int id) {
        this.entityData.set(CURRENT_TARGET_ID, id);
    }

    public int getTargetsHit() {
        return this.entityData.get(TARGET_COUNT);
    }

    public void setTargetsHit(int i) {
        this.entityData.set(TARGET_COUNT, i);
    }

    public float getProgress() {
        return this.entityData.get(PROGRESS);
    }

    public void setProgress(float progress) {
        this.entityData.set(PROGRESS, progress);
    }

    public boolean isRetracting() {
        return this.entityData.get(RETRACTING);
    }

    public void setRetracting(boolean retract) {
        this.entityData.set(RETRACTING, retract);
    }

    public boolean hasBlade() {
        return this.entityData.get(HAS_BLADE);
    }
    public boolean isCreator(Entity mob) {
        return this.getCreatorEntityUUID() != null && mob.getUUID().equals(this.getCreatorEntityUUID());
    }
}
