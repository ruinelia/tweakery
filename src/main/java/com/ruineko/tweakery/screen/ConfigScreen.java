package com.ruineko.tweakery.screen;

import com.ruineko.tweakery.config.*;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ConfigScreen {
    public static Screen create(Screen parent) {
        TweakeryConfig config = TweakeryConfig.Companion.getHANDLER().instance();

        ZoomConfig zoomConfig = config.getZoom();
        NameplateConfig nameplateConfig = config.getNameplate();
        SidebarConfig sidebarConfig = config.getSidebar();
        PrivacyConfig privacyConfig = config.getPrivacy();
        DebugConfig debugConfig = config.getDebug();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.tweakery.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tweakery.category.hud"))
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.tweakery.hud.nameplate"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.hud.nameplate.show"))
                                        .binding(true, nameplateConfig::getShowNameplate, nameplateConfig::setShowNameplate)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.hud.nameplate.show_own"))
                                        .binding(true, nameplateConfig::getShowOwn, nameplateConfig::setShowOwn)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.hud.nameplate.show_icon"))
                                        .binding(true, nameplateConfig::getShowIcon, nameplateConfig::setShowIcon)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.common.show_text_shadow"))
                                        .binding(true, nameplateConfig::getShowTextShadow, nameplateConfig::setShowTextShadow)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.common.show_text_background"))
                                        .binding(true, nameplateConfig::getShowTextBackground, nameplateConfig::setShowTextBackground)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.hud.nameplate.center"))
                                        .binding(true, nameplateConfig::getCenter, nameplateConfig::setCenter)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.tweakery.hud.sidebar"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.hud.sidebar.show"))
                                        .binding(true, sidebarConfig::getShowSidebar, sidebarConfig::setShowSidebar)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.common.show_text_shadow"))
                                        .binding(true, sidebarConfig::getShowTextShadow, sidebarConfig::setShowTextShadow)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.tweakery.common.show_text_background"))
                                        .binding(true, sidebarConfig::getShowTextBackground, sidebarConfig::setShowTextBackground)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tweakery.category.zoom"))
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("config.tweakery.zoom.factor"))
                                .binding(0.2f, zoomConfig::getZoomFactor, zoomConfig::setZoomFactor)
                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption)
                                        .range(0.0f, 1.0f)
                                        .step(0.05f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("config.tweakery.zoom.speed"))
                                .binding(0.5f, zoomConfig::getZoomSpeed, zoomConfig::setZoomSpeed)
                                .controller(floatOption -> FloatSliderControllerBuilder.create(floatOption)
                                        .range(0.0f, 1.0f)
                                        .step(0.05f))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.zoom.smooth"))
                                .binding(true, zoomConfig::getSmoothZoom, zoomConfig::setSmoothZoom)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.zoom.cinematic"))
                                .binding(true, zoomConfig::getCinematicCamera, zoomConfig::setCinematicCamera)
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
                                .name(Component.translatable("config.tweakery.common.show_text_shadow"))
                                .binding(true, debugConfig::getShowTextShadow, debugConfig::setShowTextShadow)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tweakery.common.show_text_background"))
                                .binding(true, debugConfig::getShowTextBackground, debugConfig::setShowTextBackground)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .save(TweakeryConfig.Companion.getHANDLER()::save)
                .build()
                .generateScreen(parent);
    }
}
