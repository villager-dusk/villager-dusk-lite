
package net.mcreator.buxin.skill_main;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.UUID;

public class WoopieSwordSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("d706b5bc-b98b-4413-b83e-16ae590db359");

    public WoopieSwordSkill(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            ServerPlayerPatch playerpatch = event.getPlayerPatch();
            if (event.getDamageSource() == playerpatch.getOriginal().level.damageSources().fall() && ((Player)playerpatch.getOriginal()).getMainHandItem().getItem() == BuxinModItems.WOOPIE.get()) {
                float Damage = event.getAmount();
                float FallUp = (float)((double)Damage * 0.01);
                event.setAmount(0.0F);
                event.setCanceled(true);
                if (playerpatch instanceof ServerPlayerPatch) {
                    ServerPlayer player = (ServerPlayer) playerpatch.getOriginal();
                    double x = player.getX();
                    double y = player.getY();
                    double z = player.getZ();
                    Level patt2381$temp = player.level();
                    if (patt2381$temp instanceof ServerLevel) {
                        ServerLevel world = (ServerLevel)patt2381$temp;
                        player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), new Vec3(0.0, 0.35 + (double)FallUp, 0.0)));
                        world.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }

        });
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID);
    }

    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (!container.getExecuter().isLogicalClient()) {
            ServerPlayerPatch playerpatch = (ServerPlayerPatch)container.getExecuter();
            SkillContainer WeaponInnateContainer = playerpatch.getSkill(SkillSlots.WEAPON_INNATE);
            if (((ServerPlayer)playerpatch.getOriginal()).getMainHandItem().getItem() == BuxinModItems.WOOPIE.get()) {
                WeaponInnateContainer.getSkill().setConsumptionSynchronize(playerpatch, WeaponInnateContainer.getResource() + 0.25F);
            }
        }

    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        ServerPlayer player = executer.getOriginal();
        float yaw = player.getYRot();
        float pitch = player.getXRot();
        double x_speed = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        double y_speed = -Math.sin(Math.toRadians(pitch));
        double z_speed = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        double speedMultiplier = 4;
        double highMultiplier = 3.5;
        x_speed *= speedMultiplier;
        y_speed *= highMultiplier;
        z_speed *= speedMultiplier;
        Level var23 = player.level();
        executer.playAnimationSynchronized(BuxinAnimations.SIMPLE_SWORD_AUTO_3, 0);
        double finalX_speed = x_speed;
        double finalY_speed = y_speed;
        double finalZ_speed = z_speed;
        BuxinMod.queueServerWork(5,() -> {
            if (var23 instanceof ServerLevel world) {
                executer.playSound((SoundEvent)EpicFightSounds.ENTITY_MOVE.get(), -0.05F, 0.1F);
                player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), new Vec3(finalX_speed, finalY_speed, finalZ_speed)));
            }
            if(VFXParticleConfig.VFXParticleConfig.get() && BuxinMod.isWindows()){
                VFXTool.addVFXParticle(player.position(),BuxinMod.MODID,"woopie",player.level());
            }
        });
    }
}
