package com.ruineko.tweakery.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ruineko.tweakery.Tweakery;
import com.ruineko.tweakery.config.PlayerConfig;
import com.ruineko.tweakery.record.PlayerPresence;
import com.ruineko.tweakery.serializer.JsonSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapOperation(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/scores/PlayerTeam;formatNameForTeam(Lnet/minecraft/world/scores/Team;Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent tweakery$customizeDisplayName(Team team, Component component, Operation<MutableComponent> original) {
        Player player = (Player) (Object) this;

//        if (!Tweakery.hasTweakery(player)) {
//            return original.call(team, component);
//        }

        PlayerConfig playerConfig = Tweakery.CONFIG.getPlayer();

        String nickname = playerConfig.getDisplayName();
        Component name = nickname.isEmpty() ? component : Component.literal(nickname);

        MutableComponent result;

        if (!playerConfig.getOverrideTeam()) {
            result = original.call(team, name);
        } else {
            result = Component.empty();

            String teamPrefix = playerConfig.getTeamPrefix();

            if (!teamPrefix.isEmpty()) {
                result.append(Component.literal(teamPrefix));
            } else if (team instanceof PlayerTeam playerTeam) {
                result.append(playerTeam.getPlayerPrefix());
            }

            Style nameStyle = component.getStyle().withColor(TextColor.fromRgb(playerConfig.getTeamColor().getRGB()));

            result.append(name.copy().setStyle(nameStyle));

            String teamSuffix = playerConfig.getTeamSuffix();

            if (!teamSuffix.isEmpty()) {
                result.append(Component.literal(teamSuffix));
            } else if (team instanceof PlayerTeam playerTeam) {
                result.append(playerTeam.getPlayerSuffix());
            }
        }

        Minecraft minecraft = Minecraft.getInstance();
        User user = minecraft.getUser();

        boolean local = player == minecraft.player;

        PlayerPresence presence = new PlayerPresence(user.getName(), user.getProfileId(), local);

        MutableComponent data = Component.empty().withStyle(style ->
                style.withInsertion(JsonSerializer.serialize(Tweakery.MOD_ID, presence))
        );

        return result.append(data);
    }
}
