
package net.mcreator.buxin.gameasset;

import java.util.Locale;
import javax.annotation.Nullable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;

public class NormalAnimation extends AttackAnimation {
    public NormalAnimation(float ConvertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(ConvertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature, false);
    }

    public NormalAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature, boolean directional) {
        this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, preDelay, contact, recovery, Float.MAX_VALUE, colliderJoint, collider));
        if (directional) {
            //this.addProperty(StaticAnimationProperty.POSE_MODIFIER, ActionAnimationProperty.RESET_PLAYER_COMBO_COUNTER);
            this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
        }
    }

    public NormalAnimation(float convertTime, String path, Armature armature, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);
        this.addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);
        this.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.333F));
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    public void onLoaded() {

        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.0f", 1.0F / this.getTotalTime()));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }
    }

    protected void bindPhaseState(AttackAnimation.Phase phase) {
        float preDelay = phase.preDelay;
        this.stateSpectrumBlueprint.newTimePair(phase.start, preDelay).addState(EntityState.PHASE_LEVEL, 1)
                .newTimePair(phase.start, phase.contact).addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .newTimePair(phase.start, phase.recovery).addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)
                .newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true)
                .newTimePair(phase.antic, phase.end).addState(EntityState.TURNING_LOCKED, false)
                .newTimePair(preDelay, phase.contact).addState(EntityState.ATTACKING, true)
                .addState(EntityState.PHASE_LEVEL, 2)
                .newConditionalTimePair((entitypatch) -> true ? 1 : 0, phase.contact, phase.recovery)
                .addConditionalState(0, EntityState.CAN_BASIC_ATTACK, false)
                .addConditionalState(1, EntityState.CAN_BASIC_ATTACK, true)
                .newTimePair(phase.contact, phase.end).addState(EntityState.PHASE_LEVEL, 3);
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }
}
