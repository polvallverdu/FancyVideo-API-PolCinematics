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

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * Abstract MediaPlayer. This is the minimal starting point for own implementations.
 *
 * @see MediaPlayerBase
 * @since 0.2.0.0
 */
public abstract class AbstractMediaPlayer { //NOSONAR

    /**
     * @return This returns the true {@link MediaPlayer}, allowing you to use most functions of libvlc.
     * This is now the preferred way to call into VLCJ's API. You must be aware that crashes in the native lib will lead to null pointers.
     * @since 0.2.0.0
     */
    public abstract MediaPlayer api();

    /**
     * Call this to remove (/unregister) your MediaPlayer.
     *
     * @since 0.2.0.0
     */
    public abstract void markToRemove();

    /**
     * This is forcefully called when the MediaPlayer is removed (/unregistered).
     * Implement custom cleanup behavior by overriding this methode.
     *
     * @since 0.2.0.0
     */
    public abstract void cleanup();

    /**
     * Template methode. <br>
     * If your Player provides an {@link EmbeddedMediaPlayer} this <b>must</b> return true.
     * @return True if the player provides the EmbeddedMediaPlayer API, false otherwise
     */
    public boolean providesAPI() {
        return false;
    }
}
