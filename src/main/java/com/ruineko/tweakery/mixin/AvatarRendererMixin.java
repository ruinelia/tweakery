package com.ruineko.tweakery.mixin;

import com.ruineko.tweakery.Tweakery;
import com.ruineko.tweakery.config.NameplateConfig;
import com.ruineko.tweakery.config.TweakeryConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin {
    @Unique
    private static final TweakeryConfig CONFIG = TweakeryConfig.Companion.getHANDLER().instance();

    @Inject(method = "shouldShowName(Lnet/minecraft/world/entity/Avatar;D)Z", at = @At("HEAD"), cancellable = true)
    private void tweakery$shouldShowName(Avatar entity, double distance, CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();

        NameplateConfig nameplateConfig = CONFIG.getNameplate();

        if (!nameplateConfig.getEnabled()) {
            return;
        }

        if (mc.player != null && entity == mc.player && nameplateConfig.getShowOwn()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void tweakery$extractRenderState(Avatar entity, AvatarRenderState state, float tickProgress, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;

        if (player != null && entity == player && state.nameTag != null) {
            Tweakery.currentPlayerNameTag = state.nameTag;
        }
    }
}