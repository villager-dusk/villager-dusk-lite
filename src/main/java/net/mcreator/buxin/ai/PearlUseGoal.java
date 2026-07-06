package net.mcreator.buxin.ai;

import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class PearlUseGoal extends Goal {
    private final Mob pearler;
    private LivingEntity targetLivingEntity;
    private int cooldown = reducedTickDelay(50);

    private ThrownEnderpearl enderPearlEntity;

    public PearlUseGoal(Mob pearler){
        this.pearler = pearler;
    }

    public boolean canUse() {
        LivingEntity target = this.pearler.getTarget();
        if (!(target instanceof LivingEntity))
            return false;
        if (this.pearler.distanceToSqr(target) < 25d)
            return false;

        if (--this.cooldown > 0)
            return false;

        if(pearler.isPassenger()){
            return false;
        }

        return !this.pearler.getMainHandItem().isEmpty();
    }

    public boolean canContinueToUse() {
        return this.enderPearlEntity != null && this.enderPearlEntity.isAlive();
    }

    public void start() {
        this.targetLivingEntity = this.pearler.getTarget();
        Method_114514.play_sound(pearler.level(), pearler.getX(), pearler.getY(), pearler.getZ(), "entity.ender_pearl.throw");
        ItemStack stack = Items.ENDER_PEARL.getDefaultInstance();
        this.enderPearlEntity = new ThrownEnderpearl(this.pearler.level(), this.pearler);
        enderPearlEntity.setPos(this.pearler.getEyePosition(1f).x, this.pearler.getEyePosition(1f).y, this.pearler.getEyePosition(1f).z);
        enderPearlEntity.setItem(stack);
        Vec3 vector3d = this.pearler.getEyePosition(1f);
        double x = this.targetLivingEntity.getX() - vector3d.x;
        double y = this.targetLivingEntity.getEyePosition(1f).y - vector3d.y;
        double z = this.targetLivingEntity.getZ() - vector3d.z;
        double d = Math.sqrt(x * x + z * z);
        double pitch = Mth.wrapDegrees((float)(-(Mth.atan2(y, d) * (double)(180F / (float)Math.PI))));
        double yaw = Mth.wrapDegrees((float)(Mth.atan2(z, x) * (double)(180F / (float)Math.PI)) - 90.0F);
        enderPearlEntity.shootFromRotation(this.pearler, (float) (pitch - 3f - y), (float) (yaw), 0.0F, 1.5F, 4);
        this.pearler.level().addFreshEntity(enderPearlEntity);
        this.cooldown = reducedTickDelay(75);
    }

    public void stop() {
        this.targetLivingEntity = null;
        this.enderPearlEntity = null;
        this.pearler.getNavigation().stop();
    }
}