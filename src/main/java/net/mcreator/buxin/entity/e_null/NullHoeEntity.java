
package net.mcreator.buxin.entity.e_null;

import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;

public class NullHoeEntity extends Monster {
    private UUID nullUUID;
    private NullEntity nullEntity;

    private long returnGameTime = -1L;
    private UUID playerUUID;
    private Player player;

    public void setNullUUID(UUID nullUUID) {
        this.nullUUID = nullUUID;
    }

    public void setNullEntity(NullEntity nullEntity) {
        this.nullEntity = nullEntity;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public UUID getNullUUID() {
        return nullUUID;
    }

    public void setReturnGameTime(long returnGameTime) {
        this.returnGameTime = returnGameTime;
    }

    public NullHoeEntity(EntityType<NullHoeEntity> entitytype, Level level) {
        super(entitytype, level);
        maxUpStep = (60.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NULL_HOE.get()));
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected @NotNull PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this,
                LivingEntity.class,
                10, true, false,
                target -> {
                    if (this.player == null || !this.player.isAlive()) return false;
                    var lastHurtBy = this.player.getLastHurtByMob();
                    var lastHurt = this.player.getLastHurtMob();
                    return (target == lastHurtBy || target == lastHurt)
                            && target.isAlive()
                            && !target.isAlliedTo(this.player);
                }
        ));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                && nullEntity.isAlive()
                && target != null
                && nullEntity.getTarget() == target));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                && nullEntity.isAlive()
                && target != null
                && target.getLastHurtMob() == nullEntity));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.4D, 20) {
            protected Vec3 getPosition() {
                Random random = new Random();
                double d0 = NullHoeEntity.this.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double d1 = NullHoeEntity.this.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double d2 = NullHoeEntity.this.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F));

                return new Vec3(d0, d1, d2);
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, NullEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(7, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            public boolean canUse() {
                return NullHoeEntity.this.getTarget() != null && !NullHoeEntity.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return NullHoeEntity.this.getMoveControl().hasWanted() && NullHoeEntity.this.getTarget() != null && NullHoeEntity.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = NullHoeEntity.this.getTarget();
                Vec3 vec3 = livingentity.getEyePosition(1.0F);

                NullHoeEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
            }

            public void tick() {
                LivingEntity livingentity = NullHoeEntity.this.getTarget();

                if (NullHoeEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    NullHoeEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = NullHoeEntity.this.distanceToSqr(livingentity);

                    if (d0 < 16.0D) {
                        Vec3 vec3 = livingentity.getEyePosition(1.0F);

                        NullHoeEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
                    }
                }

            }
        });
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (nullUUID != null) {
            tag.putUUID("NullUUID", nullUUID);
        }
        if (playerUUID != null) {
            tag.putUUID("OwnerUUID", playerUUID);
        }
        tag.putLong("ReturnTime", returnGameTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("NullUUID")) {
            nullUUID = tag.getUUID("NullUUID");
        }
        if (tag.hasUUID("OwnerUUID")) {
            playerUUID = tag.getUUID("OwnerUUID");
        }
        returnGameTime = tag.getLong("ReturnTime");
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
    }

    public boolean hurt(DamageSource damagesource, float f) {
        return false;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        this.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        return spawngroupdata1;
    }

    protected void checkFallDamage(double d0, boolean flag, BlockState blockstate, BlockPos blockpos) {}

    public void setNoGravity(boolean flag) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof Player hurtPlayer && this.playerUUID != null && this.playerUUID.equals(hurtPlayer.getUUID())) {
            return false;
        }
        if (pEntity instanceof NullEntity hurtNull && this.nullUUID != null && this.nullUUID.equals(hurtNull.getUUID())) {
            return false;
        }

        if (this.player != null) {
            ItemStack stack = this.getMainHandItem();
            if (!stack.isEmpty()) {
                stack.hurtAndBreak(1, this, (e) -> {
                    e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        }

        LivingEntity attacker;
        if (player != null) {
            attacker = player;
        } else if (nullEntity != null) {
            attacker = nullEntity;
        } else {
            attacker = this;
        }

        DamageSource source = pEntity.damageSources().mobAttack(attacker);
        return pEntity.hurt(source, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }


    @Override
    public void tick() {
        super.tick();
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() != BuxinModItems.NULL_HOE.get()) {
            if (this.nullEntity == null && this.player != null) {
                this.discard();
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NULL_HOE.get()));
        }
        if (this.getItemBySlot(EquipmentSlot.OFFHAND) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.HEAD) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.CHEST) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.LEGS) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.FEET) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        }
        if (!this.level().isClientSide) {
            if (nullEntity == null && nullUUID != null) {
                Entity entity = ((ServerLevel)this.level()).getEntity(nullUUID);
                if (entity instanceof NullEntity entityNull) {
                    this.nullEntity = entityNull;
                } else {
                    this.nullEntity = null;
                }
            }
            if (nullEntity != null && !nullEntity.isAlive()) {
                nullEntity = null;
                nullUUID = null;
            }
            if (player == null && playerUUID != null) {
                this.player = ((ServerLevel) this.level()).getPlayerByUUID(playerUUID);
            }
            if (player != null && !player.isAlive()) {
                this.remove(RemovalReason.KILLED);
            }
        }

        if (!this.level().isClientSide && this.nullEntity == null && this.player != null) {
            if (this.returnGameTime > 0 && ((ServerLevel)this.level()).getGameTime() >= this.returnGameTime) {
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (this.level() instanceof ServerLevel serverLevel && pReason.equals(RemovalReason.KILLED)) {
            if (this.player != null) {
                boolean added = this.player.getInventory().add(this.getMainHandItem());
                if (!added) {
                    var item = new ItemEntity(serverLevel, player.getX(), player.getY() + 0.5, player.getZ(), this.getMainHandItem());
                    item.setPickUpDelay(10);
                    serverLevel.addFreshEntity(item);
                }
                this.player.getPersistentData().remove("NullHoeUUID");
            } else {
                var item = new ItemEntity(serverLevel, this.getX(), this.getY(), this.getZ(), this.getMainHandItem());
                item.setPickUpDelay(10);
                serverLevel.addFreshEntity(item);
            }
        }
        super.remove(pReason);
    }

    public static void init() {}

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 2.0D);
        builder = builder.add(Attributes.MAX_HEALTH, 100.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 16.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 128.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 2.0D);
        return builder;
    }
}
