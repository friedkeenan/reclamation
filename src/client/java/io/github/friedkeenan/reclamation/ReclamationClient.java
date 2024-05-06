package io.github.friedkeenan.reclamation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class ReclamationClient {
	public static final ModelResourceLocation SPECTRAL_SPYGLASS_MODEL         = new ModelResourceLocation("reclamation", "spectral_spyglass",         "inventory");
    public static final ModelResourceLocation SPECTRAL_SPYGLASS_IN_HAND_MODEL = new ModelResourceLocation("reclamation", "spectral_spyglass_in_hand", "inventory");

    public static ResourceLocation SPECTRAL_SPYGLASS_SCOPE_LOCATION = new ResourceLocation("reclamation:textures/misc/spectral_spyglass_scope.png");

	public static final int DEATH_ITEM_GLOW_COLOR = 0x74F1F5;

	private static boolean IsSpectralScoping(Player player) {
        return player.isUsingItem() && player.getUseItem().is(Reclamation.SPECTRAL_SPYGLASS);
    }

	public static boolean ShouldHaveSpectralScopeGlow(Minecraft minecraft, Entity entity) {
		if (!(entity instanceof ItemEntity)) {
            return false;
        }

        if (minecraft.player == null) {
            return false;
        }

        if (!IsSpectralScoping(minecraft.player)) {
            return false;
        }

        if (!minecraft.options.getCameraType().isFirstPerson()) {
            return false;
        }

        if (minecraft.options.hideGui) {
            return false;
        }

        return ((PossibleDeathItem) entity).isDeathItem();
	}
}
