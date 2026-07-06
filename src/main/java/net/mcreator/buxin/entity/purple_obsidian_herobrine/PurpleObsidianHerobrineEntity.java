package net.mcreator.buxin.entity.purple_obsidian_herobrine;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.DelayedTask;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.entity.*;
//import net.mcreator.buxin.entity.player_mob.HerobrinePlayerMobEntity;
import net.mcreator.buxin.entity.projectileblock.BuxinBlockProjectileEntity;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.IsBuxinEntityProcedure;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
//import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;


public class PurpleObsidianHerobrineEntity extends Monster {
    private boolean wasLanding = false;
    private boolean wasAiming = false;

    public BuxinBlockProjectileEntity UpObsidian;
    public UUID UpObsidianUUID;
    public BuxinBlockProjectileEntity LeftObsidian;
    public UUID LeftObsidianUUID;
    public BuxinBlockProjectileEntity RightObsidian;
    public UUID RightObsidianUUID;
    public BuxinBlockProjectileEntity RightObsidian2;
    public UUID RightObsidianUUID2;
    public BuxinBlockProjectileEntity LeftObsidian2;
    public UUID LeftObsidianUUID2;

    public PurpleObsidianHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) BuxinModEntities.PURPLE_OBSIDIAN_HEROBRINE.get(), level);
    }

    public static void init() {
        //DungeonHooks.addDungeonMob(BuxinModEntities.PURPLE_OBSIDIAN_HEROBRINE.get(), 180);
    }
    public PurpleObsidianHerobrineEntity(EntityType<PurpleObsidianHerobrineEntity> entitytype, Level level) {
        super(entitytype, level);
        this.xpReward = 60;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(false);
        this.setPersistenceRequired();
        //this.(this.getDisplayName().getString());
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
       // this.goalSelector.addGoal(1,new ObsidianUseGoal(this,true,true,new ItemStack(BuxinModItems.HEI_YAO_SHI.get()),new ItemStack(BuxinModItems.HEI_YAO_SHI_2.get()),new ItemStack(BuxinModBlocks.PURPLE_OBSIDIAN.get()),new ItemStack(BuxinModBlocks.PURPLE_OBSIDIAN.get())));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, RedVillagerEntity.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, CunMinWeiBingEntity.class, false, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Grave2Entity.class, false, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Niubi13Entity.class, false, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Niubi14Entity.class, false, false));
        AddCommonAttackGoal.Herobrine(this);
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        IsBuxinEntityProcedure.execute(this);
        Method_114514.herobrine_born(this);
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(BuxinModBlocks.PURPLE_OBSIDIAN.get()));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModBlocks.PURPLE_OBSIDIAN.get()));
        spawnDarkObEntities();
        return retval;

    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        Random random = new Random();
        int v = random.nextInt(4);
        if (true) {
        if (v == 0) {
            if(Math.random() > 0.5){
                Method_114514.play_sound(this.level(), this.getX(), this.getY(), this.getZ(), "buxin:herobrine_attack");
            }
            Method_114514.play_sound(this.level(), this.getX(), this.getY(), this.getZ(), "buxin:obsidian_hit");

        } else {
            if (v == 1) {
                shootDarkObsAtTarget(2.0F, this);
                if(Math.random() > 0.75){
                    Method_114514.play_sound(this.level(), this.getX(), this.getY(), this.getZ(), "buxin:herobrine_attack");
                }
                BuxinMod.queueServerWork(40, this::spawnDarkObEntities);
            }
        }
        }
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getPersistentData().contains("Shooting")) {
            tag.putInt("Shooting", this.getPersistentData().getInt("Shooting"));
        }
        if (UpObsidianUUID != null) {
            tag.putUUID("DarkObUpUUID", UpObsidianUUID);
        }
        if (LeftObsidianUUID != null) {
            tag.putUUID("DarkObLeftUUID", LeftObsidianUUID);
        }
        if (RightObsidianUUID != null) {
            tag.putUUID("DarkObRightUUID", RightObsidianUUID);
        }
        if (RightObsidianUUID2 != null) {
            tag.putUUID("DarkObRightUUID2", RightObsidianUUID2);
        }
        if (LeftObsidianUUID2 != null) {
            tag.putUUID("DarkObLeftUUID2", LeftObsidianUUID2);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Shooting")) {
            this.getPersistentData().putInt("Shooting", tag.getInt("Shooting"));
        }
        if (tag.hasUUID("DarkObUpUUID")) {
            UpObsidianUUID = tag.getUUID("DarkObUpUUID");
        }
        if (tag.hasUUID("DarkObLeftUUID")) {
            LeftObsidianUUID = tag.getUUID("DarkObLeftUUID");
        }
        if (tag.hasUUID("DarkObRightUUID")) {
            RightObsidianUUID = tag.getUUID("DarkObRightUUID");
        }
        if (tag.hasUUID("DarkObRightUUID2")) {
            RightObsidianUUID2 = tag.getUUID("DarkObRightUUID2");
        }
        if (tag.hasUUID("DarkObLeftUUID2")) {
            LeftObsidianUUID2 = tag.getUUID("DarkObLeftUUID2");
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        Random random1 = new Random();
        int v = random1.nextInt(4);
        Mob entity = this;
        /*
        if(!entity.getMainHandItem().isEnchanted()){
            entity.getMainHandItem().enchant(Enchantments.SHARPNESS,1);
        }
        if(!entity.getOffhandItem().isEnchanted()){
            entity.getOffhandItem().enchant(Enchantments.SHARPNESS,1);
        }

         */
        if(entity.getTarget() != null && Math.random() > 0.917813114514 && !(this.getPersistentData().getBoolean("gp"))) {
            //entity.lookAt(EntityAnchorArgument.Anchor.EYES, entity.getTarget().getLookAngle());
            BuxinMod.queueServerWork((int) (Math.random() * 2), () -> {
                if(v == 1 && entity.getTarget() != null){
                    AnimationPlayer.playAnimation(entity, Animations.ZOMBIE_ATTACK1);
                    shootOne(this.RightObsidian, entity.getTarget().position(), 4F, "right", this);
                    if(Math.random() > 0.5){
                        shootOne(this.LeftObsidian2, entity.getTarget().position(), 4F, "right2", this);
                    }
                } else if(v == 2 && entity.getTarget() != null){
                    AnimationPlayer.playAnimation(entity, Animations.ZOMBIE_ATTACK2);
                    shootOne(this.LeftObsidian, entity.getTarget().position(), 4.5F, "left", this);
                    if(Math.random() > 0.5){
                        shootOne(this.RightObsidian2, entity.getTarget().position(), 4F, "right2", this);
                    }
                } else if(v == 3 && entity.getTarget() != null){
                    AnimationPlayer.playAnimation(entity, Animations.ZOMBIE_ATTACK1);
                    shootOne(this.UpObsidian, entity.getTarget().position(), 5F, "up", this);
                } else {
                    AnimationPlayer.playAnimation(entity, Animations.ZOMBIE_ATTACK3);
                    if(entity.getTarget() != null) {
                        shootDarkObsAtTarget(4.5F, this);
                    }
                }
                if(entity.getTarget() != null) {
                    BuxinMod.queueServerWork(25, this::spawnDarkObEntities);
                    BuxinMod.queueServerWork(6, () -> {
                        Method_114514.entity_use_command(entity.getTarget(), "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
                        Method_114514.play_sound(entity.getTarget(), "buxin:obsidian_hit");
                        Mob pearl = this;
                        if (!pearl.level.isClientSide()) {
                            ServerLevel serverLevel = (ServerLevel) pearl.level;
                            pearl.playSound(SoundEvents.ENDERMAN_TELEPORT);
                            for (int i = 0; i < 32; ++i) {
                                pearl.level.addParticle(ParticleTypes.PORTAL, pearl.getX(), pearl.getY() + pearl.random.nextDouble() * 2.0, pearl.getZ(), pearl.random.nextGaussian(), 0.0, pearl.random.nextGaussian());
                            }
                        }
                    });
                }
            });
        }
    }


    private Vec3 getUpBlockPos() {
        final double upY = 2.0;
        Vec3 eye = this.getEyePosition(1.0F);
        return eye.add(0.0, upY, 0.0);
    }

    private Vec3 getRightBlockPos() {
        final double lateral = 2.0;
        final double sideY   = 0.0;

        Vec3 eye = this.getEyePosition(1.0F);
        Vec3 look = this.getViewVector(1.0F);
        Vec3 horiz = new Vec3(look.x, 0.0, look.z);

        if (horiz.lengthSqr() < 1.0e-6) {
            float yaw = this.getYRot() * ((float)Math.PI / 180F);
            horiz = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        }
        Vec3 upAxis = new Vec3(0, 1, 0);
        Vec3 rightAxis = horiz.cross(upAxis).normalize();

        return eye.add(rightAxis.scale(lateral)).add(0.0, sideY, 0.0);
    }

    private Vec3 getRightBlockPos2() {
        final double distance = 2.0; // 改为distance更合适
        final double sideY   = 0.0;

        Vec3 eye = this.getEyePosition(1.0F);
        Vec3 look = this.getViewVector(1.0F);
        Vec3 horiz = new Vec3(look.x, 0.0, look.z);

        if (horiz.lengthSqr() < 1.0e-6) {
            float yaw = this.getYRot() * ((float)Math.PI / 180F);
            horiz = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        }

        // 标准化水平方向向量
        Vec3 forwardAxis = horiz.normalize();
        // 后方方向是前方的反向
        Vec3 backwardAxis = forwardAxis.scale(-1);

        // 计算基础位置（后面2格）
        Vec3 backPos = eye.add(backwardAxis.scale(distance)).add(0.0, sideY, 0.0);

        // 向上移动1.5格
        return backPos.add(0.0, 1.5, 0.0);
    }

    private Vec3 getLeftBlockPos() {
        final double lateral = 2.0;
        final double sideY   = 0.0;

        Vec3 eye = this.getEyePosition(1.0F);
        Vec3 look = this.getViewVector(1.0F);
        Vec3 horiz = new Vec3(look.x, 0.0, look.z);

        if (horiz.lengthSqr() < 1.0e-6) {
            float yaw = this.getYRot() * ((float)Math.PI / 180F);
            horiz = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        }
        Vec3 upAxis = new Vec3(0, 1, 0);
        Vec3 rightAxis = horiz.cross(upAxis).normalize();
        Vec3 leftAxis  = rightAxis.scale(-1);

        return eye.add(leftAxis.scale(lateral)).add(0.0, sideY, 0.0);
    }

    private Vec3 getLeftBlockPos2() {
        final double lateral = 2.0;
        final double sideY   = 0.0; // 如果需要向上移动1.5格，改为 1.5

        Vec3 eye = this.getEyePosition(1.0F);
        Vec3 look = this.getViewVector(1.0F);
        Vec3 horiz = new Vec3(look.x, 0.0, look.z);

        if (horiz.lengthSqr() < 1.0e-6) {
            float yaw = this.getYRot() * ((float)Math.PI / 180F);
            horiz = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        }

        // 标准化水平方向向量作为前方方向
        Vec3 forwardAxis = horiz.normalize();

        // 直接使用前方方向，而不是左侧方向
        return eye.add(forwardAxis.scale(lateral)).add(0.0, sideY, 0.0);
    }

    public void spawnDarkObEntities() {
        if (this.level() instanceof ServerLevel serverLevel && this.isAlive()) {
            BlockState block = BuxinModBlocks.PURPLE_OBSIDIAN.get().defaultBlockState();

            if (this.UpObsidian == null) {
                BuxinBlockProjectileEntity darkObbyUp = new BuxinBlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyUp.setNoGravity(true);
                darkObbyUp.setNotReadyForShoot(true);
                darkObbyUp.moveTo(getUpBlockPos());
                serverLevel.addFreshEntity(darkObbyUp);
                this.UpObsidianUUID = darkObbyUp.getUUID();
                this.UpObsidian = darkObbyUp;
                darkObbyUp.setCustomName(Component.literal("p"));
            }

            if (this.RightObsidian == null) {
                BuxinBlockProjectileEntity darkObbyRight = new BuxinBlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyRight.setNoGravity(true);
                darkObbyRight.setNotReadyForShoot(true);
                darkObbyRight.moveTo(getRightBlockPos());
                serverLevel.addFreshEntity(darkObbyRight);
                this.RightObsidianUUID = darkObbyRight.getUUID();
                this.RightObsidian = darkObbyRight;
                darkObbyRight.setCustomName(Component.literal("p"));
            }

            if (this.RightObsidian2 == null) {
                BuxinBlockProjectileEntity darkObbyRight2 = new BuxinBlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyRight2.setNoGravity(true);
                darkObbyRight2.setNotReadyForShoot(true);
                darkObbyRight2.moveTo(getRightBlockPos2());
                serverLevel.addFreshEntity(darkObbyRight2);
                this.RightObsidianUUID2 = darkObbyRight2.getUUID();
                this.RightObsidian2 = darkObbyRight2;
                darkObbyRight2.setCustomName(Component.literal("p"));
            }

            if (this.LeftObsidian == null) {
                BuxinBlockProjectileEntity darkObbyLeft = new BuxinBlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyLeft.setNoGravity(true);
                darkObbyLeft.setNotReadyForShoot(true);
                darkObbyLeft.moveTo(getLeftBlockPos());
                serverLevel.addFreshEntity(darkObbyLeft);
                this.LeftObsidianUUID = darkObbyLeft.getUUID();
                this.LeftObsidian = darkObbyLeft;
                darkObbyLeft.setCustomName(Component.literal("p"));
            }

            if (this.LeftObsidian2 == null) {
                BuxinBlockProjectileEntity darkObbyLeft2 = new BuxinBlockProjectileEntity(
                        this.level(),
                        this,
                        block
                );
                darkObbyLeft2.setNoGravity(true);
                darkObbyLeft2.setNotReadyForShoot(true);
                darkObbyLeft2.moveTo(getLeftBlockPos2());
                serverLevel.addFreshEntity(darkObbyLeft2);
                this.LeftObsidianUUID2 = darkObbyLeft2.getUUID();
                this.LeftObsidian2 = darkObbyLeft2;
                darkObbyLeft2.setCustomName(Component.literal("p"));
            }
        }
    }

    private static String currentEfAnimIdOrNull(LivingEntity self) {
        try {
            var patch = EpicFightCapabilities
                    .getEntityPatch(self, LivingEntityPatch.class);
            if (patch == null) return null;

            var player = patch.getAnimator().getPlayerFor((DynamicAnimation) null);
            if (player == null) return null;

            var anim = player.getAnimation();
            if (anim == null) return null;
            try {
                var m = anim.getClass().getMethod("getLocation");
                var rl = (ResourceLocation) m.invoke(anim);
                return rl != null ? rl.getPath().toLowerCase(java.util.Locale.ROOT) : null;
            } catch (Exception ignored) {
                return anim.toString().toLowerCase(java.util.Locale.ROOT);
            }
        } catch (Throwable t) {
            return null;
        }
    }

    private static boolean isLandAnimId(String id) {
        if (id == null) return false;
        return id.contains("biped/living/landing") || id.endsWith("/landing") || id.contains("landing");
    }

    private static boolean isAiming(String id) {
        if (id == null) return false;
        return id.contains("biped/combat/fist_dash") || id.endsWith("/fist_dash") || id.contains("fist_dash");
    }

    private void shootOne(BuxinBlockProjectileEntity ob, Vec3 to, double speed, String position, PurpleObsidianHerobrineEntity purpleObsidianHerobrineEntity) {
        if (ob == null || !ob.isAlive()) return;
        Vec3 dir = to.subtract(ob.position());
        if (dir.lengthSqr() < 1.0e-6) dir = this.getLookAngle();
        ob.setNoGravity(false);
        Vec3 vel = dir.normalize().scale(speed);
        ob.setDeltaMovement(vel);
        ob.setNotReadyForShoot(false);
        if (position.equals("up")) {
            purpleObsidianHerobrineEntity.UpObsidianUUID = null;
            purpleObsidianHerobrineEntity.UpObsidian = null;
        } else if (position.equals("left")) {
            purpleObsidianHerobrineEntity.LeftObsidianUUID = null;
            purpleObsidianHerobrineEntity.LeftObsidian = null;
        } else if (position.equals("right")) {
            purpleObsidianHerobrineEntity.RightObsidianUUID = null;
            purpleObsidianHerobrineEntity.RightObsidian = null;
        } else if(position.equals("right2")) {
            purpleObsidianHerobrineEntity.RightObsidianUUID2 = null;
            purpleObsidianHerobrineEntity.RightObsidian2 = null;
        } else if(position.equals("left2")) {
            purpleObsidianHerobrineEntity.LeftObsidian2 = null;
            purpleObsidianHerobrineEntity.LeftObsidianUUID2 = null;
        }
    }

    public void shootDarkObsAtTarget(double speed, PurpleObsidianHerobrineEntity purpleObsidianHerobrineEntity) {
        if (purpleObsidianHerobrineEntity.level().isClientSide) return;

        Vec3 to;
        LivingEntity target = purpleObsidianHerobrineEntity.getTarget();
        if (target != null && target.isAlive()) {
            to = target.getEyePosition(1.0F);
        } else {
            to = purpleObsidianHerobrineEntity.getEyePosition().add(purpleObsidianHerobrineEntity.getLookAngle().scale(16.0));
        }

        if (purpleObsidianHerobrineEntity.UpObsidian != null) {
            shootOne(purpleObsidianHerobrineEntity.UpObsidian, to, speed, "up", purpleObsidianHerobrineEntity);
        }
        if (purpleObsidianHerobrineEntity.LeftObsidian != null) {
            shootOne(purpleObsidianHerobrineEntity.LeftObsidian, to, speed, "left", purpleObsidianHerobrineEntity);
        }
        if (purpleObsidianHerobrineEntity.RightObsidian != null) {
            shootOne(purpleObsidianHerobrineEntity.RightObsidian, to, speed, "right", purpleObsidianHerobrineEntity);
        }
        if (purpleObsidianHerobrineEntity.RightObsidian2 != null) {
            shootOne(purpleObsidianHerobrineEntity.RightObsidian2, to, speed, "right2", purpleObsidianHerobrineEntity);
        }
        if (purpleObsidianHerobrineEntity.LeftObsidian2 != null) {
            shootOne(purpleObsidianHerobrineEntity.LeftObsidian2, to, speed, "left2", purpleObsidianHerobrineEntity);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            String animId = currentEfAnimIdOrNull(this);
            boolean isLandingNow = isLandAnimId(animId);

            if (isLandingNow && !wasLanding) {
                this.getPersistentData().putInt("Shooting", 15);
            }
            wasLanding = isLandingNow;

            int shooting = this.getPersistentData().getInt("Shooting");
            if (shooting > 0) {
                setDeltaMovement(Vec3.ZERO);
                this.getPersistentData().putInt("Shooting", shooting - 1);
            }

            if (UpObsidian == null && UpObsidianUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(UpObsidianUUID);
                if (entity instanceof BuxinBlockProjectileEntity blockProjectileEntity) {
                    this.UpObsidian = blockProjectileEntity;
                } else {
                    this.UpObsidianUUID = null;
                }
            }
            if (LeftObsidian == null && LeftObsidianUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(LeftObsidianUUID);
                if (entity instanceof BuxinBlockProjectileEntity blockProjectileEntity) {
                    this.LeftObsidian = blockProjectileEntity;
                } else {
                    this.LeftObsidianUUID = null;
                }
            }
            if (RightObsidian == null && RightObsidianUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(RightObsidianUUID);
                if (entity instanceof BuxinBlockProjectileEntity blockProjectileEntity) {
                    this.RightObsidian = blockProjectileEntity;
                } else {
                    this.RightObsidianUUID = null;
                }
            }
            if (RightObsidian2 == null && RightObsidianUUID2 != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(RightObsidianUUID2);
                if (entity instanceof BuxinBlockProjectileEntity blockProjectileEntity) {
                    this.RightObsidian2 = blockProjectileEntity;
                } else {
                    this.RightObsidianUUID2 = null;
                }
            }
            if (LeftObsidian2 == null && LeftObsidianUUID2 != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(LeftObsidianUUID2);
                if (entity instanceof BuxinBlockProjectileEntity blockProjectileEntity) {
                    this.LeftObsidian2 = blockProjectileEntity;
                } else {
                    this.LeftObsidianUUID2 = null;
                }
            }
            if (this.UpObsidian != null) {
                this.UpObsidian.moveTo(getUpBlockPos());
            }
            if (this.RightObsidian != null) {
                this.RightObsidian.moveTo(getRightBlockPos());
            }
            if (this.LeftObsidian != null) {
                this.LeftObsidian.moveTo(getLeftBlockPos());
            }
            if (this.RightObsidian2 != null) {
                this.RightObsidian2.moveTo(getRightBlockPos2());
            }
            if (this.LeftObsidian2 != null) {
                this.LeftObsidian2.moveTo(getLeftBlockPos2());
            }

            boolean aimingNow = isAiming(animId);
            if (aimingNow && !wasAiming) {
                spawnDarkObEntities();
                PurpleObsidianHerobrineEntity purpleObsidianHerobrineEntity = this;
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        if (purpleObsidianHerobrineEntity.isAlive()) {
                            shootDarkObsAtTarget(2.0F, purpleObsidianHerobrineEntity);
                        }
                    }
                };
            }
            wasAiming = aimingNow;
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.UpObsidian != null) {
            this.UpObsidian.discard();
        }
        if (this.LeftObsidian != null) {
            this.LeftObsidian.discard();
        }
        if (this.RightObsidian != null) {
            this.RightObsidian.discard();
        }
        if (this.RightObsidian2 != null) {
            this.RightObsidian2.discard();
        }
        if (this.LeftObsidian2 != null) {
            this.LeftObsidian2.discard();
        }
    }

    public static Builder createMobAttributes() {
        Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder = builder.add(Attributes.MAX_HEALTH, 185.0D);
        builder = builder.add(Attributes.ARMOR, 105.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 99.0D);
        return builder;
    }
}
