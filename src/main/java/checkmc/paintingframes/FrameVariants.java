package checkmc.paintingframes;

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

    public static void initDict() {
        frameDict.put("gold", goldFrame);
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
        return "Not found";
    }
}

