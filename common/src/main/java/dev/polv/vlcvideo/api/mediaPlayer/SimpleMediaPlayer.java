/*
 * This file is part of the VLCVideoAPI.
 *
 * The VLCVideoAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The VLCVideoAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * The VLCVideoAPI uses VLCJ, Copyright 2009-2021 Caprica Software Limited,
 * licensed under the GNU General Public License.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCVideoAPI.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2024 <https://polv.dev/>
 */

package dev.polv.vlcvideo.api.mediaPlayer; //NOSONAR

import com.mojang.blaze3d.platform.NativeImage;
import dev.polv.vlcvideo.api.internal.utils.IntegerBuffer2D;
import dev.polv.vlcvideo.Constants;
import dev.polv.vlcvideo.api.DefaultBufferFormatCallback;
import dev.polv.vlcvideo.api.DynamicResourceLocation;
import dev.polv.vlcvideo.api.MediaPlayerHandler;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.CallbackMediaListPlayerComponent;
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent;

import java.util.concurrent.Semaphore;

/**
 * For most use cases this implementation should be enough.
 *
 * @see MediaPlayerBase
 * @see AbstractMediaPlayer
 * @since 0.2.0.0
 */
public class SimpleMediaPlayer extends MediaPlayerBase {
    // Frame Holders
    protected final Semaphore semaphore = new Semaphore(1, true);
    // MediaPlayerCallback
    protected CallbackMediaPlayerComponent mediaPlayerComponent;
    protected IntegerBuffer2D videoFrame = new IntegerBuffer2D(1, 1);

    public SimpleMediaPlayer(DynamicResourceLocation resourceLocation) {
        super(resourceLocation);
        if (Constants.NO_LIBRARY_MODE) {
            mediaPlayerComponent = null;
        } else {
            mediaPlayerComponent = new CallbackMediaListPlayerComponent(MediaPlayerHandler.getInstance().getFactory(), null, null, true, null, callback, new DefaultBufferFormatCallback(this), null);
        }
    }

    /**
     * @return The VLCJ API.
     * @since 0.2.0.0
     */
    @Override
    public MediaPlayer api() {
        return Constants.NO_LIBRARY_MODE ? null : mediaPlayerComponent.mediaPlayer();
    }

    @Override
    public void markToRemove() {
        super.markToRemove();
        MediaPlayerHandler.getInstance().flagPlayerRemoval(dynamicResourceLocation);
    }

    @Override
    public void cleanup() {
        if (Constants.LOG.isDebugEnabled()) {
            Constants.LOG.debug("Removing Player '{}'", dynamicResourceLocation.toWorkingString());
        }
        if (providesAPI()) {
            mediaPlayerComponent.mediaPlayer().controls().stop();
            mediaPlayerComponent.release();
        }
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
            IntegerBuffer2D temp = new IntegerBuffer2D(videoFrame);
            semaphore.release();
            return temp.getArray();
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

    public IntegerBuffer2D getIntBuffer() {
        try {
            semaphore.acquire();
            IntegerBuffer2D currentFrame = new IntegerBuffer2D(videoFrame);
            semaphore.release();
            return currentFrame;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return super.getIntBuffer();
    }

    @Override
    public void setIntBuffer(IntegerBuffer2D in) {
        try {
            semaphore.acquire();
            videoFrame = new IntegerBuffer2D(in);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public DynamicResourceLocation renderToResourceLocation() {
        IntegerBuffer2D buffer2D = getIntBuffer();
        int width = buffer2D.getWidth();
        if (width == 0) {
            return dynamicResourceLocation;
        }
        image = new NativeImage(width, buffer2D.getHeight(), true);
        for (int i = 0; i < buffer2D.getHeight(); i++) {
            for (int j = 0; j < width; j++) {
                image.setPixelRGBA(j, i, buffer2D.get(j, i));
            }
        }
        dynamicTexture.setPixels(image);
        return dynamicResourceLocation;
    }

    @Override
    public boolean providesAPI() {
        return mediaPlayerComponent != null;
    }
}
