
package net.mcreator.buxin.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.ShifangDangShiTiGengXinKeShiProcedure;
import net.mcreator.buxin.procedures.ShifangDangShiTiSiWangShiProcedure;
import net.mcreator.buxin.procedures.ShifangShiTiChuShiShengChengShiProcedure;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
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

public class ShifangEntity extends Monster {
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

	public ShifangEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.SHIFANG.get(), world);
	}

	public ShifangEntity(EntityType<ShifangEntity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NIUBI_5.get()));
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
		AddCommonAttackGoal.Herobrine(this);
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(8, new FloatGoal(this));
		this.goalSelector.addGoal(9, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(11, new FloatGoal(this));
		this.goalSelector.addGoal(12, new LeapAtTargetGoal(this, (float) 0.5));
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
		if (source.getMsgId().equals("witherSkull"))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		ShifangDangShiTiSiWangShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		ShifangShiTiChuShiShengChengShiProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		this.getPersistentData().putBoolean("snakeblade",false);
		Method_114514.herobrine_born(this);
		return retval;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		double x = this.getX(),y = this.getY(),z = this.getZ();
		this.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entitypatch -> {
			if(entitypatch instanceof LivingEntityPatch<?> livingEntityPatch){
				//DynamicAnimation animation = livingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
				//if(!(animation instanceof AttackAnimation)){
				int value = 2;
				float min = 0.5F;
				float max = 2.5F;
				float rz;
				OpenMatrix4f transformMatrixx;
				byte nL;
				for(int i = 0; i < value; ++i) {
					rz = min + (max - min) * (new Random()).nextFloat();
					transformMatrixx = ((LivingEntityPatch<?>) entitypatch).getArmature().getBindedTransformFor(((LivingEntityPatch<?>) entitypatch).getAnimator().getPose(0f), Armatures.BIPED.toolR);
					transformMatrixx.translate(new Vec3f(0.0F, 0.0F, -rz));
					OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0031415927F, 0.0F)), transformMatrixx, transformMatrixx);
					nL = 4;
					for (int j = 0; j < nL; ++j) {
						if(Math.random() > 0.75) {
							entitypatch.getOriginal().level().addParticle(BuxinModParticleTypes.NIUBI_22.get(), (double) transformMatrixx.m30 + entitypatch.getOriginal().getX(), (double) transformMatrixx.m31 + entitypatch.getOriginal().getY(), (double) transformMatrixx.m32 + entitypatch.getOriginal().getZ(), 0.0, -0.009999999776482582, 0.0);
						}
					}
					for (int j = 0; j < 2; ++j) {
						entitypatch.getOriginal().level().addParticle(ParticleTypes.PORTAL, (double) transformMatrixx.m30 + entitypatch.getOriginal().getX(), (double) transformMatrixx.m31 + entitypatch.getOriginal().getY(), (double) transformMatrixx.m32 + entitypatch.getOriginal().getZ(), 0.0, -0.009999999776482582, 0.0);
					}
				}
			}
		});
		Mob entity = this;
		if(entity.getTarget() != null && Math.random() > 0.9 && entity.onGround() && !entity.isOnFire() && !entity.getPersistentData().getBoolean("wu") && !(entity.getMainHandItem().getItem() == BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get()) && !(this.getPersistentData().getBoolean("snakeblade"))){
			//if(Method_114514.entity1_distance_to_entity2_xyz(entity, entity.getTarget()) > 2){
			Method_114514.entity_use_command(entity,"/chain clear");
			AnimationPlayer.playAnimation(this,BuxinAnimations.SNAKE_BLADE_SKILL);
			this.getPersistentData().putBoolean("snakeblade",true);
			BuxinMod.queueServerWork(4,() -> {
				Method_114514.play_sound(this.level,x,y,z,"buxin:snake_blade");
				Method_114514.send_message_to_all_over_the_world_by_sb_self(this,this.level,"释放!",false);
				Method_114514.entity_use_command(this,"/chain");
			});
			BuxinMod.queueServerWork(144,() -> {
				this.getPersistentData().putBoolean("snakeblade",false);
			});
			//}
		}
		ShifangDangShiTiGengXinKeShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(),this);
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		//super.startSeenByPlayer(player);
		//this.bossInfo.addPlayer(player);
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
		//DungeonHooks.addDungeonMob(BuxinModEntities.SHIFANG.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 145);
		builder = builder.add(Attributes.ARMOR, 129);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 99);
		return builder;
	}
}
