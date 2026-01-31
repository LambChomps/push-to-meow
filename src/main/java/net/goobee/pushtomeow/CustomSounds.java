package net.goobee.pushtomeow;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CustomSounds {
    private CustomSounds() {}
    public static final SoundEvent MEOW_COARSE = registerSound("meow.coarse");
    public static final SoundEvent MEOW_COARSE_SHORT = registerSound("meow.coarse.short");
    public static final SoundEvent MEOW_FAT = registerSound("meow.fat");
    public static final SoundEvent MEOW_FAT_SHORT = registerSound("meow.fat.short");
    public static final SoundEvent MEOW_NORMAL = registerSound("meow.normal");
    public static final SoundEvent MEOW_NORMAL_SHORT = registerSound("meow.normal.short");
    public static final SoundEvent MEOW_PUP = registerSound("meow.pup");
    public static final SoundEvent MEOW_PUP_SHORT = registerSound("meow.pup.short");
    public static final SoundEvent MEOW_RIVULET_A = registerSound("meow.rivulet.a");
    public static final SoundEvent MEOW_RIVULET_A_SHORT = registerSound("meow.rivulet.a.short");
    public static final SoundEvent MEOW_RIVULET_B = registerSound("meow.rivulet.b");
    public static final SoundEvent MEOW_RIVULET_B_SHORT = registerSound("meow.rivulet.b.short");
    public static final SoundEvent MEOW_SOFANTHIEL = registerSound("meow.sofanthiel");
    public static final SoundEvent MEOW_SOFANTHIEL_SHORT = registerSound("meow.sofanthiel.short");
    public static final SoundEvent MEOW_SPEAR = registerSound("meow.spear");
    public static final SoundEvent MEOW_SPEAR_SHORT = registerSound("meow.spear.short");
    public static final SoundEvent MEOW_WATCHER = registerSound("meow.watcher");
    public static final SoundEvent MEOW_WATCHER_SHORT = registerSound("meow.watcher.short");
    public static final SoundEvent MEOW_WHISPERY = registerSound("meow.whispery");
    public static final SoundEvent MEOW_WHISPERY_SHORT = registerSound("meow.whispery.short");
    public static final SoundEvent MEOW_KATZEN = registerSound("meow.katzen");

    public static final SoundEvent MEOW_ALTINV_ANDREW = registerSound("meow.altinv.andrew");
    public static final SoundEvent MEOW_ALTINV_INV = registerSound("meow.altinv.inv");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = new Identifier(PushToMeow.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {
        if (PushToMeow.DEBUG) {
            PushToMeow.LOGGER.info(PushToMeow.MOD_ID + " -- Registering sounds...");
        }
        Registries.SOUND_EVENT.getEntrySet();
    }
}
