
package net.mcreator.buxin.entity;

import net.mcreator.buxin.color.BuxinWeaponColor;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.procedures.WhatShitPhase2OnEntityUpdateTick;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class WhatShitPhase2Entity extends Monster {
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.GREEN, ServerBossEvent.BossBarOverlay.PROGRESS);
	private Mob mob;
	private TargetingConditions targetConditions;
	private static final int a = 200;
	private static final int b = 999;
	private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR;

	public WhatShitPhase2Entity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.WHAT_SHIT_PHASE_2.get(), world);
	}

	public WhatShitPhase2Entity(EntityType<WhatShitPhase2Entity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setCustomName(Component.literal(BuxinWeaponColor.makeColour("测试用品")));
		setCustomNameVisible(false);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.ALLOYGIANTSWORDV_2.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.MOJANG_CAPE_CHESTPLATE.get()));
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
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
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
	public void setHealth(float p_21154_) {
		// No change needed
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.NETHERITE_SWORD));
		if (!this.level().isClientSide() && this.getServer() != null) {
			CommandSourceStack commandSourceStack = new CommandSourceStack(
					CommandSource.NULL,
					this.position(),
					this.getRotationVector(),
					(ServerLevel) this.level(),
					6,
					this.getDisplayName().getString(),
					this.getDisplayName(),
					this.level().getServer(),
					this
			);
			this.getServer().getCommands().performPrefixedCommand(commandSourceStack, "effect clear @p");
		}
		setCustomName(Component.literal(BuxinWeaponColor.makeColour("测试用品")));
		WhatShitPhase2OnEntityUpdateTick.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public void kill() {
		// No change needed
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		// No change needed
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
		SpawnPlacements.register(BuxinModEntities.WHAT_SHIT_PHASE_2.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	@Override
	public void remove(RemovalReason pReason) {
		// No change needed
	}

	@Override
	public void die(DamageSource source) {
		// No change needed
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.MAX_HEALTH, 2);
		builder = builder.add(Attributes.ARMOR, 1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, a);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, b);
		return builder;
	}

	static {
		LIVING_ENTITY_SELECTOR = (fuck) -> {
			return fuck.getMobType() != MobType.UNDEAD && fuck.attackable();
		};
	}
}
