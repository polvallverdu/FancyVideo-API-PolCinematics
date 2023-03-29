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

package nick1st.fancyvideo.api.mediaPlayer;

import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.MediaPlayerHandler;
import nick1st.fancyvideo.api.internal.AudioPlayerCallback;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class AudioMediaPlayer extends AbstractMediaPlayer {

    private static final String FORMAT = "S16N";
    private static final int RATE = 44100;
    private static final int CHANNELS = 2;

    private final MediaPlayer mediaPlayer;

    private DynamicResourceLocation resourceLocation;

    public AudioMediaPlayer(DynamicResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        this.mediaPlayer = MediaPlayerHandler.getInstance().getFactory().mediaPlayers().newMediaPlayer();
        try {
            this.mediaPlayer.audio().callback(FORMAT, RATE, CHANNELS, new AudioPlayerCallback(FORMAT, RATE, CHANNELS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MediaPlayer api() {
        return mediaPlayer;
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
