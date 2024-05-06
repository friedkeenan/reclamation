package io.github.friedkeenan.reclamation.data;

import java.util.concurrent.CompletableFuture;

import io.github.friedkeenan.reclamation.Reclamation;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class ReclamationDataGenerator implements DataGeneratorEntrypoint {
    private static class ReclamationRecipeProvider extends FabricRecipeProvider {
        public ReclamationRecipeProvider(FabricDataOutput output, CompletableFuture<Provider> future) {
            super(output, future);
        }

        @Override
        public void buildRecipes(RecipeOutput output) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Reclamation.SPECTRAL_SPYGLASS)
                .define('S', Items.SPYGLASS)
                .define('E', Items.ECHO_SHARD)
                .pattern(" E ")
                .pattern("ESE")
                .pattern(" E ")
                .unlockedBy("has_echo_shard", RecipeProvider.has(Items.ECHO_SHARD))
                .save(output);
        }

    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        final var pack = generator.createPack();

        pack.addProvider(ReclamationRecipeProvider::new);
    }
}
