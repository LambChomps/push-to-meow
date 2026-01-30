package net.goobee.pushtomeow.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.goobee.pushtomeow.PushToMeow;
import net.goobee.pushtomeow.networking.Packets;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Data {
    public static int updateCooldown(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.pushtomeow$getPersistentData();
        int cooldown = nbt.getInt("meow_cooldown");

        if (amount < 0 && cooldown == 0) return cooldown;

        cooldown += amount;
        if (cooldown < 0) cooldown = 0;

        nbt.putInt("meow_cooldown", cooldown);
        syncCustomNBT((ServerPlayerEntity) player, cooldown);
        return cooldown;
    }

    public static void syncCustomNBT(ServerPlayerEntity player, int cooldown){
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeInt(cooldown);
        ServerPlayNetworking.send(player, Packets.S2C_NBT_SYNC, buf);
    }

    public static void syncCustomNBT(IEntityDataSaver player){
        NbtCompound nbt = player.pushtomeow$getPersistentData();
        int cooldown = nbt.getInt("meow_cooldown");
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeInt(cooldown);
        ServerPlayNetworking.send((ServerPlayerEntity) player, Packets.S2C_NBT_SYNC, buf);

        // DEBUG:
        if (PushToMeow.DEBUG) {
            ((ServerPlayerEntity) player).sendMessage(Text.of("Syncing with server cooldown of: " + cooldown));
        }
    }
}
