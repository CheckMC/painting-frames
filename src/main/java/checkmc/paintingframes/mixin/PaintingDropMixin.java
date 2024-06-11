package checkmc.paintingframes.mixin;

import checkmc.paintingframes.FrameComponent;
import checkmc.paintingframes.FrameVariants;
import checkmc.paintingframes.PaintingFramesComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PaintingEntity.class)
public abstract class PaintingDropMixin extends AbstractDecorationEntity {

    protected PaintingDropMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onBreak", at = @At("TAIL"))
    public void onBreak(Entity entity, CallbackInfo ci) {
        if (this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            if (!Objects.equals(this.getComponent(PaintingFramesComponents.FRAME_TYPE).getValue(), "none")) {
                FrameComponent frameComponent = (FrameComponent) this.getComponent(PaintingFramesComponents.FRAME_TYPE);
                Item itemDrop = FrameVariants.getFrame(frameComponent.getValue()).getDropItem().getItem();
                this.dropItem(itemDrop);
            }
        }
    }
}