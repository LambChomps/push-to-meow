package net.goobee.pushtomeow.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.goobee.pushtomeow.util.Data;
import net.goobee.pushtomeow.util.IEntityDataSaver;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class MeowCooldownHandler implements ServerTickEvents.StartTick {
    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            Data.updateCooldown((IEntityDataSaver) player, -1);
        }
    }
}
