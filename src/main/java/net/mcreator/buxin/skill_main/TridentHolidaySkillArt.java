package net.mcreator.buxin.skill_main;

import io.netty.buffer.Unpooled;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TridentHolidaySkillArt extends WeaponInnateSkill {
    private final StaticAnimation[] animations;
    private static final UUID EVENT_UUID = UUID.fromString("4fbc808b-5aaa-4dfb-ad3f-12462f1b290e");

    public TridentHolidaySkillArt(Builder<? extends Skill> builder) {
        super(builder);
        this.animations = new StaticAnimation[]{BuxinAnimations.TRIDENT_HOLIDAY, BuxinAnimations.TRIDENT_HOLIDAY, BuxinAnimations.TRIDENT_HOLIDAY};
    }
    public void onInitiate(SkillContainer container) {
        if (!container.getExecuter().isLogicalClient()) {
            this.setConsumption(container, 6.0F);
            this.setConsumptionSynchronize((ServerPlayerPatch) container.getExecuter(), 6.0F);
        }
        container.setResource(6.0F);
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_POST, EVENT_UUID);
    }

    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (!container.getExecuter().isLogicalClient()) {
            ServerPlayerPatch playerpatch = (ServerPlayerPatch)container.getExecuter();
            SkillContainer WeaponInnateContainer = playerpatch.getSkill(SkillSlots.WEAPON_INNATE);
            if (((ServerPlayer)playerpatch.getOriginal()).getMainHandItem().getItem() == Items.TRIDENT) {
                WeaponInnateContainer.getSkill().setConsumptionSynchronize(playerpatch, WeaponInnateContainer.getResource() + 0.25F);
            }
        }
    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public FriendlyByteBuf gatherArguments(LocalPlayerPatch executer, ControllEngine controllEngine) {
        Input input = ((LocalPlayer) executer.getOriginal()).input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus((LivingEntity) executer.getOriginal()), 0.0F, 1.0F);
        input.tick(false, pulse);
        int forward = input.up ? 1 : 0;
        int backward = input.down ? -1 : 0;
        int left = input.left ? 1 : 0;
        int right = input.right ? -1 : 0;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(forward);
        buf.writeInt(backward);
        buf.writeInt(left);
        buf.writeInt(right);
        return buf;
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        this.setStackSynchronize(executer, 1);
        PlayerPatch<?> PlayerPatch = executer;
        if (PlayerPatch instanceof ServerPlayerPatch ServerPlayerPatch) {
            Player entity = (Player) ServerPlayerPatch.getOriginal();
            Level level = entity.level();
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            int i = args.readInt();
            Vec3 originalVelocity = entity.getDeltaMovement();
            float speed = entity.getSpeed();
            /*
            executer.playAnimationSynchronized(this.animations[i], 0.0F);

             */
            AnimationPlayer.playAnimation(entity,BuxinAnimations.TRIDENT_HOLIDAY);
            super.executeOnServer(executer, args);
            if (entity.getMainHandItem().getItem() == Items.TRIDENT) {
                if (entity.getOffhandItem().getItem() == Items.TRIDENT) {
                    if (!level.isClientSide() && level.getServer() != null)
                        level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + ">" + " 三叉戟狂欢节..."), false);
                    entity.getPersistentData().putBoolean("bk", true);
                    entity.setSpeed(0);
                    entity.teleportTo(x, y, z);
                    if (entity instanceof ServerPlayer _serverPlayer)
                        _serverPlayer.connection.teleport(x, y, z, entity.getYRot(), entity.getXRot());
                    Entity _ent = entity;
                    if (!_ent.level.isClientSide() && _ent.getServer() != null) {
                        _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level instanceof ServerLevel ? (ServerLevel) _ent.level : null, 6,
                                _ent.getName().getString(), _ent.getDisplayName(), _ent.level.getServer(), _ent), "impactful @s shake 400 5 5");
                        BuxinMod.queueServerWork(10,() -> {
                            _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level instanceof ServerLevel ? (ServerLevel) _ent.level : null, 6,
                                    _ent.getName().getString(), _ent.getDisplayName(), _ent.level.getServer(), _ent), "/impactful @s shake 200 5 5");
                        });
                    }
                    if (!level.isClientSide()) {
                        level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blue_demon_trident_holiday")), SoundSource.NEUTRAL, 2, 1);
                    } else {
                        level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blue_demon_trident_holiday")), SoundSource.NEUTRAL, 2, 1, false);
                    }
                    if (level instanceof ServerLevel _level)
                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 60, 5, 5, 5, 1);
                    BuxinMod.queueServerWork(7, () -> {
                        if (level instanceof ServerLevel _level)
                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 60, 5, 5, 5, 1);
                        BuxinMod.queueServerWork(7, () -> {
                            if (level instanceof ServerLevel _level)
                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 60, 5, 5, 5, 1);
                            if (!_ent.level.isClientSide() && _ent.getServer() != null) {
                                _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level instanceof ServerLevel ? (ServerLevel) _ent.level : null, 6,
                                        _ent.getName().getString(), _ent.getDisplayName(), _ent.level.getServer(), _ent), "impactful @s shake 400 5 5");
                            }
                            BuxinMod.queueServerWork(7, () -> {
                                if (!level.isClientSide()) {
                                    level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zjdl")), SoundSource.NEUTRAL, 2, 1);
                                } else {
                                    level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zjdl")), SoundSource.NEUTRAL, 2, 1, false);
                                }
                                if (level instanceof ServerLevel _level)
                                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 150, 5, 5, 5, 1);
                            });
                        });
                    });
                    BuxinMod.queueServerWork(35, () -> {
                        if (level instanceof ServerLevel _level)
                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
                        BuxinMod.queueServerWork(2, () -> {
                            if (level instanceof ServerLevel _level)
                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
                            BuxinMod.queueServerWork(2, () -> {
                                if (level instanceof ServerLevel _level)
                                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
                            });
                        });
                        entity.setDeltaMovement(Vec3.ZERO);
                        BuxinMod.queueServerWork(45, () -> {
                            BuxinMod.queueServerWork(15, () -> {
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 8), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                BuxinMod.queueServerWork(27,() -> {
                                    if(VFXParticleConfig.VFXParticleConfig.get())
                                        VFXTool.addVFXParticle(entity.position(),BuxinMod.MODID,"lightning",entity.level());
                                    BuxinMod.queueServerWork(17,() -> {
                                        if(VFXParticleConfig.VFXParticleConfig.get())
                                         VFXTool.addVFXParticle(entity.position(),BuxinMod.MODID,"lightning",entity.level());
                                    });
                                });
                                if (!level.isClientSide())
                                    level.explode(null, (x + 8), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z + 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z + 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 8), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 8), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z - 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z - 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 11), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 11), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z + 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z + 11), 4, Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 11), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 11), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z - 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z - 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 8), (y + 2), (z + 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 8), y, (z + 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 8), (y + 2), (z + 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 8), y, (z + 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 8), (y + 2), (z - 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 8), y, (z - 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 8), (y + 2), (z - 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 8), y, (z - 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 11), (y + 2), (z + 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 11), y, (z + 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 11), (y + 2), (z + 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 11), y, (z + 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 11), (y + 2), (z - 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 11), y, (z - 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 11), (y + 2), (z - 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 11), y, (z - 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 8), (y + 2), (z + 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 8), y, (z + 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 8), (y + 2), (z - 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 8), y, (z - 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 8), (y + 2), (z - 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 8), y, (z - 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 8), (y + 2), (z - 8), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 8), y, (z - 8), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 11), (y + 2), (z - 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 11), y, (z - 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 11), (y + 2), (z + 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 11), y, (z + 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 11), (y + 2), (z - 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 11), y, (z - 11), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 11), (y + 2), (z + 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 11), y, (z + 11), 4,  Level.ExplosionInteraction.BLOCK);
                                BuxinMod.queueServerWork(20, () -> {
                                    BuxinMod.queueServerWork(20, () -> {
                                        {
                                            final Vec3 _center = new Vec3(x, y, z);
                                            List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                                    .collect(Collectors.toList());
                                            for (Entity entityiterator : _entfound) {
                                                if ((entity == entityiterator) == false) {
                                                    entityiterator.hurt(entity.level.damageSources().generic(), entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
                                                    entityiterator.hurt(entity.level.damageSources().generic(), entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
                                                    entityiterator.hurt(entity.level.damageSources().generic(), entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
                                                    if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                                        _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 100, 1, false, false));
                                                    Method_114514.entity_use_command(entityiterator, "/vfx buxin dragon_beam_hit");
                                                }
                                                if (level instanceof ServerLevel _level) {
                                                    LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(level);
                                                    entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) entityiterator.getX(), (int) entityiterator.getY(), (int) entityiterator.getZ())));
                                                    entityToSpawn.setVisualOnly(false);
                                                    level.addFreshEntity(entityToSpawn);
                                                }
                                                if (level instanceof ServerLevel _level) {
                                                    Entity entityToSpawn = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                                                    entityToSpawn.moveTo((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 0, 0);
                                                    entityToSpawn.setYBodyRot(0);
                                                    entityToSpawn.setYHeadRot(0);
                                                    entityToSpawn.setDeltaMovement(0, 0, 0);
                                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                                    level.addFreshEntity(entityToSpawn);
                                                }
                                                if (level instanceof ServerLevel _level)
                                                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 5, (entityiterator.getX() + 1),
                                                            (entityiterator.getY() + 2), (entityiterator.getZ() + 1), 1);
                                                BuxinMod.queueServerWork(5, () -> {
                                                    if (level instanceof ServerLevel _level) {
                                                        LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(level);
                                                        entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) entityiterator.getX(), (int) entityiterator.getY(), (int) entityiterator.getZ())));
                                                        entityToSpawn.setVisualOnly(false);
                                                        level.addFreshEntity(entityToSpawn);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    {
                                        final Vec3 _center = new Vec3(x, y, z);
                                        List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                                .collect(Collectors.toList());
                                        for (Entity entityiterator : _entfound) {
                                            if (level instanceof ServerLevel _level) {
                                                LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(level);
                                                entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) entityiterator.getX(), (int) entityiterator.getY(), (int) entityiterator.getZ())));
                                                entityToSpawn.setVisualOnly(false);
                                                level.addFreshEntity(entityToSpawn);
                                            }
                                            if (level instanceof ServerLevel _level) {
                                                Entity entityToSpawn = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                                                entityToSpawn.moveTo((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 0, 0);
                                                entityToSpawn.setYBodyRot(0);
                                                entityToSpawn.setYHeadRot(0);
                                                entityToSpawn.setDeltaMovement(0, 0, 0);
                                                if (entityToSpawn instanceof Mob _mobToSpawn)
                                                    _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                                level.addFreshEntity(entityToSpawn);
                                            }
                                            if (level instanceof ServerLevel _level)
                                                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 5, (entityiterator.getX() + 1),
                                                        (entityiterator.getY() + 2), (entityiterator.getZ() + 1), 1);
                                            BuxinMod.queueServerWork(5, () -> {
                                                if (level instanceof ServerLevel _level) {
                                                    LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(level);
                                                    entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) entityiterator.getX(), (int) entityiterator.getY(), (int) entityiterator.getZ())));
                                                    entityToSpawn.setVisualOnly(false);
                                                    level.addFreshEntity(entityToSpawn);
                                                }
                                            });
                                            BuxinMod.queueServerWork(100, () -> {
                                                entityiterator.hurt(entity.level.damageSources().generic(), entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
                                                BuxinMod.queueServerWork(2, () -> {
                                                    entityiterator.hurt(entity.level.damageSources().generic(), entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
                                                    BuxinMod.queueServerWork(2, () -> {
                                                        entityiterator.hurt(entity.level.damageSources().generic(), entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
                                                    });
                                                });
                                            });
                                        }
                                    }
                                });
                            });
                            BuxinMod.queueServerWork(30, () -> {
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 14), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 14), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z + 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z + 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 14), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 14), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z - 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z - 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 17), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 17), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z + 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z + 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 17), (y + 2), z, level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 17), y, z, 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo(x, (y + 2), (z - 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, x, y, (z - 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 14), (y + 2), (z + 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 14), y, (z + 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 14), (y + 2), (z + 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 14), y, (z + 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 14), (y + 2), (z - 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 14), y, (z - 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 14), (y + 2), (z - 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 14), y, (z - 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 17), (y + 2), (z + 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 17), y, (z + 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 17), (y + 2), (z + 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 17), y, (z + 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 17), (y + 2), (z - 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 17), y, (z - 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 17), (y + 2), (z - 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 17), y, (z - 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 14), (y + 2), (z + 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 14), y, (z + 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 14), (y + 2), (z - 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 14), y, (z - 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 14), (y + 2), (z - 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 14), y, (z - 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 14), (y + 2), (z - 14), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 14), y, (z - 14), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 17), (y + 2), (z - 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 17), y, (z - 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 11), (y + 2), (z + 11), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 17), y, (z + 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x + 17), (y + 2), (z - 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x + 17), y, (z - 17), 4,  Level.ExplosionInteraction.BLOCK);
                                if (level instanceof ServerLevel _level) {
                                    Entity entityToSpawn = new ThrownTrident(EntityType.TRIDENT, level);
                                    entityToSpawn.moveTo((x - 17), (y + 2), (z + 17), level.getRandom().nextFloat() * 360F, 0);
                                    if (entityToSpawn instanceof Mob _mobToSpawn)
                                        _mobToSpawn.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                                    level.addFreshEntity(entityToSpawn);
                                }
                                if (!level.isClientSide())
                                    level.explode(null, (x - 17), y, (z + 17), 4,  Level.ExplosionInteraction.BLOCK);
                            });
                            if (!level.isClientSide()) {
                                level.explode(null, x, y, z, 16,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x + 2), y, z, 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x - 2), y, z, 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, x, y, (z + 2), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, x, y, (z - 2), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x + 2), y, (z + 2), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x - 2), y, (z - 2), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x + 2), y, (z - 2), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x - 2), y, (z + 2), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x + 6), y, z, 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x - 6), y, z, 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, x, y, (z + 6), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, x, y, (z - 6), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x + 6), y, (z + 6), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x - 6), y, (z - 6), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x + 6), y, (z - 6), 8,  Level.ExplosionInteraction.BLOCK);
                                level.explode(null, (x - 6), y, (z + 6), 8,  Level.ExplosionInteraction.BLOCK);
                                entity.setDeltaMovement(originalVelocity);
                                entity.setSpeed(speed);
                                BuxinMod.queueServerWork(170, () -> {
                                    entity.getPersistentData().putBoolean("bk", false);
                                });
                            }

                        });
                    });
                } else {
                    if (!level.isClientSide() && level.getServer() != null)
                        level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + ">" + "欸我操怎么放不出来技能"), false);
                }
            } else {
                if (!level.isClientSide() && level.getServer() != null)
                    level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + ">" + "欸我操怎么放不出来技能"), false);
            }
        }
    }
}