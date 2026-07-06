
package net.mcreator.buxin.entity;

import net.mcreator.buxin.ai.SmallTridentHolidayUseGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.LanemobentiDangShiTiShouShangShiProcedure;
import net.mcreator.buxin.procedures.LanemobentiDangShiTiSiWangShiProcedure;
import net.mcreator.buxin.procedures.LanemobentiShiTiChuShiSwhengChengShiProcedure;
import net.mcreator.buxin.procedures.L蓝恶魔Procedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class LanemobentiEntity extends Monster implements RangedAttackMob {
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

    public LanemobentiEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(BuxinModEntities.LANEMOBENTI.get(), world);
    }

    public LanemobentiEntity(EntityType<LanemobentiEntity> type, Level world) {
        super(type, world);
        maxUpStep = 0.6f;
        xpReward = 0;
        setNoAi(false);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.TRIDENT));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.LANEMO_CHESTPLATE.get()));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(2, new DrownedTridentAttackGoal(this, 1.0, 40, 10.0F));
        this.goalSelector.addGoal(2, new SmallTridentHolidayUseGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
        AddCommonAttackGoal.Bluedemon(this);
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new FloatGoal(this));
        this.goalSelector.addGoal(10, new LeapAtTargetGoal(this, (float) 0.5));
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
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(!(source.is(DamageTypes.IN_FIRE)) && !(source.is(DamageTypes.ON_FIRE)) && !(source.is(DamageTypes.MAGIC)) && !(source.is(DamageTypes.FALL)))
            LanemobentiDangShiTiShouShangShiProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (source.is(DamageTypes.LIGHTNING_BOLT))
            return false;
        if (source.is(DamageTypes.ON_FIRE))
            return false;
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        if (source.is(DamageTypes.LAVA))
            return false;

        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
    //    LanemobentiDangShiTiSiWangShiProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
    }

    private static class DrownedTridentAttackGoal extends RangedAttackGoal {
        private final LanemobentiEntity drowned;

        public DrownedTridentAttackGoal(RangedAttackMob p_32450_, double p_32451_, int p_32452_, float p_32453_) {
            super(p_32450_, p_32451_, p_32452_, p_32453_);
            this.drowned = (LanemobentiEntity)p_32450_;
        }

        public boolean canUse() {
            return super.canUse() && this.drowned.getMainHandItem().is(Items.TRIDENT);
        }

        public void start() {
            super.start();
            this.drowned.setAggressive(true);
            this.drowned.startUsingItem(InteractionHand.MAIN_HAND);
        }

        public void stop() {
            super.stop();
            this.drowned.stopUsingItem();
            this.drowned.setAggressive(false);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        this.getPersistentData().putInt("e",0);
        Method_114514.send_message_to_all_over_the_world(this.level(),this.getDisplayName().getString() + "§2已降临在 " + this.getX() + " " + this.getY() + " " + this.getZ());
        LanemobentiShiTiChuShiSwhengChengShiProcedure.execute(this);
        return retval;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        L蓝恶魔Procedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        //super.startSeenByPlayer(player);
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
        //DungeonHooks.addDungeonMob(BuxinModEntities.LANEMOBENTI.get(), 180);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 180);
        builder = builder.add(Attributes.ARMOR, 94);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
        builder = builder.add(Attributes.FOLLOW_RANGE, 999);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
        return builder;
    }

    @Override
    public void performRangedAttack(LivingEntity p_32356_, float p_32357_) {
        Method_114514.entity_use_command(this,"/indestructible @s play \"epicfight:biped/combat/mob_throw\" 0 1");
        ThrownTrident $$2 = new ThrownTrident(this.level(), this, new ItemStack(Items.TRIDENT));
        double $$3 = p_32356_.getX() - this.getX();
        double $$4 = p_32356_.getY(0.3333333333333333) - $$2.getY();
        double $$5 = p_32356_.getZ() - this.getZ();
        double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
        $$2.shoot($$3, $$4 + $$6 * 0.20000000298023224, $$5, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity($$2);
    }
}
