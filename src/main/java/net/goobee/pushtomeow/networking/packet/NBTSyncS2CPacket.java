package net.goobee.pushtomeow.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.goobee.pushtomeow.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class NBTSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        PlayerEntity player = client.player;
        if (player == null) return;

        ((IEntityDataSaver) player).pushtomeow$getPersistentData().putInt("meow_cooldown", buf.readInt());
    }
}
