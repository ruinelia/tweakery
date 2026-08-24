package com.ruineko.tweakery.feature

import com.mojang.blaze3d.platform.NativeImage
import com.ruineko.tweakery.Tweakery
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.Identifier
import java.io.ByteArrayInputStream
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class NameplateIcon {
    companion object {
        private const val NAME = "nameplate_icon"

        private val TEXTURE = Identifier.fromNamespaceAndPath(Tweakery.MOD_ID, "textures/$NAME.png")
        private val DYNAMIC_TEXTURE = Identifier.fromNamespaceAndPath(Tweakery.MOD_ID, "dynamic/$NAME")

        private val HTTP_CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(5))
            .build()

        private const val MAX_IMAGE_SIZE = 2 * 1024 * 1024

        @Volatile
        var texture: Identifier = TEXTURE
            private set

        fun reload() {
            val url = Tweakery.CONFIG.nameplate.iconUrl

            if (url.isBlank()) {
                texture = TEXTURE
                return
            }

            val request = try {
                HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build()
            } catch (_: Exception) {
                texture = TEXTURE
                return
            }

            val minecraft = Minecraft.getInstance()

            HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream()).thenApply { response ->
                if (response.statusCode() !in 200..299) {
                    throw IOException()
                }

                val bytes = response.body().readBytes()

                if (bytes.size > MAX_IMAGE_SIZE) {
                    throw IOException("Image is too large")
                }

                NativeImage.read(ByteArrayInputStream(bytes))
            }.thenApply { image ->
                minecraft.execute {
                    try {
                        val dynamicTexture = DynamicTexture({ NAME }, image)

                        minecraft.textureManager.register(DYNAMIC_TEXTURE, dynamicTexture)

                        texture = DYNAMIC_TEXTURE
                    } catch (_: IOException) {
                        image.close()
                        texture = TEXTURE
                    }
                }
            }.exceptionally {
                minecraft.execute {
                    texture = TEXTURE
                }
            }
        }
    }
}