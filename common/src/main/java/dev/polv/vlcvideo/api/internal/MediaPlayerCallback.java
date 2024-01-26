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

package dev.polv.vlcvideo.api.internal; //NOSONAR

import dev.polv.vlcvideo.api.internal.utils.IntegerBuffer2D;
import dev.polv.vlcvideo.api.mediaPlayer.MediaPlayerBase;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallbackAdapter;

/**
 * This provides a simple RenderCallbackAdapter implementation
 *
 * @since 0.1.0.0
 */
public class MediaPlayerCallback extends RenderCallbackAdapter {
    private final MediaPlayerBase mediaPlayer;
    private int width;

    public MediaPlayerCallback(int width, MediaPlayerBase mediaPlayer) {
        this.width = width;
        this.mediaPlayer = mediaPlayer;
    }

    public void setBuffer(int sourceWidth, int sourceHeight) {
        this.width = sourceWidth;
        setBuffer(new int[sourceWidth * sourceHeight]);
    }

    @Override
    protected void onDisplay(uk.co.caprica.vlcj.player.base.MediaPlayer mediaPlayer, int[] buffer) {
        this.mediaPlayer.setIntBuffer(new IntegerBuffer2D(width, buffer));
    }
}
