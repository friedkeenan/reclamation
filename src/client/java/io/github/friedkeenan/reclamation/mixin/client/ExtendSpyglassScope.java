package io.github.friedkeenan.reclamation.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import io.github.friedkeenan.reclamation.Reclamation;
import io.github.friedkeenan.reclamation.ReclamationClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;

@Mixin(Gui.class)
public class ExtendSpyglassScope {
    @Shadow
    @Final
    Minecraft minecraft;

    @ModifyArg(
        at = @At(
            value  = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V"
        ),

        method = "renderSpyglassOverlay"
    )
    private ResourceLocation extendForSpectralSpyglass(ResourceLocation original) {
        if (this.minecraft.player.getUseItem().is(Reclamation.SPECTRAL_SPYGLASS)) {
            return ReclamationClient.SPECTRAL_SPYGLASS_SCOPE_LOCATION;
        }

        return original;
    }
}
