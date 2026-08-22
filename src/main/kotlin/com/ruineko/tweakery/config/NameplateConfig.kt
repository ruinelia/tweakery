package com.ruineko.tweakery.config

import dev.isxander.yacl3.config.v2.api.SerialEntry

class NameplateConfig {
    @SerialEntry
    var showNameplate = true

    @SerialEntry
    var showOwn: Boolean = true

    @SerialEntry
    var showIcon: Boolean = true

    @SerialEntry
    var showTextShadow: Boolean = true

    @SerialEntry
    var showTextBackground: Boolean = true

    @SerialEntry
    var center: Boolean = true
}