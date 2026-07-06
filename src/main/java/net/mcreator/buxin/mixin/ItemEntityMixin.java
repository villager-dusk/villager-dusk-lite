
package net.mcreator.buxin.mixin;

import net.mcreator.buxin.config.common.ItemRemoveTimeConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void remove(CallbackInfo ci){
        ItemEntity entity = (ItemEntity) (Object)this;
        if(entity.isAlive() && !entity.level().isClientSide) {
            int time = ItemRemoveTimeConfig.ItemRemoveTime.get();
            int glowTime = time * 3/4;
            if (entity.tickCount >= time) {
                entity.discard();
            }
        }
    }
}
