package net.mcreator.buxin.entity.father;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RangedPathfinderMob extends PathfinderMob implements RangedAttackMob {
    protected RangedPathfinderMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1, 1, 30.0F));
    }

    public boolean canFireProjectileWeapon(Item item) {
        return true;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        this.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        ItemStack weaponStack = ItemStack.EMPTY;
        if (this.getMainHandItem().getItem() instanceof BowItem) {
            weaponStack = this.getMainHandItem();
        } else if (this.getOffhandItem().getItem() instanceof BowItem) {
            weaponStack = this.getOffhandItem();
        }
        ItemStack itemstack = this.getProjectile(weaponStack);
        AbstractArrow mobArrow = ProjectileUtil.getMobArrow(this, itemstack, pVelocity);
        if (this.getMainHandItem().getItem() instanceof BowItem) {
            mobArrow = ((BowItem)this.getMainHandItem().getItem()).customArrow(mobArrow);
        }
        if(Math.random() > 0.3){
            mobArrow.setCritArrow(true);
        } else {
            if(Math.random() < 0.5 && !this.level().isRaining()){
                mobArrow.setSecondsOnFire(5);
            }
        }
        double x = pTarget.getX() - this.getX();
        double y = pTarget.getEyeY() - mobArrow.getY();
        double z = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(x * x + z * z);
        mobArrow.setBaseDamage(1.5D);
        mobArrow.setOwner(this);
        mobArrow.shoot(x, y + d3 * (double)0.2F, z, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.level().addFreshEntity(mobArrow);
    }
}