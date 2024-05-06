package io.github.friedkeenan.reclamation.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;

import io.github.friedkeenan.reclamation.ReclamationClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;

@Mixin(LevelRenderer.class)
public class EnableGlowingItems {
    @WrapOperation(
        at = @At(
            value  = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z"
        ),

        method = "renderLevel"
    )
    private boolean makeDeathItemsGlow(
        Minecraft minecraft, Entity entity, Operation<Boolean> original,

        @Share("death_item") LocalBooleanRef is_death_item
    ) {
        if (original.call(minecraft, entity)) {
            return true;
        }

        if (ReclamationClient.ShouldHaveSpectralScopeGlow(minecraft, entity)) {
            /* NOTE: If we don't set this, it falls back to false. */
            is_death_item.set(true);

            return true;
        }

        return false;
    }

    @ModifyExpressionValue(
        at = @At(
            value  = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getTeamColor()I"
        ),

        method = "renderLevel"
    )
    private int colorDeathItemGlow(int original, @Share("death_item") LocalBooleanRef is_death_item) {
        if (!is_death_item.get()) {
            return original;
        }

        return ReclamationClient.DEATH_ITEM_GLOW_COLOR;
    }
}
