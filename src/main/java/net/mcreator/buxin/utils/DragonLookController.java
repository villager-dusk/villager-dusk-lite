
package net.mcreator.buxin.utils;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class DragonLookController {
    
    public static void makeDragonLookAt(EnderDragon dragon, Entity target) {
        if (dragon == null || target == null || !dragon.isAlive()) {
            return;
        }
        
        Vec3 dragonPos = dragon.position();
        Vec3 targetPos = target.position();
        
        makeDragonLookAtPosition(dragon, targetPos);
        
        rotateDragonBodyToward(dragon, targetPos);
    }
    
    public static void makeDragonLookAtPosition(EnderDragon dragon, Vec3 targetPos) {
        Vec3 dragonPos = dragon.position();
        
        double dx = targetPos.x - dragonPos.x;
        double dy = targetPos.y - (dragonPos.y + dragon.getBbHeight() * 0.6);
        double dz = targetPos.z - dragonPos.z;
        
        double yawRad = Math.atan2(dz, dx);
        float targetYaw = (float) Math.toDegrees(yawRad) - 90.0f;
        
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        float targetPitch = -(float) Math.toDegrees(Math.atan2(dy, horizontalDistance));
        
        float currentYaw = dragon.getYRot();
        float currentPitch = dragon.getXRot();
        
        float newYaw = lerpAngle(currentYaw, targetYaw, 0.2f);
        float newPitch = currentPitch + (targetPitch - currentPitch) * 0.15f;
        
        dragon.setYRot(newYaw);
        dragon.setXRot(newPitch);
        
        updateDragonHeadRotation(dragon, newYaw, newPitch);
    }
    
    private static float lerpAngle(float from, float to, float alpha) {
        float difference = to - from;
        
        while (difference < -180.0f) difference += 360.0f;
        while (difference > 180.0f) difference -= 360.0f;
        
        return from + difference * alpha;
    }
    
    private static void updateDragonHeadRotation(EnderDragon dragon, float bodyYaw, float bodyPitch) {

    }
    
    private static void rotateDragonBodyToward(EnderDragon dragon, Vec3 targetPos) {
        Vec3 dragonPos = dragon.position();
        double dx = targetPos.x - dragonPos.x;
        double dz = targetPos.z - dragonPos.z;
        
        double bodyYawRad = Math.atan2(dz, dx);
        float targetBodyYaw = (float) Math.toDegrees(bodyYawRad) - 90.0f;
        
        float currentBodyYaw = dragon.yBodyRot;
        float newBodyYaw = lerpAngle(currentBodyYaw, targetBodyYaw, 0.1f);
        
        dragon.yBodyRot = newBodyYaw;
        dragon.yBodyRotO = newBodyYaw;
    }
}
