package net.goobee.pushtomeow.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.goobee.pushtomeow.PushToMeow;
import net.goobee.pushtomeow.config.YACLServerConfig;
import net.goobee.pushtomeow.networking.Packets;
import net.goobee.pushtomeow.util.Data;
import net.goobee.pushtomeow.util.IEntityDataSaver;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;

public class MeowC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound nbt = ((IEntityDataSaver) player).pushtomeow$getPersistentData();
        int cooldown = nbt.getInt("meow_cooldown");
        if (cooldown > 0) return; // Server cooldown check

        int serverCooldown = Math.round(YACLServerConfig.cooldown * 20);
        float soundVolume = YACLServerConfig.volume;
        float soundPitch = buf.readFloat();
        float pitchAngle = buf.readFloat();
        float pitchEffect = buf.readFloat();
        int pitchAngleDefault = buf.readInt();
        int pitchAngleMax = buf.readInt();
        String soundString =  nbt.contains("meow_type") ? nbt.getString("meow_type") : buf.readString();

//        SoundEvent sound = SoundEvent.of(new Identifier(soundString));

        // Adjust sound options:
        if (soundString.contains(".short")) soundVolume *= YACLServerConfig.modifiedVolumeMul;
        if (pitchEffect != 0) {
            float pitchAngleAbs = Math.abs(pitchAngle);
            float pitchRange = Math.max(pitchAngleMax - pitchAngleDefault, 0.01f);
            float pitchAngleFromDefault = MathHelper.clamp(pitchAngleAbs - pitchAngleDefault, 0.01f, pitchRange);

            if (pitchAngle > 0) pitchAngleFromDefault *= -1;

            soundPitch += pitchEffect * (pitchAngleFromDefault / pitchRange);
        }
        soundPitch = MathHelper.clamp(soundPitch, YACLServerConfig.minPitch, YACLServerConfig.maxPitch);

        // Play sound:
            // First event is to all but the player who meows, second is only to meowing player;
            // Very minor but helps play the sound more accurately to the client.
//        player.getWorld().playSoundFromEntity(null, player, sound, SoundCategory.PLAYERS, 1f, soundPitch);
//        player.playSound(sound, SoundCategory.PLAYERS, 1f, soundPitch);

        // Add cooldown:
        if (serverCooldown > 0) {
            Data.updateCooldown((IEntityDataSaver) player, serverCooldown);
        }

        // Send sound packet:
        Set<ServerPlayerEntity> trackingPlayers = new HashSet<>(PlayerLookup.tracking(player));
        trackingPlayers.add(player); // Backup if not included in player lookup

        for (ServerPlayerEntity serverPlayerEntity : trackingPlayers) {
            PacketByteBuf buf2 = PacketByteBufs.create();
            buf2.writeUuid(player.getUuid());
            buf2.writeFloat(soundVolume);
            buf2.writeFloat(soundPitch);
            buf2.writeString(soundString);

            ServerPlayNetworking.send(serverPlayerEntity, Packets.MEOW_SOUND, buf2);
        }

        // Debug:
        if (PushToMeow.DEBUG) {
            Vec3d pos = player.getEyePos();
            server.sendMessage(Text.of(
                    "Received meow from " + player.getName().getString() + " at location: " + pos + " at pitch: " + soundPitch + " (" + pitchAngle + "°)"));
            server.sendMessage(Text.of("Bufs: " + buf.getFloat(0) + ", " + pitchEffect + ", " + pitchAngleDefault + ", " + pitchAngleMax
                    + ", " + soundString));
        }
    }
}
