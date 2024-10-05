package anjani.koohiiclient.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void onRenderLabel(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // Check if the entity has a custom nametag
        if (entity.hasCustomName() || entity.isCustomNameVisible()) {
            // Cancel the vanilla nametag rendering
            ci.cancel();
            
            // Use our custom NametagRenderer to render the nametag
            anjani.koohiiclient.renderer.NametagRenderer.renderCustomNametag(entity, text, matrices, vertexConsumers, light);
        }
    }
}