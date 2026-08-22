package com.ruineko.tweakery.text

import com.ruineko.tweakery.Tweakery

data class Text(
    val value: String,
    val color: Int? = null,
    val shadow: Boolean = false
) {
    fun encode(): String {
        return buildString {
            append("[${Tweakery.MOD_ID}$SEPARATOR")
            append(value)
            append(SEPARATOR)
            append(color?.toUInt()?.toString(16)?.padStart(8, '0') ?: "")
            append(SEPARATOR)
            append(shadow)
            append("]")
        }
    }

    companion object {
        private const val SEPARATOR = '\u001F'

        fun decode(encoded: String): Text {
            val end = encoded.indexOf(']')
            val prefix = "[${Tweakery.MOD_ID}$SEPARATOR"

            if (!encoded.startsWith(prefix) || end == -1) {
                return Text(encoded)
            }

            val metadata = encoded.substring(prefix.length, end)
            val parts = metadata.split(SEPARATOR)

            if (parts.size != 3) {
                return Text(encoded)
            }

            val color = parts[1]
                .takeIf { it.isNotEmpty() }
                ?.toUIntOrNull(16)
                ?.toInt()

            return Text(
                value = parts[0],
                color = color,
                shadow = parts[2].toBoolean()
            )
        }
    }
}