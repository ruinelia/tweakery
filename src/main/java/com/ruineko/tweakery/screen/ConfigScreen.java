package com.ruineko.tweakery.screen;

import com.ruineko.tweakery.config.DebugConfig;
import com.ruineko.tweakery.config.NameplateConfig;
import com.ruineko.tweakery.config.PrivacyConfig;
import com.ruineko.tweakery.config.TweakeryConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen {
    public static Screen create(Screen parent) {
        TweakeryConfig config = TweakeryConfig.Companion.getHANDLER().instance();

        NameplateConfig nameplateConfig = config.getNameplate();
        PrivacyConfig privacyConfig = config.getPrivacy();
        DebugConfig debugConfig = config.getDebug();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.tweakery.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tweakery.category.nameplate"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.nameplate.enable"))
                                .binding(true, nameplateConfig::getEnabled, nameplateConfig::setEnabled)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.nameplate.show_own"))
                                .binding(true, nameplateConfig::getShowOwn, nameplateConfig::setShowOwn)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.nameplate.show_icon"))
                                .binding(true, nameplateConfig::getShowIcon, nameplateConfig::setShowIcon)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.nameplate.show_shadow"))
                                .binding(true, nameplateConfig::getShowShadow, nameplateConfig::setShowShadow)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.nameplate.show_background"))
                                .binding(true, nameplateConfig::getShowBackground, nameplateConfig::setShowBackground)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.nameplate.center"))
                                .binding(true, nameplateConfig::getCenter, nameplateConfig::setCenter)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tweakery.category.privacy"))
                        .option(Option.<String>createBuilder()
                                .name(Component.translatable("config.tweakery.privacy.nickname"))
                                .binding("", privacyConfig::getNickname, privacyConfig::setNickname)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.privacy.hide_coordinates"))
                                .binding(false, privacyConfig::getHideCoordinates, privacyConfig::setHideCoordinates)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tweakery.category.debug"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.debug.show_shadow"))
                                .binding(true, debugConfig::getShadow, debugConfig::setShadow)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.debug.show_background"))
                                .binding(true, debugConfig::getBackground, debugConfig::setBackground)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .save(TweakeryConfig.Companion.getHANDLER()::save)
                .build()
                .generateScreen(parent);
    }
}
