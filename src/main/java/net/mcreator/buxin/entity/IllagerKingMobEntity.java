
package net.mcreator.buxin.entity;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.procedures.IllagerKingMobDangShiTiGengXinKeShiProcedure;
import net.mcreator.buxin.procedures.IsBuxinEntityProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class IllagerKingMobEntity extends Monster {
    public IllagerKingMobEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(BuxinModEntities.ILLAGER_KING_MOB.get(), world);
    }

    public IllagerKingMobEntity(EntityType<IllagerKingMobEntity> type, Level world) {
        super(type, world);
        maxUpStep = 0.6f;
        xpReward = 0;
        setNoAi(false);
        setCustomName(Component.literal("灾厄之王"));
        setCustomNameVisible(false);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.FUZI.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.ILLAGER_KING_ARMOR_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.ILLAGER_KING_ARMOR_CHESTPLATE.get()));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.goalSelector.addGoal(20, new MeleeAttackGoal(this, 1.4, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.targetSelector.addGoal(21, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(22, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(23, new FloatGoal(this));
        this.goalSelector.addGoal(24, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(25, new FloatGoal(this));
        this.goalSelector.addGoal(26, new LeapAtTargetGoal(this, (float) 0.5));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.ambient"));
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.death"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        IsBuxinEntityProcedure.execute(this);
        return retval;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        IllagerKingMobDangShiTiGengXinKeShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
    }

    public static void init() {
        SpawnPlacements.register(BuxinModEntities.ILLAGER_KING_MOB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 45);
        builder = builder.add(Attributes.ARMOR, 14);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5);
        return builder;
    }
}
