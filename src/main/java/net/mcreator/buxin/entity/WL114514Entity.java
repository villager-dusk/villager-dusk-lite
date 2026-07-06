
package net.mcreator.buxin.entity;

import net.mcreator.buxin.ai.AutoMineGoal;
import net.mcreator.buxin.ai.InventoryManagerGoal;
import net.mcreator.buxin.ai.VillagerQLearningGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.entity.father.cape_entity.RenderCapeRangedMonster;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.WL114514ShiTiChuShiShengChengShiProcedure;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;
import java.util.Random;

public class WL114514Entity extends RenderCapeRangedMonster {
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);
	private ItemStack originalItem;
	public WL114514Entity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.WL_114514.get(), world);
	}

	public WL114514Entity(EntityType<WL114514Entity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setCustomNameVisible(false);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.ENDER_GLOCK_17.get()));
		this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(BuxinModItems.ENDER_GLOCK_17.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
	}


	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new AutoMineGoal(this));
		this.goalSelector.addGoal(1, new InventoryManagerGoal(this));
		this.level().getServer().execute(() -> {
			if (this.goalSelector.getAvailableGoals().stream()
					.noneMatch(goal -> goal.getGoal() instanceof VillagerQLearningGoal)) {
				this.goalSelector.addGoal(6, new VillagerQLearningGoal(this));
			}
		});
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.4, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		AddCommonAttackGoal.Herobrine(this);
		AddCommonAttackGoal.Villager(this);
		AddCommonAttackGoal.Player(this);
		AddCommonAttackGoal.Bluedemon(this);
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(9, new FloatGoal(this));
		this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(12, new FloatGoal(this));
		this.goalSelector.addGoal(13, new LeapAtTargetGoal(this, (float) 0.5));
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
		VillagerQLearningGoal.onAttacked();
		return super.hurt(source, amount);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		originalItem = this.getMainHandItem().getItem().getDefaultInstance();
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		WL114514ShiTiChuShiShengChengShiProcedure.execute(world, this);
		return retval;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		Method_114514.entity_use_command(this,"/particle buxin:ender ~ ~0.9 ~ 0 0 0 0.1 8");
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
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 300);
		builder = builder.add(Attributes.ARMOR, 20);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 900);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		return builder;
	}
}
