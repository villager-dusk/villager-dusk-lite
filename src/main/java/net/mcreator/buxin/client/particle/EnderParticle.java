
package net.mcreator.buxin.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public static EnderParticleProvider provider(SpriteSet spriteSet) {
        return new EnderParticleProvider(spriteSet);
    }

    protected EnderParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, vx, vy, vz);
        this.spriteSet = spriteSet;
        this.setSize(0.4F, 0.4F);
        this.quadSize *= 0.7F;
        this.lifetime = Math.max(1, 20 + (this.random.nextInt(12) - 6));
        this.gravity = -0.1F;
        this.hasPhysics = false;
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.rCol = f * 0.9F;
        this.gCol = f * 0.3F;
        this.bCol = f;
        this.setSpriteFromAge(spriteSet);
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public void tick() {
        super.tick();

        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age) % 14, 14));
        }
    }

    public static class EnderParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public EnderParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EnderParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
