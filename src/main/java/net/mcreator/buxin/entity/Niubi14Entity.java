
package net.mcreator.buxin.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.ai.HeavyAttackUseGoal;
import net.mcreator.buxin.ai.LadderClimbGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.entity.father.RangedMonster;
import net.mcreator.buxin.entity.father.cape_entity.RenderCapeRangedMonster;
import net.mcreator.buxin.entity.woopie.WoopieEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.VFXTool;
import net.mcreator.buxin.procedures.Niubi14DangShiTiShouShangShiProcedure;
import net.mcreator.buxin.procedures.Niubi14DangShiTiSiWangShiProcedure;
import net.mcreator.buxin.procedures.Niubi14EntityTick;
import net.mcreator.buxin.procedures.Niubi14ShiTiChuShiShengChengShiProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
//import se.gory_moon.player_mobs.entity.PlayerMobEntity;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

import static net.mcreator.buxin.item.Niubi2danshouItem.breakArmor5PercentPlus7;

public class Niubi14Entity extends RenderCapeRangedMonster {
	public Niubi14Entity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.NIUBI_14.get(), world);
	}
	public Niubi14Entity(EntityType<Niubi14Entity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 900;
		setNoAi(false);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get()));
		this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
		/*
		this.setItemSlot(EquipmentSlot.CHEST, BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get().getDefaultInstance());
		this.setItemSlot(EquipmentSlot.HEAD, BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get().getDefaultInstance());

		 */
	}
	/*
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (WoopieUUID != null) {
			tag.putUUID("WoopieUUID", WoopieUUID);
		}
	}
	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.hasUUID("WoopieUUID")) {
			WoopieUUID = tag.getUUID("WoopieUUID");
		}
	}

	 */
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(16, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(17, new LadderClimbGoal(this));
		//this.goalSelector.addGoal(2, new FishingTargetGoal(this));
		//this.goalSelector.addGoal(1, new HeavyAttackGoalUseGoal(this));
		this.goalSelector.addGoal(1,new HeavyAttackUseGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		//this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerMobEntity.class, false, false));
		this.goalSelector.addGoal(15, new MeleeAttackGoal(this, 1.4, true) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		AddCommonAttackGoal.Player(this);
		this.targetSelector.addGoal(16, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(17, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(18, new FloatGoal(this));
		this.goalSelector.addGoal(19, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(20, new FloatGoal(this));
		this.goalSelector.addGoal(21, new LeapAtTargetGoal(this, (float) 0.5));
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
		if (source.is(DamageTypes.FALL)) {
			return false;
		}
		Mob entity = this;
		if(this.getMainHandItem().getItem() != BuxinModItems.NIUBI_2DANSHOU.get() && entity.getTarget() != null && Method_114514.entity1_distance_to_entity2_xyz(entity, entity.getTarget()) < 4.5){
			Method_114514.entity_use_command(entity, "/effect give @s epicfight:stun_immunity 12 5");
			this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get()));
		}
		if(Math.random() > 0.999871 && !source.is(DamageTypes.IN_WALL) && !source.is(DamageTypes.MAGIC)){
            Method_114514.send_message_to_all_over_the_world(entity.level(), Component.translatable("chat.buxin.woopie").getString());
            float yaw = entity.yRotO;
            float pitch = entity.xRotO;
            double x_speed = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            double y_speed = -Math.sin(Math.toRadians(pitch));
            double z_speed = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            double speedMultiplier = 4;
            double highMultiplier = 2.5;
            x_speed *= speedMultiplier;
            y_speed *= highMultiplier;
            z_speed *= speedMultiplier;
            Level level = entity.level();
            AnimationPlayer.playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
            double finalX_speed = x_speed;
            double finalY_speed = y_speed;
            double finalZ_speed = z_speed;
            entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
            BuxinMod.queueServerWork(5, () -> {
				entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
                if (level instanceof ServerLevel) {
                    Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "epicfight:sfx.entity_move");
//(player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), new Vec3(finalX_speed, finalY_speed, finalZ_speed)));
                    entity.setDeltaMovement(new Vec3(finalX_speed, finalY_speed, finalZ_speed));
                }
				entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
                if (VFXParticleConfig.VFXParticleConfig.get() && BuxinMod.isWindows() && Math.random() > 0.5) {
                    VFXTool.addVFXParticle(entity.position(), BuxinMod.MODID, "woopie", entity.level());
                }
                BuxinMod.queueServerWork(55, () -> {
                    entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.NIUBI_2DANSHOU.get().getDefaultInstance());
                });
            });
        }
		//Niubi14DangShiTiShouShangShiProcedure.execute(this.level(),this.getX(),this.getY(),this.getZ(),this,source.getEntity());
		return super.hurt(source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		Niubi14DangShiTiSiWangShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
		/*
		if(this.WoopieEntity != null)
			this.WoopieEntity.discard();

		 */
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		if(this.level() instanceof ServerLevel) {
			this.setNoAi(true);
            this.getPersistentData().putBoolean("x_50", false);
			this.getPersistentData().putBoolean("wu", false);
			/*
			if(Math.random() > 0.5) {
				WoopieEntity woopieEntity = new WoopieEntity(BuxinModEntities.WOOPIE.get(), levelaccessor);
				woopieEntity.moveTo(this.getX() + Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), this.getY() + Mth.nextDouble(RandomSource.create(), 1.0D, 3.0D), this.getZ() + Mth.nextDouble(RandomSource.create(), 1.0D, 5.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
				woopieEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
				levelaccessor.addFreshEntity(woopieEntity);
				this.WoopieUUID = woopieEntity.getUUID();
				this.WoopieEntity = woopieEntity;
				woopieEntity.setNullEntity(this);
				woopieEntity.setsteveUUID(this.getUUID());
			}

			 */
			BuxinMod.queueServerWork(15,() -> {
				Method_114514.diamond_protect(this);
				BuxinMod.queueServerWork(15,() -> {
					this.setNoAi(false);
				});
			});
			Niubi14ShiTiChuShiShengChengShiProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
			/*
			((LivingEntity) this).getItemBySlot(EquipmentSlot.FEET).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			((LivingEntity) this).getItemBySlot(EquipmentSlot.LEGS).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			((LivingEntity) this).getItemBySlot(EquipmentSlot.CHEST).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			((LivingEntity) this).getItemBySlot(EquipmentSlot.HEAD).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);

			 */
		}
		return retval;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		/*if(this.getPersistentData().getBoolean("wu") && this.getMainHandItem().getItem() != BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get()){
			this.setItemSlot(EquipmentSlot.MAINHAND,BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get().getDefaultInstance());
		}

		 */
		if(this.onGround()){
			Method_114514.AngrySteveshootArrowAi(this);
		}
		Niubi14EntityTick.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
	}

	/*LivingEntity target = this.getTarget();
		double distanceToTarget = 0;
		if (target != null) {
			distanceToTarget = this.distanceTo(target);
		}
		super.customServerAiStep();
		if(!(target == null)){
			this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(BuxinModItems.WOOPIE.get()));
			if (!this.getPersistentData().getBoolean("wu")) {
				if (distanceToTarget < 4.1) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get()));
					this.goalSelector.removeGoal(new RangedAttackGoal(this, 1.2, 20, 15));
					this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.2, 20, 15f));
				} else {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
					//this.goalSelector.removeGoal(new MeleeAttackGoal(this, 1.2, false));
					this.goalSelector.removeGoal(new RangedAttackGoal(this, 1.2, 20, 15));
					this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.2, 20, 15f));
					//this.goalSelector.addGoal(22, new RangedAttackGoal(this, 1.25, 20, 35.1f));
				}
			} else if(this.getPersistentData().getBoolean("wu")){
				if(!this.getPersistentData().getBoolean("erpl")) {
					if(!this.getPersistentData().getBoolean("eatGoldenApple")) {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get()));
					}
				}
			} else if(this.getPersistentData().getBoolean("erpl")){
				if(!this.getPersistentData().getBoolean("wu")) {
					if(!this.getPersistentData().getBoolean("eatGoldenApple")) {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.ENDER_PEARL));
					}
				}
			} else if(this.getPersistentData().getBoolean("eatGoldenApple")){
				if(!this.getPersistentData().getBoolean("wu")) {
					if(!this.getPersistentData().getBoolean("erpl")) {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_APPLE));
					}
				}
			}
		}

	 */
	/*
	@Override
	protected void customServerAiStep() {
		try {
			LivingEntity target = this.getTarget();
			double distanceToTarget = 0.0;
			if (target != null) {
				distanceToTarget = (double) this.distanceTo(target);
			}
			super.customServerAiStep();
			if (target != null) {
				this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack((ItemLike) BuxinModItems.WOOPIE.get()));
				if (!this.getPersistentData().getBoolean("wu")) {
					if (distanceToTarget < 4.1) {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) BuxinModItems.NIUBI_2DANSHOU.get()));
						this.goalSelector.removeGoal(new RangedAttackGoal(this, 1.2, 20, 15.0F));
						this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.2, 20, 15.0F));
					} else {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
						this.goalSelector.removeGoal(new RangedAttackGoal(this, 1.2, 20, 15.0F));
						this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.2, 20, 15.0F));
					}
				} else if (this.getPersistentData().getBoolean("wu")) {
					if (!this.getPersistentData().getBoolean("erpl") && !this.getPersistentData().getBoolean("eatGoldenApple")) {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get()));
					}
				} else if (this.getPersistentData().getBoolean("erpl")) {
					if (!this.getPersistentData().getBoolean("wu") && !this.getPersistentData().getBoolean("eatGoldenApple")) {
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.ENDER_PEARL));
					}
				} else if (this.getPersistentData().getBoolean("eatGoldenApple") && !this.getPersistentData().getBoolean("wu") && !this.getPersistentData().getBoolean("erpl")) {
					this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_APPLE));
				}
			}
		} catch (Exception e) {
        }
    }

	 */
	/*
	public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
		ItemStack weaponStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, this::canFireProjectileWeapon));
		ItemStack itemstack = this.getProjectile(weaponStack);
		AbstractArrow mobArrow = ProjectileUtil.getMobArrow(this, itemstack, pDistanceFactor);
		if (this.getMainHandItem().getItem() instanceof BowItem) {
			mobArrow = ((BowItem)this.getMainHandItem().getItem()).customArrow(mobArrow);
		}
		PathNavigation navigation = this.getNavigation();
		Path path = navigation.createPath(pTarget, 0);
		if (path != null) {
			navigation.moveTo(path, 2.12);
		} else {
			//System.out.println("cant go");
			this.getLookControl().setLookAt(pTarget, 10.0f, 10.0f);
		}
		this.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
			if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
				if(!(this.getMainHandItem().getItem() == BuxinModItems.NIUBI_2DANSHOU.get()))
					if(!(this.getMainHandItem().getItem() == BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get())){
						LivingEntityPatch.playAnimationSynchronized(BuxinAnimations.BOW_SHOOT,0F);
					}
			}
		});
		double x = pTarget.getX() - this.getX();
		double y = pTarget.getY(0.3333333333333333) - mobArrow.getY();
		double z = pTarget.getZ() - this.getZ();
		double d3 = Math.sqrt(x * x + z * z);
		//if(!this.getPersistentData().getBoolean("wu")){
			//if(!this.getPersistentData().getBoolean("erpl")) {
				mobArrow.shoot(x, y + d3 * 0.20000000298023224, z, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
				this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
				this.level.addFreshEntity(mobArrow);
			//}
		//}
	}


	protected AbstractArrow getArrow(ItemStack p_32156_, float p_32157_) {
		return ProjectileUtil.getMobArrow(this, p_32156_, p_32157_);
	}


	 */
	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	public static void init() {
		//DungeonHooks.addDungeonMob(BuxinModEntities.NIUBI_14.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.375);
		builder = builder.add(Attributes.MAX_HEALTH, 100);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 888);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 988);
		return builder;
	}
	/*
	public boolean canFireProjectileWeapon(ProjectileWeaponItem p_32144_) {
		return p_32144_ == Items.BOW;
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

	 */
	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean $$1 = super.doHurtTarget(entity);
		if(entity instanceof LivingEntity livingEntity) {
			if (Method_114514.isWearingAnyArmor(livingEntity)) {
			/*
			entity.getItemBySlot(EquipmentSlot.CHEST).setDamageValue(entity.getItemBySlot(EquipmentSlot.CHEST).getDamageValue() - (int) (entity.getItemBySlot(EquipmentSlot.CHEST).getMaxDamage() * 0.15) + 35);
			entity.getItemBySlot(EquipmentSlot.LEGS).setDamageValue(entity.getItemBySlot(EquipmentSlot.LEGS).getDamageValue() - (int) (entity.getItemBySlot(EquipmentSlot.LEGS).getMaxDamage() * 0.15) + 30);
			entity.getItemBySlot(EquipmentSlot.HEAD).setDamageValue(entity.getItemBySlot(EquipmentSlot.HEAD).getDamageValue() - (int) (entity.getItemBySlot(EquipmentSlot.HEAD).getMaxDamage() * 0.15) + 35);
			entity.getItemBySlot(EquipmentSlot.FEET).setDamageValue(entity.getItemBySlot(EquipmentSlot.FEET).getDamageValue() - (int) (entity.getItemBySlot(EquipmentSlot.FEET).getMaxDamage() * 0.15) + 30);

			 */
				breakArmor5PercentPlus7(livingEntity);
				entity.playSound(SoundEvents.GLASS_BREAK, 6.75f, 0.9f + new Random().nextFloat(0.15f));
				Random random = new Random();
				int a = random.nextInt(4);
				if (a == 0) {
					if (!livingEntity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
						Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem()) + " ~ ~1. ~ 0 0 0 0.15 60");
					}
				} else if (a == 1) {
					if (!livingEntity.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
						Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem()) + " ~ ~0.55 ~ 0 0 0 0.15 60");
					}
				} else if (a == 2) {
					if (!livingEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
						Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem()) + " ~ ~1.45 ~ 0 0 0 0.15 60");
					}
				} else {
					if (!livingEntity.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
						Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem()) + " ~ ~0.25 ~ 0 0 0 0.15 60");
					}
				}
			} else if (!livingEntity.getMainHandItem().isEmpty()) {
				livingEntity.getMainHandItem().hurtAndBreak(20 + new Random().nextInt(10), livingEntity, e -> {
				});
				entity.playSound(SoundEvents.GLASS_BREAK, 6.75f, 0.9f + new Random().nextFloat(0.15f));
				Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(livingEntity.getItemBySlot(EquipmentSlot.MAINHAND).getItem()) + " ~ ~1. ~ 0 0 0 0.15 60");
			} else if (!livingEntity.getOffhandItem().isEmpty()) {
				livingEntity.getOffhandItem().hurtAndBreak(20 + new Random().nextInt(10), livingEntity, e -> {
				});
				entity.playSound(SoundEvents.GLASS_BREAK, 6.75f, 0.9f + new Random().nextFloat(0.15f));
				Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(livingEntity.getItemBySlot(EquipmentSlot.OFFHAND).getItem()) + " ~ ~1. ~ 0 0 0 0.15 60");
			} else {
				entity.hurt(this.damageSources().mobAttack(livingEntity), (float) (livingEntity.getMaxHealth() * 0.2 + 4));
			}
		}
		return $$1;
	}
}
