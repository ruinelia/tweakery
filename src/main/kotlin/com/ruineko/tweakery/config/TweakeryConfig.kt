package com.ruineko.tweakery.config

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.Identifier

class TweakeryConfig {
    companion object {
        val HANDLER: ConfigClassHandler<TweakeryConfig> = ConfigClassHandler.createBuilder(TweakeryConfig::class.java)
            .id(Identifier.fromNamespaceAndPath("tweakery", "config"))
            .serializer { config ->
                GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().configDir.resolve("tweakery.json5"))
                    .setJson5(true)
                    .build()
            }
            .build()
    }

    @SerialEntry
    var nameplate = NameplateConfig()

    @SerialEntry
    var sidebar = SidebarConfig()

    @SerialEntry
    var zoom = ZoomConfig()

    @SerialEntry
    var privacy = PrivacyConfig()

    @SerialEntry
    var debug = DebugConfig()
}