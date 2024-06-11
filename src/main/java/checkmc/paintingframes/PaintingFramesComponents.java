package checkmc.paintingframes;

import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentFactory;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class PaintingFramesComponents implements EntityComponentInitializer {

    public static final ComponentKey<StringComponent> FRAME_TYPE =
            ComponentRegistry.getOrCreate(new Identifier("paintingframes", "frame_type"), StringComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        ComponentFactory<PaintingEntity, FrameComponent> factory = new ComponentFactory<PaintingEntity, FrameComponent>() {
            @Override
            public @NotNull FrameComponent createComponent(PaintingEntity paintingEntity) {
                return new FrameComponent(paintingEntity);
            }
        };

        registry.registerFor(PaintingEntity.class, FRAME_TYPE, factory);
    }
}
