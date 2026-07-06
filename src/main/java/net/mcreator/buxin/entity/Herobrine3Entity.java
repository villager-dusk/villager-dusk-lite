
package net.mcreator.buxin.entity;

import net.mcreator.buxin.ai.ObsidianUseGoal;
import net.mcreator.buxin.ai.SpecialDarkObsidianZhuUseGoal;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class Herobrine3Entity extends Monster {
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.WHITE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public Herobrine3Entity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.HEROBRINE_3.get(), world);
	}

	public Herobrine3Entity(EntityType<Herobrine3Entity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.OBS.get()));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.NIUBI_20_HELMET.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.NIUBI_19_CHESTPLATE.get()));
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1,new ObsidianUseGoal(this,true,true,new ItemStack(BuxinModItems.OBS_5.get()),new ItemStack(BuxinModItems.OBS_5.get()),new ItemStack(BuxinModItems.OBS.get()),new ItemStack(Items.AIR)));
		this.goalSelector.addGoal(2,new SpecialDarkObsidianZhuUseGoal(this));
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
		Herobrine3DangShiTiShouShangShi2Procedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
		if (source.is(DamageTypes.CRAMMING)) {
			return false;
		}
		if(source.is(DamageTypes.IN_WALL)){
			return false;
		}
		if (source.is(DamageTypes.FALL)) {
			return false;
		}
		if(Math.random() > 0.5){
			return false;
		}
		return super.hurt(source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		Herobrine3DangShiTiSiWangShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		Herobrine3ShiTiChuShiShengChengShiProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		Method_114514.herobrine_born(this);
		return retval;
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		Herobrine3DangZheGeShiTiShaSiLingGeShiTiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), entity, this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
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
		SpawnPlacements.register(BuxinModEntities.HEROBRINE_3.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getRawBrightness(pos, 0) > 4));
		//DungeonHooks.addDungeonMob(BuxinModEntities.HEROBRINE_3.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder.add(Attributes.MAX_HEALTH, 120);
		builder.add(Attributes.ARMOR, 176);
		builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder.add(Attributes.FOLLOW_RANGE, 900);
		builder.add(Attributes.KNOCKBACK_RESISTANCE, 4);
		return builder;
	}
}
