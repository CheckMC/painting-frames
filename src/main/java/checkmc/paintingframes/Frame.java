package checkmc.paintingframes;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;

public class Frame {

    private ItemStack dropItem;
    private Color color;

    public Frame(ItemStack returnItem, Color frameColor) {
        dropItem = returnItem;
        color = frameColor;
    }

    public ItemStack getDropItem() {
        return dropItem;
    }

    public Color getColor() {
        return color;
    }

    public int getColorInt() {
        return color.getRGB();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Frame) {
            return color.equals(((Frame) other).getColor());
        }
        return false;
    }

    @Override
    public String toString() {
        return dropItem.getItem().toString() + " Frame";
    }
}
