
package net.mcreator.buxin.entity.woopie;

import net.mcreator.buxin.entity.Niubi14Entity;
import net.mcreator.buxin.init.BuxinModEntities;
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
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;

public class WoopieEntity extends Monster {
    private UUID steveUUID;
    private Niubi14Entity nullEntity;

    private long returnGameTime = -1L;
    private UUID playerUUID;
    private Player player;

    public void setsteveUUID(UUID steveUUID) {
        this.steveUUID = steveUUID;
    }

    public void setNullEntity(Niubi14Entity nullEntity) {
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

    public UUID getsteveUUID() {
        return steveUUID;
    }

    public void setReturnGameTime(long returnGameTime) {
        this.returnGameTime = returnGameTime;
    }

    // 修改1：MC 1.20.1+Forge 47.4.10中，SpawnEntity构造器已废弃，使用EntityType构造器即可（原构造器仅用于网络同步，实际实体创建不依赖它）
    // 移除已废弃的SpawnEntity构造器，保留主构造器即可
    public WoopieEntity(EntityType<WoopieEntity> entitytype, Level level) {
        super(entitytype, level);
        maxUpStep = 60.0F;
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }
    
    // 修改2：MC 1.20.1中，getAddEntityPacket()方法签名已改为返回Packet<ClientGamePacketListener>，且NetworkHooks.getEntitySpawningPacket()仍存在但参数类型不变
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // 修改3：MC 1.20.1中，createNavigation()方法参数类型仍为Level，未变；FlyingPathNavigation构造器参数仍为(Entity, Level)，未变 → 无需修改
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
                double x = WoopieEntity.this.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double y = WoopieEntity.this.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double z = WoopieEntity.this.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F));

                return new Vec3(x, y, z);
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Niubi14Entity.class, 6.0F));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        // 修改4：MC 1.20.1中，HurtByTargetGoal构造器参数从Class[0]改为Class<?>[]（即Object[]），但空数组写法仍兼容，无需改；但为明确起见，使用显式空数组语法（无实质变更）
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(7, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            public boolean canUse() {
                return WoopieEntity.this.getTarget() != null && !WoopieEntity.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return WoopieEntity.this.getMoveControl().hasWanted() && WoopieEntity.this.getTarget() != null && WoopieEntity.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = WoopieEntity.this.getTarget();
                Vec3 vec3 = livingentity.getEyePosition(1.0F);

                WoopieEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
            }

            public void tick() {
                LivingEntity livingentity = WoopieEntity.this.getTarget();

                if (WoopieEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    WoopieEntity.this.doHurtTarget(livingentity);
                } else {
                    double x = WoopieEntity.this.distanceToSqr(livingentity);

                    if (x < 16.0D) {
                        Vec3 vec3 = livingentity.getEyePosition(1.0F);

                        WoopieEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
                    }
                }

            }
        });
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (steveUUID != null) {
            tag.putUUID("steveUUID", steveUUID);
        }
        if (playerUUID != null) {
            tag.putUUID("OwnerUUID", playerUUID);
        }
        tag.putLong("ReturnTime", returnGameTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("steveUUID")) {
            steveUUID = tag.getUUID("steveUUID");
        }
        if (tag.hasUUID("OwnerUUID")) {
            playerUUID = tag.getUUID("OwnerUUID");
        }
        returnGameTime = tag.getLong("ReturnTime");
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    // 修改5：MC 1.20.1中，removeWhenFarAway(double)方法已被废弃（@Deprecated），并替换为shouldDespawnInPeaceful()和/或isNoDespawnRequired()逻辑
    // 但此方法仍存在且可重写，且语义未变（返回false表示不因距离自动移除），故保留原实现（无需修改）
    public boolean removeWhenFarAway(double x) {
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

    // 修改6：MC 1.20.1中，causeFallDamage(float, float, DamageSource)已被废弃，替换为fallOn(BlockState, BlockPos, float, float)
    // 但该方法仍存在且可重写，且本实现返回false符合预期（禁用摔落伤害），故保留（无需修改）
    public boolean causeFallDamage(float f, float f1, DamageSource damagesourfce) {
        return false;
    }

    // 修改7：MC 1.20.1中，hurt(DamageSource, float)方法签名未变，但需注意：若依赖EpicFight或Indestructible等模组的防护逻辑，应确保其兼容1.20.1
    // 本方法返回false表示完全免疫伤害，与原意一致，无需修改
    public boolean hurt(DamageSource damagesource, float f) {
        return false;
    }

    // 修改8：MC 1.20.1中，finalizeSpawn方法签名未变（ServerLevelAccessor, DifficultyInstance, MobSpawnType, SpawnGroupData, CompoundTag），参数类型一致，无需修改
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);

        this.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        return spawngroupdata1;
    }

    // 修改9：MC 1.20.1中，checkFallDamage方法已被废弃，替换为fallOn(...)，但此方法仍存在且可重写，本实现为空，保留（无需修改）
    protected void checkFallDamage(double x, boolean flag, BlockState blockstate, BlockPos blockpos) {}

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
        if (pEntity instanceof Niubi14Entity hurtNull && this.steveUUID != null && this.steveUUID.equals(hurtNull.getUUID())) {
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

        // 修改10：MC 1.20.1中，DamageSource.INDIRECT_MOB_ATTACK 已被移除，改用DamageSources.indirectMobAttack()或使用DamageSource.mobAttack(attacker)
        // 此处与1.19.2完全相同，无需修改
        //DamageSource source = DamageSource.mobAttack(attacker);
        return pEntity.hurt(pEntity.damageSources().generic(), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }


    @Override
    public void tick() {
        super.tick();
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() != BuxinModItems.WOOPIE.get()) {
            if (this.nullEntity == null && this.player != null) {
                this.discard();
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
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
            if (nullEntity == null && steveUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(steveUUID);
                if (entity instanceof Niubi14Entity entityNull) {
                    this.nullEntity = entityNull;
                } else {
                    this.nullEntity = null;
                }
            }
            if (nullEntity != null && !nullEntity.isAlive()) {
                nullEntity = null;
                steveUUID = null;
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
                this.player.getPersistentData().remove("WoopieUUID");
            } else {
                var item = new ItemEntity(serverLevel, this.getX(), this.getY(), this.getZ(), this.getMainHandItem());
                item.setPickUpDelay(10);
                serverLevel.addFreshEntity(item);
            }
        }
        super.remove(pReason);
    }

    public static void init() {}

    // 修改11：MC 1.20.1中，Mob.createMobAttributes()仍存在且返回AttributeSupplier.Builder，API未变，无需修改
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
