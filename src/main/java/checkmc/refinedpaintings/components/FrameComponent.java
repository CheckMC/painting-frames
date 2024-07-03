package checkmc.refinedpaintings.components;

import checkmc.refinedpaintings.frames.Frame;
import checkmc.refinedpaintings.frames.FrameVariants;
import checkmc.refinedpaintings.componentInterfaces.StringComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

// This basically stores a Frame by converting it back and forth between strings and frame
public class FrameComponent implements StringComponent, AutoSyncedComponent {
    private Frame frame_type;
    private Entity entity;

    public FrameComponent() {}

    public FrameComponent(Entity entity) {
        this.entity = entity;
    }

    public void setValue(Frame frame) {
        this.frame_type = frame;
        PaintingFramesComponents.FRAME_TYPE.sync(entity);
    }

    @Override
    public String getValue() {
        return FrameVariants.getKey(frame_type);
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.frame_type = FrameVariants.getFrame(tag.getString("frame_type"));
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putString("frame_type", FrameVariants.getKey(frame_type));
    }
}
