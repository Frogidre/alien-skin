package com.alienskin.mixin;

import com.alienskin.AlienSkin;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Reshapes the player model: huge head, thin torso, long spindly arms. */
@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin {

    @Unique private boolean alienskin$applied;

    @Inject(
            method = "setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V",
            at = @At("TAIL")
    )
    private void alienskin$reshape(PlayerEntityRenderState state, CallbackInfo ci) {
        PlayerEntityModel m = (PlayerEntityModel) (Object) this;
        BipedEntityModel<?> b = m;

        if (AlienSkin.enabled) {
            alienskin$applied = true;
            //                        x     y     z
            scale(b.head,  1.65f, 1.95f, 1.65f);
            scale(b.hat,   1.65f, 1.95f, 1.65f);
            scale(b.body,  0.60f, 0.95f, 0.50f);
            scale(m.jacket, 0.60f, 0.95f, 0.50f);
            scale(b.leftArm,  0.50f, 1.45f, 0.50f);
            scale(b.rightArm, 0.50f, 1.45f, 0.50f);
            scale(m.leftSleeve,  0.50f, 1.45f, 0.50f);
            scale(m.rightSleeve, 0.50f, 1.45f, 0.50f);
            scale(b.leftLeg,  0.55f, 1.00f, 0.55f);
            scale(b.rightLeg, 0.55f, 1.00f, 0.55f);
            scale(m.leftPants,  0.55f, 1.00f, 0.55f);
            scale(m.rightPants, 0.55f, 1.00f, 0.55f);
        } else if (alienskin$applied) {
            alienskin$applied = false;
            ModelPart[] all = {b.head, b.hat, b.body, m.jacket, b.leftArm, b.rightArm,
                    m.leftSleeve, m.rightSleeve, b.leftLeg, b.rightLeg, m.leftPants, m.rightPants};
            for (ModelPart p : all) scale(p, 1f, 1f, 1f);
        }
    }

    @Unique
    private static void scale(ModelPart part, float x, float y, float z) {
        part.xScale = x;
        part.yScale = y;
        part.zScale = z;
    }
}
