package io.github.friedkeenan.reclamation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.friedkeenan.reclamation.DeathItemLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;

@Mixin(Fox.class)
public abstract class TrackDroppingDeathItemsForFoxes extends Animal {
    protected TrackDroppingDeathItemsForFoxes(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "dropAllDeathLoot")
    private void onStartDroppingDeathItems(DamageSource damage, CallbackInfo info) {
        if (this.level() instanceof ServerLevel) {
            ((DeathItemLevel) this.level()).setDroppingDeathItems(true);
        }
    }

    @Inject(at = @At("RETURN"), method = "dropAllDeathLoot")
    private void onStopDroppingDeathItems(DamageSource damageSource, CallbackInfo info) {
        if (this.level() instanceof ServerLevel) {
            ((DeathItemLevel) this.level()).setDroppingDeathItems(false);
        }
    }
}
