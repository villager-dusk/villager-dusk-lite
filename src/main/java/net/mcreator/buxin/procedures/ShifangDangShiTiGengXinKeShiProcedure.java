
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ShifangDangShiTiGengXinKeShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		/*
		if (Math.random() < 0.025) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.5, (float) 0.85);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.75, (float) 0.85, false);
				}
			}
		}

		 */
		double attack = 0;
		AtomicReference<Double> dx = new AtomicReference<>(entity.getX());
		AtomicReference<Double> dy = new AtomicReference<>(entity.getY());
		AtomicReference<Double> dz = new AtomicReference<>(entity.getZ());
		
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.PINK_LIGHT_PARTICLES.get()), x, y, z, 4, 1, 1, 1, 1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 1, 1, 1, 1, 1);
		
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS)) {
			entity.getPersistentData().putDouble("timer", (entity.getPersistentData().getDouble("timer") + 1));
			if (entity.getPersistentData().getDouble("timer") == 80) {
				entity.getPersistentData().putDouble("timer", 0);
				attack = Mth.nextInt(RandomSource.create(), 1, 24);
				if(attack == 1 && entity instanceof Mob m && m.getTarget() != null){
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
							.collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
							if(entityiterator instanceof Player)
								entityiterator.getPersistentData().putInt("press_x_c",0);
							if(entity != entityiterator && m.getTarget() == entityiterator){
								Method_114514.execute_event(entity,entityiterator, BuxinAnimations.GREATSWORD_DIE,BuxinAnimations.PUTENTITYTODIEBEENATTACK,0.2F,49, 75,true,false,0.07, 0.05, 0.07, 0.25,false);
							}
					}
				}
			}
		}
	}
}
