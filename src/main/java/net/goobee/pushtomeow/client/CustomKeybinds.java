package net.goobee.pushtomeow.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.goobee.pushtomeow.PushToMeow;
import net.goobee.pushtomeow.client.event.Meowing;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CustomKeybinds {
    private CustomKeybinds() {}

    public static final KeyBinding MEOW =
            CustomKeybinds.registerKeybindings("meow", "meow", GLFW.GLFW_KEY_M);
    public static final KeyBinding MEOW_MODIFIER =
            CustomKeybinds.registerKeybindings("meow_modifier", "meow", GLFW.GLFW_KEY_UNKNOWN);

    // Extended
    public static final KeyBinding EXTENDED_MEOW_COARSE =
            CustomKeybinds.registerKeybindings("extended.meow_coarse", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_FAT =
            CustomKeybinds.registerKeybindings("extended.meow_fat", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_NORMAL =
            CustomKeybinds.registerKeybindings("extended.meow_normal", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_PUP =
            CustomKeybinds.registerKeybindings("extended.meow_pup", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_RIVULET_A =
            CustomKeybinds.registerKeybindings("extended.meow_rivulet_a", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_RIVULET_B =
            CustomKeybinds.registerKeybindings("extended.meow_rivulet_b", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_SOFANTHIEL =
            CustomKeybinds.registerKeybindings("extended.meow_sofanthiel", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_SPEAR =
            CustomKeybinds.registerKeybindings("extended.meow_spear", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_WATCHER =
            CustomKeybinds.registerKeybindings("extended.meow_watcher", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_WHISPERY =
            CustomKeybinds.registerKeybindings("extended.meow_whispery", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_ALTINV_ANDREW =
            CustomKeybinds.registerKeybindings("extended.meow_altinv_andrew", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);
    public static final KeyBinding EXTENDED_MEOW_ALTINV_INV =
            CustomKeybinds.registerKeybindings("extended.meow_altinv_inv", "meow_extended", GLFW.GLFW_KEY_UNKNOWN);

    public static KeyBinding registerKeybindings(String id, String category, int key) {
        return KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.pushtomeow." + id, InputUtil.Type.KEYSYM, key,"key.category.pushtomeow." + category)
        );
    }

    public static void initialize() {
        if (PushToMeow.DEBUG) PushToMeow.LOGGER.info("Registering keybinds...");

        Meowing.pushToMeow();
    }
}
