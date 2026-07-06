
package net.mcreator.buxin.skill_main;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.fake_entity.FakeEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class HeavyAttackSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("d206b5bc-b98b-4413-b81e-16ae590db319");

    public HeavyAttackSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID);
    }

    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (!container.getExecuter().isLogicalClient()) {
            ServerPlayerPatch playerpatch = (ServerPlayerPatch)container.getExecuter();
            SkillContainer WeaponInnateContainer = playerpatch.getSkill(SkillSlots.WEAPON_INNATE);
            if (playerpatch.getOriginal().getMainHandItem().getItem() == BuxinModItems.NIUBI_2DANSHOU.get()) {
                WeaponInnateContainer.getSkill().setConsumptionSynchronize(playerpatch, WeaponInnateContainer.getResource() + 0.25F);
            }
        }
    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        LivingEntity entity = executer.getOriginal();
        Level serverLevel = entity.level();
        if(!serverLevel.isClientSide){
            entity.getPersistentData().putBoolean("player_wake_up", false);
            if (entity.getOffhandItem().getItem() == BuxinModItems.WOOPIE.get()) {
                entity.getPersistentData().putBoolean("player_wake_up", true);
                RandomSource random1 = RandomSource.create();
                entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) 2.6, 110));
                Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "buxin:attack");
                Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "buxin:test");
                Method_114514.entity_use_command(entity, "/effect give @s minecraft:strength 10 20");
                AnimationPlayer.playAnimation(entity, Animations.GREATSWORD_GUARD);
                BuxinMod.queueServerWork(8, () -> {
                    entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                        if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                            LivingEntityPatch.playAnimationSynchronized(BuxinAnimations.Legendary_Sword_Heavy_Attack, 0F);
                        }
                    });
                });
                BuxinMod.queueServerWork(6, () -> {
                    Method_114514.play_sound(entity, "epicfight:sfx.entity_move", 6f, 0.7f + RandomSource.create().nextFloat());
                });
                {
                    final Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List<Entity> _entfound = serverLevel.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                    for (Entity entityiterator : _entfound) {
                        if ((entity == entityiterator) == false) {
                            if (entityiterator instanceof LivingEntity _entity && !_entity.level.isClientSide())
                                _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 100, 1, false, false));
                        }
                    }
                }

                BuxinMod.queueServerWork(8, () -> {
                    Method_114514.entity_use_command(entity, "/particle minecraft:totem_of_undying ~ ~ ~ 0 0 0 0.5 100");
                });
                Method_114514.play_sound(entity, "item.totem.use");
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 10));
                BuxinMod.queueServerWork(24, () -> {
                    Method_114514.entity_use_command(entity, "/impactful @s shake 34 4 4");
                });
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 90, 3));
                ItemStack _setstack = new ItemStack(BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get());
                _setstack.setCount(1);
                entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                if (!serverLevel.isClientSide() && serverLevel.getServer() != null)
                    serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("<" + entity.getDisplayName().getString() + ">" + " " + "\u89C9\u9192\u5427\uFF01\u6211\u7684\u6218\u795E\u4E4B\u5203\uFF01")), false);
                {
                    ItemStack _ist = entity.getMainHandItem();
                    if (_ist.hurt(_ist.getDamageValue(), RandomSource.create(), null)) {
                        _ist.shrink(1);
                        _ist.setDamageValue(0);
                    }
                }
                BuxinMod.queueServerWork(80, () -> {
                    if (entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get())) : false) {
                        if (entity instanceof Player _player) {
                            ItemStack _stktoremove = new ItemStack(BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get());
                            _player.getInventory()
                                    .clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
                        }
                        entity.getPersistentData().putBoolean("player_wake_up", false);
                        entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get()));
                        if (entity instanceof Player _player)
                            _player.getCooldowns().addCooldown(entity.getMainHandItem().getItem(), 195);
                    }
                });
            } else {
                if (!serverLevel.isClientSide() && serverLevel.getServer() != null)
                    serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("<" + entity.getDisplayName().getString() + ">" + " " + "\u662F\u4E0D\u662F\u5C11\u4E86\u70B9\u4EC0\u4E48\u4E1C\u897F\u5462?")), false);
            }
        }
    }
}
