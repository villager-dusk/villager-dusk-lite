
package net.mcreator.buxin.entity.greg_hb;

import com.mojang.authlib.GameProfile;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.VFXTool;
import net.mcreator.buxin.procedures.IsBuxinEntityProcedure;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GregHbEntity extends Monster {
	public static void init() {
		DungeonHooks.addDungeonMob(BuxinModEntities.GREG_HB.get(), 180);
	}
	// MODIFIED: EpicFight 20.9.7 uses LivingEntityPatch<?> instead of raw LivingEntityPatch, and getEntityPatch now requires Level parameter
	// Also: moved from field to method to avoid NPE during construction (this is not fully initialized yet)
	private LivingEntityPatch<?> livingentitypatch;

	public static boolean isUseHerobrineTexture = false;

	public LivingEntityPatch<?> getLivingentitypatch() {
		if (livingentitypatch == null && this.level() != null) {
			livingentitypatch = (LivingEntityPatch<?>) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
		}
		return livingentitypatch;
	}

	public GregHbEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
		this(BuxinModEntities.GREG_HB.get(), level);
	}

	public GregHbEntity(EntityType<GregHbEntity> entitytype, Level level) {
		super(entitytype, level);
		maxUpStep = 0.3f;
		this.xpReward = 50;
		this.setNoAi(false);
		this.setPersistenceRequired();
		this.setCustomName(Component.literal("Greg"));
		this.setCustomNameVisible(false);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, VillagerScoutEntity.class, 1.0F, 1.2D, 1.8D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, CunMinWeiBingEntity.class, 1.0F, 1.2D, 1.8D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PurpleVillagerCavalryEntity.class, 1.0F, 1.2D, 1.8D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, GreenVillagerCavalryEntity.class, 1.0F, 1.2D, 1.8D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, RedVillagerEntity.class, 1.0F, 1.2D, 1.8D));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
	}

	public @NotNull MobType getMobType() {
		return MobType.UNDEFINED;
	}

	public double getMyRidingOffset() {
		return -0.35D;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		Entity entity = this;
		LevelAccessor world = this.level();
	}

	/*
	@Override
	public void tick() {
		super.tick();
		this.floatOnAnyFluid();
		this.checkInsideBlocks();
		if (!this.level().isClientSide()) {
			if (!isDay(this.level())) {
				if (!this.isWhiteEye()) {
					setWhiteEye(true);
				}
				if (!this.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get())) {
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
				}
			} else {
				if (this.summonTiming == -1) {
					setWhiteEye(false);
				}
				if (!this.combatMode && this.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get())) {
					this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
				}
			}

			if (this.level().getDayTime() % 24000L == this.summonTimestamp) {
				this.summonTimestamp = -2;
				this.combatMode = true;
				this.setNoAi(true);
				this.summoning = true;
				this.summonTiming = 20;
				this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 120, 3, false, false));
			}

			if (this.getHealth() <= 2 && this.summonTiming == -1) {
				if (!isDay(this.level())) {
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
				}
				setWhiteEye(true);
				this.setNoAi(true);
				this.summoning = true;
				this.summonTiming = 20;
				this.setHealth(1);
				this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 120, 3, false, false));
			}

			if (this.summonTiming > 0) {
				this.summonTiming = this.summonTiming - 1;
			}
			if (this.summonTiming == 10) {
				try {
					this.getServer().getCommands().getDispatcher().execute(
							"playsound buxin:summoning voice @a ~ ~ ~",
							this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
				} catch (CommandSyntaxException e) {
				}
				this.level().getServer().getPlayerList().broadcastSystemMessage(Component.translatable("chat.buxin.greg_hb_summon"), false);
			}
			if (this.summonTiming == 1) {
				if (this.combatMode) {
					summonHerobrines();
				} else {
					summonHerobrinesAndEscape();
				}
			}

			if (this.escapeTiming > 0) {
				this.escapeTiming = this.escapeTiming - 1;
			}
			if (this.escapeTiming == 60 && this.combatMode) {
				try {
					this.getServer().getCommands().getDispatcher().execute(
							"playsound buxin:summoning neutral @a ~ ~ ~",
							this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
				} catch (CommandSyntaxException e) {
				}
				if (livingentitypatch != null) {
					livingentitypatch.playAnimationSynchronized(BuxinAnimations.SUMMONING, 0.0F);
				}
			}
			if (this.escapeTiming == 40) {
				if (this.level() instanceof ServerLevel serverLevel) {
					sinkIntoGround(serverLevel, this, 0.06);
				}
			}
			if (this.escapeTiming == 1) {
				//this.discard();
			}

			if (firstSummonedHerobrine == null && firstSummonedHerobrineUUID != null) {
				Entity entity = ((ServerLevel) this.level()).getEntity(firstSummonedHerobrineUUID);
				if (entity instanceof Monster || entity instanceof HerobrineDsEntity) {
					firstSummonedHerobrine = entity;
				} else {
					firstSummonedHerobrineUUID = null;
				}
			}
			if (firstSummonedHerobrine != null && !firstSummonedHerobrine.isAlive()) {
				firstSummonedHerobrineUUID = null;
			}

			if (secondSummonedHerobrine == null && secondSummonedHerobrineUUID != null) {
				Entity entity = ((ServerLevel) this.level()).getEntity(secondSummonedHerobrineUUID);
				if (entity instanceof Monster || entity instanceof HerobrineDsEntity) {
					secondSummonedHerobrine = entity;
				} else {
					secondSummonedHerobrineUUID = null;
				}
			}
			if (secondSummonedHerobrine != null && !secondSummonedHerobrine.isAlive()) {
				secondSummonedHerobrineUUID = null;
			}

			if (thirdSummonedHerobrine == null && thirdSummonedHerobrineUUID != null) {
				Entity entity = ((ServerLevel) this.level()).getEntity(thirdSummonedHerobrineUUID);
				if (entity instanceof Monster || entity instanceof HerobrineDsEntity) {
					thirdSummonedHerobrine = entity;
				} else {
					thirdSummonedHerobrineUUID = null;
				}
			}
			if (thirdSummonedHerobrine != null && !thirdSummonedHerobrine.isAlive()) {
				thirdSummonedHerobrineUUID = null;
			}

			if (this.combatMode && this.escapeTiming == -1 && this.summonTiming == -2
					&& this.firstSummonedHerobrineUUID == null
					&& this.secondSummonedHerobrineUUID == null
					&& this.thirdSummonedHerobrineUUID == null) {
				this.escapeTiming = 80;
				this.setNoAi(true);
			}

			if (this.combatMode && this.escapeTiming == -1 && this.recallTime >= 0) {
				this.recallTime = this.recallTime - 1;
				if (this.recallTime == 20) {
					this.setNoAi(true);
				}
				if (this.recallTime <= 0) {
					this.escapeTiming = 61;
				}
			}

			if (this.combatMode) {
				this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 1, 3, false, false));
			}
		}
	}
	*/

	private void summonHerobrine(String herobrineMobId, double spawnX, double spawnY,
								 double spawnZ, double summonLookX, double summonLookZ, boolean renderPortal) {
		if (this.level() instanceof ServerLevel levelaccessor) {
			ResourceLocation mobResourceLocation = new ResourceLocation(herobrineMobId);
			EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(mobResourceLocation);
			if (type != null && type.create(this.level()) instanceof Mob herobrine) {
				if (herobrine instanceof HerobrineDsEntity hb) {
					hb.setSummoned(true);
				} else if (herobrine instanceof HerobrineDsEntity lowShadowHerobrineCloneEntity) {
					lowShadowHerobrineCloneEntity.setSummoned(true);
				}
				Random random1 = new Random();
				if(Math.random() > 0.5) {
					herobrine.moveTo(spawnX- random1.nextInt(10), spawnY, spawnZ- random1.nextInt(15), this.getYRot(), this.getXRot());
				} else {
					herobrine.moveTo(spawnX + random1.nextInt(15), spawnY, spawnZ+ random1.nextInt(20), this.getYRot(), this.getXRot());
				}
				herobrine.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(summonLookX, spawnY, summonLookZ));
				herobrine.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				levelaccessor.addFreshEntity(herobrine);
			}
		}
	}

	private void spawnHerobrineOffset(String id,
									  double forwardDist, double lateralDist, double baseY,
									  double fx, double fz, double lx, double lz, boolean renderPortal) {
		double spawnX = this.getX() + fx * forwardDist + lx * lateralDist;
		double spawnZ = this.getZ() + fz * forwardDist + lz * lateralDist;

		double lookX = spawnX + fx * 10.0;
		double lookZ = spawnZ + fz * 10.0;

		summonHerobrine(id, spawnX, baseY, spawnZ, lookX, lookZ, renderPortal);
	}

	private enum ElitePattern {
		SOLO_1E,
		ONEE_PLUS_1S,
		ONEE_PLUS_2S,
		TWO_E,
		TWOE_PLUS_1S,
		THREE_E
	}

	private ElitePattern pickWeightedElitePattern(Random random) {
		double roll = random.nextDouble();
		if (roll <= 0.1F) {
			return ElitePattern.THREE_E;
		} else if (roll <= 0.2F) {
			return ElitePattern.TWOE_PLUS_1S;
		} else if (roll <= 0.3F) {
			return ElitePattern.TWO_E;
		} else if (roll <= 0.4F) {
			return ElitePattern.ONEE_PLUS_2S;
		} else if (roll <= 0.5F) {
			return ElitePattern.ONEE_PLUS_1S;
		} else {
			return ElitePattern.SOLO_1E;
		}
	}

	private static <T> T pickRandom(List<T> list, Random random) {
		return list.remove(random.nextInt(list.size()));
	}

	private void summonAtNight() {
		List<String> herobrines = new ArrayList<>();
		herobrines.add("buxin:shadow_herobrine");
		herobrines.add("buxin:null");
		herobrines.add("buxin:herobrine_7");

		List<String> elites = new ArrayList<>();
		elites.add("buxin:herobrine_ds");
		elites.add("buxin:herobrine_ds");
		elites.add("buxin:herobrine_ds");
		elites.add("buxin:shifang");
		elites.add("buxin:yingchuihim");
		elites.add("buxin:changdaohimnb");
		elites.add("buxin:shifang");
		elites.add("buxin:black_shield_herobrine");
		elites.add("buxin:herobrine_ds");
		elites.add("buxin:herobrine_ds");
		elites.add("buxin:herobrine_ds");

		float yaw = this.getYRot();
		double rad = Math.toRadians(yaw);
		double fx = -Math.sin(rad);
		double fz =  Math.cos(rad);
		double lx =  Math.cos(rad);
		double lz =  Math.sin(rad);

		double baseY = this.getY();
		double centerForward = 3.0;
		double side = 1.0;
		double thirdForward = 4.0;

		double centerX = this.getX() + fx * centerForward;
		double centerZ = this.getZ() + fz * centerForward;
		double lookX   = centerX + fx * 10.0;
		double lookZ   = centerZ + fz * 10.0;

		this.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(centerX, baseY, centerZ));

		if (!(this.level() instanceof ServerLevel)) return;
		Random random = new Random();

		ElitePattern pattern = pickWeightedElitePattern(random);

		switch (pattern) {
			case SOLO_1E -> {
				summonHerobrine(pickRandom(elites, random), centerX, baseY, centerZ, lookX, lookZ, false);
			}
			case ONEE_PLUS_1S -> {
				spawnHerobrineOffset(pickRandom(elites, random), centerForward, +side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset("buxin:herobrine_ds", centerForward, -side, baseY, fx, fz, lx, lz, false);
			}
			case ONEE_PLUS_2S -> {
				spawnHerobrineOffset("buxin:herobrine_ds", centerForward, +side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset("buxin:herobrine_ds", centerForward, -side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset(pickRandom(elites, random), thirdForward, 0.0, baseY, fx, fz, lx, lz, false);
			}
			case TWO_E -> {
				spawnHerobrineOffset(pickRandom(elites, random), centerForward, +side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset(pickRandom(elites, random), centerForward, -side, baseY, fx, fz, lx, lz, false);
			}
			case TWOE_PLUS_1S -> {
				spawnHerobrineOffset("buxin:herobrine_ds", centerForward, +side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset(pickRandom(elites, random), centerForward, -side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset(pickRandom(elites, random), thirdForward, 0.0, baseY, fx, fz, lx, lz, false);
			}
			case THREE_E -> {
				spawnHerobrineOffset(pickRandom(elites, random), centerForward, +side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset(pickRandom(elites, random), centerForward, -side, baseY, fx, fz, lx, lz, false);
				spawnHerobrineOffset(pickRandom(elites, random), thirdForward, 0.0, baseY, fx, fz, lx, lz, false);
			}
		}
	}

	private void summonHerobrines() {
		if (livingentitypatch != null) {
			livingentitypatch.playAnimationSynchronized(BuxinAnimations.SUMMONING, 0.0F);
		}
		this.setCustomNameVisible(false);
		summonAtNight();
	}

	public boolean removeWhenFarAway(double x) {
		return false;
	}

	public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	public @NotNull SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		//Method_114514.add_fakeplayer(this);
		this.getPersistentData().putBoolean("isUseHerobrineTexture",false);
		BuxinMod.queueServerWork(40,() -> {
			this.getPersistentData().putBoolean("isUseHerobrineTexture",true);
			this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 3, false, false));
			ServerLevel serverLevel = (ServerLevel) world;
			IsBuxinEntityProcedure.execute(this);
			this.setNoAi(true);
			BuxinMod.queueServerWork(65,() -> {
				this.setNoAi(false);
			});
			VFXTool.addVFXParticle(this.position(),BuxinMod.MODID,"greg_summon",this.level());
			Method_114514.entity_use_command(this, "/effect give @s minecraft:slowness 3 255");
			Random random2 = new Random();
			int v = random2.nextInt(4);
			for (int i = 0; i < v + 2; i++) {
				if (Math.random() > 0.95) {
					summonAtNight();
				} else {
					summonHerobrines();
				}
				//AnimationPlayer.playAnimation(this,BuxinAnimations.SUMMONING);
				BuxinMod.queueServerWork(5,() -> {
					final Vec3 _center = new Vec3(this.getX(),this.getY(), this.getZ());
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(46 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
						if(!(entityiterator instanceof Player) && !(entityiterator == this))
							AnimationPlayer.playAnimation(entityiterator,BuxinAnimations.HB_born);
					}
				});
			}
			Method_114514.play_sound(this.level(), this.getX(), this.getY(), this.getZ(), "buxin:summoning");
			BlockPos blockPos = this.getOnPos();
			int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
			BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
			this.moveTo(spawnPos, this.getYRot(), this.getXRot());

			if (!this.level().isClientSide() && this.level().getServer() != null)
				this.level().getServer().getPlayerList().broadcastSystemMessage(Component.translatable("chat.buxin.greg_hb_summon"), false);
			BuxinMod.queueServerWork(100,() -> {
				this.getPersistentData().putBoolean("isUseHerobrineTexture",false);
			});
		});
		return retval;
	}


	public void awardKillScore(Entity entity, int i, DamageSource damagesource) {
		super.awardKillScore(entity, i, damagesource);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 3, false, false));
		if (!this.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get())) {
			this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();

		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5D);
		builder = builder.add(Attributes.MAX_HEALTH, 80.0D);
		builder = builder.add(Attributes.ARMOR, 20.0D);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
		return builder;
	}
}
