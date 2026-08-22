package com.ruineko.tweakery.mixin;

import com.ruineko.tweakery.Tweakery;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public class PlayerMixin {
    @ModifyArg(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/scores/PlayerTeam;formatNameForTeam(Lnet/minecraft/world/scores/Team;Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;"), index = 1)
    private Component tweakery$addNameplateIconMarker(Component component) {
        Player player = (Player)(Object)this;

        if (!Tweakery.hasTweakery(player)) {
            return component;
        }

        String nickname = Tweakery.CONFIG.getPrivacy().getNickname();
        Component name = nickname.isEmpty() ? component : Component.literal(nickname);

        return name.copy().withStyle(style -> style.withInsertion(Tweakery.NAMEPLATE_ICON_INSERTION));
    }
}
