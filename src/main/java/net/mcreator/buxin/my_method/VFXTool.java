package net.mcreator.buxin.my_method;

import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static net.mcreator.buxin.BuxinMod.isWindows;

public class VFXTool {
    public static void addVFXParticle(Vec3 position, String modid, String VFXname, Level world){
        if((isWindows()) && VFXParticleConfig.VFXParticleConfig.get()) {
            try {
                // 注释掉的AAA Particles相关代码：该库未在升级后的依赖列表中（geckolib 4.8.3、epicfight 20.9.7、indestructible 20.9.7均不提供AAALevel/ParticleEmitterInfo）
                // 原始AAA Particles API已被移除，且无对应1.20.1兼容版本在当前依赖中，故保持注释状态，不启用也不删除（避免编译错误）
                // ParticleEmitterInfo VFX = new ParticleEmitterInfo(new ResourceLocation(modid, VFXname));
                // ParticleEmitterInfo info = VFX.clone().position(position);
                // AAALevel.addParticle(world, info);
            } catch (Exception e) {
                // 保留空catch：原逻辑为静默失败，升级后仍需保持行为一致
            }
        }
        //entity.getposition() —— 此行是注释，无需修改
    }
}