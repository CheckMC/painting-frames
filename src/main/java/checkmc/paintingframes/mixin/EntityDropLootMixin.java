package checkmc.paintingframes.mixin;

import checkmc.paintingframes.PaintingFrames;
import checkmc.paintingframes.components.PaintingFramesComponents;
import checkmc.paintingframes.paintingUnlocks.PaintingUnlocks;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class EntityDropLootMixin extends LivingEntity {
    protected EntityDropLootMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropLoot", at = @At(value="HEAD"))
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
        // Check if entity is included in the dict
        Identifier paintingIdentifier = PaintingUnlocks.getIdentifier(this);
        if (causedByPlayer && paintingIdentifier != null) {
            damageSource.getAttacker().getComponent(PaintingFramesComponents.PAINTING_UNLOCKS).addValue(paintingIdentifier.toString());
            PaintingFrames.LOGGER.info("Added painting unlock " + paintingIdentifier.toString() +" to "+damageSource.getAttacker().getNameForScoreboard());

        }

    }
}
