package net.goobee.pushtomeow.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.goobee.pushtomeow.CustomSounds;
import net.goobee.pushtomeow.PushToMeow;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YACLServerConfig {
    // Config File:
    public static final ConfigClassHandler<YACLServerConfig> HANDLER = ConfigClassHandler.createBuilder(YACLServerConfig.class)
            .id(new Identifier(PushToMeow.MOD_ID, "server_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("pushtomeow_server.json5"))
                    .setJson5(true)
                    .build())
            .build();

    public static String[] soundBlacklistArray = {CustomSounds.MEOW_KATZEN.getId().toString(), "pushtomeow:meow.example"};
    // Config Entries:
    @SerialEntry(comment = "Don't touch !")
    public static int configVersion = 1;
//    @SerialEntry (comment = "Meows that will be replaced by the normal meow. Format as sound id: \"pushtomeow:meow.<name>\" (Default: \"pushtomeow:meow.katzen\")\nMeows: coarse, fat, normal, pup, rivulet.a, rivulet.b, sofanthiel, spear, watcher, whispery, altinv.andrew, altinv.inv")
//    public static List<String> soundBlacklist = new ArrayList<>(Arrays.asList(YACLServerConfig.soundBlacklistArray));
    @SerialEntry (comment = "The cooldown (in seconds) for meowing. (Default: 0.5s)")
    public static float cooldown = 0.5f;
    @SerialEntry (comment = "The volume all meows play at, >1.0 only affects sound distance. Total distance is this value * ~16! (Default: 1.5)")
    public static float volume = 1.5f;
    @SerialEntry (comment = "The volume multiplier for modified meows. (Default: 0.75)")
    public static float modifiedVolumeMul = 0.75f;
    @SerialEntry (comment = "The max meow sound pitch allowed. >2.0 does nothing! (Default: 2.0)")
    public static float maxPitch = 2.0f;
    @SerialEntry (comment = "The min meow sound pitch allowed. <0.5 does nothing! (Default: 0.5)")
    public static float minPitch = 0.5f;
}
