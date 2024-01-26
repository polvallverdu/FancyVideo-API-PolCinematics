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

package dev.polv.vlcvideo.api.eventbus.event; //NOSONAR

import dev.polv.vlcvideo.api.DynamicResourceLocation;
import dev.polv.vlcvideo.api.MediaPlayerHandler;
import dev.polv.vlcvideo.api.eventbus.EventPhase;

/**
 * Base class of all PlayerRegistry Events
 *
 * @since 0.2.0.0
 */
public class PlayerRegistryEvent {

    /**
     * Private constructor
     *
     * @since 0.2.0.0
     */
    private PlayerRegistryEvent() {
    }

    /**
     * Main Event for registering a new MediaPlayer. <br>
     * Adding a player later on is always safe.
     *
     * @since 0.2.0.0
     */
    public static class AddPlayerEvent extends Event {

        @Override
        public boolean isCancelable() {
            return false;
        }

        @Override
        public void onFinished() {
            // We don't need any cleanup, so this is empty.
        }

        /**
         * Convenience methode to get the {@link MediaPlayerHandler}.
         *
         * @return The MediaPlayerHandler Instance.
         * @since 0.2.0.0
         */
        public MediaPlayerHandler handler() {
            return MediaPlayerHandler.getInstance();
        }

    }

    /**
     * This event gets fired when a mediaPlayer is marked to remove. <br>
     * PRE-Phase: canceling is possible, negating the remove mark. <br>
     * EVENT-Phase: mediaPlayer is removed, at least now you <b>MUST</b> set your reference for this player to null. <br>
     * POST-Phase: called after the player was removed. Can be used to re-register a player with the same
     * {@link DynamicResourceLocation}. <br>
     *
     * @see MediaPlayerHandler
     * @since 0.2.0.0
     */
    @SuppressWarnings("unused")
    public static class RemovePlayerEvent extends PhasedEvent {

        public final DynamicResourceLocation resourceLocation;

        public RemovePlayerEvent(DynamicResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        /**
         * Convenience methode to get the {@link MediaPlayerHandler}.
         *
         * @return The MediaPlayerHandler Instance.
         * @since 0.2.0.0
         */
        public MediaPlayerHandler handler() {
            return MediaPlayerHandler.getInstance();
        }

        @Override
        public void afterPhase() {
            // Cleanup the requested player
            if (phase == EventPhase.EVENT) {
                MediaPlayerHandler.getInstance().getMediaPlayer(resourceLocation).cleanup();
            }
        }
    }
}
