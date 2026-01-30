package net.goobee.pushtomeow.client;

import net.fabricmc.api.ClientModInitializer;
import net.goobee.pushtomeow.networking.Packets;

public class PushToMeowClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CustomKeybinds.initialize();

        Packets.registerS2CPackets();
    }
}
