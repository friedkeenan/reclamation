package io.github.friedkeenan.reclamation.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import io.github.friedkeenan.reclamation.Reclamation;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin(PlayerItemInHandLayer.class)
public class ExtendRenderArm {
    @WrapOperation(
        at = @At(
            value  = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
        ),

        method = "renderArmWithItem"
    )
    private boolean extendForSpectralSpyglass(ItemStack item, Item spyglass, Operation<Boolean> original) {
        return item.is(Reclamation.SPECTRAL_SPYGLASS) || original.call(item, spyglass);
    }
}
