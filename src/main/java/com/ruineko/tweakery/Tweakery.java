package com.ruineko.tweakery;

import com.ruineko.tweakery.config.TweakeryConfig;
import com.ruineko.tweakery.feature.NameplateIcon;
import net.fabricmc.api.ModInitializer;

public class Tweakery implements ModInitializer {
    public static final String MOD_ID = "tweakery";

    public static TweakeryConfig CONFIG;

    @Override
    public void onInitialize() {
        TweakeryConfig.Companion.getHANDLER().load();
        CONFIG = TweakeryConfig.Companion.getHANDLER().instance();

        NameplateIcon.Companion.reload();
    }
}