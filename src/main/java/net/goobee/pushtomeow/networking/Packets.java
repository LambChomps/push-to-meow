package net.goobee.pushtomeow.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.goobee.pushtomeow.PushToMeow;
import net.goobee.pushtomeow.networking.packet.NBTSyncC2SPacket;
import net.goobee.pushtomeow.networking.packet.MeowC2SPacket;
import net.goobee.pushtomeow.networking.packet.NBTSyncS2CPacket;
import net.goobee.pushtomeow.networking.packet.MeowSoundS2CPacket;
import net.minecraft.util.Identifier;

public class Packets {
    // Client to Server:
    public static final Identifier MEOW = new Identifier(PushToMeow.MOD_ID, "meow");
    public static final Identifier C2S_NBT_SYNC = new Identifier(PushToMeow.MOD_ID, "c2s_nbt_sync");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MEOW, MeowC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(C2S_NBT_SYNC, NBTSyncC2SPacket::receive);
    }

    // Server to Client:
    public static final Identifier MEOW_SOUND = new Identifier(PushToMeow.MOD_ID, "meow_sound");
    public static final Identifier S2C_NBT_SYNC = new Identifier(PushToMeow.MOD_ID, "s2c_nbt_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(MEOW_SOUND, MeowSoundS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(S2C_NBT_SYNC, NBTSyncS2CPacket::receive);
    }
}
