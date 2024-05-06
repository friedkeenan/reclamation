package io.github.friedkeenan.reclamation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.friedkeenan.reclamation.PossibleDeathItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

@Mixin(ItemEntity.class)
public abstract class EnableDeathItems extends Entity implements PossibleDeathItem {
    private static final EntityDataAccessor<Boolean> DATA_DEATH_ITEM = SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.BOOLEAN);

    private static final String DEATH_ITEM_TAG = "DeathItem";

    protected EnableDeathItems(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isDeathItem() {
        return this.entityData.get(DATA_DEATH_ITEM);
    }

    @Override
    public void setDeathItem(boolean death_item) {
        this.entityData.set(DATA_DEATH_ITEM, death_item);
    }

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    private void syncDeathItem(SynchedEntityData.Builder builder, CallbackInfo info) {
        builder.define(DATA_DEATH_ITEM, false);
    }

    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    private void saveDeathItem(CompoundTag data, CallbackInfo info) {
        data.putBoolean(DEATH_ITEM_TAG, this.isDeathItem());
    }

    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    private void readDeathItem(CompoundTag data, CallbackInfo info) {
        this.setDeathItem(data.getBoolean(DEATH_ITEM_TAG));
    }
}
