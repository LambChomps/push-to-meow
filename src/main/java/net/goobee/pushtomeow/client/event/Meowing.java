package net.goobee.pushtomeow.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.goobee.pushtomeow.CustomSounds;
import net.goobee.pushtomeow.client.CustomKeybinds;
import net.goobee.pushtomeow.config.YACLClientConfig;
import net.goobee.pushtomeow.networking.Packets;
import net.goobee.pushtomeow.util.IEntityDataSaver;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Meowing {
    public static void pushToMeow() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player == null) return; // Null Cancel

            IEntityDataSaver playerData = (IEntityDataSaver) player;
            int cooldown = playerData.pushtomeow$getPersistentData().getInt("meow_cooldown");
            if (cooldown > 0) return; // Client cooldown check

            float soundPitch = YACLClientConfig.configPitchDefault;
            // Check sound variance:
            float soundPitchVariance = YACLClientConfig.configPitchVariance;
            if (soundPitchVariance != 0) soundPitch += MathHelper.nextBetween(Random.create(), -soundPitchVariance, soundPitchVariance);

            float playerPitch = player.getPitch();
            float pitchEffect = YACLClientConfig.configPitchPitchEffect;
            int pitchAngleDefault = YACLClientConfig.configPitchAngleDefault;
            int pitchAngleMax = YACLClientConfig.configPitchAngleMax;
            // Modifier check:
            boolean modified = isModified(player);

            // Create list of sounds to play:
            Set<String> soundIDs = getKeysPressed();
            // Send list to server with client settings:
            for (String soundID : soundIDs) {
                if (modified) soundID += ".short";
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeFloat(soundPitch);
                buf.writeFloat(playerPitch);
                buf.writeFloat(pitchEffect);
                buf.writeInt(pitchAngleDefault);
                buf.writeInt(pitchAngleMax);
                buf.writeString(soundID);

                ClientPlayNetworking.send(Packets.MEOW, buf);
            }
        });
    }

    private static boolean isModified(ClientPlayerEntity player) {
        YACLClientConfig.soundModifierType t = YACLClientConfig.configModifyType;
        boolean modified = t.equals(YACLClientConfig.soundModifierType.ALWAYS);
        if (t == YACLClientConfig.soundModifierType.DEFAULT || t == YACLClientConfig.soundModifierType.INVERTED) {
            modified = CustomKeybinds.MEOW_MODIFIER.isUnbound() ?
                    player.isSneaking() : CustomKeybinds.MEOW_MODIFIER.isPressed();
            if (YACLClientConfig.configModifyType == YACLClientConfig.soundModifierType.INVERTED) modified = !modified;
        }
        return modified;
    }

    private static @NotNull Set<String> getKeysPressed() {
        Set<String> soundIDs = new HashSet<>();
        
        if (CustomKeybinds.MEOW.isPressed())
            soundIDs.add(getIDString(YACLClientConfig.configDefaultSound.getSoundEvent()));
        if (CustomKeybinds.EXTENDED_MEOW_COARSE.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_COARSE));
        if (CustomKeybinds.EXTENDED_MEOW_FAT.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_FAT));
        if (CustomKeybinds.EXTENDED_MEOW_NORMAL.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_NORMAL));
        if (CustomKeybinds.EXTENDED_MEOW_PUP.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_PUP));
        if (CustomKeybinds.EXTENDED_MEOW_RIVULET_A.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_RIVULET_A));
        if (CustomKeybinds.EXTENDED_MEOW_RIVULET_B.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_RIVULET_B));
        if (CustomKeybinds.EXTENDED_MEOW_SOFANTHIEL.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_SOFANTHIEL));
        if (CustomKeybinds.EXTENDED_MEOW_SPEAR.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_SPEAR));
        if (CustomKeybinds.EXTENDED_MEOW_WATCHER.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_WATCHER));
        if (CustomKeybinds.EXTENDED_MEOW_WHISPERY.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_WHISPERY));
        if (CustomKeybinds.EXTENDED_MEOW_ALTINV_ANDREW.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_ALTINV_ANDREW));
        if (CustomKeybinds.EXTENDED_MEOW_ALTINV_INV.isPressed())
            soundIDs.add(getIDString(CustomSounds.MEOW_ALTINV_INV));
        return soundIDs;
    }

    private static String getIDString(SoundEvent soundEvent) {
        return soundEvent.getId().toString();
    }

//    static long cooldown = 0;
//
//    public static void pushToMeow() {
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            ClientPlayerEntity player = client.player;
//            if (player == null) return;
//
//            if (cooldown != 0) cooldown--;
//            else if (CustomKeybinds.NORMAL_MEOW.isPressed()) {
//                cooldown = 5;
//                ClientPlayNetworking.send(Packets.MEOW, PacketByteBufs.create());
//            }
//        });
//    }
}
