
package net.mcreator.buxin.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.shadow_herorbrine.ShadowHerobrineEntity;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.DarkHerobrineDangShiTiShouShangShiProcedure;
import net.mcreator.buxin.procedures.DarkHerobrineDangShiTidGengXinKeShiwProcedure;
import net.mcreator.buxin.procedures.DarkHerobrineShiTiChuShiShengChengShiProcedure;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class DarkHerobrineEntity extends Monster {
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

    public DarkHerobrineEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(BuxinModEntities.DARK_HEROBRINE.get(), world);
    }

    public DarkHerobrineEntity(EntityType<DarkHerobrineEntity> type, Level world) {
        super(type, world);
        maxUpStep = 0.6f;
        xpReward = 0;
        setNoAi(false);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.OBS.get()));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(BuxinModBlocks.AMY.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.WHAT_2.get()));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));

        this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.4, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(9, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new FloatGoal(this));
        this.goalSelector.addGoal(12, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(14, new FloatGoal(this));
        this.goalSelector.addGoal(15, new LeapAtTargetGoal(this, (float) 0.5));
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
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        DarkHerobrineDangShiTiShouShangShiProcedure.execute(this);
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        DarkHerobrineShiTiChuShiShengChengShiProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
        Method_114514.herobrine_born(this);
        Method_114514.entity_use_command(this,"/effect give @s epicfight:stun_immunity 4 5");
        this.getPersistentData().putBoolean("isbuxinentity",true);
        return retval;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        Mob entity = this;
        ShadowHerobrineEntity corpse = new ShadowHerobrineEntity(BuxinModEntities.SHADOW_HEROBRINE.get(), this.level());
        corpse.moveTo(entity.getX() + 5, entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
        corpse.lookAt(EntityAnchorArgument.Anchor.EYES, entity.position());
        BuxinMod.queueServerWork(60,() -> {
            if(entity.getTarget() instanceof Mob m){
                m.setTarget(corpse);
            }
        });
        corpse.setTarget(entity.getTarget());
        if(this.level() instanceof ServerLevel serverLevel && !(this.level()).isClientSide) {
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        }
        this.level().addFreshEntity(corpse);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        DarkHerobrineDangShiTidGengXinKeShiwProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        //this.bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public static void init() {
        //DungeonHooks.addDungeonMob(BuxinModEntities.DARK_HEROBRINE.get(), 180);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 120);
        builder = builder.add(Attributes.ARMOR, 30);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
        builder = builder.add(Attributes.FOLLOW_RANGE, 900);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 4);
        return builder;
    }
}
