package io.github.friedkeenan.reclamation;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpyglassItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reclamation implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("reclamation");

	public static final Item SPECTRAL_SPYGLASS = Registry.register(
		BuiltInRegistries.ITEM,
		new ResourceLocation("reclamation:spectral_spyglass"),
		new SpyglassItem(new Item.Properties().stacksTo(1))
	);

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
			content.addAfter(Items.SPYGLASS, SPECTRAL_SPYGLASS);
		});

		LOGGER.info("reclamation initialized!");
	}
}
