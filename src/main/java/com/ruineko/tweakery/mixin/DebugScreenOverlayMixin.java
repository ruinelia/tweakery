package com.ruineko.tweakery.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ruineko.tweakery.Tweakery;
import com.ruineko.tweakery.color.TweakeryColors;
import com.ruineko.tweakery.text.Text;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin {
    @WrapOperation(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;width(Ljava/lang/String;)I"))
    private int tweakery$measureVisibleWidth(Font instance, String string, Operation<Integer> original) {
        return original.call(instance, Text.Companion.decode(string).getValue());
    }

    @WrapOperation(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void tweakery$drawLineBackground(GuiGraphics instance, int i, int j, int k, int l, int m, Operation<Void> original) {
        if (!Tweakery.CONFIG.getDebug().getShowTextBackground()) {
            return;
        }

        original.call(instance, i, j, k, l, m);
    }

    @WrapOperation(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V"))
    private void tweakery$drawLineText(GuiGraphics instance, Font font, String string, int i, int j, int k, boolean bl, Operation<Void> original) {
        Text text = Text.Companion.decode(string);

        int color = text.getColor() != null ? text.getColor() : k;
        boolean shadow = Tweakery.CONFIG.getDebug().getShowTextShadow() || text.getShadow();

        original.call(instance, font, text.getValue(), i, j, color, shadow);
    }
}
