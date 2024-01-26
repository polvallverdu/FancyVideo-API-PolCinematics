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

package dev.polv.vlcvideo.api; //NOSONAR

import dev.polv.vlcvideo.Constants;
import dev.polv.vlcvideo.api.mediaPlayer.MediaPlayerBase;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallbackAdapter;

/**
 * Default implementation of a buffer format callback that returns a buffer format suitable for rendering RGB frames.
 *
 * @since 0.0.0.0
 */
public class DefaultBufferFormatCallback extends BufferFormatCallbackAdapter {
    public final MediaPlayerBase mediaPlayerBase;

    public DefaultBufferFormatCallback(MediaPlayerBase mediaPlayer) {
        mediaPlayerBase = mediaPlayer;
    }

    @Override
    public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
        Constants.LOG.info("Dimensions of player {}: {} | {}", mediaPlayerBase.dynamicResourceLocation, sourceWidth, sourceHeight);
        mediaPlayerBase.callback.setBuffer(sourceWidth, sourceHeight);
        return new RGBABufferFormat(sourceWidth, sourceHeight);
    }

    /**
     * Implementation of a buffer format for RGB.
     * <p>
     * RGBA is a 32-bit RGBA format in a single plane.
     */
    public static class RGBABufferFormat extends BufferFormat {

        /**
         * Creates an RGBA buffer format with the given width and height.
         *
         * @param width width of the buffer
         * @param height height of the buffer
         */
        public RGBABufferFormat(int width, int height) {
            super("RGBA", width, height, new int[] {width * 4}, new int[] {height});
        }

    }
}
