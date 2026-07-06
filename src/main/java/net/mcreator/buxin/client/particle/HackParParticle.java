package net.mcreator.buxin.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class HackParParticle extends TextureSheetParticle {
    Random random = new Random();
    float r = random.nextFloat();
    float g = random.nextFloat();
    float b = random.nextFloat();

    public static HackParParticleProvider provider(SpriteSet spriteSet) {
        return new HackParParticleProvider(spriteSet);
    }

    public static class HackParParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public HackParParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new HackParParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected HackParParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, vx, vy, vz);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 2f;
        this.lifetime = 7;
        this.gravity = 0f;
        this.hasPhysics = true;
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        r = random.nextFloat();
        g = random.nextFloat();
        b = random.nextFloat();
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 1) % 16 + 1, 16));
        }
    }
}