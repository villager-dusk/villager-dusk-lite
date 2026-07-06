//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.mcreator.buxin.skill_main;

import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.server.SPSkillExecutionFeedback;
import yesman.epicfight.skill.ChargeableSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HeavyDashSkill extends WeaponInnateSkill implements ChargeableSkill {
    private static final SkillDataKey<Integer> CHARGING_POWER;
    private static final UUID EVENT_UUID;
    private final StaticAnimation chargingAnimation;
    private final StaticAnimation attackAnimation;

    public static int getChargingPower(SkillContainer skillContainer) {
        return (Integer)skillContainer.getDataManager().getDataValue(CHARGING_POWER);
    }

    public HeavyDashSkill(Skill.Builder<? extends Skill> builder) {
        super(builder);
        this.chargingAnimation = Animations.STEEL_WHIRLWIND_CHARGING;
        this.attackAnimation = BuxinAnimations.Super_Whirlwind;
    }

    public void onInitiate(SkillContainer container) {
        container.getDataManager().registerData(CHARGING_POWER);
        PlayerEventListener listener = container.getExecuter().getEventListener();
        listener.addEventListener(EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, (event) -> {
            if (((LocalPlayerPatch)event.getPlayerPatch()).isChargingSkill(this)) {
                LocalPlayer clientPlayer = (LocalPlayer)((LocalPlayerPatch)event.getPlayerPatch()).getOriginal();
                clientPlayer.setSprinting(false);
                Minecraft mc = Minecraft.getInstance();
                ClientEngine.getInstance().controllEngine.setKeyBind(mc.options.keySprint, false);
                Input var10000 = event.getMovementInput();
                var10000.forwardImpulse *= 1.0F - 0.8F * (float)((LocalPlayerPatch)event.getPlayerPatch()).getSkillChargingTicks() / 30.0F;
            }

        });
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID);
    }

    public int getAllowedMaxChargingTicks() {
        return 60;
    }

    public int getMaxChargingTicks() {
        return 30;
    }

    public int getMinChargingTicks() {
        return 6;
    }

    public void startCharging(PlayerPatch<?> caster) {
        caster.playAnimationSynchronized(this.chargingAnimation, 0.0F);
    }

    public void resetCharging(PlayerPatch<?> caster) {
    }

    public void castSkill(ServerPlayerPatch caster, SkillContainer skillContainer, int chargingTicks, SPSkillExecutionFeedback feedbackPacket, boolean onMaxTick) {
        caster.getSkill(this).getDataManager().setDataSync(CHARGING_POWER, chargingTicks, (ServerPlayer)caster.getOriginal());
        caster.playAnimationSynchronized(this.attackAnimation, 0.0F);
        this.cancelOnServer(caster, (FriendlyByteBuf)null);
    }

    @Override
    public void gatherChargingArguemtns(LocalPlayerPatch localPlayerPatch, ControllEngine controllEngine, FriendlyByteBuf friendlyByteBuf) {

    }

    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }

    static {
        CHARGING_POWER = SkillDataKey.createSkillDataKey(
                (buf, value) -> buf.writeInt(value),
                (buf) -> buf.readInt(),
                0,
                true
        );
        EVENT_UUID = UUID.fromString("a2d057cc-e30f-11ed-b05b-0242ac120004");
    }
}