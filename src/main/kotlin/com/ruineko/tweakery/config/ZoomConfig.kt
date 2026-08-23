package com.ruineko.tweakery.config

import dev.isxander.yacl3.config.v2.api.SerialEntry

class ZoomConfig {
    @SerialEntry
    var zoomFactor = 0.2f;

    @SerialEntry
    var zoomSpeed = 0.5f;

    @SerialEntry
    var smoothZoom = true

    @SerialEntry
    var cinematicCamera = true
}