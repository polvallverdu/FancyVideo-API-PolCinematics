/*
 * This file is part of the FancyVideo-API.
 *
 * The FancyVideo-API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The FancyVideo-API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * The FancyVideo-API uses VLCJ, Copyright 2009-2021 Caprica Software Limited,
 * licensed under the GNU General Public License.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You should have received a copy of the GNU General Public License
 * along with FancyVideo-API.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2022 Nick1st.
 */

package nick1st.fancyvideo.api.mediaPlayer; //NOSONAR

import com.mojang.blaze3d.platform.NativeImage;
import nick1st.fancyvideo.Constants;
import nick1st.fancyvideo.api.DefaultBufferFormatCallback;
import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.MediaPlayerHandler;
import nick1st.fancyvideo.api.internal.AdvancedFrame;
import uk.co.caprica.vlcj.player.component.CallbackMediaListPlayerComponent;
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * For most use cases this implementation should be enough.
 *
 * @see nick1st.fancyvideo.api.mediaPlayer.MediaPlayerBase
 * @see nick1st.fancyvideo.api.mediaPlayer.AbstractMediaPlayer
 * @since 0.2.0.0
 */
public class SimpleMediaPlayer extends MediaPlayerBase {
    // Frame Holders
    protected final Semaphore semaphore = new Semaphore(1, true);
    // MediaPlayerCallback
    protected CallbackMediaPlayerComponent mediaPlayerComponent;
    protected AdvancedFrame videoFrame = new AdvancedFrame(new int[0], 0);

    public SimpleMediaPlayer(DynamicResourceLocation resourceLocation) {
        super(resourceLocation);
        mediaPlayerComponent = new CallbackMediaListPlayerComponent(MediaPlayerHandler.getInstance().getFactory(), null, null, true, null, callback, new DefaultBufferFormatCallback(this), null);
    }

    /**
     * @return The VLCJ API.
     * @since 0.2.0.0
     */
    @Override
    public EmbeddedMediaPlayer api() {
        return mediaPlayerComponent.mediaPlayer();
    }

    @Override
    public void markToRemove() {
        super.markToRemove();
        MediaPlayerHandler.getInstance().flagPlayerRemoval(dynamicResourceLocation);
    }

    @Override
    public void cleanup() {
        if (Constants.LOG.isDebugEnabled()) {
            Constants.LOG.debug("Removing Player {}", dynamicResourceLocation.toWorkingString());
        }
        mediaPlayerComponent.mediaPlayer().controls().stop();
        mediaPlayerComponent.release();
    }

    /**
     * This returns the current video frame as an RGBA int[] suitable for drawing to a {@link com.mojang.blaze3d.vertex.PoseStack}.
     * Use {@link #getWidth()} to get the buffer width.
     *
     * @since 0.2.0.0
     */
    @Override
    public int[] getIntFrame() {
        try {
            semaphore.acquire();
            AdvancedFrame advancedFrame = new AdvancedFrame(videoFrame);
            semaphore.release();
            int[] currentFrame = advancedFrame.getFrame();
            int[] rgbaFrame = new int[currentFrame.length];
            IntStream.range(0, currentFrame.length).forEach(index -> {
                int color = currentFrame[index];
                color <<= 8;
                color |= 0xFF;
                color = Integer.reverseBytes(color);

                rgbaFrame[index] = color;
            });
            return rgbaFrame;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return new int[0];
    }

    /**
     * This returns the width of the current video frame. <br>
     * Some useful math: <br>
     * int x = index % getWidth(); <br>
     * int y = index / getWidth(); <br>
     *
     * @since 0.2.0.0
     */
    @Override
    public int getWidth() {
        int width = 0;
        try {
            semaphore.acquire();
            width = videoFrame.getWidth();
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return width;
    }

    @Override
    public AdvancedFrame getAdvancedFrame() {
        try {
            semaphore.acquire();
            AdvancedFrame currentFrame = new AdvancedFrame(videoFrame);
            semaphore.release();
            return currentFrame;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return super.getAdvancedFrame();
    }

    @Override
    public void setAdvancedFrame(AdvancedFrame in) {
        try {
            semaphore.acquire();
            videoFrame = new AdvancedFrame(in);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public DynamicResourceLocation renderToResourceLocation() {
        AdvancedFrame frameAdvanced = getAdvancedFrame();
        int[] frame = frameAdvanced.getFrame();
        int width = frameAdvanced.getWidth();
        if (width == 0) {
            return dynamicResourceLocation;
        }
        image = new NativeImage(width, frame.length / width, true);
        IntStream.range(0, frame.length).forEach(index -> {
            int x = index % width;
            int y = index / width;

            int color = frame[index];
            color <<= 8;
            color |= 0xFF;
            color = Integer.reverseBytes(color);

            image.setPixelRGBA(x, y, color);
        });
        dynamicTexture.setPixels(image);
        return dynamicResourceLocation;
    }
}
