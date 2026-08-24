package com.ruineko.tweakery.config

import dev.isxander.yacl3.config.v2.api.SerialEntry
import java.awt.Color

class PlayerConfig {
    @SerialEntry
    var displayName = ""

    @SerialEntry
    var overrideTeam = false

    @SerialEntry
    var teamColor: Color = Color.WHITE

    @SerialEntry
    var teamPrefix = ""

    @SerialEntry
    var teamSuffix = ""
}
