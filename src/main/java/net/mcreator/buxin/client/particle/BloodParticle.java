
package net.mcreator.buxin.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class BloodParticle extends TextureSheetParticle {
    protected BloodParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.x = x + (this.random.nextDouble() - 0.5) * 0.2;
        this.y = y + (this.random.nextDouble() + 0.5) * 0.5;
        this.z = z + (this.random.nextDouble() - 0.5) * 0.2;
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BloodParticle particle = new BloodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            Random random = new Random();
            particle.move(0.0, 0.5, 0.0);
            particle.setLifetime(5 + (int) (random.nextFloat() * 25.0F));
            particle.gravity = random.nextFloat() * 3.0F;
            particle.yd = (double)((0.9F - random.nextFloat()) * 0.4F);
            particle.setSize(0.15F, 0.15F);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
