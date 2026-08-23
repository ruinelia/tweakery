package com.ruineko.tweakery.mixin;

import com.ruineko.tweakery.feature.Zoom;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void tweakery$adjustFov(Camera camera, float f, boolean bl, CallbackInfoReturnable<Float> cir) {
        float fov = cir.getReturnValue();

        cir.setReturnValue(Zoom.INSTANCE.apply(fov, f));
    }
}
