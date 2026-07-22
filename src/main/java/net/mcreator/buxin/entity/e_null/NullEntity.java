package net.mcreator.buxin.entity.e_null;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.VFXTool;
import net.mcreator.buxin.procedures.NullDangShiTiShouShangShiProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumSet;
import java.util.UUID;

public class NullEntity extends Monster {

    private NullSwordEntity nullSwordEntity;
    private UUID nullSwordUUID;
    private NullAxeEntity nullAxeEntity;
    private UUID nullAxeUUID;
    private NullPickaxeEntity nullPickaxeEntity;
    private UUID nullPickaxeUUID;
    private NullShovelEntity nullShovelEntity;
    private UUID nullShovelUUID;
    private NullHoeEntity nullHoeEntity;
    private UUID nullHoeUUID;
    private boolean initialSpawn;

    public NullEntity(EntityType<NullEntity> type, Level level) {
        super(type, level);
        maxUpStep = 3.0f;
        xpReward = 80;
        setPersistenceRequired();
        moveControl = new FlyingMoveControl(this, 10, true);
        setCustomName(Component.literal("Null"));
    }

    // ==================== 网络 ====================
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    // ==================== 持久化 ====================
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        putUUID(tag, "NullSwordUUID", nullSwordUUID);
        putUUID(tag, "NullAxeUUID", nullAxeUUID);
        putUUID(tag, "NullPickaxeUUID", nullPickaxeUUID);
        putUUID(tag, "NullShovelUUID", nullShovelUUID);
        putUUID(tag, "NullHoeUUID", nullHoeUUID);
        tag.putBoolean("InitialSpawn", initialSpawn);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        nullSwordUUID   = getUUID(tag, "NullSwordUUID");
        nullAxeUUID     = getUUID(tag, "NullAxeUUID");
        nullPickaxeUUID = getUUID(tag, "NullPickaxeUUID");
        nullShovelUUID  = getUUID(tag, "NullShovelUUID");
        nullHoeUUID     = getUUID(tag, "NullHoeUUID");
        initialSpawn    = tag.getBoolean("InitialSpawn");
    }

    private static void putUUID(CompoundTag tag, String key, UUID uuid) {
        if (uuid != null) tag.putUUID(key, uuid);
    }

    private static UUID getUUID(CompoundTag tag, String key) {
        return tag.hasUUID(key) ? tag.getUUID(key) : null;
    }

    // ==================== 初始化武器 ====================
    private void initialSpawn() {
        if (!(level() instanceof ServerLevel serverLevel)) return;
        initialSpawn = true;

        nullSwordEntity   = spawnTool(serverLevel, BuxinModEntities.NULL_SWORD.get(), NullSwordEntity.class);
        nullAxeEntity     = spawnTool(serverLevel, BuxinModEntities.NULL_AXE.get(), NullAxeEntity.class);
        nullPickaxeEntity = spawnTool(serverLevel, BuxinModEntities.NULL_PICKAXE.get(), NullPickaxeEntity.class);
        nullShovelEntity  = spawnTool(serverLevel, BuxinModEntities.NULL_SHOVEL.get(), NullShovelEntity.class);
        nullHoeEntity     = spawnTool(serverLevel, BuxinModEntities.NULL_HOE.get(), NullHoeEntity.class);

        Method_114514.herobrine_born(this);
    }

    private <T extends NullWeaponEntity> T spawnTool(ServerLevel level, EntityType<T> type, Class<T> clazz) {
        T tool = type.create(level);
        tool.moveTo(
            getX() + Mth.nextDouble(random, 1, 10),
            getY() + Mth.nextDouble(random, 1, 10),
            getZ() + Mth.nextDouble(random, 1, 10),
            random.nextFloat() * 360, 0
        );
        tool.finalizeSpawn(level, level.getCurrentDifficultyAt(blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        level.addFreshEntity(tool);
        tool.setNullEntity(this);
        tool.setNullUUID(getUUID());
        return tool;
    }

    // ==================== tick ====================
    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) return;

        if (!initialSpawn) initialSpawn();

        nullSwordEntity   = tryRestoreTool(nullSwordEntity, nullSwordUUID, NullSwordEntity.class);
        nullAxeEntity     = tryRestoreTool(nullAxeEntity, nullAxeUUID, NullAxeEntity.class);
        nullPickaxeEntity = tryRestoreTool(nullPickaxeEntity, nullPickaxeUUID, NullPickaxeEntity.class);
        nullShovelEntity  = tryRestoreTool(nullShovelEntity, nullShovelUUID, NullShovelEntity.class);
        nullHoeEntity     = tryRestoreTool(nullHoeEntity, nullHoeUUID, NullHoeEntity.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends NullWeaponEntity> T tryRestoreTool(T field, UUID uuid, Class<T> clazz) {
        if (field != null || uuid == null) return field;
        Entity entity = ((ServerLevel) level()).getEntity(uuid);
        return clazz.isInstance(entity) ? (T) entity : null;
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (BuxinMod.isWindows() && VFXParticleConfig.VFXParticleConfig.get() && Math.random() > 0.8848) {
            VFXTool.addVFXParticle(new Vec3(getX(), getY() + 1, getZ()), BuxinMod.MODID, "null", level());
        }

        if (Math.random() <= 0.1) {
            RandomSource rs = level().getRandom();
            teleportTool(nullSwordEntity,   rs);
            teleportTool(nullAxeEntity,     rs);
            teleportTool(nullPickaxeEntity, rs);
            teleportTool(nullShovelEntity,  rs);
            teleportTool(nullHoeEntity,     rs);
        }

        LivingEntity target = getTarget();
        if (target != null) {
            Vec3 dir = target.position().subtract(position()).normalize().scale(0.2);
            setDeltaMovement(getDeltaMovement().add(dir));
        }
    }

    private void teleportTool(NullWeaponEntity tool, RandomSource rs) {
        if (tool != null) {
            tool.moveTo(
                getX() + Mth.nextInt(rs, -4, 4),
                getY() + Mth.nextInt(rs, -2, 2),
                getZ() + Mth.nextInt(rs, -4, 4)
            );
        }
    }

    // ==================== AI ====================
    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return mob.getBbWidth() * mob.getBbWidth() + entity.getBbWidth();
            }
        });
        goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        goalSelector.addGoal(5, new FloatGoal(this));

        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        addTarget(6, GreenVillagerCavalryEntity.class);
        addTarget(7, Grave2Entity.class);
        addTarget(8, Player.class);
        addTarget(9, PurpleVillagerCavalryEntity.class);
        addTarget(10, RedVillagerEntity.class);
        addTarget(11, CunMinWeiBingEntity.class);
        addTarget(12, Jshaman2Entity.class);
        addTarget(13, ShifangEntity.class);
        addTarget(14, LanemobentiEntity.class);

        AddCommonAttackGoal.Herobrine(this);

        goalSelector.addGoal(24, new FlyToTargetGoal());
    }

    private void addTarget(int priority, Class<? extends LivingEntity> clazz) {
        targetSelector.addGoal(priority, new NearestAttackableTargetGoal<>(this, clazz, true, false));
    }

    private class FlyToTargetGoal extends Goal {
        FlyToTargetGoal() { setFlags(EnumSet.of(Flag.MOVE)); }

        @Override public boolean canUse() { return getTarget() != null && !getMoveControl().hasWanted(); }

        @Override public boolean canContinueToUse() {
            LivingEntity t = getTarget();
            return t != null && t.isAlive() && getMoveControl().hasWanted();
        }

        @Override public void start() {
            LivingEntity t = getTarget();
            if (t != null) moveControl.setWantedPosition(t.getX(), t.getEyeY(), t.getZ(), 1.0);
        }

        @Override public void tick() {
            LivingEntity t = getTarget();
            if (t == null) return;
            if (getBoundingBox().intersects(t.getBoundingBox())) {
                doHurtTarget(t);
            } else if (distanceToSqr(t) < 16) {
                moveControl.setWantedPosition(t.getX(), t.getEyeY(), t.getZ(), 5.0);
            }
        }
    }

    // ==================== 伤害 ====================
    @Override
    public boolean hurt(DamageSource src, float amount) {
        NullDangShiTiShouShangShiProcedure.execute(level(), getX(), getY(), getZ(), this, src.getEntity());
        if (src.is(DamageTypes.CACTUS) || src.is(DamageTypes.WITHER) || src.is(DamageTypes.DROWN)
            || src.is(DamageTypes.WITHER_SKULL) || src.is(DamageTypes.DRAGON_BREATH)
            || src.is(DamageTypes.ON_FIRE) || src.is(DamageTypes.IN_FIRE)
            || src.getDirectEntity() instanceof AbstractArrow) return false;
        return super.hurt(src, amount);
    }

    @Override
    public void die(DamageSource src) {
        super.die(src);
        if (level() instanceof ServerLevel) {
            discardTool(nullSwordEntity);
            discardTool(nullAxeEntity);
            discardTool(nullHoeEntity);
            discardTool(nullShovelEntity);
            discardTool(nullPickaxeEntity);
            setInvisible(true);
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        if (level() instanceof ServerLevel && reason == RemovalReason.DISCARDED) {
            discardTool(nullSwordEntity);
            discardTool(nullAxeEntity);
            discardTool(nullHoeEntity);
            discardTool(nullShovelEntity);
            discardTool(nullPickaxeEntity);
        }
        super.remove(reason);
    }

    private void discardTool(Entity tool) {
        if (tool != null) tool.remove(RemovalReason.DISCARDED);
    }

    // ==================== 杂物 ====================
    @Override public MobType getMobType() { return MobType.UNDEAD; }
    @Override public boolean removeWhenFarAway(double d) { return false; }
    @Override public double getMyRidingOffset() { return -0.35; }
    @Override public boolean causeFallDamage(float f1, float f2, DamageSource src) { return false; }
    @Override public void checkFallDamage(double d, boolean b, BlockState state, BlockPos pos) {}
    @Override public void aiStep() { super.aiStep(); setNoGravity(true); }

    @Override public SoundEvent getHurtSound(DamageSource src) { return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt")); }
    @Override public SoundEvent getDeathSound() { return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death")); }

    public static void init() { DungeonHooks.addDungeonMob(BuxinModEntities.NULL.get(), 180); }

    public static Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 3)
                .add(Attributes.MAX_HEALTH, 275)
                .add(Attributes.ARMOR, 90)
                .add(Attributes.ATTACK_DAMAGE, 8)
                .add(Attributes.FOLLOW_RANGE, 128)
                .add(Attributes.FLYING_SPEED, 3);
    }
}
