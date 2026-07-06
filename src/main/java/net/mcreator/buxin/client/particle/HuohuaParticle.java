
package net.mcreator.buxin.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HuohuaParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public static HuohuaParticle.SparkParticleProvider provider(SpriteSet spriteset) {
        return new HuohuaParticle.SparkParticleProvider(spriteset);
    }

    protected HuohuaParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2, d3, d4, d5);
        this.spriteSet = spriteset;
        this.setSize(0.3F, 0.2F);
        this.quadSize *= 0.25F;
        this.lifetime = Math.max(1, 23 + (this.random.nextInt(20) - 10));
        this.gravity = 0.5F;
        this.hasPhysics = true;
        this.pickSprite(spriteset);
    }

    public int getLightColor(float f) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public void tick() {
        super.tick();
    }

    public static class SparkParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public SparkParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(SimpleParticleType simpleparticletype, ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5) {
            return new HuohuaParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
