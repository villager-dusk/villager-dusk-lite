package net.mcreator.buxin.skill_main;

import io.netty.buffer.Unpooled;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class SingleSparkCanStartPrairieFireArt extends WeaponInnateSkill {
    private final StaticAnimation[] animations;
    private static final UUID EVENT_UUID = UUID.fromString("4fbc808b-5aaa-4dfb-ad3f-12462f1b290a");

    public SingleSparkCanStartPrairieFireArt(Builder<? extends Skill> builder) {
        super(builder);
        this.animations = new StaticAnimation[]{BuxinAnimations.SingleSparkCanStartPrairieFire, BuxinAnimations.SingleSparkCanStartPrairieFire, BuxinAnimations.SingleSparkCanStartPrairieFire};
    }

    public void onInitiate(SkillContainer container) {
        if (!container.getExecuter().isLogicalClient()) {
            this.setConsumption(container, 6.0F);
          //  container.syncResource();
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
            if (((ServerPlayer)playerpatch.getOriginal()).getMainHandItem().getItem() == BuxinModItems.HUO_QBJ.get()) {
                float newResource = WeaponInnateContainer.getResource() + 0.25F;
                WeaponInnateContainer.setResource(newResource);
            //    WeaponInnateContainer.syncResource();
            }
        }
    }

    public static void createExpandingExplosions(Entity sourceEntity, int iterations, double baseRadius) {
        if (sourceEntity.level().isClientSide()) return;

        Level level = sourceEntity.level();
        double centerX = sourceEntity.getX();
        double centerY = sourceEntity.getY();
        double centerZ = sourceEntity.getZ();

        for (int i = 1; i <= iterations; i++) {
            final int currentIteration = i;
            final double radius = baseRadius * currentIteration;
            BuxinMod.queueServerWork((int) (iterations + Math.random() * 4),() -> {
                int points = 8 + currentIteration * 2;
                for (int j = 0; j < points; j++) {
                    double angle = 2 * Math.PI * j / points;
                    double offsetX = Math.cos(angle) * radius;
                    double offsetZ = Math.sin(angle) * radius;

                    double explosionX = centerX + offsetX;
                    double explosionY = centerY;
                    double explosionZ = centerZ + offsetZ;
                    if(Math.random() > 0.5)
                        VFXTool.addVFXParticle(new Vec3(explosionX,explosionY,explosionZ),BuxinMod.MODID,"electronic_2",level);
                }
            });
        }
    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public void gatherArguments(LocalPlayerPatch executer, ControllEngine controllEngine, FriendlyByteBuf buf) {
        Input input = ((LocalPlayer) executer.getOriginal()).input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus((LivingEntity) executer.getOriginal()), 0.0F, 1.0F);
        input.tick(false, pulse);
        int forward = input.up ? 1 : 0;
        int backward = input.down ? -1 : 0;
        int left = input.left ? 1 : 0;
        int right = input.right ? -1 : 0;
        buf.writeInt(forward);
        buf.writeInt(backward);
        buf.writeInt(left);
        buf.writeInt(right);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        this.setStackSynchronize(executer, 1);
        super.executeOnServer(executer, args);
        Player player = executer.getOriginal();
        Level level = player.level();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        int i = args.readInt();
        Method_114514.entity_use_command(player,"/weather clear");
        executer.playAnimationSynchronized(this.animations[i], 0.0F);
        player.playSound(SoundEvents.FIRE_AMBIENT);
        Method_114514.play_sound(player.level(),player.getX(),player.getY(),player.getZ(),"block.fire.extinguish");
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,7, 255, false, false));
        Vec3 player_pos = new Vec3(player.getX(),player.getY() + 1,player.getZ());
        BuxinMod.queueServerWork(4,() -> {
            if (VFXParticleConfig.VFXParticleConfig.get()) {
                VFXTool.addVFXParticle(player_pos, BuxinMod.MODID, "Flame", player.level());
            }
            BuxinMod.queueServerWork(35,() -> {
                Method_114514.play_sound(player.level(),player.getX(),player.getY(),player.getZ(),"block.fire.extinguish");
            });
            final Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(13 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if(entityiterator instanceof LivingEntity) {
                    entityiterator.setSecondsOnFire(30);
                    BuxinMod.queueServerWork(35,() -> {
                        entityiterator.hurt(level.damageSources().generic(), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.6));
                    });
                }
            }
        });
    }
}