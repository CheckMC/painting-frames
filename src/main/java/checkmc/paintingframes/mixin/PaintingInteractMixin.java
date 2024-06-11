package checkmc.paintingframes.mixin;

import checkmc.paintingframes.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.ladysnake.cca.api.v3.component.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class PaintingInteractMixin {

	// override     public ActionResult interact(PlayerEntity player, Hand hand) in PaintingEntity

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		// Your custom code here
		if ((Object) this instanceof PaintingEntity painting) {

			// Item player used on the painting
			Item itemUsed = player.getMainHandStack().getItem();
			PaintingFrames.LOGGER.info("Painting interacted with. Item = " + itemUsed);

			// Finding the frame to add
			Frame frame = FrameVariants.frameFromItem(itemUsed);
			if (frame != null) {
				// Frame to add
				PaintingFrames.LOGGER.info(frame.toString());
				// Decrementing item stack (frame is being applied)
				if (!player.getAbilities().creativeMode) {
					player.getMainHandStack().decrement(1);
				}
				Component frameComponent = painting.getComponent(PaintingFramesComponents.FRAME_TYPE);
			}

		}
		// To cancel the interaction and provide a custom result:
		cir.setReturnValue(ActionResult.SUCCESS);
	}
}