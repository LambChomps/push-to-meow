package net.goobee.pushtomeow.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.goobee.pushtomeow.client.MeowSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class MeowSoundS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (client.player == null) return;

        UUID uuid = buf.readUuid();
        PlayerEntity sentPlayer = client.player.getWorld().getPlayerByUuid(uuid);
        if (sentPlayer == null) return;

        float volume = buf.readFloat();
        float pitch = buf.readFloat();
        String soundString = buf.readString();
        SoundEvent sound = SoundEvent.of(new Identifier(soundString));

        client.getSoundManager().play(new MeowSoundInstance(sentPlayer, sound, SoundCategory.PLAYERS, volume, pitch));
    }
}
