
package net.mcreator.buxin.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.ai.AggressiveMiningGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.procedures.PlayermobsAiProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;

import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class EnchanterEntity extends Monster {
	public EnchanterEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.ENCHANTER.get(), world);
	}

	public EnchanterEntity(EntityType<EnchanterEntity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
		//this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(WOMItems.HERRSCHER.get()));
	//	this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(WOMItems.GESETZ.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
		this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		// MC 1.20.1: NetworkHooks.getEntitySpawningPacket now takes Entity instead of MobEntity
		// Also, PlayMessages.SpawnEntity is deprecated - but since we're in constructor and not spawning via packet,
		// this method remains valid as-is for custom entity spawning
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		// MC 1.20.1: NearestAttackableTargetGoal constructor signature unchanged
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		AddCommonAttackGoal.Player(this);
		// MC 1.20.1: MeleeAttackGoal constructor signature unchanged
		this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		this.goalSelector.addGoal(3, new AggressiveMiningGoal(this, 8));
		// MC 1.20.1: RandomStrollGoal constructor signature unchanged
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1));
		// MC 1.20.1: HurtByTargetGoal constructor signature unchanged
		this.targetSelector.addGoal(9, new HurtByTargetGoal(this));
		// MC 1.20.1: RandomLookAroundGoal constructor signature unchanged
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		// MC 1.20.1: FloatGoal constructor signature unchanged
		this.goalSelector.addGoal(11, new FloatGoal(this));
		this.goalSelector.addGoal(12, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(14, new FloatGoal(this));
		// MC 1.20.1: LeapAtTargetGoal constructor signature unchanged
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
		// MC 1.20.1: ForgeRegistries.SOUND_EVENTS.getValue() still valid, no change needed
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		// MC 1.20.1: ForgeRegistries.SOUND_EVENTS.getValue() still valid, no change needed
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
		double x = this.getX(), y = this.getY(), z = this.getZ();
		LevelAccessor world = this.level();

		BuxinMod.queueServerWork(5, () -> {
			if (world instanceof Level _level && !_level.isClientSide()) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
				entityToSpawn.setPickUpDelay(10);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
			BuxinMod.queueServerWork(5, () -> {
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.OFFHAND) : ItemStack.EMPTY));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.DIAMOND));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.DIAMOND));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				BuxinMod.queueServerWork(5, () -> {
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.TOTEM_OF_UNDYING));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLDEN_APPLE));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLDEN_APPLE));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
				});
			});
		});
		//Grave2DangShiTiSiWangShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		PlayermobsAiProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		Entity entity = this;
		// MC 1.20.1: getItemBySlot() still exists on LivingEntity, no change needed
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
		// MC 1.20.1: getMainHandItem() and getOffhandItem() still exist on LivingEntity, no change needed
		((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 5);
		((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 5);
		entity.getPersistentData().putBoolean("isfuck", false);
		entity.getPersistentData().putBoolean("iskicking", false);
		entity.getPersistentData().putDouble("battle", 0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);
		// MC 1.20.1: Component.literal() still valid, setCustomName() still takes Component
		entity.setCustomName(Component.literal(entity.getDisplayName().getString()));
		entity.setCustomNameVisible(false);

		return retval;
	}

	public static void init() {
		//DungeonHooks.addDungeonMob(BuxinModEntities.ENCHANTER.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 120);
		builder = builder.add(Attributes.ARMOR, 14);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 999);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 900);
		return builder;
	}
}
