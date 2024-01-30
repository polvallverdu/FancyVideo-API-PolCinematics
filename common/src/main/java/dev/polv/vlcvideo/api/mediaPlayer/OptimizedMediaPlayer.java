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

import dev.polv.vlcvideo.api.DynamicResourceLocation;
import dev.polv.vlcvideo.api.MediaPlayerHandler;
import dev.polv.vlcvideo.api.OptimizedBufferFormatCallback;
import dev.polv.vlcvideo.api.internal.AudioPlayerCallback;
import dev.polv.vlcvideo.api.internal.OptimizedMediaPlayerCallback;
import net.minecraft.client.Minecraft;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface;

/**
 * For most custom implementation this should be enough to override.
 *
 * @see AbstractMediaPlayer
 * @since 0.2.0.0
 */
@SuppressWarnings("unused")
public class OptimizedMediaPlayer extends AbstractMediaPlayer {

    private static final String FORMAT = "S16N";
    private static final int RATE = 44100;
    private static final int CHANNELS = 2;

    protected OptimizedMediaPlayerCallback renderer;
    protected EmbeddedMediaPlayer mediaPlayer;

    private DynamicResourceLocation resourceLocation;

    public OptimizedMediaPlayer(DynamicResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;

        renderer = new OptimizedMediaPlayerCallback(false);
        this.mediaPlayer = MediaPlayerHandler.getInstance().getFactory().mediaPlayers().newEmbeddedMediaPlayer();

        try {
            this.mediaPlayer.audio().callback(FORMAT, RATE, CHANNELS, new AudioPlayerCallback(FORMAT, RATE, CHANNELS));
        } catch (Exception e) {
            e.printStackTrace();
        }

        CallbackVideoSurface videoSurface = MediaPlayerHandler.getInstance().getFactory().videoSurfaces().newVideoSurface(new OptimizedBufferFormatCallback(), renderer, true);
        this.mediaPlayer.videoSurface().set(videoSurface);
        this.mediaPlayer.videoSurface().attachVideoSurface();

        Minecraft.getInstance().submit(renderer::initialize);
    }

    /**
     * Template methode. <br>
     * Overrides should always return a valid {@link EmbeddedMediaPlayer}
     *
     * @return null
     * @since 0.2.0.0
     */
    @Override
    public MediaPlayer api() {
        return this.mediaPlayer;
    }

    public OptimizedMediaPlayerCallback getRenderer() {
        return renderer;
    }

    @Override
    public void markToRemove() {
        MediaPlayerHandler.getInstance().flagPlayerRemoval(this.resourceLocation);

    }

    @Override
    public void cleanup() {
        if (providesAPI()) {
            this.mediaPlayer.controls().stop();
            mediaPlayer.release();
        }
    }

    @Override
    public boolean providesAPI() {
        return true;
    }

}
