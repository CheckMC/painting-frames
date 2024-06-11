package checkmc.paintingframes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FrameVariants {

    private static Map<String, Frame> frameDict = new HashMap<String, Frame>();
    public static Frame goldFrame = new Frame(new ItemStack(Items.GOLD_INGOT), new Color(250,214,74));
    public static Frame diamondFrame = new Frame(new ItemStack(Items.DIAMOND), new Color(41, 204, 255));
    public static Frame ironFrame = new Frame(new ItemStack(Items.IRON_INGOT), new Color(188, 188, 188));
    public static Frame copperFrame = new Frame(new ItemStack(Items.COPPER_INGOT), new Color(255, 154, 21));
    public static Frame oakFrame = new Frame(new ItemStack(Items.OAK_PLANKS), new Color(145, 117, 77));
    public static Frame birchFrame = new Frame(new ItemStack(Items.BIRCH_PLANKS), new Color(202, 192, 141));

    // each frame key is the same as the item id that it drops.
    public static void initDict() {
        frameDict.put("gold_ingot", goldFrame);
        frameDict.put("diamond", diamondFrame);
        frameDict.put("iron_ingot", ironFrame);
        frameDict.put("copper_ingot", copperFrame);
        frameDict.put("oak_planks", oakFrame);
        frameDict.put("birch_planks", birchFrame);
    }

    public static Frame getFrame(String key) {
        if (!frameDict.containsKey(key)) {
            PaintingFrames.LOGGER.info("Key "+key+" does not exist in frame dictionary.");
        }
        return frameDict.get(key);
    }

    public static String getKey(Frame frame) {
        for (String key : frameDict.keySet()) {
            if (frameDict.get(key).equals(frame)) {
                return key;
            }
        }
        return "none";
    }

    public static Frame frameFromItem(Item item) {
        for (Frame frame : frameDict.values()) {
            if (frame.getDropItem().getItem().equals(item)) {
                return frame;
            }
        }
        return null;
    }

}

