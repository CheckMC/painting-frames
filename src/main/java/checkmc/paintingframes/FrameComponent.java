package checkmc.paintingframes;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

// This basically stores a Frame by converting it back and forth between strings and frame
public class FrameComponent implements StringComponent {
    private Frame frame_type;

    public FrameComponent() {}

    public FrameComponent(Frame frame) {
        this.frame_type = frame;
    }

    public void setValue(Frame frame) {
        this.frame_type = frame;
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
