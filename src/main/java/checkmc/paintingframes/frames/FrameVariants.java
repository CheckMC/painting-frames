package checkmc.paintingframes.frames;

import checkmc.paintingframes.PaintingFrames;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FrameVariants {

    private static final Map<String, checkmc.paintingframes.frames.Frame> frameDict = new HashMap<String, checkmc.paintingframes.frames.Frame>();

    // each frame key is the same as the item id that it drops.
    public static void initDict() {
        addNewFrame(new Color(157,157,177), Items.LIGHT_GRAY_DYE, "light_gray_dye");
        addNewFrame(new Color(177,177,177), Items.GRAY_DYE, "gray_dye");
        addNewFrame(new Color(39,38,60), Items.BLACK_DYE, "black_dye");
        addNewFrame(new Color(186,118,71), Items.BROWN_DYE, "brown_dye");
        addNewFrame(new Color(248, 75, 75), Items.RED_DYE, "red_dye");
        addNewFrame(new Color(255, 118, 66), Items.ORANGE_DYE, "orange_dye");
        addNewFrame(new Color(243, 196, 10), Items.YELLOW_DYE, "yellow_dye");
        addNewFrame(new Color(175, 255, 55), Items.LIME_DYE, "lime_dye");
        addNewFrame(new Color(6, 136, 0), Items.GREEN_DYE, "green_dye");
        addNewFrame(new Color(38, 136, 155), Items.CYAN_DYE, "cyan_dye");
        addNewFrame(new Color(128, 227, 250), Items.LIGHT_BLUE_DYE, "light_blue_dye");
        addNewFrame(new Color(41, 91, 206), Items.BLUE_DYE, "blue_dye");
        addNewFrame(new Color(162, 13, 208), Items.PURPLE_DYE, "purple_dye");
        addNewFrame(new Color(217, 63, 234), Items.MAGENTA_DYE, "magenta_dye");
        addNewFrame(new Color(246, 127, 224), Items.PINK_DYE, "pink_dye");
        addNewFrame(new Color(252,252,252), Items.WHITE_DYE, "white_dye");
    }

    public static void addNewFrame(Color color, Item item, String key) {
        checkmc.paintingframes.frames.Frame frame = new checkmc.paintingframes.frames.Frame(new ItemStack(item), color);
        frameDict.put(key, frame);
    }

    public static checkmc.paintingframes.frames.Frame getFrame(String key) {
        //PaintingFrames.LOGGER.info("Key "+key+" does not exist in frame dictionary.");
        return frameDict.get(key);
    }

    public static String getKey(checkmc.paintingframes.frames.Frame frame) {
        for (String key : frameDict.keySet()) {
            if (frameDict.get(key).equals(frame)) {
                return key;
            }
        }
        return "none";
    }

    public static Frame frameFromItem(Item item) {
        for (checkmc.paintingframes.frames.Frame frame : frameDict.values()) {
            if (frame.getDropItem().getItem().equals(item)) {
                return frame;
            }
        }
        return null;
    }

}

