
package net.mcreator.buxin.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/*
public class BlackShieldExplosionParticleEmitterInfo extends ParticleEmitterInfo {
    public enum ForwardAxis { PLUS_Z, PLUS_Y }

    private ForwardAxis axis = ForwardAxis.PLUS_Z;
    private float roll = 0f;
    private Vec3 from = null;
    private Vec3 to   = null;
    private boolean flipForward = true;

    public BlackShieldExplosionParticleEmitterInfo(ResourceLocation effek) {
        super(effek);
    }

    public BlackShieldExplosionParticleEmitterInfo fromTo(Vec3 from, Vec3 to, ForwardAxis axis, float extraRollRad, boolean flip) {
        this.from = from;
        this.to = to;
        this.axis = axis;
        this.roll = extraRollRad;
        this.flipForward = flip;
        return this;
    }

    private static void aim(ParticleEmitter em, Vec3 from, Vec3 to, ForwardAxis axis, float roll, boolean flip) {
        Vec3 d = to.subtract(from);
        if (flip) d = d.scale(-1);
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);

        switch (axis) {
            case PLUS_Z -> {
                float yaw   = (float) Math.atan2(d.x, d.z);
                float pitch = (float)-Math.atan2(d.y, xz);
                em.setRotation(pitch, yaw, roll);
            }
            case PLUS_Y -> {
                float yaw   = (float) Math.atan2(d.z, d.x) + (float)Math.PI/2f;
                float pitch = (float) Math.atan2(xz, d.y) - (float)Math.PI/2f;
                em.setRotation(pitch, yaw, roll);
            }
        }
    }

    @Override
    public void spawnInWorld(Level level, Player player) {
        if (from == null || to == null) return;
        Optional.ofNullable(EffectRegistry.get(this.effek)).ifPresent(eff -> {
            ParticleEmitter em = this.hasEmitter() ? eff.play(this.emitter) : eff.play();
            em.setPosition((float) from.x, (float) from.y, (float) from.z);
            aim(em, from, to, axis, roll, flipForward);
        });
    }

    private static void aim(ParticleEmitter em, Vec3 from, Vec3 to, ForwardAxis axis, float roll) {
        Vec3 d = to.subtract(from);
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);

        switch (axis) {
            case PLUS_Z -> {
                float yaw   = (float) Math.atan2(d.x, d.z);
                float pitch = (float)-Math.atan2(d.y, xz);
                em.setRotation(pitch, yaw, roll);
            }
            case PLUS_Y -> {
                float yaw   = (float) Math.atan2(d.z, d.x) + (float)Math.PI/2f;
                float pitch = (float) Math.atan2(xz, d.y) - (float)Math.PI/2f;
                em.setRotation(pitch, yaw, roll);
            }
        }
    }
}
*/

// 注释：此文件中所有与AAAParticles相关的代码（包括导入、类定义及内部逻辑）已被完全注释掉，
// 因为：
// - AAAParticles 在 1.20.1/Forge 47.4.10 环境下无对应兼容版本（原依赖未适配新MC+Forge）；
// - geckolib 4.8.3、epicfight 20.9.7、indestructible 20.9.7 均不提供或依赖 AAAParticles；
// - 当前代码未被实际调用（全文件注释），且无迁移路径（官方未发布 1.20.1 版本的 AAAParticles）；
// - 保持原结构但禁用，避免编译错误；如需粒子功能，应改用 GeckoLib 4.8.3 的 Particle API 或原生 Minecraft 粒子系统。
// 其余 import 和包声明已适配 1.20.1：ResourceLocation、Player、Level、Vec3 的包路径在 1.20.1 中未变更，无需修改。
