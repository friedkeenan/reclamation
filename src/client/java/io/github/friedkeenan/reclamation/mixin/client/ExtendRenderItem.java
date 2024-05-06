package io.github.friedkeenan.reclamation.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;

import io.github.friedkeenan.reclamation.Reclamation;
import io.github.friedkeenan.reclamation.ReclamationClient;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemRenderer.class)
public class ExtendRenderItem {
    @WrapOperation(
        at = @At(
            value   = "INVOKE",
            target  = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",
            ordinal = 1
        ),

        method = "render"
    )
    private boolean extendFlatCheckForSpectralSpyglass(
        ItemStack item, Item spyglass, Operation<Boolean> original,

        @Share("is_spectral") LocalBooleanRef is_spectral
    ) {
        if (item.is(Reclamation.SPECTRAL_SPYGLASS)) {
            is_spectral.set(true);

            return true;
        }

        return original.call(item, spyglass);
    }

    @ModifyArg(
        at = @At(
            value   = "INVOKE",
            target  = "Lnet/minecraft/client/resources/model/ModelManager;getModel(Lnet/minecraft/client/resources/model/ModelResourceLocation;)Lnet/minecraft/client/resources/model/BakedModel;",
            ordinal = 1
        ),

        method = "render"
    )
    private ModelResourceLocation extendFlatModelForSpectralSpyglass(
        ModelResourceLocation original,

        @Share("is_spectral") LocalBooleanRef is_spectral
    ) {
        if (is_spectral.get()) {
            return ReclamationClient.SPECTRAL_SPYGLASS_MODEL;
        }

        return original;
    }

    @WrapOperation(
        at = @At(
            value   = "INVOKE",
            target  = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",
            ordinal = 1
        ),

        method = "getModel"
    )
    private boolean extendRoundedCheckForSpectralSpyglass(
        ItemStack item, Item spyglass, Operation<Boolean> original,

        @Share("is_spectral") LocalBooleanRef is_spectral
    ) {
        if (item.is(Reclamation.SPECTRAL_SPYGLASS)) {
            is_spectral.set(true);

            return true;
        }

        return original.call(item, spyglass);
    }

    @ModifyArg(
        at = @At(
            value   = "INVOKE",
            target  = "Lnet/minecraft/client/resources/model/ModelManager;getModel(Lnet/minecraft/client/resources/model/ModelResourceLocation;)Lnet/minecraft/client/resources/model/BakedModel;",
            ordinal = 1
        ),

        method = "getModel"
    )
    private ModelResourceLocation extendRoundedModelForSpectralSpyglass(
        ModelResourceLocation original,

        @Share("is_spectral") LocalBooleanRef is_spectral
    ) {
        if (is_spectral.get()) {
            return ReclamationClient.SPECTRAL_SPYGLASS_IN_HAND_MODEL;
        }

        return original;
    }
}
