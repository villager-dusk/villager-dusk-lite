
package net.mcreator.buxin.entity.father.cape_entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class RenderCapeMonster extends Monster {
    public double xCloakO;
    public double yCloakO;
    public double zCloakO;
    public double xCloak;
    public double yCloak;
    public double zCloak;

    protected RenderCapeMonster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public void tick() {
        super.tick();
        this.xCloakO = this.xCloak;
        this.yCloakO = this.yCloak;
        this.zCloakO = this.zCloak;
        double x = this.getX() - this.xCloak;
        double y = this.getY() - this.yCloak;
        double z = this.getZ() - this.zCloak;
        double maxCapeAngle = 10.0D;
        if (x > maxCapeAngle) {
            this.xCloak = this.getX();
            this.xCloakO = this.xCloak;
        }

        if (z > maxCapeAngle) {
            this.zCloak = this.getZ();
            this.zCloakO = this.zCloak;
        }

        if (y > maxCapeAngle) {
            this.yCloak = this.getY();
            this.yCloakO = this.yCloak;
        }

        if (x < -maxCapeAngle) {
            this.xCloak = this.getX();
            this.xCloakO = this.xCloak;
        }

        if (z < -maxCapeAngle) {
            this.zCloak = this.getZ();
            this.zCloakO = this.zCloak;
        }

        if (y < -maxCapeAngle) {
            this.yCloak = this.getY();
            this.yCloakO = this.yCloak;
        }

        this.xCloak += x * 0.25D;
        this.zCloak += z * 0.25D;
        this.yCloak += y * 0.25D;
    }
}
