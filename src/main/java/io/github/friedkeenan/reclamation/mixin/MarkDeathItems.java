package io.github.friedkeenan.reclamation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.friedkeenan.reclamation.DeathItemLevel;
import io.github.friedkeenan.reclamation.PossibleDeathItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

@Mixin(ServerLevel.class)
public class MarkDeathItems implements DeathItemLevel {
    private boolean dropping_death_items = false;

    @Override
    public void setDroppingDeathItems(boolean dropping_death_items) {
        this.dropping_death_items = dropping_death_items;
    }

    @Inject(at = @At("RETURN"), method = "addFreshEntity")
    private void markDeathItems(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (!this.dropping_death_items) {
            return;
        }

        if (!info.getReturnValueZ()) {
            return;
        }

        if (!(entity instanceof ItemEntity)) {
            return;
        }

        final var item = (PossibleDeathItem) entity;

        item.setDeathItem(true);
    }
}
