package checkmc.paintingframes;

import checkmc.paintingframes.components.FrameComponent;
import checkmc.paintingframes.components.PaintingFramesComponents;
import checkmc.paintingframes.components.PaintingUnlocksComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PaintingTable extends Block {
    public PaintingTable(Settings settings) {
        super(settings);
    }

}
