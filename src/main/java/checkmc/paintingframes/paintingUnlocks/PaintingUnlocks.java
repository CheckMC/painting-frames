package checkmc.paintingframes.paintingUnlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class PaintingUnlocks {
    // Entity killed : painting id

    private static final Map<Class<? extends Entity>, Identifier> entityDict = new HashMap<>();

    public static void initMobDict() {
        entityDict.put(EnderDragonEntity.class, Identifier.of("paintingframes:vanquished"));
        entityDict.put(ZombieEntity.class, Identifier.of("paintingframes:warden"));
    }

    // Structure dict?

    public static String identiferToText(Identifier id) {
        String toReturn = null;
        if (id.equals(Identifier.of("paintingframes:warden"))) {
            return "Warden";
        }
        return null;
    }


    public static Identifier getIdentifier(Entity entity) {
        return entityDict.get(entity.getClass());
    }
}
