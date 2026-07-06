
package net.mcreator.buxin.ai;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HeavyAttackUseGoal extends Goal {
    private final Mob entity;
    private LivingEntity targetLivingEntity;
    private int cooldown = reducedTickDelay(50);

    public HeavyAttackUseGoal(Mob entity){
        this.entity = entity;
    }

    public boolean canUse() {
        LivingEntity target = this.entity.getTarget();
        if (!(target instanceof LivingEntity))
            return false;

        if (--this.cooldown > 0)
            return false;
        if(entity.distanceTo(target) > 10){
            return false;
        }

        return !this.entity.getMainHandItem().isEmpty();
    }

    public void start() {
        this.targetLivingEntity = this.entity.getTarget();
        double x = entity.getX(),y = entity.getY(),z = entity.getZ();
        LevelAccessor world = entity.level();
        if(!entity.level().isClientSide()) {
            if (entity.getOffhandItem().getItem() == BuxinModItems.WOOPIE.get()) {
                if(SystemMethod.isWindows()) {
                    VFXTool.addVFXParticle(new Vec3(targetLivingEntity.getX(),targetLivingEntity.getY() + 2.2,targetLivingEntity.getZ()),"buxin","danger",entity.level());
                    Method_114514.play_sound(targetLivingEntity,"buxin:danger");
                }
                BuxinMod.queueServerWork(10,() -> {
                    entity.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get()));
                    entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) 2.6, 100));
                    Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "buxin:attack");
                    Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "buxin:test");
                    BuxinMod.queueServerWork(6,() -> {
                        Method_114514.play_sound(entity,"epicfight:sfx.entity_move",6f,0.7f + new Random().nextFloat(0.25f));
                    });
                    Method_114514.entity_use_command(entity,"/effect give @s minecraft:strength 10 20");
                    BuxinMod.queueServerWork(8, () -> {
                        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                            if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                                LivingEntityPatch.playAnimationSynchronized(BuxinAnimations.Legendary_Sword_Heavy_Attack, 0F);
                            }
                        });
                    });
                    {
                        final Vec3 _center = new Vec3(x, y, z);
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                        for (Entity entityiterator : _entfound) {
                            if (!(entity == entityiterator)) {
                                if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                    _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 100, 1, false, false));
                            }
                        }
                    }
                    BuxinMod.queueServerWork(8,() -> {
                        Method_114514.entity_use_command(entity,"/particle minecraft:totem_of_undying ~ ~ ~ 0 0 0 0.5 100");
                    });
                    if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                            _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
                                    SoundSource.NEUTRAL, 1, 1);
                        } else {
                            _level.playLocalSound(x, y, z,
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
                                    SoundSource.NEUTRAL, 1, 1, false);
                        }
                    }
                    entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 10));
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 90, 3));
                    entity.setItemSlot(EquipmentSlot.MAINHAND,BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get().getDefaultInstance());
                    BuxinMod.queueServerWork(80, () -> {
                        entity.setItemSlot(EquipmentSlot.MAINHAND,BuxinModItems.NIUBI_2DANSHOU.get().getDefaultInstance());
                    });
                });
            }
        }
        this.cooldown = reducedTickDelay(400);
    }

    public void stop() {
        this.targetLivingEntity = null;
        this.entity.getNavigation().stop();
    }
}
