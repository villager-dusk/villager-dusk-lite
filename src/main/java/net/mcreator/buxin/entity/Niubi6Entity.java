
package net.mcreator.buxin.entity;

import com.mojang.authlib.GameProfile;
import net.mcreator.buxin.ai.TNTBlockUseGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.IsBuxinEntityProcedure;
import net.mcreator.buxin.procedures.Niubi6DangShiTiSiWangShiProcedure;
import net.mcreator.buxin.procedures.PlayermobsAiProcedure;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;

public class Niubi6Entity extends Monster {
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

    public Niubi6Entity(PlayMessages.SpawnEntity packet, Level world) {
        this(BuxinModEntities.NIUBI_6.get(), world);
    }

    public Niubi6Entity(EntityType<Niubi6Entity> type, Level world) {
        super(type, world);
        maxUpStep = 0.6f;
        xpReward = 0;
        setNoAi(false);
        setPersistenceRequired();
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NIUBI_7.get()));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(BuxinModItems.ZUAN_JIAN_2.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.NIUBI_18_CHESTPLATE.get()));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new TNTBlockUseGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
        AddCommonAttackGoal.Grave(this);
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));
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
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
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
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        Entity entity = this;
        LevelAccessor world = this.level();

        Niubi6DangShiTiSiWangShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        IsBuxinEntityProcedure.execute(this);
        this.getPersistentData().putBoolean("isfuck", false);
        this.getPersistentData().putBoolean("iskicking", false);
        this.getPersistentData().putBoolean("isbuxinentity", true);
        Method_114514.entity_use_command(this,"/team join av_player");
        if(Math.random() > 0.9 && this.level().getDifficulty() == Difficulty.EASY){
            this.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
            this.setItemSlot(EquipmentSlot.OFFHAND, Items.IRON_SWORD.getDefaultInstance());
            this.setItemSlot(EquipmentSlot.CHEST,Items.IRON_CHESTPLATE.getDefaultInstance());
        }
        return retval;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        PlayermobsAiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
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
        SpawnPlacements.register(BuxinModEntities.NIUBI_6.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getRawBrightness(pos, 0) > 4));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 240);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }
}
