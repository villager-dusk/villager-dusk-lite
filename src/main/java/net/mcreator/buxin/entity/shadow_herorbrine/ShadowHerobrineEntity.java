package net.mcreator.buxin.entity.shadow_herorbrine;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.DelayedTask;
import net.mcreator.buxin.ai.ObsidianUseGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.entity.*;
//import net.mcreator.buxin.entity.player_mob.HerobrinePlayerMobEntity;
import net.mcreator.buxin.entity.projectileblock.BuxinBlockProjectileEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.IsBuxinEntityProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
//import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;


public class ShadowHerobrineEntity extends Monster {
    private boolean wasLanding = false;
    private boolean wasAiming = false;

    public BuxinBlockProjectileEntity UpObsidian;
    public UUID UpObsidianUUID;
    public BuxinBlockProjectileEntity LeftObsidian;
    public UUID LeftObsidianUUID;

    public BuxinBlockProjectileEntity RightObsidian;
    public UUID RightObsidianUUID;

    public ShadowHerobrineEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) BuxinModEntities.SHADOW_HEROBRINE.get(), level);
    }
    public static void init() {
        //DungeonHooks.addDungeonMob(BuxinModEntities.SHADOW_HEROBRINE.get(), 180);
    }
    public ShadowHerobrineEntity(EntityType<ShadowHerobrineEntity> entitytype, Level level) {
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
        this.goalSelector.addGoal(1,new ObsidianUseGoal(this,true,true,new ItemStack(BuxinModItems.OBS_5.get()),new ItemStack(BuxinModItems.OBS_5.get()),new ItemStack(BuxinModItems.OBS.get()),new ItemStack(Items.AIR)));
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
        Method_114514.send_message_to_all_over_the_world(this.level(),this.getDisplayName().getString() + "§2已降临在 " + this.getX() + " " + this.getY() + " " + this.getZ());
        var pos = new Vec3(this.getX(),this.getY() + 1,this.getZ());
        //VFXTool.addVFXParticle(pos,BuxinMod.MODID,"herobrine_getpower",this.level());
        BuxinMod.queueServerWork(120, this::spawnDarkObEntities);
        /*
        ServerLevel server = (ServerLevel)this.level();
        Vec3 from = getArmPosition(this, new Vec3f(0,0,0), Armatures.BIPED.toolR, 1.2F, 0.0F);
        Vec3 to = this.getEyePosition();

        AABB box = this.getBoundingBox().inflate(0.05);
        Vec3 end = box.clip(from, to).orElse(to);

        Vec3 d = end.subtract(from);
        double len = d.length();

        Vec3 dir = d.scale(1.0 / len);

        Vec3 any = Math.abs(dir.y) < 0.99 ? new Vec3(0,1,0) : new Vec3(1,0,0);
        Vec3 u = dir.cross(any).normalize();
        Vec3 v = dir.cross(u).normalize();

        int steps = Mth.clamp((int)(len * 6.0), 6, 72);
        double step = len / steps;

        final int stride = 4;
        int phase = (this.tickCount >> 1) % stride;
        RandomSource r = this.getRandom();

        for (int i = phase; i <= steps; i += stride) {
            if (r.nextFloat() < 0.70f) continue;

            double t = (i * step) / len;
            double R = 0.05 + 0.20 * t;
            double ang = r.nextDouble() * (Math.PI * 2);
            double rad = R * Math.sqrt(r.nextDouble());
            Vec3 off = u.scale(Math.cos(ang) * rad).add(v.scale(Math.sin(ang) * rad));

            Vec3 p = from.add(dir.scale(i * step)).add(off);

            double vx = dir.x * 0.02 + off.x * 0.10;
            double vy = dir.y * 0.02 + off.y * 0.10;
            double vz = dir.z * 0.02 + off.z * 0.10;

            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, p.x, p.y, p.z, 1, vx, vy, vz, 0.0);
        }

         */

        Method_114514.entity_use_command(this,"/indestructible @s play \"buxin:biped/buxin/herobrine_get_power\" 0 1");
        IsBuxinEntityProcedure.execute(this);

        /*
        HerobrinePlayerMobEntity corpse = new HerobrinePlayerMobEntity(BuxinModEntities.Infected_PlayerMOB.get(), this.level());
        corpse.moveTo(this.getX() + 5, this.getY(), this.getZ(), this.getYRot(), this.getXRot());
        corpse.lookAt(EntityAnchorArgument.Anchor.EYES,this.position());
        corpse.finalizeSpawn(world, this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        this.level().addFreshEntity(corpse);

        HerobrinePlayerMobEntity corpse2 = new HerobrinePlayerMobEntity(BuxinModEntities.Infected_PlayerMOB.get(), this.level());
        corpse2.moveTo(this.getX(), this.getY(), this.getZ() + 5, this.getYRot(), this.getXRot());
        corpse2.lookAt(EntityAnchorArgument.Anchor.EYES,this.position());
        corpse2.finalizeSpawn(world, this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        this.level().addFreshEntity(corpse2);

        HerobrinePlayerMobEntity corpse3 = new HerobrinePlayerMobEntity(BuxinModEntities.Infected_PlayerMOB.get(), this.level());
        corpse3.moveTo(this.getX(), this.getY(), this.getZ() - 5, this.getYRot(), this.getXRot());
        corpse3.lookAt(EntityAnchorArgument.Anchor.EYES,this.position());
        corpse3.finalizeSpawn(world, this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        this.level().addFreshEntity(corpse3);

        HerobrinePlayerMobEntity corpse4 = new HerobrinePlayerMobEntity(BuxinModEntities.Infected_PlayerMOB.get(), this.level());
        corpse4.moveTo(this.getX() - 5, this.getY(), this.getZ(), this.getYRot(), this.getXRot());
        corpse4.lookAt(EntityAnchorArgument.Anchor.EYES,this.position());
        corpse4.finalizeSpawn(world, this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        this.level().addFreshEntity(corpse4);


        AnimationPlayer.playAnimation(this, BuxinAnimations.HB_getpower);
        AnimationPlayer.playAnimation(corpse, BuxinAnimations.HB_sendpower);
        AnimationPlayer.playAnimation(corpse2, BuxinAnimations.HB_sendpower);
        AnimationPlayer.playAnimation(corpse3, BuxinAnimations.HB_sendpower);
        AnimationPlayer.playAnimation(corpse4, BuxinAnimations.HB_sendpower);
        corpse.setPossessedByEntity(this);
        corpse2.setPossessedByEntity(this);
        corpse3.setPossessedByEntity(this);
        corpse4.setPossessedByEntity(this);


        this.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 3, false, false));
        corpse.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 3, false, false));
        corpse2.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 3, false, false));
        corpse3.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 3, false, false));
        corpse4.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 3, false, false));

        Method_114514.play_sound(this.level(),this.getX(),this.getY(),this.getZ(),"buxin:understood");

        this.getPersistentData().putBoolean("gp",true);
        corpse.getPersistentData().putBoolean("gp",true);
        corpse2.getPersistentData().putBoolean("gp",true);
        corpse3.getPersistentData().putBoolean("gp",true);
        corpse4.getPersistentData().putBoolean("gp",true);

        Method_114514.send_message_to_all_over_the_world(this.level(),"§4<Herobrine> Understood ? ");
        BuxinMod.queueServerWork(20,() -> {
            Method_114514.send_message_to_all_over_the_world(this.level(),"§0<" + corpse.getDisplayName().getString() + "> UnderStood！！！");
            Method_114514.send_message_to_all_over_the_world(this.level(),"§0<" + corpse2.getDisplayName().getString() + "> UnderStood！！！");
            Method_114514.send_message_to_all_over_the_world(this.level(),"§0<" + corpse3.getDisplayName().getString() + "> UnderStood！！！");
            Method_114514.send_message_to_all_over_the_world(this.level(),"§0<" + corpse4.getDisplayName().getString() + "> UnderStood！！！");
        });
        this.setNoAi(true);
        corpse.setNoAi(true);
        corpse2.setNoAi(true);
        corpse3.setNoAi(true);
        corpse4.setNoAi(true);

        Method_114514.herobrine_born(corpse);
        Method_114514.herobrine_born(corpse2);
        Method_114514.herobrine_born(corpse3);
        Method_114514.herobrine_born(corpse4);
*/
        this.moveTo(this.getX(),this.getY() + 4.5,this.getZ());

/*
        if(this.getPersistentData().getBoolean("gp")){
            AnimationPlayer.playAnimation(this, BuxinAnimations.HB_sendpower);
            ServerLevel server = (ServerLevel) this.level();
            Vec3 from = getArmPosition(this, new Vec3f(0,0,0), Armatures.BIPED.toolR, 1.2F, 0.0F);
            Vec3 to = corpse.getEyePosition();

            AABB box = corpse.getBoundingBox().inflate(0.05);
            Vec3 end = box.clip(from, to).orElse(to);

            Vec3 d = end.subtract(from);
            double len = d.length();

            Vec3 dir = d.scale(1.0 / len);

            Vec3 any = Math.abs(dir.y) < 0.99 ? new Vec3(0,1,0) : new Vec3(1,0,0);
            Vec3 u = dir.cross(any).normalize();
            Vec3 v = dir.cross(u).normalize();

            int steps = Mth.clamp((int)(len * 6.0), 6, 72);
            double step = len / steps;

            final int stride = 4;
            int phase = (this.tickCount >> 1) % stride;
            RandomSource r = this.getRandom();

            for (int i = phase; i <= steps; i += stride) {
                if (r.nextFloat() < 0.70f) continue;

                double t = (i * step) / len;
                double R = 0.05 + 0.20 * t;
                double ang = r.nextDouble() * (Math.PI * 2);
                double rad = R * Math.sqrt(r.nextDouble());
                Vec3 off = u.scale(Math.cos(ang) * rad).add(v.scale(Math.sin(ang) * rad));

                Vec3 p = from.add(dir.scale(i * step)).add(off);

                double vx = dir.x * 0.02 + off.x * 0.10;
                double vy = dir.y * 0.02 + off.y * 0.10;
                double vz = dir.z * 0.02 + off.z * 0.10;

                server.sendParticles(ParticleTypes.WHITE_ASH, p.x, p.y, p.z, 1, vx, vy, vz, 0.0);
            }
        }

 */

        BuxinMod.queueServerWork(100,() -> {
            this.getPersistentData().putBoolean("gp",false);
            /*
            corpse.getPersistentData().putBoolean("gp",false);
            corpse2.getPersistentData().putBoolean("gp",false);
            corpse3.getPersistentData().putBoolean("gp",false);
            corpse4.getPersistentData().putBoolean("gp",false);
            this.setNoAi(false);

            corpse.discard();
            corpse2.discard();
            corpse3.discard();
            corpse4.discard();{
                GameProfile profile = new GameProfile(corpse.getUUID(), corpse.getDisplayName().getString());
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                ServerLevel overworld = server.getLevel(ServerLevel.OVERWORLD);
                ServerPlayer fakePlayer = FakePlayerFactory.get(overworld, profile);
                ClientboundPlayerInfoPacket removePacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, new ServerPlayer[]{fakePlayer});
                server.getPlayerList().broadcastAll(removePacket);
            }
            {
                GameProfile profile = new GameProfile(corpse2.getUUID(), corpse2.getDisplayName().getString());
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                ServerLevel overworld = server.getLevel(ServerLevel.OVERWORLD);
                ServerPlayer fakePlayer = FakePlayerFactory.get(overworld, profile);
                ClientboundPlayerInfoPacket removePacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, new ServerPlayer[]{fakePlayer});
                server.getPlayerList().broadcastAll(removePacket);
            }
            {
                GameProfile profile = new GameProfile(corpse3.getUUID(), corpse3.getDisplayName().getString());
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                ServerLevel overworld = server.getLevel(ServerLevel.OVERWORLD);
                ServerPlayer fakePlayer = FakePlayerFactory.get(overworld, profile);
                ClientboundPlayerInfoPacket removePacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, new ServerPlayer[]{fakePlayer});
                server.getPlayerList().broadcastAll(removePacket);
            }
            {
                GameProfile profile = new GameProfile(corpse4.getUUID(), corpse4.getDisplayName().getString());
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                ServerLevel overworld = server.getLevel(ServerLevel.OVERWORLD);
                ServerPlayer fakePlayer = FakePlayerFactory.get(overworld, profile);
                ClientboundPlayerInfoPacket removePacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, new ServerPlayer[]{fakePlayer});
                server.getPlayerList().broadcastAll(removePacket);
            }
            if (!world.isClientSide() && world.getServer() != null && FakePlayerJoinMessageConfig.FakePlayerJoinMessage.get())
                world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("tooltip.buxin.chat.left",corpse.getDisplayName().getString()).withStyle(ChatFormatting.YELLOW), false);
            if (!world.isClientSide() && world.getServer() != null && FakePlayerJoinMessageConfig.FakePlayerJoinMessage.get())
                world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("tooltip.buxin.chat.left",corpse2.getDisplayName().getString()).withStyle(ChatFormatting.YELLOW), false);
            if (!world.isClientSide() && world.getServer() != null && FakePlayerJoinMessageConfig.FakePlayerJoinMessage.get())
                world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("tooltip.buxin.chat.left",corpse3.getDisplayName().getString()).withStyle(ChatFormatting.YELLOW), false);
            if (!world.isClientSide() && world.getServer() != null && FakePlayerJoinMessageConfig.FakePlayerJoinMessage.get())
                world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("tooltip.buxin.chat.left",corpse4.getDisplayName().getString()).withStyle(ChatFormatting.YELLOW), false);
            */
        });
        IsBuxinEntityProcedure.execute(this);
        //this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.WHAT_2.get()));
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
        if (damagesource.getMsgId().equals("inWall")) {
            return false;
        }
        Entity entity = this;
        double d0 = this.getX(), d1 = this.getY(), d2 = this.getZ();
        Level level = this.level();
        Level level1;
        LivingEntity livingentity;
        ItemStack itemstack;
        Player player;
        Random random = new Random();
        int v = random.nextInt(4);
        if (true) {
        if (v == 0) {
            for (int i = 0; i < 2; i++) {
                shootChain(this, BuxinModBlocks.AMY.get().defaultBlockState(), 1F, 25);
            }
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
        if (Math.random() <= 0.2D) {
            if (level instanceof Level) {
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "metal1")), SoundSource.BLOCKS, 0.2F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "metal1")), SoundSource.BLOCKS, 0.2F, 1.0F, false);
                }
            }
        }

        if (Math.random() <= 0.25D) {
            if (level instanceof Level) {
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "metal1")), SoundSource.BLOCKS, 0.2F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "metal1")), SoundSource.BLOCKS, 0.2F, 1.0F, false);
                }
            }

            new DelayedTask(1) {
                @Override
                public void run() {
                    Entity entity2 = entity;

                    if (true) {
                        if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                            try {
                                entity2.getServer().getCommands().getDispatcher().execute(
                                        "execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 buxin:amy",
                                        entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                            }
                        }
                    }
                }
            };
        }

        if (Math.random() <= 0.42D) {
            if (true) {
                if (level instanceof Level) {
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                    }
                }
            }
            if (true) {
                if (level instanceof Level) {
                    if (!level.isClientSide()) {
                        level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
            }

            level1 = entity.level();
            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player) livingentity;
                    player.getInventory().setChanged();
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player) livingentity;
                    player.getInventory().setChanged();
                }
            }
            new DelayedTask(5) {
                public void run() {
                    Entity entity1 = entity;
                    Level level2 = entity1.level();


                    LivingEntity livingentity1;
                    ItemStack itemstack1;
                    Player player1;

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        itemstack1 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                        itemstack1.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                        if (livingentity1 instanceof Player) {
                            player1 = (Player) livingentity1;
                            player1.getInventory().setChanged();
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        itemstack1 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                        itemstack1.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                        if (livingentity1 instanceof Player) {
                            player1 = (Player) livingentity1;
                            player1.getInventory().setChanged();
                        }
                    }


                    if (level instanceof Level) {

                        if (!((Level) level2).isClientSide()) {
                            ((Level) level2).playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    }
                    new DelayedTask(4) {
                        public void run() {
                            LivingEntity livingentity2;
                            ItemStack itemstack2;
                            Player player2;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity) entity;
                                itemstack2 = new ItemStack((ItemLike) BuxinModItems.OBS.get());
                                itemstack2.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                if (livingentity2 instanceof Player) {
                                    player2 = (Player) livingentity2;
                                    player2.getInventory().setChanged();
                                }
                            }

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity) entity;
                                itemstack2 = new ItemStack(Blocks.AIR);
                                itemstack2.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                if (livingentity2 instanceof Player) {
                                    player2 = (Player) livingentity2;
                                    player2.getInventory().setChanged();
                                }
                            }
                        }
                    };
                }
            };
        } else if (Math.random() <= 0.4D) {
            if (level instanceof Level) {
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }

            if (level instanceof Level) {
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.stone.place")), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
            }

            level1 = entity.level();

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = new ItemStack((ItemLike) BuxinModItems.OBSIDIAN_2.get());
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player) livingentity;
                    player.getInventory().setChanged();
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity) entity;
                itemstack = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                itemstack.setCount(1);
                livingentity.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                if (livingentity instanceof Player) {
                    player = (Player) livingentity;
                    player.getInventory().setChanged();
                }
            }
            new DelayedTask(1) {
                public void run() {
                    LivingEntity livingentity1;
                    ItemStack itemstack1;
                    Player player1;

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        itemstack1 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                        itemstack1.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.MAIN_HAND, itemstack1);
                        if (livingentity1 instanceof Player) {
                            player1 = (Player) livingentity1;
                            player1.getInventory().setChanged();
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity1 = (LivingEntity) entity;
                        itemstack1 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                        itemstack1.setCount(1);
                        livingentity1.setItemInHand(InteractionHand.OFF_HAND, itemstack1);
                        if (livingentity1 instanceof Player) {
                            player1 = (Player) livingentity1;
                            player1.getInventory().setChanged();
                        }
                    }

                    Entity entity1 = entity;
                    Level level2 = entity1.level();

                    LevelAccessor levelaccessor1 = level2;

                    if (levelaccessor1 instanceof Level) {
                        Level level3 = (Level) levelaccessor1;

                        if (!level3.isClientSide()) {
                            level3.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    }
                    new DelayedTask(1) {
                        public void run() {
                            LivingEntity livingentity2;
                            ItemStack itemstack2;
                            Player player2;

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity) entity;
                                itemstack2 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                itemstack2.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.MAIN_HAND, itemstack2);
                                if (livingentity2 instanceof Player) {
                                    player2 = (Player) livingentity2;
                                    player2.getInventory().setChanged();
                                }
                            }

                            if (entity instanceof LivingEntity) {
                                livingentity2 = (LivingEntity) entity;
                                itemstack2 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                itemstack2.setCount(1);
                                livingentity2.setItemInHand(InteractionHand.OFF_HAND, itemstack2);
                                if (livingentity2 instanceof Player) {
                                    player2 = (Player) livingentity2;
                                    player2.getInventory().setChanged();
                                }
                            }

                            Entity entity2 = entity;
                            Level level4 = entity2.level();

                            new DelayedTask(1) {
                                public void run() {
                                    Entity entity3 = entity;
                                    Level level5 = entity3.level();

                                    LivingEntity livingentity3;
                                    ItemStack itemstack3;
                                    Player player3;

                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity) entity;
                                        itemstack3 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                        itemstack3.setCount(1);
                                        livingentity3.setItemInHand(InteractionHand.MAIN_HAND, itemstack3);
                                        if (livingentity3 instanceof Player) {
                                            player3 = (Player) livingentity3;
                                            player3.getInventory().setChanged();
                                        }
                                    }

                                    if (entity instanceof LivingEntity) {
                                        livingentity3 = (LivingEntity) entity;
                                        itemstack3 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                        itemstack3.setCount(1);
                                        livingentity3.setItemInHand(InteractionHand.OFF_HAND, itemstack3);
                                        if (livingentity3 instanceof Player) {
                                            player3 = (Player) livingentity3;
                                            player3.getInventory().setChanged();
                                        }
                                    }
                                    new DelayedTask(1) {
                                        public void run() {
                                            Entity entity4 = entity;
                                            Level level6 = entity4.level();

                                            LivingEntity livingentity4;
                                            ItemStack itemstack4;
                                            Player player4;

                                            if (entity instanceof LivingEntity) {
                                                livingentity4 = (LivingEntity) entity;
                                                itemstack4 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                                itemstack4.setCount(1);
                                                livingentity4.setItemInHand(InteractionHand.MAIN_HAND, itemstack4);
                                                if (livingentity4 instanceof Player) {
                                                    player4 = (Player) livingentity4;
                                                    player4.getInventory().setChanged();
                                                }
                                            }

                                            if (entity instanceof LivingEntity) {
                                                livingentity4 = (LivingEntity) entity;
                                                itemstack4 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                                itemstack4.setCount(1);
                                                livingentity4.setItemInHand(InteractionHand.OFF_HAND, itemstack4);
                                                if (livingentity4 instanceof Player) {
                                                    player4 = (Player) livingentity4;
                                                    player4.getInventory().setChanged();
                                                }
                                            }

                                            LevelAccessor levelaccessor2 = level4;

                                            if (levelaccessor2 instanceof Level) {
                                                Level level7 = (Level) levelaccessor2;

                                                if (!level7.isClientSide()) {
                                                    level7.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                }
                                            }
                                            new DelayedTask(1) {
                                                public void run() {
                                                    Entity entity5 = entity;
                                                    Level level8 = entity5.level();


                                                    LivingEntity livingentity5;
                                                    ItemStack itemstack5;
                                                    Player player5;

                                                    if (entity instanceof LivingEntity) {
                                                        livingentity5 = (LivingEntity) entity;
                                                        itemstack5 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                                        itemstack5.setCount(1);
                                                        livingentity5.setItemInHand(InteractionHand.MAIN_HAND, itemstack5);
                                                        if (livingentity5 instanceof Player) {
                                                            player5 = (Player) livingentity5;
                                                            player5.getInventory().setChanged();
                                                        }
                                                    }

                                                    if (entity instanceof LivingEntity) {
                                                        livingentity5 = (LivingEntity) entity;
                                                        itemstack5 = new ItemStack((ItemLike) BuxinModItems.OBS_5.get());
                                                        itemstack5.setCount(1);
                                                        livingentity5.setItemInHand(InteractionHand.OFF_HAND, itemstack5);
                                                        if (livingentity5 instanceof Player) {
                                                            player5 = (Player) livingentity5;
                                                            player5.getInventory().setChanged();
                                                        }
                                                    }
                                                    new DelayedTask(4) {
                                                        public void run() {
                                                            Entity entity6 = entity;
                                                            Level level9 = entity6.level();

                                                            if (!level9.isClientSide()) {
                                                                HeiYaoShiEntity stealthAttackEntity = new HeiYaoShiEntity((EntityType) BuxinModEntities.HEI_YAO_SHI.get(), level1);
                                                                stealthAttackEntity.setOwner(entity6);
                                                                stealthAttackEntity.setBaseDamage((double) 17.0F);
                                                                stealthAttackEntity.setKnockback(4);
                                                                stealthAttackEntity.setSilent(true);
                                                                stealthAttackEntity.setPos(entity6.getX(), entity6.getEyeY() - 0.1D, entity6.getZ());
                                                                stealthAttackEntity.shoot(entity6.getLookAngle().x, entity6.getLookAngle().y, entity6.getLookAngle().z, 1.0F, 0.0F);
                                                                level9.addFreshEntity(stealthAttackEntity);
                                                            }

                                                            if (level instanceof Level) {
                                                                Level level10 = level;

                                                                if (!level10.isClientSide()) {
                                                                    level10.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                                }
                                                            }

                                                            LivingEntity livingentity6;
                                                            ItemStack itemstack6;
                                                            Player player6;

                                                            if (entity instanceof LivingEntity) {
                                                                livingentity6 = (LivingEntity) entity;
                                                                itemstack6 = new ItemStack((ItemLike) BuxinModItems.OBS.get());
                                                                itemstack6.setCount(1);
                                                                livingentity6.setItemInHand(InteractionHand.MAIN_HAND, itemstack6);
                                                                if (livingentity6 instanceof Player) {
                                                                    player6 = (Player) livingentity6;
                                                                    player6.getInventory().setChanged();
                                                                }
                                                            }

                                                            if (entity instanceof LivingEntity) {
                                                                livingentity6 = (LivingEntity) entity;
                                                                itemstack6 = new ItemStack(Blocks.AIR);
                                                                itemstack6.setCount(1);
                                                                livingentity6.setItemInHand(InteractionHand.OFF_HAND, itemstack6);
                                                                if (livingentity6 instanceof Player) {
                                                                    player6 = (Player) livingentity6;
                                                                    player6.getInventory().setChanged();
                                                                }
                                                            }
                                                        }
                                                    };


                                                    if (level instanceof Level) {

                                                        if (!level.isClientSide()) {
                                                            level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.BLOCKS, 1.0F, 1.0F);
                                                        }
                                                    }
                                                }
                                            };
                                        }
                                    };
                                }
                            };
                        }
                    };
                }
            };
        }

        if (Math.random() == 0.04D && level instanceof Level) {

            if (!level.isClientSide()) {
                level.playSound((Player) null, new BlockPos((int) d0, (int) d1, (int) d2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:herobrine_attack")), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                level.playLocalSound(d0, d1, d2, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:herobrine_attack")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        }
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        /*
        if (this.darkObUp != null) {
            this.darkObUp.discard();
        }
        if (this.darkObRight != null) {
            this.darkObRight.discard();
        }
        if (this.darkObLeft != null) {
            this.darkObLeft.discard();
        }

         */
        if (this.level() instanceof ServerLevel serverLevel) {
            if(Math.random() > 0.9) {
                /*
                HerobrinePlayerMobEntity corpse = new HerobrinePlayerMobEntity(BuxinModEntities.Infected_PlayerMOB.get(), serverLevel);

                corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                String killedName = this.getPersistentData().getString("killed_name");
                corpse.getPersistentData().putString("possessed_by", "shadow_herobrine");
                if (killedName.isEmpty()) {
                    killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
                }
                corpse.setUsername(killedName);
                corpse.setCustomName(Component.literal(killedName));
                corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                this.setInvisible(true);
                this.remove(RemovalReason.KILLED);
                serverLevel.addFreshEntity(corpse);

                 */
            }
        }
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
    }

    @Override
    public void baseTick() {
        super.baseTick();
        Random random1 = new Random();
        int v = random1.nextInt(4);
        Mob entity = this;
        if(entity.getTarget() != null && Math.random() > 0.917813114514 && !(this.getPersistentData().getBoolean("gp"))) {
            //entity.lookAt(EntityAnchorArgument.Anchor.EYES, entity.getTarget().getLookAngle());
            BuxinMod.queueServerWork((int) (Math.random() * 2), () -> {
                if(v == 1){
                    shootOne(this.RightObsidian, entity.getTarget().position(), 2F, "right", this);
                } else if(v == 2){
                    shootOne(this.LeftObsidian, entity.getTarget().position(), 2F, "left", this);
                } else if(v == 3){
                    shootOne(this.UpObsidian, entity.getTarget().position(), 2F, "up", this);
                } else {
                    shootDarkObsAtTarget(2.0F, this);
                }
                BuxinMod.queueServerWork(25, this::spawnDarkObEntities);
                BuxinMod.queueServerWork(6,() -> {
                    if(entity.getTarget() != null) {
                        Method_114514.entity_use_command(entity.getTarget(), "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
                        Method_114514.play_sound(this.level(), entity.getTarget().getX(), entity.getTarget().getY(), entity.getTarget().getZ(), "buxin:obsidian_hit");
                    }
                });
            });
        }
        if(this.getPersistentData().getBoolean("gp")){
            AnimationPlayer.playAnimation(this,BuxinAnimations.HB_getpower);
        }
    }


    private Vec3 getUpBlockPos() {
        final double upY     = 2.0;
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

    public void spawnDarkObEntities() {
        if (this.level() instanceof ServerLevel serverLevel) {
            BlockState block = BuxinModBlocks.DARK_BLOCK_ZHU.get().defaultBlockState();

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
            }
        }
    }

    private static void shootChain(Entity shooter, BlockState block, float velocity, int length) {
        Level level = shooter.level();
        if (level.isClientSide) return;

        double eyeY = shooter.getEyeY();
        Vec3 look = shooter.getLookAngle().normalize();
        RandomSource rand = level.getRandom();

        for (int i = 0; i < length; i++) {
            BuxinBlockProjectileEntity proj = new BuxinBlockProjectileEntity(
                    level,
                    shooter instanceof LivingEntity ? (LivingEntity) shooter : null,
                    block
            );

            Vec3 forward = look.scale(i * 1.0);

            double sideX = (rand.nextDouble() - 0.5) * 2.0;
            double sideY = (rand.nextDouble() - 0.5) * 2.0;
            double sideZ = (rand.nextDouble() - 0.5) * 2.0;

            proj.setPos(
                    shooter.getX() + forward.x + sideX,
                    eyeY + forward.y + sideY,
                    shooter.getZ() + forward.z + sideZ
            );
            proj.setDeltaMovement(look.scale(velocity));

            level.addFreshEntity(proj);
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
                var rl = (net.minecraft.resources.ResourceLocation) m.invoke(anim);
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

    private void shootOne(BuxinBlockProjectileEntity ob, Vec3 to, double speed, String position, ShadowHerobrineEntity shadowHerobrineEntity) {
        if (ob == null || !ob.isAlive()) return;
        Vec3 dir = to.subtract(ob.position());
        if (dir.lengthSqr() < 1.0e-6) dir = this.getLookAngle();
        ob.setNoGravity(false);
        Vec3 vel = dir.normalize().scale(speed);
        ob.setDeltaMovement(vel);
        ob.setNotReadyForShoot(false);
        if (position.equals("up")) {
            shadowHerobrineEntity.UpObsidianUUID = null;
            shadowHerobrineEntity.UpObsidian = null;
        } else if (position.equals("left")) {
            shadowHerobrineEntity.LeftObsidianUUID = null;
            shadowHerobrineEntity.LeftObsidian = null;
        } else if (position.equals("right")) {
            shadowHerobrineEntity.RightObsidianUUID = null;
            shadowHerobrineEntity.RightObsidian = null;
        }
    }

    public void shootDarkObsAtTarget(double speed, ShadowHerobrineEntity shadowHerobrineEntity) {
        if (shadowHerobrineEntity.level().isClientSide) return;

        Vec3 to;
        LivingEntity target = shadowHerobrineEntity.getTarget();
        if (target != null && target.isAlive()) {
            to = target.getEyePosition(1.0F);
        } else {
            to = shadowHerobrineEntity.getEyePosition().add(shadowHerobrineEntity.getLookAngle().scale(16.0));
        }

        if (shadowHerobrineEntity.UpObsidian != null) {
            shootOne(shadowHerobrineEntity.UpObsidian, to, speed, "up", shadowHerobrineEntity);
        }
        if (shadowHerobrineEntity.LeftObsidian != null) {
            shootOne(shadowHerobrineEntity.LeftObsidian, to, speed, "left", shadowHerobrineEntity);
        }
        if (shadowHerobrineEntity.RightObsidian != null) {
            shootOne(shadowHerobrineEntity.RightObsidian, to, speed, "right", shadowHerobrineEntity);
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
                BlockState block = BuxinModBlocks.AMY.get().defaultBlockState();
                setDeltaMovement(Vec3.ZERO);
                shootChain(this, block, 2.5F, 5);
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
            if (this.UpObsidian != null) {
                this.UpObsidian.moveTo(getUpBlockPos());
            }
            if (this.RightObsidian != null) {
                this.RightObsidian.moveTo(getRightBlockPos());
            }
            if (this.LeftObsidian != null) {
                this.LeftObsidian.moveTo(getLeftBlockPos());
            }

            boolean aimingNow = isAiming(animId);
            if (aimingNow && !wasAiming) {
                spawnDarkObEntities();
                ShadowHerobrineEntity shadowHerobrineEntity = this;
                new DelayedTask(10) {
                    @Override
                    public void run() {
                        if (shadowHerobrineEntity.isAlive()) {
                            shootDarkObsAtTarget(2.0F, shadowHerobrineEntity);
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
    }

    public static Builder createMobAttributes() {
        Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999.35D);
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 175.0D);
        builder = builder.add(Attributes.ARMOR, 75.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        return builder;
    }
}
