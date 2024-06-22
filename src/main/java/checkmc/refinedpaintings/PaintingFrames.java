package checkmc.refinedpaintings;

import checkmc.refinedpaintings.componentInterfaces.StringComponent;
import checkmc.refinedpaintings.frames.FrameVariants;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaintingFrames implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("refinedpaintings");
	public static final ComponentKey<StringComponent> frame_type =
			ComponentRegistry.getOrCreate(Identifier.of("refinedpaintings", "frame_type"), StringComponent.class);


	//public static final Identifier whiteTexture

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		FrameVariants.initDict();
		//PaintingUnlocks.initMobDict();

		LOGGER.info("Refined Paintings is ready!");
	}
}