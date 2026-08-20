package com.ruineko.tweakery.config

import dev.isxander.yacl3.config.v2.api.SerialEntry

class PrivacyConfig {

    @SerialEntry
    var nickname = ""

    @SerialEntry
    var hideCoordinates = false;
}