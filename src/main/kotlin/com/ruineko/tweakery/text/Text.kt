package com.ruineko.tweakery.text

import com.ruineko.tweakery.Tweakery

data class Text(
    val value: String,
    val color: Int? = null,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val shadow: Boolean = false
) {
    fun encode(): String {
        return buildString {
            append("[${Tweakery.identifier}")
            append(":")
            append(color?.toUInt()?.toString(16)?.padStart(8, '0') ?: "")
            append(":")
            append(bold)
            append(":")
            append(italic)
            append(":")
            append(shadow)
            append("]")
            append(value)
        }
    }

    companion object {
        fun decode(encoded: String): Text {
            val end = encoded.indexOf(']')
            val identifier = Tweakery.identifier

            if (!encoded.startsWith("[$identifier:") || end == -1) {
                return Text(encoded)
            }

            val metadata = encoded.substring("[$identifier:".length, end)

            val parts = metadata.split(":")

            if (parts.size != 4) {
                return Text(encoded)
            }

            val color = parts[0].takeIf { it.isNotEmpty() }?.toULongOrNull(16)?.toInt()

            return Text(
                value = encoded.substring(end + 1),
                color = color,
                bold = parts[1].toBoolean(),
                italic = parts[2].toBoolean(),
                shadow = parts[3].toBoolean()
            )
        }
    }
}