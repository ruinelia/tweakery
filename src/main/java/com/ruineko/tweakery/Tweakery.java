package com.ruineko.tweakery;

import com.ruineko.tweakery.config.TweakeryConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.network.chat.Component;

public class Tweakery implements ModInitializer {

    public static String identifier = "tweakery";

    public static Component currentPlayerNameTag;

    @Override
    public void onInitialize() {
        TweakeryConfig.Companion.getHANDLER().load();
    }
}