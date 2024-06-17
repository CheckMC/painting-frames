package checkmc.paintingframes.mixin;

import checkmc.paintingframes.components.FrameComponent;
import checkmc.paintingframes.frames.FrameVariants;
import checkmc.paintingframes.components.PaintingFramesComponents;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PaintingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Objects;
@Debug(export = true)
@Mixin(PaintingEntityRenderer.class)

public abstract class PaintingRenderingMixin extends EntityRenderer<PaintingEntity> {
    //@Shadow protected abstract void renderPainting(MatrixStack matrices, VertexConsumer vertexConsumer, PaintingEntity entity, int width, int height, Sprite paintingSprite, Sprite backSprite);

    protected PaintingRenderingMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render*", at = @At(value="INVOKE", target="Lnet/minecraft/client/util/math/MatrixStack;pop()V"))
    public void render(PaintingEntity paintingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        Identifier TEXTURE = Identifier.of("minecraft","textures/misc/white.png");
        //VertexConsumer vertexConsumerFrame = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(TEXTURE));
        VertexConsumer vertexConsumerFrame = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));

        paintingFramesRenderFrame(paintingEntity, f, g, matrixStack, vertexConsumerFrame, i);
    }

    @Unique
    private void paintingFramesRenderFrame(PaintingEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumer consumer, int i) {
        //Identifier TEXTURE = Identifier.of("minecraft","textures/misc/white.png");
        //VertexConsumer vertexConsumerCorrected = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(TEXTURE));
        //matrixStack.push();
        MatrixStack.Entry entry = matrixStack.peek();

        PaintingVariant paintingVariant = (PaintingVariant)entity.getVariant().value();

        int width = paintingVariant.width();
        int height = paintingVariant.height();

        //PaintingFrames.LOGGER.info("width = "+width+" height = "+height);

        FrameComponent frameComponent = (FrameComponent) entity.getComponent(PaintingFramesComponents.FRAME_TYPE);
        if (Objects.equals(frameComponent.getValue(), "none")) {
            //PaintingFrames.LOGGER.info("DOES NOT HAVE A FRAME");
            return;
        }

        Color color = FrameVariants.getFrame(frameComponent.getValue()).getColor();

        //MatrixStack.Entry entry = matrixStack.peek();
        float frameWidth = 0.0625F; // 1 pixel frame

        // width 1: f1 = -0.5, xStart = 0.5, xEnd = 0.5

        float f1 = (float)(-width) / 2.0F;
        float g1 = (float)(-height) / 2.0F;
        float z = 0.03125F;
        float xStart = -f1;
        float xEnd = f1;
        float yEnd = g1 + (float)height;
        Direction direction = entity.getHorizontalFacing();
        int light = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getBlockPos());

        switch (direction) {
            case NORTH:
                paintingFramesRenderBorder(entry, consumer, xStart, xEnd, g1, yEnd, z, 0, 0, -1, light, frameWidth, color);
                break;
            case SOUTH:
                paintingFramesRenderBorder(entry, consumer, xStart, xEnd, g1, yEnd, z, 0, 0, 1, light, frameWidth, color);
                break;
            case WEST:
                paintingFramesRenderBorder(entry, consumer, xStart, xEnd, g1, yEnd, z, -1, 0, 0, light, frameWidth, color);
                break;
            case EAST:
                paintingFramesRenderBorder(entry, consumer, xStart, xEnd, g1, yEnd, z, 1, 0, 0, light, frameWidth, color);
                break;
            default:
                break;
        }
        //matrixStack.pop();
    }

    @Unique
    private void paintingFramesRenderBorder(MatrixStack.Entry entry, VertexConsumer vertexConsumer, float xStart, float xEnd, float yStart, float yEnd, float z, int normalX, int normalY, int normalZ, int light, float frameWidth, Color color) {
        
        //PaintingFrames.LOGGER.info("xStart = "+xStart+" xEnd = "+xEnd);

        // Top border
        paintingFramesVertex1(entry, vertexConsumer, (float) (xStart-0.0625), yEnd, 0.0F, 1.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, (float) (xStart-0.0625), yEnd - frameWidth, 0.0F, 0.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, (float) (xEnd+0.0625), yEnd - frameWidth, 1.0F, 0.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, (float) (xEnd+0.0625), yEnd, 1.0F, 1.0F, z, normalX, normalY, normalZ, light, color);

        // Bottom border
        paintingFramesVertex1(entry, vertexConsumer, (float) (xStart-0.0625), yStart + frameWidth, 0.0F, 0.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, (float) (xStart-0.0625), yStart, 0.0F, 1.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, (float) (xEnd+0.0625), yStart, 1.0F, 1.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, (float) (xEnd+0.0625), yStart + frameWidth, 1.0F, 0.0F, z, normalX, normalY, normalZ, light, color);

        //Left border
        paintingFramesVertex1(entry, vertexConsumer, xStart - frameWidth, yStart, 0.0F, 0.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, xStart - frameWidth, yEnd, 0.0F, 1.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, xStart, yEnd, 1.0F, 0.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, xStart, yStart, 1.0F, 1.0F, z, normalX, normalY, normalZ, light, color);
        //Right border
        paintingFramesVertex1(entry, vertexConsumer, xEnd, yStart, 0.0F, 0.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, xEnd, yEnd, 0.0F, 1.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, xEnd + frameWidth, yEnd, 1.0F, 0.0F, z, normalX, normalY, normalZ, light, color);
        paintingFramesVertex1(entry, vertexConsumer, xEnd + frameWidth, yStart, 1.0F, 1.0F, z, normalX, normalY, normalZ, light, color);
    }

    @Unique
    private void paintingFramesVertex1(MatrixStack.Entry matrix, VertexConsumer vertexConsumer1, float x, float y, float u, float v, float z, int normalX, int normalY, int normalZ, int light, Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        //PaintingFrames.LOGGER.info("vertex");
        //VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(this.getTexture(paintingEntity)));
        vertexConsumer1.vertex(matrix, x, y, z - 0.0655F).color(red, green, blue, 160).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, normalX, normalY, normalZ);
    }


}
