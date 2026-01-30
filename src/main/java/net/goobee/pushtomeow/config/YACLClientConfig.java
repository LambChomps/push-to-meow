package net.goobee.pushtomeow.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.goobee.pushtomeow.CustomSound;
import net.goobee.pushtomeow.CustomSounds;
import net.goobee.pushtomeow.PushToMeow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class YACLClientConfig {
    public enum soundModifierType {
        DEFAULT,
        INVERTED,
        ALWAYS,
        NEVER
    }

    // Config File:
    public static final ConfigClassHandler<YACLClientConfig> HANDLER = ConfigClassHandler.createBuilder(YACLClientConfig.class)
            .id(new Identifier(PushToMeow.MOD_ID, "client_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("pushtomeow_client.json5"))
                    .setJson5(true)
                    .build())
            .build();

    // Config Entries:
    @SerialEntry (comment = "Don't touch !")
    public static int configVersion = 1;
    @SerialEntry (comment = "Meow played by the default keybind, overwritten if set by server!")
    public static CustomSound configDefaultSound = CustomSound.NORMAL;
    @SerialEntry (comment = "Invert whether modifier is pressed?")
    public static soundModifierType configModifyType = soundModifierType.DEFAULT;
    @SerialEntry (comment = "Default meow sound pitch (0.5-2.0)")
    public static float configPitchDefault = 1.0f;
    @SerialEntry (comment = "The base +-variance applied to the default sound pitch (0.0-0.5)")
    public static float configPitchVariance = 0.1f;
    @SerialEntry (comment = "Effect that player pitch angle has on meow sound pitch (0.0-1.0)")
    public static float configPitchPitchEffect = 0.25f;
    @SerialEntry (comment = "The +-angle while pitch effects are considered min (0-90)")
    public static int configPitchAngleDefault = 0;
    @SerialEntry (comment = "The +-angle after pitch effects are considered max (0-90)")
    public static int configPitchAngleMax = 75;

    private static final Identifier F1 = new Identifier(PushToMeow.MOD_ID, "textures/saint_pop1.png");
    private static final Identifier F2 = new Identifier(PushToMeow.MOD_ID, "textures/saint_pop2.png");
    private static Identifier fa = F1;

    // Config Menu:
    public static Screen config(Screen parentScreen) {
        HANDLER.load();
        YACLServerConfig.HANDLER.load();
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config.menu.pushtomeow.main"))
                // Client Config Menu:
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.category.pushtomeow.client"))
                        .tooltip(Text.translatable("config.category.tooltip.pushtomeow.client"))

                        // Version Label:
                        .option(LabelOption.create(Text.literal("Config Version: " + configVersion)))
                        // Meow Button:
                        .option(ButtonOption.createBuilder()
                                .name(Text.translatable("config.option.pushtomeow.meow_button"))
                                .description(OptionDescription.createBuilder()
                                        .image(fa, 0, 0, 256, 256, 256, 256)
                                        .text(Text.translatable("config.option.description.pushtomeow.meow_button"))
                                        .build())
                                .text(Text.empty())
                                .action((yaclScreen, buttonOption) -> {
                                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(
                                            CustomSounds.soundList.get(Random.create().nextInt(CustomSounds.soundList.size())), configPitchDefault, 1.0f));
                                    if (fa == F1) fa = F2; else fa = F1;
                                    HANDLER.save();
                                    Screen screen = config(parentScreen);
                                    MinecraftClient.getInstance().setScreen(screen);
                                })
                                .build())

                        // General Settings Group:
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.group.pushtomeow.general"))
                                // Meow Selection:
                                .option(Option.<CustomSound>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.meow_select"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.meow_select")))
                                        .binding(CustomSound.NORMAL, () -> configDefaultSound, newVal -> configDefaultSound = newVal)
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(CustomSound.class))
                                        .build())
                                // Invert Modify:
                                .option(Option.<soundModifierType>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.modifier_type"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.modifier_type")))
                                        .binding(soundModifierType.DEFAULT, () -> configModifyType, newVal -> configModifyType = newVal)
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(soundModifierType.class)
                                                .formatValue(value -> {
                                                    switch (value) {
                                                        case INVERTED -> {return Text.of("§7§l" + value);}
                                                        case ALWAYS -> {return Text.of("§a§l" + value);}
                                                        case NEVER -> {return Text.of("§c§l" + value);}
                                                        default -> {return Text.of("§f§l" + value);}
                                                    }
                                                }))
                                        .build())
                                .build())

                        // Pitch Settings Group:
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.group.pushtomeow.pitch"))
                                // Default Pitch:
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.pitch_default"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.pitch_default")))
                                        .binding(1.0f, () -> configPitchDefault, newVal -> configPitchDefault = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .range(0.5f, 2.0f)
                                                .step(0.01f)
                                                .formatValue(value -> Text.literal(String.format("%,.2f", value))))
                                        .build())
                                // Meow Pitch Variance:
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.pitch_variance"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.pitch_variance")))
                                        .binding(0.1f, () -> configPitchVariance, newVal -> configPitchVariance = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .range(0.0f, 0.5f)
                                                .step(0.01f)
                                                .formatValue(value -> Text.literal(String.format("%,.2f", value))))
                                        .build())
                                // Pitch Effect:
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.pitch_effect"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.pitch_effect")))
                                        .binding(0.25f, () -> configPitchPitchEffect, newVal -> configPitchPitchEffect = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .range(0.0f, 1.0f)
                                                .step(0.01f)
                                                .formatValue(value -> Text.literal(String.format("%,.2f", value))))
                                        .build())
                                // Pitch Default Angle:
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.pitch_angle_default"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.pitch_angle_default")))
                                        .binding(0, () -> configPitchAngleDefault, newVal -> configPitchAngleDefault = newVal)
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 90)
                                                .step(1)
                                                .formatValue(value -> Text.literal(String.format("%d°", value))))
                                        .build())
                                // Pitch Max Angle:
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.pitch_angle_max"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.pitch_angle_max")))
                                        .binding(75, () -> configPitchAngleMax, newVal -> configPitchAngleMax = newVal)
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 90)
                                                .step(1)
                                                .formatValue(value -> Text.literal(String.format("%d°", value))))
                                        .build())
                                .build())
                        .build())

                // Server Config Menu:
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.category.pushtomeow.server"))
                        .tooltip(Text.translatable("config.category.tooltip.pushtomeow.server"))
                        // Blacklist:
//                        .option(ListOption.<String>createBuilder()
//                                .name(Text.translatable("config.option.pushtomeow.blacklist"))
//                                .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.blacklist")))
//                                .binding(new ArrayList<>(Arrays.asList(YACLServerConfig.soundBlacklistArray)), () -> YACLServerConfig.soundBlacklist, newVal -> YACLServerConfig.soundBlacklist = newVal)
//                                .controller(StringControllerBuilder::create)
//                                .initial("pushtomeow:meow.")
//                                .build())

                        // Limits Group:
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.group.pushtomeow.server"))
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.cooldown"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.cooldown")))
                                        .binding(0.5f, () -> YACLServerConfig.cooldown, newVal -> YACLServerConfig.cooldown = newVal)
                                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                                .min(0.0f)
                                                .formatValue(value -> Text.literal(String.format("%,.2f", value))))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.volume"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.volume")))
                                        .binding(1.5f, () -> YACLServerConfig.volume, newVal -> YACLServerConfig.volume = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .range(0.0f, 10.0f)
                                                .step(0.1f)
                                                .formatValue(value -> Text.literal(String.format("%,.1f", value))))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.modified_volume_mul"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.modified_volume_mul")))
                                        .binding(0.75f, () -> YACLServerConfig.modifiedVolumeMul, newVal -> YACLServerConfig.modifiedVolumeMul = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .range(0.1f, 2.0f)
                                                .step(0.01f)
                                                .formatValue(value -> Text.literal(String.format("%,.2f", value))))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.min_pitch"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.min_pitch")))
                                        .binding(0.5f, () -> YACLServerConfig.minPitch, newVal -> YACLServerConfig.minPitch = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .range(0.5f, 2.0f)
                                                .step(0.01f)
                                                .formatValue(value -> Text.literal(String.format("%,.2f", value))))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("config.option.pushtomeow.max_pitch"))
                                        .description(OptionDescription.of(Text.translatable("config.option.description.pushtomeow.max_pitch")))
                                        .binding(2.0f, () -> YACLServerConfig.maxPitch, newVal -> YACLServerConfig.maxPitch = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .range(0.5f, 2.0f)
                                                .step(0.01f)
                                                .formatValue(value -> Text.literal(String.format("%,.2f", value))))
                                        .build())
                                .build())
                        .build())
                .save(HANDLER::save)
                .build()
                .generateScreen(parentScreen);
    }

}
