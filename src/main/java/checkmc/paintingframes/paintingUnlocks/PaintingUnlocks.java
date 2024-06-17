package checkmc.paintingframes.paintingUnlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class PaintingUnlocks {
    // Entity killed : painting id

    private static final Map<Class<? extends Entity>, Identifier> entityDict = new HashMap<>();

    public static void initDict() {
        entityDict.put(EnderDragonEntity.class, Identifier.of("paintingframes:vanquished"));
        entityDict.put(ZombieEntity.class, Identifier.of("paintingframes:warden"));
    }

    public static Identifier getIdentifier(Entity entity) {
        return entityDict.get(entity.getClass());
    }
}
