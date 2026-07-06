
package net.mcreator.buxin.capabilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class GloveCapability extends WeaponCapability {
    protected GloveCapability(CapabilityItem.Builder builder) {
        super(builder);
    }

    public boolean checkOffhandValid(LivingEntityPatch<?> entitypatch) {
        LivingEntity originalEntity = (LivingEntity) entitypatch.getOriginal();
        ItemStack offhandItem = originalEntity.getOffhandItem();
        CapabilityItem itemCap = EpicFightCapabilities.getItemStackCapability(offhandItem);
        boolean isFist = itemCap.getWeaponCategory() == WeaponCategories.FIST;
        return isFist || !(offhandItem.getItem() instanceof SwordItem) && !(offhandItem.getItem() instanceof DiggerItem);
    }

    public boolean canHoldInOffhandAlone() {
        return true;
    }
}
