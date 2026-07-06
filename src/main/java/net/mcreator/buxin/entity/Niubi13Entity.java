
package net.mcreator.buxin.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.ai.FishingTargetGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.Niubi13DangShiTiSiWangShiProcedure;
import net.mcreator.buxin.procedures.Niubi13ShiTiChuShiShengChengShiProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;

import javax.annotation.Nullable;
import java.util.Objects;

public class Niubi13Entity extends Monster implements RangedAttackMob {

	public Niubi13Entity(EntityType<Niubi13Entity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setCustomName(Component.literal("Steve"));
		setCustomNameVisible(false);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
		this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.FISHING_ROD));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if(this.getTarget() != null && !this.level().isClientSide){
			if(Method_114514.entity1_distance_to_entity2_xyz(this,this.getTarget()) > 4) {
				if (this.getMainHandItem().getItem() != Items.BOW) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
					this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.POWER_ARROWS,5);
				}
			} else {
				if(this.getPersistentData().getBoolean("eatGoldenApple") && this.getMainHandItem().getItem() != Items.GOLDEN_APPLE){
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_APPLE));
				}
				if(this.getHealth() > this.getMaxHealth() * 0.5){
					if(this.getMainHandItem().getItem() != Items.DIAMOND_SWORD){
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
						this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.SHARPNESS,5);
					}
				} else {
					if(this.getMainHandItem().getItem() != BuxinModItems.WOOPIE.get()){
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
						this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.SHARPNESS,5);
					}
				}
			}
		}
		//Method_114514.shootArrowAi(this);
	}

	public boolean canFireProjectileWeapon(Item item) {
		return true;
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
		this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1, 1, 30.0F));
		this.goalSelector.addGoal(2, new FishingTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(8, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(10, new FloatGoal(this));
		this.goalSelector.addGoal(11, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(13, new FloatGoal(this));
		this.goalSelector.addGoal(14, new LeapAtTargetGoal(this, (float) 0.5));
		AddCommonAttackGoal.Player(this);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
		this.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		ItemStack weaponStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, this::canFireProjectileWeapon));
		ItemStack itemstack = this.getProjectile(weaponStack);
		AbstractArrow mobArrow = ProjectileUtil.getMobArrow(this, itemstack, pVelocity);
		if (this.getMainHandItem().getItem() instanceof BowItem) {
			mobArrow = ((BowItem)this.getMainHandItem().getItem()).customArrow(mobArrow);
		}
		if(Math.random() > 0.3){
			mobArrow.setCritArrow(true);
		} else {
			if(Math.random() < 0.5 && !this.level().isRaining()){
				mobArrow.setSecondsOnFire(5);
			}
		}
		double x = pTarget.getX() - this.getX();
		double y = pTarget.getY(0.3333333333333333) - mobArrow.getY();
		double z = pTarget.getZ() - this.getZ();
		double d3 = Math.sqrt(x * x + z * z);
		mobArrow.setBaseDamage(1.5D);
		mobArrow.setOwner(this);
		mobArrow.shoot(x, y + d3 * (double)0.2F, z, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
		this.level().addFreshEntity(mobArrow);
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
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.big_fall")), 0.15f, 1);
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
		Mob entity = this;
		if (!entity.getPersistentData().getBoolean("eatGoldenApple") && this.getHealth() < this.getMaxHealth() * 0.5 && Math.random() > 0.85 && !this.level().isClientSide) {
			ItemStack oldItem = entity.getMainHandItem();
			ItemStack _setstack = new ItemStack(Items.GOLDEN_APPLE);
			_setstack.setCount(1);
			entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
			entity.getPersistentData().putBoolean("eatGoldenApple", true);
			AnimationPlayer.playAnimation(entity, Animations.BIPED_EAT);
			AnimationPlayer.playAnimation(this,Animations.BIPED_EAT);
			Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "entity.generic.eat");
			if (!entity.level().isClientSide() && entity.getServer() != null) {
				Method_114514.entity_use_command(entity, "/execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 20");
			}
			for (int i = 0; i < 7; i++) {
				BuxinMod.queueServerWork(4 * i, () -> {
					Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "entity.generic.eat");
					if (!entity.level().isClientSide() && entity.getServer() != null) {
						Method_114514.entity_use_command(entity, "/execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 20");
					}
				});
			}

			BuxinMod.queueServerWork(28, () -> {
				if (!entity.level().isClientSide()) {
					entity.level().playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()),
							(SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp"))),
							SoundSource.NEUTRAL, 1.5F, 1.0F);
				} else {
					entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(),
							(SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp"))),
							SoundSource.NEUTRAL, 1.5F, 1.0F, false);
				}
				if (!entity.level().isClientSide())
					entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 2, false, true));
			});

			BuxinMod.queueServerWork(31, () -> {
				if (!entity.isRemoved()) {
					entity.setItemSlot(EquipmentSlot.MAINHAND, oldItem);
				}
				BuxinMod.queueServerWork(20, () -> {
					entity.getPersistentData().putBoolean("eatGoldenApple", false);
				});
			});
		}
		return super.hurt(source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		Niubi13DangShiTiSiWangShiProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		Niubi13ShiTiChuShiShengChengShiProcedure.execute(this);
		return retval;
	}

	public static void init() {
		SpawnPlacements.register(BuxinModEntities.NIUBI_13.get(), SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> {
					BlockState blockBelow = world.getBlockState(pos.below());
					return blockBelow.is(Blocks.GRASS_BLOCK) && world.getRawBrightness(pos, 0) > 4;
				});
		//DungeonHooks.addDungeonMob(BuxinModEntities.NIUBI_13.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27);
		builder = builder.add(Attributes.MAX_HEALTH, 90);
		builder = builder.add(Attributes.ARMOR, 59);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 21);
		builder = builder.add(Attributes.FOLLOW_RANGE, 999);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}
}
