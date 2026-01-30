package net.goobee.pushtomeow.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClothClientConfig {
    private static final ConfigBuilder BUILDER = ConfigBuilder.create().setTitle(Text.translatable("config.title.pushtomeow.meow"));
    private static final ConfigEntryBuilder ENTRY_BUILDER = BUILDER.entryBuilder();
    private static final ConfigCategory MEOW = BUILDER.getOrCreateCategory(Text.translatable("config.category.pushtomeow.meow"));
//    private static final ConfigCategory MEOW_EXTENDED = BUILDER.getOrCreateCategory(Text.translatable("config.category.pushtomeow.meow_extendedKeybinds"));

    public static boolean configInvertModify = false;

    public static int configPitchDefault = 100;
    public static int configPitchPitchEffect = 100;
    public static int configPitchAngleDefault = 0;
    public static int configPitchAngleMax = 75;

    static {
        // Settings:
        MEOW.addEntry(ENTRY_BUILDER.startBooleanToggle(Text.translatable("config.entry.pushtomeow.invertModify"), configInvertModify)
                .setTooltip(Text.translatable("config.tooltip.pushtomeow.invertModify"))
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> configInvertModify = newValue)
                .build());
        MEOW.addEntry(ENTRY_BUILDER.startIntSlider(Text.translatable("config.entry.pushtomeow.pitchDefault"), configPitchDefault, 50, 200)
                .setTooltip(Text.translatable("config.tooltip.pushtomeow.pitchDefault"))
                .setDefaultValue(100)
                .setSaveConsumer(newValue -> configPitchDefault = newValue)
                .build());
        MEOW.addEntry(ENTRY_BUILDER.startIntSlider(Text.translatable("config.entry.pushtomeow.pitchPitchEffect"), configPitchPitchEffect, 0, 200)
                .setTooltip(Text.translatable("config.tooltip.pushtomeow.pitchPitchEffect"))
                .setDefaultValue(100)
                .setSaveConsumer(newValue -> configPitchPitchEffect = newValue)
                .build());
        MEOW.addEntry(ENTRY_BUILDER.startIntSlider(Text.translatable("config.entry.pushtomeow.pitchAngleDefault"), configPitchAngleDefault, 0, 90)
                .setTooltip(Text.translatable("config.tooltip.pushtomeow.pitchAngleDefault"))
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> configPitchAngleDefault = newValue)
                .build());
        MEOW.addEntry(ENTRY_BUILDER.startIntSlider(Text.translatable("config.entry.pushtomeow.pitchAngleMax"), configPitchAngleMax, 0, 90)
                .setTooltip(Text.translatable("config.tooltip.pushtomeow.pitchAngleMax"))
                .setDefaultValue(75)
                .setSaveConsumer(newValue -> configPitchAngleMax = newValue)
                .build());

        // Extended Keybinds: (Hiding these from vanilla keybind menu was out of this project's scope :<)
//        MEOW_EXTENDED.addEntry(ENTRY_BUILDER.startBooleanToggle(Text.translatable("config.entry.pushtomeow.extended.registerExtendedKeybinds"), PushToMeowClient.registerExtendedKeybinds)
//                .setTooltip(Text.translatable("config.tooltip.pushtomeow.extended.registerExtendedKeybinds"))
//                .setDefaultValue(true)
//                .setSaveConsumer(newValue -> PushToMeowClient.registerExtendedKeybinds = newValue)
//                .build());
//        MEOW_EXTENDED.addEntry(ENTRY_BUILDER.fillKeybindingField(Text.translatable("key.pushtomeow.extended.meow_coarse"), CustomKeybindsExtended.EXTENDED_MEOW_COARSE)
//                .build());
    }

    public static Screen clientConfigScreen = BUILDER.build();
}
