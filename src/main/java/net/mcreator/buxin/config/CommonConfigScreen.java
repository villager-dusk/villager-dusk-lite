package net.mcreator.buxin.config;

import net.mcreator.buxin.config.client.BrightValueConfig;
import net.mcreator.buxin.config.client.ClientGuiConfig;
import net.mcreator.buxin.config.client.RenderCapeConfig;
import net.mcreator.buxin.config.common.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class CommonConfigScreen extends Screen {
    private final Screen parent;
    private ConfigList list;
    private int EntityDodgePercentage;
    private int GuardShakeTimeValue;
    private int GuardShakeAmplitudeValue;
    private int GuardShakeFrequencyValue;
    private int SwordBattleNeutralizeValue;
    private int BrightValue;
    private int SnakeBladeMaxTarget;
    private int SnakeBladeMaxRange;
    private int SnakeBladeSmoothValue;
    private boolean FakePlayerJoinMessage;
    private boolean EnableClientGui;
    private boolean VFXParticle;
    private double RunningSpeedMultiple;
    private boolean RenderCape;
    private int ItemRemoveTime;
    public CommonConfigScreen(Screen parent) {
        super(Component.translatable("ui.buxin.title"));
        this.parent = parent;
        this.SwordBattleNeutralizeValue = SwordBattleConfig.SwordBattleNeutralizeValue.get();
        this.FakePlayerJoinMessage = FakePlayerJoinMessageConfig.FakePlayerJoinMessage.get();
        this.EnableClientGui = ClientGuiConfig.EnableTimeGui.get();
        this.EntityDodgePercentage = DodgePercentConfig.EntityDodgePercentage.get();
        this.GuardShakeTimeValue = GuardShakeValueConfig.GuardShakeTimeValue.get();
        this.GuardShakeAmplitudeValue = GuardShakeValueConfig.GuardShakeAmplitudeValue.get();
        this.GuardShakeFrequencyValue = GuardShakeValueConfig.GuardShakeFrequencyValue.get();
        this.SnakeBladeMaxRange = SnakeBladeConfig.SnakeBladeMaxRange.get();
        this.SnakeBladeMaxTarget = SnakeBladeConfig.SnakeBladeMaxTarget.get();
        this.SnakeBladeSmoothValue = SnakeBladeConfig.SnakeBladeSmoothValue.get();
        this.BrightValue = BrightValueConfig.BrightValue.get();
        this.VFXParticle = VFXParticleConfig.VFXParticleConfig.get();
        this.RenderCape = RenderCapeConfig.RenderCape.get();
        this.RunningSpeedMultiple = RunningSpeedMultipleConfig.RunningSpeedMultiple.get();
        this.ItemRemoveTime = ItemRemoveTimeConfig.ItemRemoveTime.get();
    }

    @Override
    protected void init() {
        super.init();
        this.list = new ConfigList(
                this.minecraft,
                this.width,
                this.height,
                32,
                this.height - 32,
                25
        );
        this.addRenderableWidget(this.list);

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.ItemRemoveTime"), () -> {
            return this.ItemRemoveTime;
        }, 100, 1200, (value) -> {
            this.ItemRemoveTime = value;
        }));

        double min = 1.0d;
        BigDecimal bd = new BigDecimal(min);
        double result = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();

        double max = 10.0d;
        BigDecimal bd2 = new BigDecimal(max);
        double result2 = bd2.setScale(2, RoundingMode.HALF_UP).doubleValue();

        this.list.addEntry(new SliderOptionEntryDouble(Component.translatable("ui.buxin.switch_render.RunningSpeedMultiple"), () -> {
            return this.RunningSpeedMultiple;
        }, result, result2, (value) -> {
            double f = value;
            BigDecimal bd3 = new BigDecimal(f);
            this.RunningSpeedMultiple = bd3.setScale(2, RoundingMode.HALF_UP).doubleValue();
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.SnakeBladeMaxRange"), () -> {
            return this.SnakeBladeMaxRange;
        }, 2, 100, (value) -> {
            this.SnakeBladeMaxRange = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.SnakeBladeMaxTarget"), () -> {
            return this.SnakeBladeMaxTarget;
        }, 1, 100, (value) -> {
            this.SnakeBladeMaxTarget = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.SnakeBladeSmoothValue"), () -> {
            return this.SnakeBladeSmoothValue;
        }, 0, 100, (value) -> {
            this.SnakeBladeSmoothValue = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.EntityDodgePercentage"), () -> {
            return this.EntityDodgePercentage;
        }, 0, 100, (value) -> {
            this.EntityDodgePercentage = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.BrightValue"), () -> {
            return this.BrightValue;
        }, 0, 15728880, (value) -> {
            this.BrightValue = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.SwordBattleNeutralizeValue"), () -> {
            return this.SwordBattleNeutralizeValue;
        }, 1, 100, (value) -> {
            this.SwordBattleNeutralizeValue = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.GuardShakeTimeValue"), () -> {
            return this.GuardShakeTimeValue;
        }, 0, 400, (value) -> {
            this.GuardShakeTimeValue = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.GuardShakeFrequencyValue"), () -> {
            return this.GuardShakeFrequencyValue;
        }, 0, 10, (value) -> {
            this.GuardShakeFrequencyValue = value;
        }));

        this.list.addEntry(new SliderOptionEntry(Component.translatable("ui.buxin.switch_render.GuardShakeAmplitudeValue"), () -> {
            return this.GuardShakeAmplitudeValue;
        }, 0, 10, (value) -> {
            this.GuardShakeAmplitudeValue = value;
        }));

        this.list.addEntry(new BooleanOptionEntry(Component.translatable("ui.buxin.switch_render.FakePlayerJoinMessage"), () -> {
            return this.FakePlayerJoinMessage;
        }, (value) -> {
            this.FakePlayerJoinMessage = value;
        }));

        this.list.addEntry(new BooleanOptionEntry(Component.translatable("ui.buxin.switch_render.RenderCapeConfig"), () -> {
            return this.RenderCape;
        }, (value) -> {
            this.RenderCape = value;
        }));

        this.list.addEntry(new BooleanOptionEntry(Component.translatable("ui.buxin.switch_render.EnableClientGui"), () -> {
            return this.EnableClientGui;
        }, (value) -> {
            this.EnableClientGui = value;
        }));

        this.list.addEntry(new BooleanOptionEntry(Component.translatable("ui.buxin.switch_render.VFXParticleConfig"), () -> {
            return this.VFXParticle;
        }, (value) -> {
            this.VFXParticle = value;
        }));

        this.addRenderableWidget(Button.builder(
                Component.translatable("buxin.done"),
                button -> {
                    DodgePercentConfig.EntityDodgePercentage.set(EntityDodgePercentage);
                    BrightValueConfig.BrightValue.set(BrightValue);
                    GuardShakeValueConfig.GuardShakeFrequencyValue.set(GuardShakeFrequencyValue);
                    GuardShakeValueConfig.GuardShakeTimeValue.set(GuardShakeTimeValue);
                    GuardShakeValueConfig.GuardShakeAmplitudeValue.set(GuardShakeAmplitudeValue);
                    SwordBattleConfig.SwordBattleNeutralizeValue.set(SwordBattleNeutralizeValue);
                    FakePlayerJoinMessageConfig.FakePlayerJoinMessage.set(FakePlayerJoinMessage);
                    ClientGuiConfig.EnableTimeGui.set(EnableClientGui);
                    VFXParticleConfig.VFXParticleConfig.set(VFXParticle);
                    SnakeBladeConfig.SnakeBladeMaxTarget.set(SnakeBladeMaxTarget);
                    SnakeBladeConfig.SnakeBladeMaxRange.set(SnakeBladeMaxRange);
                    SnakeBladeConfig.SnakeBladeSmoothValue.set(SnakeBladeSmoothValue);
                    RunningSpeedMultipleConfig.RunningSpeedMultiple.set(RunningSpeedMultiple);
                    RenderCapeConfig.RenderCape.set(RenderCape);
                    ItemRemoveTimeConfig.ItemRemoveTime.set(ItemRemoveTime);
                    ItemRemoveTimeConfig.saveConfig();
                    RenderCapeConfig.saveConfig();
                    SnakeBladeConfig.saveConfig();
                    ClientGuiConfig.saveConfig();
                    BrightValueConfig.saveConfig();
                    DodgePercentConfig.saveConfig();
                    FakePlayerJoinMessageConfig.saveConfig();
                    GuardShakeValueConfig.saveConfig();
                    SwordBattleConfig.saveConfig();
                    VFXParticleConfig.saveConfig();
                    RunningSpeedMultipleConfig.saveConfig();
                    if (this.minecraft != null) {
                        this.minecraft.setScreen(parent);
                    }
                }
        ).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.translatable("buxin.reset"),
                button -> {
                    DodgePercentConfig.EntityDodgePercentage.set(DodgePercentConfig.EntityDodgePercentage.getDefault());
                    BrightValueConfig.BrightValue.set(BrightValueConfig.BrightValue.getDefault());
                    GuardShakeValueConfig.GuardShakeFrequencyValue.set(GuardShakeValueConfig.GuardShakeFrequencyValue.getDefault());
                    GuardShakeValueConfig.GuardShakeTimeValue.set(GuardShakeValueConfig.GuardShakeTimeValue.getDefault());
                    GuardShakeValueConfig.GuardShakeAmplitudeValue.set(GuardShakeValueConfig.GuardShakeAmplitudeValue.getDefault());
                    SwordBattleConfig.SwordBattleNeutralizeValue.set(SwordBattleConfig.SwordBattleNeutralizeValue.getDefault());
                    FakePlayerJoinMessageConfig.FakePlayerJoinMessage.set(FakePlayerJoinMessageConfig.FakePlayerJoinMessage.getDefault());
                    ClientGuiConfig.EnableTimeGui.set(ClientGuiConfig.EnableTimeGui.getDefault());
                    VFXParticleConfig.VFXParticleConfig.set(VFXParticleConfig.VFXParticleConfig.getDefault());
                    SnakeBladeConfig.SnakeBladeMaxTarget.set(SnakeBladeConfig.SnakeBladeMaxTarget.getDefault());
                    SnakeBladeConfig.SnakeBladeMaxRange.set(SnakeBladeConfig.SnakeBladeMaxRange.getDefault());
                    SnakeBladeConfig.SnakeBladeSmoothValue.set(SnakeBladeConfig.SnakeBladeSmoothValue.getDefault());
                    RunningSpeedMultipleConfig.RunningSpeedMultiple.set(RunningSpeedMultipleConfig.RunningSpeedMultiple.getDefault());
                    ItemRemoveTimeConfig.ItemRemoveTime.set(ItemRemoveTimeConfig.ItemRemoveTime.getDefault());
                    RenderCapeConfig.RenderCape.set(RenderCapeConfig.RenderCape.getDefault());
                    ItemRemoveTimeConfig.saveConfig();
                    RenderCapeConfig.saveConfig();
                    SnakeBladeConfig.saveConfig();
                    ClientGuiConfig.saveConfig();
                    BrightValueConfig.saveConfig();
                    DodgePercentConfig.saveConfig();
                    FakePlayerJoinMessageConfig.saveConfig();
                    GuardShakeValueConfig.saveConfig();
                    SwordBattleConfig.saveConfig();
                    VFXParticleConfig.saveConfig();
                    RunningSpeedMultipleConfig.saveConfig();
                    if (this.minecraft != null) {
                        this.minecraft.setScreen(parent);
                    }
                }
        ).bounds(this.width - 10 - 100, this.height - 27, 100, 20).build());
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        this.list.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawString(this.font, this.title, this.width / 2 - this.title.getString().length() * 2, 15, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(parent);
        }
    }

    private static class ConfigList extends ObjectSelectionList<ConfigList.Entry> {
        public ConfigList(Minecraft minecraft, int width, int height, int top, int bottom, int itemHeight) {
            super(minecraft, width, height, top, bottom, itemHeight);
            this.setRenderBackground(false);
            this.setRenderTopAndBottom(false);
        }

        public int addEntry(@NotNull Entry entry) {
            super.addEntry(entry);
            return 0;
        }

        public static abstract class Entry extends ObjectSelectionList.Entry<Entry> {
            public abstract void render(
                    @NotNull GuiGraphics guiGraphics,
                    int index, int top,
                    int left, int width, int height,
                    int mouseX, int mouseY, boolean isMouseOver,
                    float partialTicks);
        }
    }
    private class BooleanOptionEntry extends CommonConfigScreen.ConfigList.Entry {
        private final Component label;
        private final Button button;
        private final MutableComponent on = Component.translatable("ui.on");
        private final MutableComponent off = Component.translatable("ui.off");
        public BooleanOptionEntry(Component label, BooleanSupplier getter, Consumer<Boolean> setter) {
            this.label = label;
            this.button = Button.builder(Component.literal(getter.getAsBoolean() ? on.getString() : off.getString()), (button) -> {
                boolean newValue = !getter.getAsBoolean();
                setter.accept(newValue);
                button.setMessage(Component.literal(newValue ? on.getString() : off.getString()));
            }).bounds(0, 0, 100, 20).build();
        }

        public void render(@NotNull GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
            guiGraphics.drawString(CommonConfigScreen.this.font, this.label, left, top + (height - 8) / 2, 16777215);
            this.button.setX(left + width - 110);
            this.button.setY(top);
            this.button.setWidth(100);
            this.button.render(guiGraphics, mouseX, mouseY, partialTicks);
        }

        public @NotNull List<? extends GuiEventListener> children() {
            return List.of(this.button);
        }

        public @NotNull List<? extends NarratableEntry> narratables() {
            return List.of(this.button);
        }

        @Override
        public Component getNarration() {
            return null;
        }
    }
    private class SliderOptionEntry extends ConfigList.Entry {
        private final Component label;
        private final AbstractSliderButton slider;
        public SliderOptionEntry(Component label, IntSupplier getter, int min, int max, Consumer<Integer> setter) {
            this.label = label;
            int initialValue = getter.getAsInt();
            double sliderValue = (double)(initialValue - min) / (max - min);
            this.slider = new AbstractSliderButton(0, 0, 100, 20, Component.literal(""), sliderValue) {
                @Override
                public void updateMessage() {
                    int actualValue = (int) (this.value * (max - min) + min);
                    this.setMessage(Component.literal(String.valueOf(actualValue)));
                }
                @Override
                protected void applyValue() {
                    int value = (int) (this.value * (max - min) + min);
                    setter.accept(value);
                }
            };
            this.slider.updateMessage();
        }

        @Override
        public void render(
                @NotNull GuiGraphics guiGraphics,
                int index, int top,
                int left, int width, int height,
                int mouseX, int mouseY, boolean isMouseOver,
                float partialTicks) {

            guiGraphics.drawString(
                    font,
                    label,
                    left,
                    top + (height - 8) / 2,
                    0xFFFFFF
            );
            slider.setX(left + width - 110);
            slider.setY(top);
            slider.setWidth(100);
            slider.render(guiGraphics, mouseX, mouseY, partialTicks);
        }

        @Override
        public Component getNarration() {
            return null;
        }
    }
    private class SliderOptionEntryDouble extends ConfigList.Entry {
        private final Component label;
        private final AbstractSliderButton slider;
        public SliderOptionEntryDouble(Component label, DoubleSupplier getter, double min, double max, Consumer<Double> setter) {
            this.label = label;
            double initialValue = getter.getAsDouble();
            double sliderValue = (initialValue - min) / (max - min);
            this.slider = new AbstractSliderButton(0, 0, 100, 20, Component.literal(""), sliderValue) {
                @Override
                public void updateMessage() {
                    double actualValue = (this.value * (max - min) + min);
                    BigDecimal bd2 = new BigDecimal(actualValue);
                    double result2 = bd2.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    this.setMessage(Component.literal(String.valueOf(result2)));
                }
                @Override
                protected void applyValue() {
                    double value = (this.value * (max - min) + min);
                    setter.accept(value);
                }
            };
            this.slider.updateMessage();
        }

        @Override
        public void render(
                @NotNull GuiGraphics guiGraphics,
                int index, int top,
                int left, int width, int height,
                int mouseX, int mouseY, boolean isMouseOver,
                float partialTicks) {

            guiGraphics.drawString(
                    font,
                    label,
                    left,
                    top + (height - 8) / 2,
                    0xFFFFFF
            );
            slider.setX(left + width - 110);
            slider.setY(top);
            slider.setWidth(100);
            slider.render(guiGraphics, mouseX, mouseY, partialTicks);
        }

        @Override
        public Component getNarration() {
            return null;
        }
    }
}