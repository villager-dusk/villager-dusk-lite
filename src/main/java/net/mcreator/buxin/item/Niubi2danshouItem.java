
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.buxin.color.BuxinWeaponColor;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.HuoQbjDangShiTiBeiGaiWuPinJiZhongProcedure;
import net.mcreator.buxin.procedures.Niubi2DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class Niubi2danshouItem extends CustomEnchantableItem {
    public @NotNull Component getName(@NotNull ItemStack stack) {
        String currentLanguage = Minecraft.getInstance().options.languageCode;
        return Component.literal("§bLegendary Sword§d");
    }

    public Niubi2danshouItem() {
        super(new CustomEnchantableItem.Properties());
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(this);
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 9999;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 32d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        Minecraft mc = Minecraft.getInstance();
        String currentLanguage = mc.options.languageCode;
        Player player = mc.player;
        ItemStack mainItem = BuxinModItems.NIUBI_2DANSHOU.get().getDefaultInstance();
        if (!player.getCooldowns().isOnCooldown(mainItem.getItem())) {
            String wx = "无限";
            if ("zh_cn".equals(currentLanguage)) {
                list.add(Component.literal((BuxinWeaponColor.makeColour2("+" + wx +" 点攻击伤害"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("锋利 X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("重创 VIII"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("击退 IX"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("耐久 VII"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("自我修补 V"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("能量 IV"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("重型攻击 V"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("箭矢偏转 VII"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("保护 X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("快速出鞘 X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("占用 X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("神圣祝福 X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("战斗经验 3230"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("学习 547"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("未知 ###"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("错误 785A"))));
            } else {
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Sharpness X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Smite VIII"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Knockback IX"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Unbreaking VII"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Mending V"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Energy IV"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("HeavyAttack V"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("ArrowDeflection VII"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Protection X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("QuickDraw X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Possession X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("HolyBlessing X"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Experience 3230"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("Learning 547"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("UNKNOWN ###"))));
                list.add(Component.literal((BuxinWeaponColor.makeColour2("ERROR 785A"))));
            }
        }
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal((BuxinWeaponColor.makeColour2("The Blade of War is an elegantly crafted longsword with a deep silver blade that glows faintly blue. Its hilt is engraved with magical runes, and the crossguard holds a radiant gem—the source of its power.In battle, it can cut through anything, from rock to enemy, with ease. It also unleashes destructive energy waves and allows the wielder to fight beyond normal limits. When paired with Woopie, it unlocks a heavy strike form."))));
    }

    public static void breakArmor5PercentPlus7(LivingEntity entity) {
        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : armorSlots) {
            ItemStack armor = entity.getItemBySlot(slot);
            if (!armor.isEmpty() && armor.getItem() instanceof ArmorItem) {
                int maxDurability = armor.getMaxDamage();
                int damage = (int) (maxDurability * 0.025 + 5 + new Random().nextInt(5));
                armor.hurtAndBreak(damage, entity, e -> {});
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        if(Method_114514.isWearingAnyArmor(entity)) {
            breakArmor5PercentPlus7(entity);
            entity.playSound(SoundEvents.GLASS_BREAK, 6.75f, 0.9f + new Random().nextFloat(0.15f));
            Random random = new Random();
            int a = random.nextInt(4);
            if(a == 0) {
                if(!entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                    int maxDurability = entity.getItemBySlot(EquipmentSlot.CHEST).getMaxDamage();
                    Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(entity.getItemBySlot(EquipmentSlot.CHEST).getItem()) + " ~ ~1. ~ 0 0 0 0.15 60");
                }
            } else if(a == 1) {
                if(!entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                    int maxDurability = entity.getItemBySlot(EquipmentSlot.LEGS).getMaxDamage();
                    Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(entity.getItemBySlot(EquipmentSlot.LEGS).getItem()) + " ~ ~0.55 ~ 0 0 0 0.15 60");
                }
            } else if(a == 2) {
                if(!entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                    int maxDurability = entity.getItemBySlot(EquipmentSlot.HEAD).getMaxDamage();
                    Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(entity.getItemBySlot(EquipmentSlot.HEAD).getItem()) + " ~ ~1.45 ~ 0 0 0 0.15 60");
                }
            } else {
                if(!entity.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                    int maxDurability = entity.getItemBySlot(EquipmentSlot.FEET).getMaxDamage();
                    Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(entity.getItemBySlot(EquipmentSlot.FEET).getItem()) + " ~ ~0.25 ~ 0 0 0 0.15 60");
                }
            }
        } else if(!entity.getMainHandItem().isEmpty()){
            entity.getMainHandItem().hurtAndBreak(20 + new Random().nextInt(10),entity,e -> {});
            entity.playSound(SoundEvents.GLASS_BREAK, 6.75f, 0.9f + new Random().nextFloat(0.15f));
            Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem()) + " ~ ~1. ~ 0 0 0 0.15 60");
        } else if(!entity.getOffhandItem().isEmpty()){
            entity.getOffhandItem().hurtAndBreak(20 + new Random().nextInt(10),entity,e -> {});
            entity.playSound(SoundEvents.GLASS_BREAK, 6.75f, 0.9f + new Random().nextFloat(0.15f));
            Method_114514.entity_use_command(entity, "/particle minecraft:item" + " " + ForgeRegistries.ITEMS.getKey(entity.getItemBySlot(EquipmentSlot.OFFHAND).getItem()) + " ~ ~1. ~ 0 0 0 0.15 60");
        } else {
            entity.hurt(new DamageSource(entity.damageSources().mobAttack(sourceentity).typeHolder()), (float) (sourceentity.getMaxHealth() * 0.2 + 4));
        }
        return retval;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ItemStack itemstack = ar.getObject();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        return ar;
    }
}
