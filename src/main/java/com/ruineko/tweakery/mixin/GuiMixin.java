package com.ruineko.tweakery.mixin;

import com.ruineko.tweakery.Tweakery;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Inject(method = "displayScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void tweakery$showSidebar(GuiGraphics guiGraphics, Objective objective, CallbackInfo ci) {
        if (Tweakery.CONFIG.getSidebar().getShowSidebar()) {
            return;
        }

        ci.cancel();
    }

    @Redirect(method = "displayScoreboardSidebar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void tweakery$showSidebarBackground(GuiGraphics instance, int i, int j, int k, int l, int m) {
        if (!Tweakery.CONFIG.getSidebar().getShowTextBackground()) {
            return;
        }

        instance.fill(i, j, k, l, m);
    }

    @ModifyArg(method = "displayScoreboardSidebar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"), index = 5)
    private boolean tweakery$showSidebarTextShadow(boolean bl) {
        return Tweakery.CONFIG.getSidebar().getShowTextShadow();
    }
}
