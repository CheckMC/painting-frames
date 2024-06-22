package checkmc.paintingframes.components;

import checkmc.paintingframes.componentInterfaces.ListComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.Arrays;

public class PaintingUnlocksComponent implements ListComponent, AutoSyncedComponent {

    private ArrayList<String> unlockedPaintingVariants;
    private Entity entity;

    public PaintingUnlocksComponent(Entity entity) {
        this.entity = entity;
    }

    @Override
    public ArrayList<Identifier> getValue() {
        // return an identifer list
        ArrayList<Identifier> returnList = new ArrayList<>();
        if (unlockedPaintingVariants == null) {
            return new ArrayList<Identifier>();
        }
        for (String str : unlockedPaintingVariants) {
            returnList.add(Identifier.of(str));
        }

        return returnList;
    }

    @Override
    public void addValue(String add) {
        if (!unlockedPaintingVariants.contains(add)) {
            unlockedPaintingVariants.add(add);
        }
        PaintingFramesComponents.PAINTING_UNLOCKS.sync(entity);
    }

    /**
     * Reads this component's properties from a {@link NbtCompound}.
     *
     * @param tag            a {@code NbtCompound} on which this component's serializable data has been written
     * @param registryLookup access to dynamic registry data
     * @implNote implementations should not assert that the data written on the tag corresponds to any
     * specific scheme, as saved data is susceptible to external tempering, and may come from an earlier
     * version.
     */
    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        // Convert string to ArrayList
        String[] elements = tag.getString("painting_variant_unlocks").split(",");
        this.unlockedPaintingVariants = new ArrayList<>(Arrays.asList(elements));
    }

    /**
     * Writes this component's properties to a {@link NbtCompound}.
     *
     * @param tag            a {@code NbtCompound} on which to write this component's serializable data
     * @param registryLookup access to dynamic registry data
     */
    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        // Convert ArrayList to a single string, then put
        StringBuilder builder = new StringBuilder();
        if (unlockedPaintingVariants == null) {
            tag.putString("painting_variant_unlocks","");
            return;
        }
        for (String string : unlockedPaintingVariants) {
            builder.append(string).append(",");
        }
        // Remove the last comma
        if (!builder.isEmpty()) {
            builder.setLength(builder.length() - 1);
        }

        tag.putString("painting_variant_unlocks",builder.toString());
    }
}
