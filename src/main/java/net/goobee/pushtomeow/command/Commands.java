package net.goobee.pushtomeow.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.goobee.pushtomeow.config.YACLServerConfig;
import net.goobee.pushtomeow.util.IEntityDataSaver;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class Commands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                // Main command keyword:
                dispatcher.register(CommandManager.literal("pushtomeow")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("""
                        
                                    §f§nServer Commands:
                                    §f/pushtomeow setmeow {§etarget§f} {§esound id / "clear"§f} §7- Sets / Removes target(s) meow sound.
    
                                    §f§nServer Config Commands:
                                    §f/pushtomeow reload §7- Reloads the server config from its file.
                                    §f/pushtomeow cooldown <§eseconds§f> §7- Checks / Sets the server meow cooldown.
                                    §f/pushtomeow volume <§evolume§f> §7- Checks / Sets the server meow volume."""), false);
                            return 1;
                        })
                        // Command Selection:
                        .then(CommandManager.argument("command", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    if (context.getSource().hasPermissionLevel(2)) {
                                        builder.suggest("setmeow");
                                        builder.suggest("reload");
                                    }
                                    builder.suggest("cooldown");
                                    builder.suggest("volume");
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    String command = StringArgumentType.getString(context, "command");
                                    switch (command) {
                                        case "setmeow":
                                            if (context.getSource().hasPermissionLevel(2)) {
                                                context.getSource().sendFeedback(() -> Text.literal("[PTM] §cPlease provide a player to check."), false);
                                                return 0;
                                            } else throw INVALID_PERMISSIONS.create();
                                        case "reload":
                                            if (context.getSource().hasPermissionLevel(2)) {
                                                YACLServerConfig.HANDLER.load();
                                                context.getSource().sendFeedback(() -> Text.literal("[PTM] §7Config file reloaded!"), true);
                                                return 1;
                                            } else throw INVALID_PERMISSIONS.create();
                                        case "cooldown":
                                            context.getSource().sendFeedback(() -> Text.literal("[PTM] §7Meow cooldown is currently: " + YACLServerConfig.cooldown + "s"), false);
                                            return 1;
                                        case "volume":
                                            context.getSource().sendFeedback(() -> Text.literal("[PTM] §Meow volume is currently: " + YACLServerConfig.volume), false);
                                            return 1;
                                        default:
                                            throw INVALID_COMMAND.create();
                                    }
                                })
                                // Optional value for config commands:
                                .then(CommandManager.argument("value", FloatArgumentType.floatArg(0f))
                                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                                        .executes(context -> {
                                            String command = StringArgumentType.getString(context, "command");
                                            float value = FloatArgumentType.getFloat(context, "value");

                                            switch (command) {
                                                case "cooldown":
                                                    YACLServerConfig.cooldown = value;
                                                    YACLServerConfig.HANDLER.save();
                                                    context.getSource().sendFeedback(() -> Text.literal("[PTM] §7Meow cooldown set to: " + value + "s"), true);
                                                    return 1;
                                                case "volume":
                                                    YACLServerConfig.volume = value;
                                                    YACLServerConfig.HANDLER.save();
                                                    context.getSource().sendFeedback(() -> Text.literal("[PTM] §Meow volume set to: " + value), true);
                                                    return 1;
                                                default:
                                                    throw INVALID_COMMAND.create();
                                            }
                                        }))
                                // Setmeow target:
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                                        .suggests(new PlayerSuggestionProvider())
                                        .executes(context -> {
                                            context.getSource().sendFeedback(() -> Text.literal("[PTM] §cPlease provide a sound ID to be applied."), false);
                                            return 0;
                                        })
                                        // Setmeow sound ID:
                                        .then(CommandManager.argument("value", StringArgumentType.greedyString())
                                                .suggests(SuggestionProviders.AVAILABLE_SOUNDS)
                                                .executes(context -> {
                                                    Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
                                                    Set<String> targetNames = new HashSet<>();
                                                    String value = StringArgumentType.getString(context, "value");

                                                    if (value.equals("clear")) {
                                                        for (PlayerEntity target : targets) {
                                                            ((IEntityDataSaver) target).pushtomeow$getPersistentData().remove("meow_type");
                                                            targetNames.add(target.getName().getString());
                                                        }
                                                        context.getSource().sendFeedback(() -> Text.literal("[PTM] §7Cleared set meow sound for: " + targetNames), true);
                                                    } else {
                                                        for (PlayerEntity target : targets) {
                                                            ((IEntityDataSaver) target).pushtomeow$getPersistentData().putString("meow_type", value);
                                                            targetNames.add(target.getName().getString());
                                                        }
                                                        context.getSource().sendFeedback(() -> Text.literal("[PTM] §7Applied " + value + " to " + targetNames + "."), true);
                                                    }

                                                    return 1;
                                                }))))));
    }

    private static final SimpleCommandExceptionType INVALID_COMMAND = new SimpleCommandExceptionType(
            Text.literal("Invalid request, use \"/pushtomeow\" for valid commands")
    );

    private static final SimpleCommandExceptionType INVALID_PERMISSIONS = new SimpleCommandExceptionType(
            Text.literal("Invalid permission level")
    );
}
