package com.ruineko.tweakery.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.ruineko.tweakery.Tweakery;
import com.ruineko.tweakery.feature.Zoom;
import com.ruineko.tweakery.screen.ConfigScreen;
import com.ruineko.tweakery.screen.MenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class TweakeryClient implements ClientModInitializer {
    private static final KeyMapping.Category TWEAKERY_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(Tweakery.MOD_ID, "category")
    );

    public static final KeyMapping ZOOM_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.tweakery.zoom",
                    InputConstants.Type.KEYSYM,
                    InputConstants.KEY_C,
                    TWEAKERY_CATEGORY
            )
    );

    public static final KeyMapping OPEN_CONFIG_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.tweakery.open_config",
                    InputConstants.Type.KEYSYM,
                    InputConstants.KEY_BACKSLASH,
                    TWEAKERY_CATEGORY
            )
    );

    public static final KeyMapping OPEN_MENU_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.tweakery.open_menu",
                    InputConstants.Type.KEYSYM,
                    InputConstants.UNKNOWN.getValue(),
                    TWEAKERY_CATEGORY
            )
    );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Zoom.INSTANCE.tick(ZOOM_KEY.isDown());

            while (OPEN_CONFIG_KEY.consumeClick()) {
                client.setScreen(ConfigScreen.create(client.screen));
            }

            while (OPEN_MENU_KEY.consumeClick()) {
                client.setScreen(new MenuScreen());
            }
        });
    }
}
