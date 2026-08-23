package com.ruineko.tweakery.feature

import com.ruineko.tweakery.Tweakery
import net.minecraft.client.Minecraft
import net.minecraft.util.Mth
import kotlin.math.abs

object Zoom {
    private var progress = 0.0f
    private var previousProgress = 0.0f

    private var cinematicCameraApplied = false
    private var previousSmoothCamera = false

    fun tick(zooming: Boolean) {
        previousProgress = progress

        val target = if (zooming) 1.0f else 0.0f
        val speed = Tweakery.CONFIG.zoom.zoomSpeed.coerceIn(0.0f, 1.0f)

        if (Tweakery.CONFIG.zoom.smoothZoom) {
            progress += (target - progress) * speed

            if (abs(target - progress) < 0.0001f) {
                progress = target
            }
        } else {
            progress = target
            previousProgress = target
        }

        updateSmoothCamera(zooming)
    }

    fun apply(fov: Float, tickProgress: Float): Float {
        val interpolatedProgress = Mth.lerp(tickProgress, previousProgress, progress)
        val factor = Tweakery.CONFIG.zoom.zoomFactor.coerceIn(0.1f, 1.0f)

        return Mth.lerp(interpolatedProgress, fov, (fov * factor))
    }

    private fun updateSmoothCamera(zooming: Boolean) {
        val minecraft = Minecraft.getInstance()

        if (!Tweakery.CONFIG.zoom.cinematicCamera) {
            if (cinematicCameraApplied) {
                minecraft.options.smoothCamera = previousSmoothCamera
                cinematicCameraApplied = false
            }

            return
        }

        if (zooming && !cinematicCameraApplied) {
            previousSmoothCamera = minecraft.options.smoothCamera
            minecraft.options.smoothCamera = true
            cinematicCameraApplied = true
        }

        if (!zooming && cinematicCameraApplied) {
            minecraft.options.smoothCamera = previousSmoothCamera
            cinematicCameraApplied = false
        }
    }
}