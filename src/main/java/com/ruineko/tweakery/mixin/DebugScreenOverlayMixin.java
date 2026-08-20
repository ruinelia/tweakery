package com.ruineko.tweakery.mixin;

import com.ruineko.tweakery.config.DebugConfig;
import com.ruineko.tweakery.config.TweakeryConfig;
import com.ruineko.tweakery.text.Text;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Unique
    private static final DebugConfig DEBUG_CONFIG = TweakeryConfig.Companion.getHANDLER().instance().getDebug();

    @Redirect(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void tweakery$fill(GuiGraphics instance, int i, int j, int k, int l, int m) {
        if (DEBUG_CONFIG.getBackground()) {
            instance.fill(i, j, k, l, m);
        }
    }

    @Redirect(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V"))
    private void tweakery$renderLines(GuiGraphics instance, Font font, String string, int i, int j, int k, boolean bl) {
        Text text = Text.Companion.decode(string);

        if (text.getColor() != null) {
            k = text.getColor();
        }

        if (DEBUG_CONFIG.getShadow()) {
            bl = true;
        }

        if (!text.getValue().equals(string)) {
            i += font.width(string) - font.width(text.getValue());
        }

        instance.drawString(font, text.getValue(), i, j, k, bl);
    }
}
