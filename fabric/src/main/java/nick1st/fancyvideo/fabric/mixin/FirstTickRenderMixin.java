package nick1st.fancyvideo.fabric.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import nick1st.fancyvideo.fabric.FancyVideoAPIFabric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class FirstTickRenderMixin {

    @Inject(at = @At("HEAD"), method = "setOverlay")
    private void onSetOverlay(Overlay overlay, CallbackInfo info) {
        FancyVideoAPIFabric.getInstance().firstRenderTick();
    }

}
