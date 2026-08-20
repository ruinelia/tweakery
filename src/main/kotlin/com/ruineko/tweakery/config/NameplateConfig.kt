package com.ruineko.tweakery.config

import dev.isxander.yacl3.config.v2.api.SerialEntry

class NameplateConfig {
    @SerialEntry
    var enabled: Boolean = true

    @SerialEntry
    var showOwn: Boolean = true

    @SerialEntry
    var showIcon: Boolean = true

    @SerialEntry
    var showShadow: Boolean = true

    @SerialEntry
    var showBackground: Boolean = true

    @SerialEntry
    var center: Boolean = true
}