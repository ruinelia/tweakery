package com.ruineko.tweakery.mixin;

import com.ruineko.tweakery.color.TweakeryColors;
import com.ruineko.tweakery.config.TweakeryConfig;
import com.ruineko.tweakery.text.Text;
import net.minecraft.client.gui.components.debug.DebugEntryPosition;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(DebugEntryPosition.class)
public class DebugEntryPositionMixin {
    @Unique
    private static final TweakeryConfig CONFIG = TweakeryConfig.Companion.getHANDLER().instance();

    @ModifyArg(method = "display", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/debug/DebugScreenDisplayer;addToGroup(Lnet/minecraft/resources/Identifier;Ljava/util/Collection;)V"), index = 1)
    private Collection<String> tweakery$modifyPositionInfo(Collection<String> collection) {
        if (!CONFIG.getPrivacy().getHideCoordinates()) {
            return collection;
        }

        List<String> modified = new ArrayList<>(collection);

        modified.subList(0, 3).clear();
        modified.add(0, new Text(Component.translatable("debug.tweakery.coordinates_protected").getString(), TweakeryColors.ACCENT, false, false, true).encode());
        modified.add(1, new Text(Component.translatable("debug.tweakery.coordinates_protected_hint").getString(), TweakeryColors.ACCENT, false, false, true).encode());

        return modified;
    }
}
