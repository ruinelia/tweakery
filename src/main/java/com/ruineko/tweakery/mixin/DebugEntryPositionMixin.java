package com.ruineko.tweakery.mixin;

import com.ruineko.tweakery.Tweakery;
import com.ruineko.tweakery.color.TweakeryColors;
import com.ruineko.tweakery.text.Text;
import net.minecraft.client.gui.components.debug.DebugEntryPosition;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(DebugEntryPosition.class)
public abstract class DebugEntryPositionMixin {
    @ModifyArg(method = "display", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/debug/DebugScreenDisplayer;addToGroup(Lnet/minecraft/resources/Identifier;Ljava/util/Collection;)V"), index = 1)
    private Collection<String> tweakery$hideCoordinates(Collection<String> collection) {
        if (!Tweakery.CONFIG.getPrivacy().getHideCoordinates()) {
            return collection;
        }

        List<String> modified = new ArrayList<>(collection);

        modified.subList(0, 3).clear();
        modified.add(0, new Text(Component.translatable("debug.tweakery.coordinates_protected").getString(), TweakeryColors.ACCENT, false).encode());
        modified.add(1, new Text(Component.translatable("debug.tweakery.coordinates_protected_hint").getString(), TweakeryColors.ACCENT, false).encode());

        return modified;
    }
}
