package com.ruineko.tweakery.screen

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.dsl.pixels
import java.awt.Color

class MenuScreen : WindowScreen(ElementaVersion.V10) {
    init {
        val panel = UIBlock()
            .setColor(Color(0xFF202020.toInt()))
            .constrain {
                x = CenterConstraint()
                y = CenterConstraint()
                width = 320.pixels()
                height = 180.pixels()
            }
            .childOf(window)

        UIText("Tweakery")
            .constrain {
                x = CenterConstraint()
                y = 20.pixels()
            }
            .childOf(panel)

        UIText("Hello Elementa!")
            .constrain {
                x = CenterConstraint()
                y = 60.pixels()
            }
            .childOf(panel)
    }
}