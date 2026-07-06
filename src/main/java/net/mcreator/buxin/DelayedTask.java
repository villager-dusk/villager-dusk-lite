
package net.mcreator.buxin;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public abstract class DelayedTask {
    private int ticks = 0;
    private final int waitTicks;

    public DelayedTask(int waitTicks) {
        this.waitTicks = waitTicks;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        // 此方法需在实际使用时通过静态列表管理DelayedTask实例，并调用每个实例的tick方法
        // 由于DelayedTask是抽象类且无法直接访问实例，实际使用时建议改用具体实现类或静态任务管理器
    }

    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ++this.ticks;
            if (this.ticks >= this.waitTicks) {
                this.run();
                this.ticks = 0;
            }
        }
    }

    public abstract void run();
}
