package checkmc.paintingframes;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaintingFrames implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("paintingframes");
	public static final ComponentKey<StringComponent> frame_type =
			ComponentRegistry.getOrCreate(Identifier.of("paintingframes", "frame_type"), StringComponent.class);

	public static final Block PAINTING_TABLE = new Block(AbstractBlock.Settings.copy(Blocks.LOOM));

	//public static final Identifier whiteTexture

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		FrameVariants.initDict();

		Registry.register(Registries.BLOCK, Identifier.of("paintingframes", "painting_table"), PAINTING_TABLE);
		Registry.register(Registries.ITEM, Identifier.of("paintingframes", "painting_table"), new BlockItem(PAINTING_TABLE, new Item.Settings()));

		//LOGGER.info("Hello Fabric world!");
	}
}