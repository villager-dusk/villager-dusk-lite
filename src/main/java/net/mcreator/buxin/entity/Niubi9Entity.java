
package net.mcreator.buxin.entity;

import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.entity.dragon.baby.BabyDragonBeamEntity;
import net.mcreator.buxin.entity.dragon.baby.BabyEnderDragonEntity;
import net.mcreator.buxin.entity.dragon.normal.DragonBeamEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Math114514;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.Niubi9DangShiTiGengXinKeShiProcedure;
import net.mcreator.buxin.procedures.Niubi9ShiTiChuShiShengChengShiProcedure;
import net.mcreator.buxin.utils.DragonLookController;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

import static net.mcreator.buxin.entity.dragon.baby.BabyEnderDragonEntity.posBehind3D;

public class Niubi9Entity extends Monster {
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);
	private EnderDragon enderDragon;
	private UUID enderDragonUUID;
	private boolean spawnEnderDragon = false;
	private int breathCooldown = 0;
	private int nextStack = 3;
	private int dragonSummonCooldown = 3600;

	public Niubi9Entity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.NIUBI_9.get(), world);
	}

	public Niubi9Entity(EntityType<Niubi9Entity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NIUBI_8.get()));
	}

	public int getBreathCooldown(){
		return breathCooldown;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		//this.goalSelector.addGoal(1,new DragonGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		AddCommonAttackGoal.Herobrine(this);
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(5, new HurtByTargetGoal(this).setAlertOthers());
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(7, new FloatGoal(this));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(10, new FloatGoal(this));
		this.goalSelector.addGoal(11, new LeapAtTargetGoal(this, (float) 0.5));
	}
	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (enderDragonUUID != null) {
			tag.putUUID("EnderDragonUUID", enderDragonUUID);
		}
		tag.putBoolean("SpawnEnderDragon", spawnEnderDragon);
		tag.putInt("NextStack", nextStack);
		tag.putInt("DragonSummonCooldown", dragonSummonCooldown);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.hasUUID("EnderDragonUUID")) {
			enderDragonUUID = tag.getUUID("EnderDragonUUID");
		}
		spawnEnderDragon = tag.getBoolean("SpawnEnderDragon");
		nextStack = tag.contains("NextStack") ? tag.getInt("NextStack") : nextStack;
		dragonSummonCooldown = tag.contains("DragonSummonCooldown") ? tag.getInt("DragonSummonCooldown") : dragonSummonCooldown;
	}

	private void spawnEnderDragon() {
		if (this.level() instanceof ServerLevel levelaccessor) {

            EnderDragon dragon = new EnderDragon(EntityType.ENDER_DRAGON, levelaccessor);
			dragon.moveTo(this.getX() + new Random().nextInt(20), this.getY() + 15.0D, this.getZ() - new Random().nextInt(10), new Random().nextFloat() * 360.0F, 0.0F);
			if(levelaccessor instanceof ServerLevel) {
				dragon.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(dragon.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
			}

		//	dragon.setFightOrigin(BlockPos.containing(this.getX(), this.getY(), this.getZ()));
			dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
			dragon.getPersistentData().putUUID("herobrine_uuid", this.getUUID());
			dragon.setCustomName(Component.literal("Herobrine-Ender-Dragon"));
			dragon.getPersistentData().putBoolean("CirclingAnchor", true);
			dragon.getPersistentData().putUUID("AnchorUUID", this.getUUID());
			levelaccessor.addFreshEntity(dragon);
			this.enderDragonUUID = dragon.getUUID();
			this.enderDragon = dragon;
			//this.startRiding(enderDragon);
			AddCommonAttackGoal.Herobrine(dragon);
			if (this.level().getServer() != null) {
				this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getDisplayName().getString() + "> " + Component.translatable("chat.buxin.summon_ender_dragon").getString()), false);
			}
		}
	}
	private void updateLinkedDragon() {
		if(enderDragon == null){
			return;
		}
		if (enderDragon.getPhaseManager().getCurrentPhase().getPhase() != EnderDragonPhase.HOLDING_PATTERN) {
			enderDragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
		}

		enderDragon.getPersistentData().putDouble("AnchorX", this.getX());
		enderDragon.getPersistentData().putDouble("AnchorY", this.getY() + 15.0D);
		enderDragon.getPersistentData().putDouble("AnchorZ", this.getZ());
	}
	public void shootThunderBreathAtTarget(LivingEntity target) {
		if (target != null){
				EnderDragonPart head = enderDragon.head;
				Vec3 headPos = new Vec3(head.getX(), head.getY(), head.getZ());
				Vec3 from = new Vec3(enderDragon.head.getX(), enderDragon.head.getEyeY(), enderDragon.head.getZ());
				DragonBeamEntity beam = new DragonBeamEntity(BuxinModEntities.DRAGON_BEAM.get(), this.level(), enderDragon, target, headPos.x, headPos.y, headPos.z, 80, 2);
				this.level().addFreshEntity(beam);
		} else {
			Method_114514.send_message_to_all_over_the_world(this.level(),"Warning:entity.getTarget() is null");
		}
	}
	@Override
	public void remove(RemovalReason pReason) {
		if (this.enderDragon != null) {
			this.enderDragon.discard();
		}
		super.remove(pReason);
	}
	public int getCooldownTicks() {
		return this.getPersistentData().getInt("DragonCooldown");
	}
	public void setCooldownTicks(int ticks) {
		this.getPersistentData().putInt("DragonCooldown", ticks);
	}
	@Override
	public void tick() {
		super.tick();
		boolean playSound = false;
		updateLinkedDragon();

		if(this.getTarget() != null && this.getTarget() instanceof EnderDragon){
			this.setTarget(null);
		}
		if (!this.level().isClientSide) {
			if(this.isOnFire()){
				this.clearFire();
			}
			if (!spawnEnderDragon) {
				this.spawnEnderDragon = true;
				spawnEnderDragon();
			}
			if (enderDragon == null && enderDragonUUID == null) {
				if (dragonSummonCooldown <=0) {
					spawnEnderDragon = false;
				} else {
					dragonSummonCooldown--;
				}
			}
			if (enderDragon == null && enderDragonUUID != null) {
				Entity entity = ((ServerLevel) this.level()).getEntity(enderDragonUUID);
				if (entity instanceof EnderDragon dragon) {
					enderDragon = dragon;
				} else {
					enderDragon = null;
				}
			}
			if (enderDragon != null && enderDragon.getHealth() <= 50) {
				enderDragon.discard();
				enderDragon = null;
				enderDragonUUID = null;
				dragonSummonCooldown = 3600;
			}
			if (enderDragon != null && enderDragon.isAlive()) {
				LivingEntity target = this.getTarget();
				if (target != null && target.isAlive()) {
					if (breathCooldown <= 0) {
						shootThunderBreathAtTarget(target);
						breathCooldown = 60 + this.getRandom().nextInt(20);
					}
				}
			}
			if (breathCooldown > 0) breathCooldown--;

			if (this.getPersistentData().getBoolean("SecondForm")) {
				int cooldown = getCooldownTicks();
				if (cooldown > 0) {
					setCooldownTicks(cooldown - 1);
				} else {
					this.getPersistentData().remove("SecondForm");
				}
			} else if (!this.getPersistentData().getBoolean("SecondForm") && this.getPersistentData().getInt("HitCount") >= nextStack) {
				this.getPersistentData().putBoolean("SecondForm", true);
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2));
				this.getPersistentData().remove("HitCount");
				nextStack = new Random().nextInt(3, 6);
				playSound = true;
			}
		}
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

	public EnderDragon getEnderDragon(){
		return enderDragon;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.LIGHTNING_BOLT))
			return false;
		if (source.is(DamageTypes.DRAGON_BREATH))
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.MAGIC))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		Method_114514.herobrine_born(this);
		Niubi9ShiTiChuShiShengChengShiProcedure.execute(this);
		return retval;
	}
	private void spawnBabyEnderDragon(ItemStack itemstack, Entity entity) {
		if (entity.level() instanceof ServerLevel levelaccessor) {
            BabyEnderDragonEntity babyEnderDragonEntity = new BabyEnderDragonEntity((EntityType) BuxinModEntities.BABY_ENDER_DRAGON.get(), (ServerLevel) levelaccessor);
			Vec3 posBehind3D = posBehind3D(entity, 1.0D, 2.0D, 1.0D);
			babyEnderDragonEntity.moveTo(posBehind3D.x, posBehind3D.y, posBehind3D.z);
			babyEnderDragonEntity.setFollowTarget(entity);
			babyEnderDragonEntity.setFollowTargetUUID(entity.getUUID());
			babyEnderDragonEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
			levelaccessor.addFreshEntity(babyEnderDragonEntity);
            if (itemstack.getTag() != null) {
                itemstack.getTag().putUUID("DragonSummon", babyEnderDragonEntity.getUUID());
            }
        }
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if(enderDragon != null){
			if(enderDragon.tickCount % 10 == 0){
				if(this.getTarget() != null) {
					DragonLookController.makeDragonLookAt(enderDragon, this.getTarget());
				}
			}
			if(enderDragon.getX() != this.getX() - 15) {
				enderDragon.moveTo(this.getX() - 15, enderDragon.getY(), enderDragon.getZ());
			}
			if(enderDragon.getY() != this.getY() + 20) {
				enderDragon.moveTo(enderDragon.getX(), this.getY() + 20, enderDragon.getZ());
			}
			if (enderDragon.getZ() != this.getZ() + 15) {
				enderDragon.moveTo(enderDragon.getX(), enderDragon.getY(), this.getZ() + 15);
			}
		}
		Niubi9DangShiTiGengXinKeShiProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
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
	@Override
	public boolean doHurtTarget(Entity pEntity) {
		if (!pEntity.level().isClientSide()) {
			if (!this.getPersistentData().getBoolean("SecondForm")) {
				this.getPersistentData().putInt("HitCount", (this.getPersistentData().contains("HitCount") ? this.getPersistentData().getInt("HitCount") : 0) + 1);
			}
		}
		return super.doHurtTarget(pEntity);
	}
	public static void init() {
		SpawnPlacements.register(BuxinModEntities.NIUBI_9.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getRawBrightness(pos, 0) > 4));
		DungeonHooks.addDungeonMob(BuxinModEntities.NIUBI_9.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 220);
		builder = builder.add(Attributes.ARMOR, 80);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 200);
		return builder;
	}
}
