package checkmc.paintingframes;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

// This basically stores a Frame by converting it back and forth between strings and frame
public class FrameComponent implements StringComponent {
    private Frame frameType;

    public FrameComponent() {}

    public FrameComponent(Frame frame) {
        this.frameType = frame;
    }

    public void setFrameType(Frame frame) {
        this.frameType = frame;
    }

    @Override
    public String getValue() {
        return FrameVariants.getKey(frameType);
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.frameType = FrameVariants.getFrame(tag.getString("frameType"));
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putString("frameType", FrameVariants.getKey(frameType));
    }
}
