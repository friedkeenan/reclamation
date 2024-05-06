package io.github.friedkeenan.reclamation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.friedkeenan.reclamation.DeathItemLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class TrackDroppingDeathItems extends Entity {
    protected TrackDroppingDeathItems(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    private boolean isFox() {
        return (Object) this instanceof Fox;
    }

    @Inject(at = @At("HEAD"), method = "dropAllDeathLoot")
    private void onStartDroppingDeathItems(DamageSource damage, CallbackInfo info) {
        /* NOTE: Foxes override this method but call the super method. */
        if (this.isFox()) {
            return;
        }

        if (this.level() instanceof ServerLevel) {
            ((DeathItemLevel) this.level()).setDroppingDeathItems(true);
        }
    }

    @Inject(at = @At("RETURN"), method = "dropAllDeathLoot")
    private void onStopDroppingDeathItems(DamageSource damageSource, CallbackInfo info) {
        if (this.isFox()) {
            return;
        }

        if (this.level() instanceof ServerLevel) {
            ((DeathItemLevel) this.level()).setDroppingDeathItems(false);
        }
    }
}
