package net.goobee.pushtomeow;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;

public enum CustomSound implements NameableEnum, StringIdentifiable {
    COARSE(CustomSounds.MEOW_COARSE),
    FAT(CustomSounds.MEOW_FAT),
    NORMAL(CustomSounds.MEOW_NORMAL),
    PUP(CustomSounds.MEOW_PUP),
    RIVULET_A(CustomSounds.MEOW_RIVULET_A),
    RIVULET_B(CustomSounds.MEOW_RIVULET_B),
    SOFANTHIEL(CustomSounds.MEOW_SOFANTHIEL),
    SPEAR(CustomSounds.MEOW_SPEAR),
    WATCHER(CustomSounds.MEOW_WATCHER),
    WHISPERY(CustomSounds.MEOW_WHISPERY),
    ALTINV_ANDREW(CustomSounds.MEOW_ALTINV_ANDREW),
    ALTINV_INV(CustomSounds.MEOW_ALTINV_INV);

    private final SoundEvent soundEvent;

    CustomSound(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("customsound." + PushToMeow.MOD_ID + "." + name().toLowerCase());
    }

    @Override
    public String asString() {
        return "customsound." + PushToMeow.MOD_ID + "." + name().toLowerCase();
    }
}
