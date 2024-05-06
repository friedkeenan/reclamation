package io.github.friedkeenan.reclamation.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.friedkeenan.reclamation.ReclamationClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

@Mixin(Entity.class)
public class RenderDeathItemsFurther {
    @Inject(at = @At("HEAD"), method = "shouldRenderAtSqrDistance", cancellable = true)
    private void renderFurther(double dist_sq, CallbackInfoReturnable<Boolean> info) {
        if (ReclamationClient.ShouldHaveSpectralScopeGlow(Minecraft.getInstance(), (Entity) (Object) this))

        if ((Object) this instanceof ItemEntity) {
            info.setReturnValue(true);
        }
    }
}
