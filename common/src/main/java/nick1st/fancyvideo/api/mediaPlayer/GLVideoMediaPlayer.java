package nick1st.fancyvideo.api.mediaPlayer;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.Window;
import com.sun.jna.Pointer;
import dev.architectury.event.events.client.ClientGuiEvent;
import net.minecraft.client.Minecraft;
import nick1st.fancyvideo.api.DynamicResourceLocation;
import org.lwjgl.glfw.GLFW;
import uk.co.caprica.vlcj.player.embedded.videosurface.videoengine.VideoEngineCallbackAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.videoengine.VideoEngineResizeCallback;

public class GLVideoMediaPlayer extends MediaPlayer {

    public GLVideoMediaPlayer(DynamicResourceLocation resourceLocation) {
        super(resourceLocation, false);
    }

    @Override
    public void init() {
        ClientGuiEvent.RENDER_HUD.register((matrices, tickDelta) -> {
            matrices.
            if (this.mediaPlayer != null) {
                this.mediaPlayer.videoSurface().attach(new VideoEngineCallbackDemo());
            }
        });
    }

    /**
     * This class is the bridge between the native video engine and the LWJGL rendering surface.
     * <p>
     * The semaphore may be a bit overkill since in this example the main thread never sets the current context again
     * after it has finished initialisation, however the first acquire at least protects us from a race during startup.
     * <p>
     * The callback methods here all execute on a native thread coming from LibVLC.
     */
    private class VideoEngineHandler extends VideoEngineCallbackAdapter {
        @Override
        public void onSwap(Pointer opaque) {
            GLFW.glfwSwapBuffers(Minecraft.getInstance().getWindow().getWindow());
        }

        @Override
        public boolean onMakeCurrent(Pointer opaque, boolean enter) {
            return false;
        }

        @Override
        public long onGetProcAddress(Pointer opaque, String functionName) {
            return 0;
        }
        /*@Override
        public long onGetProcAddress(Pointer opaque, String functionName) {
            return glfwGetProcAddress(functionName);
        }

        @Override
        public boolean onMakeCurrent(Pointer opaque, boolean enter) {
            if (enter) {
                try {
                    contextSemaphore.acquire();
                    glfwMakeContextCurrent(window);
                }
                catch (InterruptedException e) {
                    return false;
                }
                catch (Exception e) {
                    glfwMakeContextCurrent(0L);
                    contextSemaphore.release();
                    return false;
                }
            } else {
                try {
                    glfwMakeContextCurrent(0L);
                }
                finally {
                    contextSemaphore.release();
                }
            }
            return true;
        }

        @Override
        public void onSwap(Pointer opaque) {
            glfwSwapBuffers(window);
        }

        @Override
        public void onSetResizeCallback(VideoEngineResizeCallback resizeCallback) {
            VideoEngineCallbackDemo.this.resizeCallback = resizeCallback;

            // FIXME is it ok to do this here and call back into the native library on this thread, also outside of any
            //       GLFW context - it seems to work... but it feels like this should be synchronized - in theory it's
            //       possible that the reportSizeChanged callback could become invalidated while processing this?
            if (resizeCallback != null && window != 0) {
                int[] w = {0};
                int[] h = {0};

                try {
                    contextSemaphore.acquire();
                    glfwMakeContextCurrent(window);

                    glfwGetWindowSize(window, w, h);
                    resizeCallback.setSize(w[0], h[0]);
                } catch (InterruptedException e) {

                } finally {
                    contextSemaphore.release();
                }
            }
        }*/
    }
}
