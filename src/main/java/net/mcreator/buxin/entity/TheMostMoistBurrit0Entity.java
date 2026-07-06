
package net.mcreator.buxin.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class TheMostMoistBurrit0Entity extends Monster implements RangedAttackMob {
	public TheMostMoistBurrit0Entity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.TheMostMoistBurrit0.get(), world);
	}

	public TheMostMoistBurrit0Entity(EntityType<TheMostMoistBurrit0Entity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
	}

	public boolean canFireProjectileWeapon(Item item) {
		boolean var10000;
		if (item instanceof ProjectileWeaponItem weaponItem) {
			if (this.canFireProjectileWeapon(weaponItem)) {
				var10000 = true;
				return var10000;
			}
		}

		var10000 = false;
		return var10000;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1, 1, 15.0F));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
		AddCommonAttackGoal.Grave(this);
		this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.2, false) {
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
	public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
		//for (int i = 0; i < 2 + new Random().nextInt(2); i++) {
		//BuxinMod.queueServerWork(i * 2,() -> {
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
			if(Math.random() < 0.2 && !this.level().isRaining()){
				mobArrow.setSecondsOnFire(5);
			}
		}
		double x = pTarget.getX() - this.getX();
		double y = pTarget.getY(0.3333333333333333) - mobArrow.getY();
		double z = pTarget.getZ() - this.getZ();
		double d3 = Math.sqrt(x * x + z * z);
		mobArrow.setBaseDamage(10.5D);
		mobArrow.setOwner(this);
		mobArrow.shoot(x, y + d3 * (double)0.2F, z, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
		this.level().addFreshEntity(mobArrow);
		//});
		//}
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
		LivingEntity entity = this;
		Random random1 = new Random();
		if (!entity.getPersistentData().getBoolean("eatGoldenApple") && this.getHealth() < this.getMaxHealth() * 0.5 && Math.random() > 0.85 && !entity.level().isClientSide) {
			ItemStack oldItem = entity.getMainHandItem();
			ItemStack _setstack = new ItemStack(Items.GOLDEN_APPLE);
			_setstack.setCount(1);
			entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
			entity.getPersistentData().putBoolean("eatGoldenApple", true);
			AnimationPlayer.playAnimation(entity, Animations.BIPED_EAT);
			AnimationPlayer.playAnimation(this, Animations.BIPED_EAT);
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
					entity.level().playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F);
				} else {
					entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
				}
				if (!entity.level.isClientSide())
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

	private static void dropLoot(LevelAccessor world, double x, double y, double z) {
		if (!(world instanceof Level level) || level.isClientSide()) return;

		Item[] drops = new Item[]{Items.DIAMOND, Items.DIAMOND, Items.IRON_INGOT, Items.EMERALD, Items.EMERALD, Items.ENCHANTED_GOLDEN_APPLE, Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE, Items.ENDER_EYE, Items.ENDER_PEARL,BuxinModItems.YUMIJUAN.get(),Items.BOW,Items.ARROW,Items.ARROW,Items.ARROW,Items.ARROW,Items.ARROW,Items.ARROW,Items.ARROW,Items.ARROW};
		for (Item item : drops) {
			ItemEntity entity = new ItemEntity(level, x, y, z, new ItemStack(item));
			entity.setPickUpDelay(10);
			level.addFreshEntity(entity);
		}
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if(this.getMainHandItem().getItem() == Items.GOLDEN_APPLE && AnimationPlayer.getAnimation(this) != Animations.BIPED_EAT){
			AnimationPlayer.playAnimation(this,Animations.BIPED_EAT);
		}
		//PlayermobsAiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		Method_114514.entity_use_command(this,"/team join av_player");
		this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENDER_PEARL));
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
		this.getItemBySlot(EquipmentSlot.HEAD).enchant(Enchantments.ALL_DAMAGE_PROTECTION,5);
		this.getItemBySlot(EquipmentSlot.CHEST).enchant(Enchantments.ALL_DAMAGE_PROTECTION,5);
		this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.POWER_ARROWS,5);
		//Method_114514.add_fakeplayer(this);
		return retval;
	}

	public static void init() {
		//DungeonHooks.addDungeonMob(BuxinModEntities.TheMostMoistBurrit0.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 100);
		builder = builder.add(Attributes.ARMOR, 19);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 999);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 900);
		return builder;
	}
}
