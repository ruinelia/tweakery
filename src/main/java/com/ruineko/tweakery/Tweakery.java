package com.ruineko.tweakery;

import com.ruineko.tweakery.config.TweakeryConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class Tweakery implements ModInitializer {
    public static final String MOD_ID = "tweakery";

    public static final String NAMEPLATE_ICON_INSERTION = "tweakery:nameplate_icon";

    public static TweakeryConfig CONFIG;

    @Override
    public void onInitialize() {
        TweakeryConfig.Companion.getHANDLER().load();
        CONFIG = TweakeryConfig.Companion.getHANDLER().instance();
    }

    public static boolean hasTweakery(Player player) {
        Player localPlayer = Minecraft.getInstance().player;
        return localPlayer != null && localPlayer == player;
    }
}