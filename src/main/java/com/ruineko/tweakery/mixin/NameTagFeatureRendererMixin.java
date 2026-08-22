package com.ruineko.tweakery.mixin;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ruineko.tweakery.Tweakery;
import com.ruineko.tweakery.config.NameplateConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NameTagFeatureRenderer.class)
public abstract class NameTagFeatureRendererMixin {
    @Unique
    private static final Identifier WHITE_TEXTURE = Identifier.fromNamespaceAndPath("tweakery", "textures/misc/white.png");

    @Unique
    private static final Identifier ICON_TEXTURE = Identifier.fromNamespaceAndPath("tweakery", "textures/nameplate_icon.png");

    @Redirect(method = "render(Lnet/minecraft/client/renderer/SubmitNodeCollection;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/gui/Font;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V"))
    private void tweakery$drawNameplate(@NonNull Font font, @NonNull Component text, float x, float y, int color, boolean shadow, Matrix4f pose, MultiBufferSource bufferSource, Font.DisplayMode displayMode, int backgroundColor, int lightCoords) {
        NameplateConfig nameplateConfig = Tweakery.CONFIG.getNameplate();

        if (!nameplateConfig.getShowNameplate()) {
            return;
        }

        boolean showIcon = nameplateConfig.getShowIcon() && hasIconMarker(text) && (color >>> 24) != 0;

        float textWidth = font.width(text);

        float iconSize = 8.0f;
        float gap = 2.0f;

        // Vanilla padding
        float paddingLeft = 1.0f;
        float paddingRight = 0.0f;

        float offsetX = 0.0f;

        float iconWidth = 0.0f;
        float extraLeft = 0.0f;

        if (showIcon) {
            iconWidth = iconSize;
            extraLeft = iconWidth + gap + 1.0f;

            if (nameplateConfig.getCenter()) {
                offsetX = extraLeft * 0.5f;
            }

            paddingLeft += extraLeft;
        }

        /* Pass 1: Background */
        if (nameplateConfig.getShowTextBackground()) {
            float backgroundLeft = x - paddingLeft + offsetX;
            float backgroundRight = x + textWidth + paddingRight + offsetX;
            float backgroundTop = y - 1.0f;
            float backgroundBottom = y + 9.0f;
            float backgroundZ = -0.01f;

            RenderType backgroundType = switch (displayMode) {
                case NORMAL -> RenderTypes.text(WHITE_TEXTURE);
                case SEE_THROUGH -> RenderTypes.textSeeThrough(WHITE_TEXTURE);
                case POLYGON_OFFSET -> RenderTypes.textPolygonOffset(WHITE_TEXTURE);
            };

            VertexConsumer background = bufferSource.getBuffer(backgroundType);
            background.addVertex(pose, backgroundLeft, backgroundBottom, backgroundZ).setColor(backgroundColor).setUv(0.0f, 0.0f).setLight(lightCoords);
            background.addVertex(pose, backgroundRight, backgroundBottom, backgroundZ).setColor(backgroundColor).setUv(0.0f, 0.0f).setLight(lightCoords);
            background.addVertex(pose, backgroundRight, backgroundTop, backgroundZ).setColor(backgroundColor).setUv(0.0f, 0.0f).setLight(lightCoords);
            background.addVertex(pose, backgroundLeft, backgroundTop, backgroundZ).setColor(backgroundColor).setUv(0.0f, 0.0f).setLight(lightCoords);
        }

        font.drawInBatch(Component.empty(), x + offsetX, y, 0x00000000, false, pose, bufferSource, displayMode, 0x00000000, lightCoords);

        /* Pass 2: Icon & Text */
        if (showIcon) {
            float iconRight = x - gap + offsetX;
            float iconLeft = iconRight - iconWidth;
            float iconTop = y;
            float iconBottom = y + iconWidth;
            float iconZ = 0.0f;

            RenderType iconType = switch (displayMode) {
                case NORMAL -> RenderTypes.text(ICON_TEXTURE);
                case SEE_THROUGH -> RenderTypes.textSeeThrough(ICON_TEXTURE);
                case POLYGON_OFFSET -> RenderTypes.textPolygonOffset(ICON_TEXTURE);
            };

            VertexConsumer icon = bufferSource.getBuffer(iconType);
            icon.addVertex(pose, iconLeft, iconBottom, iconZ).setColor(color).setUv(0.0f, 1.0f).setLight(lightCoords);
            icon.addVertex(pose, iconRight, iconBottom, iconZ).setColor(color).setUv(1.0f, 1.0f).setLight(lightCoords);
            icon.addVertex(pose, iconRight, iconTop, iconZ).setColor(color).setUv(1.0f, 0.0f).setLight(lightCoords);
            icon.addVertex(pose, iconLeft, iconTop, iconZ).setColor(color).setUv(0.0f, 0.0f).setLight(lightCoords);
        }

        font.drawInBatch(text, x + offsetX, y, color, nameplateConfig.getShowTextShadow(), pose, bufferSource, displayMode, 0x00000000, lightCoords);
    }

    @Unique
    private static boolean hasIconMarker(Component text) {
        if (Tweakery.NAMEPLATE_ICON_INSERTION.equals(text.getStyle().getInsertion())) {
            return true;
        }

        for (Component sibling : text.getSiblings()) {
            if (hasIconMarker(sibling)) {
                return true;
            }
        }

        return false;
    }
}