package io.github.friedkeenan.reclamation.mixin.client;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.friedkeenan.reclamation.ReclamationClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelBakery.LoadedJson;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;

@Mixin(ModelBakery.class)
public abstract class AddRoundedSpectralSpyglassModel {
    @Shadow
    private void loadTopLevel(ModelResourceLocation model) {
        throw new AssertionError();
    }

    @Inject(
        at = @At(
            value   = "INVOKE",
            target  = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
            ordinal = 3
        ),

        method = "<init>"
    )
    private void addModel(
        BlockColors colors, ProfilerFiller profiler, Map<ResourceLocation, BlockModel> models, Map<ResourceLocation, List<LoadedJson>> block_states,

        CallbackInfo info
    ) {
        this.loadTopLevel(ReclamationClient.SPECTRAL_SPYGLASS_IN_HAND_MODEL);
    }
}
