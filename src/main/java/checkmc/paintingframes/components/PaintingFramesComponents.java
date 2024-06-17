package checkmc.paintingframes.components;

import checkmc.paintingframes.componentInterfaces.ListComponent;
import checkmc.paintingframes.componentInterfaces.StringComponent;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentFactory;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

import java.util.List;

public class PaintingFramesComponents implements EntityComponentInitializer {

    public static final ComponentKey<StringComponent> FRAME_TYPE =
            ComponentRegistry.getOrCreate(Identifier.of("paintingframes", "frame_type"), StringComponent.class);

    public static final ComponentKey<ListComponent> PAINTING_UNLOCKS =
            ComponentRegistry.getOrCreate(Identifier.of("paintingframes", "painting_variant_unlocks"), ListComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // Painting frame component for painting entity.
        ComponentFactory<PaintingEntity, FrameComponent> frameComponentFactory = new ComponentFactory<PaintingEntity, FrameComponent>() {
            @Override
            public @NotNull FrameComponent createComponent(PaintingEntity paintingEntity) {
                return new FrameComponent(paintingEntity);
            }
        };

        // Painting unlocks component for PlayerEntity
        ComponentFactory<ServerPlayerEntity, PaintingUnlocksComponent> unlocksComponentFactory = new ComponentFactory<ServerPlayerEntity, PaintingUnlocksComponent>() {
            @Override
            public @NotNull PaintingUnlocksComponent createComponent(ServerPlayerEntity playerEntity) {
                return new PaintingUnlocksComponent(playerEntity);
            }
        };

        registry.registerFor(PaintingEntity.class, FRAME_TYPE, frameComponentFactory);
        registry.registerFor(ServerPlayerEntity.class, PAINTING_UNLOCKS, unlocksComponentFactory);

    }
}
