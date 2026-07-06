
package net.mcreator.buxin.entity;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.procedures.BaozhajianDangTouSheWuJiZhongWanJiaShiProcedure;
import net.mcreator.buxin.procedures.BaozhajianTouZhiWuFeiXingKeProcedure;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(value = Dist.CLIENT)
public class BaozhajianEntity extends AbstractArrow {
	public BaozhajianEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(BuxinModEntities.BAOZHAJIAN.get(), world);
	}

	public BaozhajianEntity(EntityType<? extends BaozhajianEntity> type, Level world) {
		super(type, world);
	}

	public BaozhajianEntity(EntityType<? extends BaozhajianEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public BaozhajianEntity(EntityType<? extends BaozhajianEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	@Override
	public void playerTouch(Player entity) {
		super.playerTouch(entity);
		BaozhajianDangTouSheWuJiZhongWanJiaShiProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
	}

	@Override
	protected ItemStack getPickupItem() {
		return null;
	}

	@Override
	public void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		BaozhajianDangTouSheWuJiZhongWanJiaShiProcedure.execute(this.level, blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());
	}

	@Override
	public void tick() {
		super.tick();
		BaozhajianTouZhiWuFeiXingKeProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
		if (this.inGround)
			this.discard();
	}

	public static BaozhajianEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		BaozhajianEntity entityarrow = new BaozhajianEntity(BuxinModEntities.BAOZHAJIAN.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static BaozhajianEntity shoot(LivingEntity entity, LivingEntity target) {
		BaozhajianEntity entityarrow = new BaozhajianEntity(BuxinModEntities.BAOZHAJIAN.get(), entity, entity.level);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(5);
		entityarrow.setKnockback(5);
		entityarrow.setCritArrow(false);
		entity.level.addFreshEntity(entityarrow);
		entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS, 1, 1f / (entity.level.random.nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
