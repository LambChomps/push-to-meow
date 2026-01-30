package net.goobee.pushtomeow;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.goobee.pushtomeow.command.Commands;
import net.goobee.pushtomeow.config.YACLClientConfig;
import net.goobee.pushtomeow.config.YACLServerConfig;
import net.goobee.pushtomeow.event.JoinSync;
import net.goobee.pushtomeow.event.MeowCooldownHandler;
import net.goobee.pushtomeow.networking.Packets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushToMeow implements ModInitializer {
    public static final String MOD_ID = "pushtomeow";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final boolean DEBUG = true;

    @Override
    public void onInitialize() {
        int initMsg = (int) (Math.random() * 2);

        if (initMsg == 0) LOGGER.info("You may now meow!");
        else LOGGER.info("You may meow now!");

//        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
//        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        YACLClientConfig.HANDLER.load();
        YACLServerConfig.HANDLER.load();

        CustomSounds.initialize();
        Packets.registerC2SPackets();

        ServerTickEvents.START_SERVER_TICK.register(new MeowCooldownHandler());
        ClientPlayConnectionEvents.JOIN.register(new JoinSync());

        Commands.registerCommands();
    }
}
