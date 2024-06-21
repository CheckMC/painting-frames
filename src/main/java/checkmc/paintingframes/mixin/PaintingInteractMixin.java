package checkmc.paintingframes.mixin;

import checkmc.paintingframes.*;
import checkmc.paintingframes.components.FrameComponent;
import checkmc.paintingframes.components.PaintingFramesComponents;
import checkmc.paintingframes.frames.Frame;
import checkmc.paintingframes.frames.FrameVariants;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.client.texture.PaintingManager;
import net.minecraft.component.Component;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.PaintingVariantTags;
import net.minecraft.server.command.TitleCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Entity.class)
public abstract class PaintingInteractMixin {

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	private void onInteractPainting(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if ((Object) this instanceof PaintingEntity painting) {
			Item itemUsed = player.getMainHandStack().getItem();
			PaintingVariant thisVariant = painting.getVariant().value();

			PaintingFrames.LOGGER.info("Test Logger");

			// BRUSHING LOGIC -----------------------------------------------------------------------
            if (itemUsed.equals(Items.BRUSH)) {

                // Generate list of painting variants
                ArrayList<PaintingVariant> list = new ArrayList<>();
                player.getWorld().getRegistryManager().get(RegistryKeys.PAINTING_VARIANT).forEach(list::add);

				ArrayList<PaintingVariant> filteredList = new ArrayList<>();

				// Sort list down into paintings of the same size
				for (int i = 0; i < list.size(); i++) {
					PaintingVariant variant = list.get(i);
					// Remove the ones that don't match sizing
					if (variant.width() == thisVariant.width()) {
						if (variant.height() == thisVariant.height()) {
							filteredList.add(list.get(i));
						}
					}
				}

				// add any unlocked paintings to the list
				if (player instanceof ServerPlayerEntity) {
					ArrayList<Identifier> playerUnlocks = player.getComponent(PaintingFramesComponents.PAINTING_UNLOCKS).getValue();
					for (PaintingVariant entry : list) {
						if (playerUnlocks.contains(entry.assetId())) {
							if (entry.width() == thisVariant.width()) {
								if (entry.height() == thisVariant.height()) {
									filteredList.add(entry);
								}
							}
						}
					}
				}

				int indexOfCurrent = filteredList.indexOf(thisVariant);
				if (indexOfCurrent == filteredList.size()-1) {
					indexOfCurrent = 0;
				}

				PaintingVariant nextVariant = filteredList.get((indexOfCurrent+1));
				painting.setVariant(RegistryEntry.of(nextVariant));

				SoundEvent soundEvent = SoundEvents.ITEM_BRUSH_BRUSHING_GENERIC;
				player.getWorld().playSound(player, painting.getBlockPos(), soundEvent, SoundCategory.BLOCKS);
				PaintingFrames.LOGGER.info(filteredList.toString());
				cir.setReturnValue(ActionResult.SUCCESS);
			}

			// DYE LOGIC ------------------

			Frame frame = FrameVariants.frameFromItem(itemUsed);
			// Make sure frame is not null and that it isn't the current one.
			if (frame != null && !frame.equals(FrameVariants.getFrame(painting.getComponent(PaintingFramesComponents.FRAME_TYPE).getValue()))) {
				if (!player.getAbilities().creativeMode) {
					player.getMainHandStack().decrement(1);
				}

				FrameComponent frameComponent = (FrameComponent) painting.getComponent(PaintingFramesComponents.FRAME_TYPE);
				PaintingFrames.LOGGER.info("Painting dyed with " + frame.toString());
				frameComponent.setValue(frame);
				SoundEvent soundEvent = SoundEvents.ITEM_DYE_USE;
				player.getWorld().playSound(player, painting.getBlockPos(), soundEvent, SoundCategory.BLOCKS);
				cir.setReturnValue(ActionResult.SUCCESS);

			}
        }
    }

}