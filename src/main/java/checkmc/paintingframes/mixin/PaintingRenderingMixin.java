package checkmc.paintingframes.mixin;

import checkmc.paintingframes.FrameComponent;
import checkmc.paintingframes.FrameVariants;
import checkmc.paintingframes.PaintingFrames;
import checkmc.paintingframes.PaintingFramesComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureData;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PaintingEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        VertexConsumer vertexConsumerFrame = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(this.getTexture(paintingEntity)));
        
        renderFrame(paintingEntity, f,g, matrixStack, vertexConsumerFrame, i);
    }

    private void renderFrame(PaintingEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumer consumer, int i) {
        //Identifier TEXTURE = Identifier.of("minecraft","textures/misc/white.png");
        //VertexConsumer vertexConsumerCorrected = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(TEXTURE));
        MatrixStack.Entry entry = matrixStack.peek();

        PaintingVariant paintingVariant = (PaintingVariant)entity.getVariant().value();

        int width = paintingVariant.getWidth();
        int height = paintingVariant.getHeight();

        FrameComponent frameComponent = (FrameComponent) entity.getComponent(PaintingFramesComponents.FRAME_TYPE);
        if (Objects.equals(frameComponent.getValue(), "none")) {
            //PaintingFrames.LOGGER.info("DOES NOT HAVE A FRAME");
            return;
        }

        Color color = FrameVariants.getFrame(frameComponent.getValue()).getColor();

        //MatrixStack.Entry entry = matrixStack.peek();
        int frameWidth = 1; // 1 pixel frame

        float f1 = (float)(-width) / 2.0f;
        float g1 = (float)(-height) / 2.0f;
        float h1 = 0.5f;

        float xStart = f+1;
        float xEnd = f + width-1;
        float yStart = g;
        float yEnd = g + height;

        Direction direction = entity.getHorizontalFacing();
        int light = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getBlockPos());

        switch (direction) {
            case NORTH:
                renderBorder(entry, consumer, xStart, xEnd, yStart, yEnd, h1, 0, 0, -1, light, frameWidth, color);
                break;
            case SOUTH:
                renderBorder(entry, consumer, xStart, xEnd, yStart, yEnd, h1, 0, 0, 1, light, frameWidth, color);
                break;
            case WEST:
                renderBorder(entry, consumer, xStart, xEnd, yStart, yEnd, h1, -1, 0, 0, light, frameWidth, color);
                break;
            case EAST:
                renderBorder(entry, consumer, xStart, xEnd, yStart, yEnd, h1, 1, 0, 0, light, frameWidth, color);
                break;
        }
    }

    private void renderBorder(MatrixStack.Entry entry, VertexConsumer vertexConsumer, float xStart, float xEnd, float yStart, float yEnd, float z, int normalX, int normalY, int normalZ, int light, int frameWidth, Color color) {
        // Top border
        vertex1(entry, vertexConsumer, xStart, yEnd, 0, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xEnd, yEnd, 0, 1, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xEnd, yEnd - frameWidth, 1, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xStart, yEnd - frameWidth, 1, 1, z, normalX, normalY, normalZ, light, color);

        // Bottom border
        vertex1(entry, vertexConsumer, xStart, yStart + frameWidth, 0, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xEnd, yStart + frameWidth, 1, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xEnd, yStart, 0, 1, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xStart, yStart, 1, 1, z, normalX, normalY, normalZ, light, color);

        // Left border
        vertex1(entry, vertexConsumer, xStart - frameWidth, yStart, 0, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xStart - frameWidth, yEnd, 0, 1, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xStart, yEnd, 1, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xStart, yStart, 1, 1, z, normalX, normalY, normalZ, light, color);

        // Right border
        vertex1(entry, vertexConsumer, xEnd, yStart, 0, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xEnd, yEnd, 0, 1, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xEnd + frameWidth, yEnd, 1, 0, z, normalX, normalY, normalZ, light, color);
        vertex1(entry, vertexConsumer, xEnd + frameWidth, yStart, 1, 1, z, normalX, normalY, normalZ, light, color);
    }

    private void vertex1(MatrixStack.Entry matrix, VertexConsumer vertexConsumer1, float x, float y, float u, float v, float z, int normalX, int normalY, int normalZ, int light, Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        //PaintingFrames.LOGGER.info("vertex");
        //VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(this.getTexture(paintingEntity)));
        vertexConsumer1.vertex(matrix, x, y, z-1.02f).color(red, green, blue, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, normalX, normalY, normalZ).next();
    }


}
