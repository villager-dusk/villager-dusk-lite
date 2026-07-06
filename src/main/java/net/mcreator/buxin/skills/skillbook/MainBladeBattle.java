package net.mcreator.buxin.skills.skillbook;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.SwordBattleConfig;
import net.mcreator.buxin.init.BuxinModGameRules;
import net.mcreator.buxin.init.BuxinModSounds;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainBladeBattle extends PassiveSkill {
    private static final UUID EVENT_UUID = UUID.fromString("b422f7a0-f378-3344-1111-0252ac131003");
    private static final WeaponCategory[] CAN_BE_USED_WEAPONS;
    private float speedBonus;

    public MainBladeBattle(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        this.speedBonus = parameters.getFloat("speed_bonus");
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID, (event) -> {
            WeaponCategory heldWeaponCategory = event.getItemCapability().getWeaponCategory();
            for (WeaponCategory weaponCategory : CAN_BE_USED_WEAPONS) {
                if (weaponCategory == heldWeaponCategory) {
                    float attackSpeed = event.getAttackSpeed();
                    event.setAttackSpeed(attackSpeed * (1.0F + this.speedBonus * 0.01F));
                    break;
                }
            }

        });
        container.getExecuter().getEventListener().addEventListener(EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            PlayerPatch<?> playerpatch = event.getPlayerPatch();
            ServerPlayer player = (ServerPlayer)((ServerPlayerPatch)event.getPlayerPatch()).getOriginal();
            DamageSource damageSource = event.getDamageSource();
            DynamicAnimation animation = playerpatch.getAnimator().getPlayerFor(null).getAnimation();
            float damage = event.getAmount();
            if (true) {
                boolean isFront = false;
                Vec3 sourceLocation = damageSource.getSourcePosition();
                if (sourceLocation != null) {
                    Vec3 viewVector = player.getViewVector(1.0F);
                    Vec3 toSourceLocation = sourceLocation.subtract(player.position()).normalize();
                    if (toSourceLocation.dot(viewVector) > 0.0) {
                        isFront = true;
                    }
                }

                CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapability(player.getMainHandItem());
                WeaponCategory weaponCategory = capabilityItem.getWeaponCategory();
                List<AnimationProvider<?>> autoAnimations = capabilityItem.getAutoAttckMotion(event.getPlayerPatch());
                List<WeaponCategory> weaponCategoriesList = Arrays.asList(CAN_BE_USED_WEAPONS);
                if (isFront) {
                    if (!weaponCategoriesList.contains(weaponCategory)) {
                        return;
                    }
                    if (autoAnimations.contains(animation)) {
                        event.setCanceled(true);
                        event.setResult(AttackResult.ResultType.BLOCKED);
                        Player playerEntity = (Player) event.getPlayerPatch().getOriginal();
                        Entity entity = damageSource.getEntity();
                        if (entity != null && entity.getPersistentData().getDouble("battle") > SwordBattleConfig.SwordBattleNeutralizeValue.get()) {
                            for (int i = 0; i < 3; i++) {
                                BuxinMod.queueServerWork(i * 2, () -> {
                                    Method_114514.entity_use_command(entity, "/particle buxin:huohua ^ ^1.5 ^ 0 0 0 0.11" + " " + entity.level().getLevelData().getGameRules().getInt(BuxinModGameRules.SPARK_NUMBER));
                                });
                            }
                            Vec3 knockbackVec = entity.getLookAngle().normalize().scale(1.0).add(0, ((LivingEntity) entity).getSpeed(), 0);
                            playerEntity.setDeltaMovement(knockbackVec);
                            entity.getPersistentData().putDouble("battle", 0);
                            try {
                                AnimationPlayer.playAnimation(entity, Animations.BIPED_COMMON_NEUTRALIZED);
                                Method_114514.play_sound(entity, "buxin:hit");
                                Method_114514.entity_use_command(entity, "/playsound epicfight:sfx.neutralize_mobs voice @s");
                            } catch (Exception e) {
                            }
                        }
                        if (playerEntity != null && playerEntity.getPersistentData().getDouble("battle") > SwordBattleConfig.SwordBattleNeutralizeValue.get()) {
                            Vec3 knockbackVec = playerEntity.getLookAngle().normalize().scale(1.0).add(0, ((LivingEntity) playerEntity).getSpeed(), 0);
                            if (entity != null) {
                                entity.setDeltaMovement(knockbackVec);
                            }
                            playerEntity.getPersistentData().putDouble("battle", 0);
                            try {
                                AnimationPlayer.playAnimation(playerEntity, Animations.BIPED_COMMON_NEUTRALIZED);
                                Method_114514.entity_use_command(playerEntity, "/playsound epicfight:sfx.neutralize_mobs voice @s");
                                Method_114514.play_sound(playerEntity, "buxin:hit");
                            } catch (Exception e) {
                            }
                        }
                        if (entity != null) {
                            entity.getPersistentData().putDouble("battle", entity.getPersistentData().getDouble("battle") + 1);
                        }

                        if (playerEntity != null) {
                            playerEntity.getPersistentData().putDouble("battle", playerEntity.getPersistentData().getDouble("battle") + 1);
                            double x = playerEntity.getX();
                            double y = playerEntity.getY();
                            double z = playerEntity.getZ();
                            Level world = playerEntity.level();
                            if (Math.random() > 0.5) {
                                if (!world.isClientSide()) {
                                    world.playSound(null, new BlockPos((int)x, (int)(y + 1.3), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("epicfight:entity.hit.clash")), SoundSource.NEUTRAL, 1, 0.8f + new Random().nextFloat(0.2f));
                                } else {
                                    world.playLocalSound(x, (y + 1.3), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("epicfight:entity.hit.clash")), SoundSource.VOICE, 1, 0.6f + new Random().nextFloat(0.25f), false);
                                }
                            } else {
                                if (!world.isClientSide()) {
                                    world.playSound(null, new BlockPos((int)x, (int)(y + 1.3), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("epicfight:entity.hit.clash")), SoundSource.NEUTRAL, 1, 0.8f + new Random().nextFloat(0.2f));
                                } else {
                                    world.playLocalSound(x, (y + 1.3), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("epicfight:entity.hit.clash")), SoundSource.VOICE, 1, 0.8f + new Random().nextFloat(0.25f), false);
                                }
                            }
                        }
                        Method_114514.entity_use_command(entity, "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
                        Method_114514.entity_use_command(entity, "/particle buxin:huohua ^ ^1.5 ^ 0 0 0 0.11" + " " + entity.level().getLevelData().getGameRules().getInt(BuxinModGameRules.SPARK_NUMBER));
                        if (playerEntity != null) {
                            playerEntity.playSound(BuxinModSounds.SEKIRO_BLOCK.get(), 1f, 0.8f + new Random().nextFloat(0.3F));
                        }
                        ItemStack mainHandItem = ((Player)playerpatch.getOriginal()).getMainHandItem();
                        if (damageSource.getDirectEntity() instanceof AbstractArrow) {
                            Entity directEntity = damageSource.getDirectEntity();
                            directEntity.remove(Entity.RemovalReason.KILLED);
                        }

                        if (mainHandItem.isDamageableItem()) {
                            int currentDamage = mainHandItem.getDamageValue();
                            int maxDamage = mainHandItem.getMaxDamage();
                            int newDamage = (int)((float)currentDamage + damage);
                            mainHandItem.setDamageValue(Math.min(newDamage, maxDamage));
                        }
                    }
                }

            }
        });
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID);
    }

    @OnlyIn(Dist.CLIENT)
    public List<Object> getTooltipArgsOfScreen(List<Object> list) {
        list.add(String.format("%.0f", this.speedBonus));
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < CAN_BE_USED_WEAPONS.length; ++i) {
            sb.append(WeaponCategory.ENUM_MANAGER.toTranslated(CAN_BE_USED_WEAPONS[i]));
            if (i < CAN_BE_USED_WEAPONS.length - 1) {
                sb.append(", ");
            }
        }

        list.add(sb.toString());
        return list;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        return true;
    }

    static {
        CAN_BE_USED_WEAPONS = new WeaponCategory[]{CapabilityItem.WeaponCategories.UCHIGATANA, CapabilityItem.WeaponCategories.LONGSWORD, CapabilityItem.WeaponCategories.SWORD, CapabilityItem.WeaponCategories.TACHI, CapabilityItem.WeaponCategories.GREATSWORD};
    }
}