
package net.mcreator.buxin.entity;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.VFXTool;
import net.mcreator.buxin.procedures.ZhanshenzhirenemoDangShiTiGengXinKeShiProcedure;
import net.mcreator.buxin.procedures.ZhanshenzhirenemoShiTiChuShiSwhengChengShiProcedure;
import net.mcreator.buxin.skill_main.SingleSparkCanStartPrairieFireArt;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ZhanshenzhirenemoEntity extends Monster {
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

    public ZhanshenzhirenemoEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(BuxinModEntities.ZHANSHENZHIRENEMO.get(), world);
    }

    public ZhanshenzhirenemoEntity(EntityType<ZhanshenzhirenemoEntity> type, Level world) {
        super(type, world);
        maxUpStep = 0.6f;
        xpReward = 0;
        setNoAi(false);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.LANEMO_CHESTPLATE.get()));
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
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
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
        if (source.is(DamageTypes.LIGHTNING_BOLT))
            return false;
        if (source.is(DamageTypes.ON_FIRE))
            return false;
        if (source.is(DamageTypes.IN_FIRE))
            return false;
        if (source.is(DamageTypes.LAVA))
            return false;
        if (source.is(DamageTypes.FALLING_STALACTITE))
            return false;
        if(!(this.getPersistentData().getInt("e") == 20)){
            this.getPersistentData().putInt("e",this.getPersistentData().getInt("e") + 1);
        } else if(!this.level().isClientSide) {
            AnimationPlayer.playAnimation(this, BuxinAnimations.BlueDemonElectric);
            BuxinMod.queueServerWork(10,() -> {
                this.getPersistentData().putBoolean("YH",true);
                this.getPersistentData().putInt("se",1);
                this.setNoAi(true);
            });
            Method_114514.play_sound(this.level(),this.getX(),this.getY(),this.getZ(),"buxin:blue_demon_electronic");
            final Vec3 _center = new Vec3(this.getX(), this.getY(), this.getZ());
            List<Entity> _entfound = this.level().getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(9 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            Method_114514.send_message_to_all_over_the_world(this.level(),"<" + this.getDisplayName().getString() + "> 电场!");
            Method_114514.entity_use_command(this,"/effect give @s epicfight:stun_immunity 7 255");
            if (this.level() instanceof ServerLevel _level)
                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 1);
            BuxinMod.queueServerWork(30,() -> {
                SingleSparkCanStartPrairieFireArt.createExpandingExplosions(this,5,5);
                if (this.level() instanceof ServerLevel _level)
                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 3);
                for (Entity entityiterator : _entfound) {
                    if(entityiterator instanceof LivingEntity) {
                        if(entityiterator != this)
                            entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                    }
                }
                BuxinMod.queueServerWork(30,() -> {
                    if (this.level() instanceof ServerLevel _level)
                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 5);
                    for (Entity entityiterator : _entfound) {
                        if(entityiterator instanceof LivingEntity) {
                            if(entityiterator != this)
                                entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                        }
                    }
                    SingleSparkCanStartPrairieFireArt.createExpandingExplosions(this,2,2);
                    BuxinMod.queueServerWork(30,() -> {
                        if (this.level() instanceof ServerLevel _level)
                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 5);
                        for (Entity entityiterator : _entfound) {
                            if(entityiterator instanceof LivingEntity) {
                                if(entityiterator != this)
                                    entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                            }
                        }
                    });
                });
            });
            for (Entity entityiterator : _entfound) {
                if(entityiterator instanceof LivingEntity) {
                    if(entityiterator != this)
                        entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.1));
                }
            }
            Method_114514.entity_use_command(this,"/particle buxin:electronic ~ ~ ~ 4.5 3 4.5 1 1145 normal");
            VFXTool.addVFXParticle(this.position(),BuxinMod.MODID,"blue_demon_electronic",this.level());
            SingleSparkCanStartPrairieFireArt.createExpandingExplosions(this,6,6);
            this.getPersistentData().putInt("e",0);
            BuxinMod.queueServerWork(95,() -> {
                this.getPersistentData().putBoolean("YH",false);
                this.getPersistentData().putInt("se",0);
                this.setNoAi(false);
            });
        }
        return super.hurt(source, amount);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        this.getPersistentData().putInt("e",0);
        this.getPersistentData().putInt("se",0);
        this.getPersistentData().putBoolean("YH",false);
        ZhanshenzhirenemoShiTiChuShiSwhengChengShiProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
        AnimationPlayer.playAnimation(this, BuxinAnimations.BlueDemonElectric);
        BuxinMod.queueServerWork(60,() -> {
            AnimationPlayer.playAnimation(this, BuxinAnimations.BlueDemonElectric);
            BuxinMod.queueServerWork(10,() -> {
                this.getPersistentData().putBoolean("YH",true);
                this.getPersistentData().putInt("se",1);
                this.setNoAi(true);
            });
            Method_114514.play_sound(this.level(),this.getX(),this.getY(),this.getZ(),"buxin:blue_demon_electronic");
            final Vec3 _center = new Vec3(this.getX(), this.getY(), this.getZ());
            List<Entity> _entfound = this.level().getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(9 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            Method_114514.send_message_to_all_over_the_world(this.level(),"<" + this.getDisplayName().getString() + "> 电场!");
            Method_114514.entity_use_command(this,"/effect give @s epicfight:stun_immunity 7 255");
            for (Entity entityiterator : _entfound) {
                if(entityiterator instanceof LivingEntity) {
                    if(entityiterator != this)
                        entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.1));
                }
            }
            if (this.level() instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 1);
            BuxinMod.queueServerWork(30,() -> {
                SingleSparkCanStartPrairieFireArt.createExpandingExplosions(this,4,4);
                if (this.level() instanceof ServerLevel _level)
                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 3);
                for (Entity entityiterator : _entfound) {
                    if(entityiterator instanceof LivingEntity) {
                        if(entityiterator != this)
                            entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                    }
                }
                BuxinMod.queueServerWork(30,() -> {
                    if (this.level() instanceof ServerLevel _level)
                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 5);
                    for (Entity entityiterator : _entfound) {
                        if(entityiterator instanceof LivingEntity) {
                            if(entityiterator != this)
                                entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                        }
                    }
                    SingleSparkCanStartPrairieFireArt.createExpandingExplosions(this,4,4);
                    BuxinMod.queueServerWork(30,() -> {
                        SingleSparkCanStartPrairieFireArt.createExpandingExplosions(this,2,2);
                        if (this.level() instanceof ServerLevel _level)
                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), this.getX(), this.getY(), this.getZ(), 91, 3, 2, 3, 5);
                        for (Entity entityiterator : _entfound) {
                            if(entityiterator instanceof LivingEntity) {
                                if(entityiterator != this)
                                    entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.15));
                            }
                        }
                    });
                });
            });
            for (Entity entityiterator : _entfound) {
                if(entityiterator instanceof LivingEntity) {
                    if(entityiterator != this)
                        entityiterator.hurt(this.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.1));
                }
            }
            Method_114514.entity_use_command(this,"/particle buxin:electronic ~ ~ ~ 4.5 3 4.5 1 1145 normal");
            VFXTool.addVFXParticle(this.position(),BuxinMod.MODID,"blue_demon_electronic",this.level());
            SingleSparkCanStartPrairieFireArt.createExpandingExplosions(this,6,6);
            this.getPersistentData().putInt("e",0);
            BuxinMod.queueServerWork(95,() -> {
                this.getPersistentData().putBoolean("YH",false);
                this.getPersistentData().putInt("se",0);
                this.setNoAi(false);
            });
        });
        return retval;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if(this.getPersistentData().getInt("se") == 1 && AnimationPlayer.entity_getAnimation(this) != BuxinAnimations.BlueDemonElectricShort){
            AnimationPlayer.playAnimation(this,BuxinAnimations.BlueDemonElectricShort);
        }
        ZhanshenzhirenemoDangShiTiGengXinKeShiProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
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
        builder = builder.add(Attributes.MAX_HEALTH, 412);
        builder = builder.add(Attributes.ARMOR, 45);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
        builder = builder.add(Attributes.FOLLOW_RANGE, 99);
        return builder;
    }
}
