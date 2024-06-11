package checkmc.paintingframes.mixin;

import checkmc.paintingframes.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
			// Make sure frame is not null and that it isn't the current one.
			if (frame != null && !frame.equals(FrameVariants.getFrame(painting.getComponent(PaintingFramesComponents.FRAME_TYPE).getValue()))) {
				// Frame to add
				PaintingFrames.LOGGER.info(frame.toString());
				// Decrementing item stack (frame is being applied)
				if (!player.getAbilities().creativeMode) {
					player.getMainHandStack().decrement(1);
				}

				FrameComponent frameComponent = (FrameComponent) painting.getComponent(PaintingFramesComponents.FRAME_TYPE);
				// Dropping the current frame item before applying the new one
				if (FrameVariants.getFrame(frameComponent.getValue()) != null) {
					//ItemEntity itemEntity = new ItemEntity(player.getWorld(), painting.getX()+0.5, painting.getY()+0.5, painting.getZ()+0.5, FrameVariants.getFrame(frameComponent.getValue()).getDropItem());
					//player.getWorld().spawnEntity(itemEntity);

					painting.dropItem(FrameVariants.getFrame(frameComponent.getValue()).getDropItem().getItem());
				}


				// Set the new frame value
				frameComponent.setValue(frame);
			}

		}
		// To cancel the interaction and provide a custom result:
		cir.setReturnValue(ActionResult.SUCCESS);
	}
}