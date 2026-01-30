package net.goobee.pushtomeow.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.goobee.pushtomeow.PushToMeow;
import net.goobee.pushtomeow.util.Data;
import net.goobee.pushtomeow.util.IEntityDataSaver;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class NBTSyncC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (PushToMeow.DEBUG) server.sendMessage(Text.of("Requesting sync from " + player.getName().getString()));

        Data.syncCustomNBT((IEntityDataSaver) player);
    }
}
