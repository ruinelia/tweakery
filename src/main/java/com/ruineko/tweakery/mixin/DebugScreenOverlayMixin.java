package com.ruineko.tweakery.mixin;

import com.google.common.base.Strings;
import com.ruineko.tweakery.config.DebugConfig;
import com.ruineko.tweakery.config.TweakeryConfig;
import com.ruineko.tweakery.text.Text;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.*;

import java.awt.*;
import java.util.List;
import java.util.Objects;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Unique
    private static final DebugConfig DEBUG_CONFIG = TweakeryConfig.Companion.getHANDLER().instance().getDebug();

    @Shadow
    @Final
    private Font font;

    /**
     * @author Ruineko
     * @reason Replace vanilla rendering logic to support Tweakery text decoding
     */
    @Overwrite
    private void renderLines(GuiGraphics guiGraphics, List<String> list, boolean bl) {
        Objects.requireNonNull(this.font);
        int lineHeight = 9;

        for (int j = 0; j < list.size(); ++j) {
            String string = list.get(j);

            if (!Strings.isNullOrEmpty(string)) {
                Text text = Text.Companion.decode(string);

                int width = this.font.width(text.getValue());
                int x = bl ? 2 : guiGraphics.guiWidth() - 2 - width;
                int y = 2 + lineHeight * j;

                if (DEBUG_CONFIG.getBackground()) {
                    guiGraphics.fill(x - 1, y - 1, x + width + 1, y + lineHeight - 1, -1873784752);
                }
            }
        }

        for (int j = 0; j < list.size(); ++j) {
            String string = list.get(j);

            if (!Strings.isNullOrEmpty(string)) {
                Text text = Text.Companion.decode(string);

                int width = this.font.width(text.getValue());
                int x = bl ? 2 : guiGraphics.guiWidth() - 2 - width;
                int y = 2 + lineHeight * j;

                int color = text.getColor() != null ? text.getColor() : -2039584;

                boolean shadow = DEBUG_CONFIG.getShadow() || text.getShadow();

                guiGraphics.drawString(this.font, text.getValue(), x, y, color, shadow);
            }
        }
    }
}
