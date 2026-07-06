
package net.mcreator.buxin.gameasset;

import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.model.Armature;

public class PutEntityToDieAnimation extends ActionAnimation {
    public PutEntityToDieAnimation(float convertTime, String path, Armature armature) {
        super(convertTime, path, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);

        this.addState(EntityState.TURNING_LOCKED, true);
        this.addState(EntityState.HURT_LEVEL, 2);
        this.addState(EntityState.MOVEMENT_LOCKED, true);
        this.addState(EntityState.UPDATE_LIVING_MOTION, false);
        this.addState(EntityState.CAN_BASIC_ATTACK, false);
        this.addState(EntityState.CAN_SKILL_EXECUTION, false);
    }
}
