package anjani.koohiiclient.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import net.minecraft.client.gui.DrawContext;

public class NametagRenderer {
    private static final Identifier ICON_TEXTURE = Identifier.of("koohiiclient", "textures/gui/nametag_icon.png");
    private static final int ICON_SIZE = 32;

    public static void renderCustomNametag(Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        MinecraftClient client = MinecraftClient.getInstance();
        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
        TextRenderer textRenderer = client.textRenderer;

        double d = dispatcher.getSquaredDistanceToCamera(entity);
        if (d > 4096.0) {
            return;
        }

        boolean bl = !entity.isInvisible();
        float f = entity.getHeight() + 0.5F;
        int i = "deadmau5".equals(text.getString()) ? -10 : 0;

        matrices.push();
        matrices.translate(0.0, f, 0.0);
        matrices.multiply(dispatcher.getRotation());
        matrices.scale(-0.025F, -0.025F, 0.025F);

        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
        int j = (int)(g * 255.0F) << 24;
        float h = (float)(-textRenderer.getWidth(text) / 2);

        // Render the icon
        matrices.push();
        matrices.scale(0.5F, 0.5F, 0.5F);
        matrices.translate(h - ICON_SIZE / 2, -ICON_SIZE / 2, 0);
        DrawContext drawContext = new DrawContext(client, client.getBufferBuilders().getEntityVertexConsumers());
        drawContext.drawTexture(ICON_TEXTURE, 0, 0, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
        matrices.pop();

        // Render the nametag text
        textRenderer.draw(text, h, 0, 553648127, false, matrix4f, vertexConsumers, TextRenderer.TextLayerType.NORMAL, j, light);
        if (bl) {
            textRenderer.draw(text, h, 0, -1, false, matrix4f, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, light);
        }

        matrices.pop();
    }
}