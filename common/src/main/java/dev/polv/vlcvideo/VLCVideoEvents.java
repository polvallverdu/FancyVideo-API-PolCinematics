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

package dev.polv.vlcvideo; //NOSONAR

import dev.polv.vlcvideo.api.eventbus.VLCVideoEvent;
import dev.polv.vlcvideo.api.eventbus.event.PlayerRegistryEvent;
import dev.polv.vlcvideo.api.internal.utils.IntegerBuffer2D;
import dev.polv.vlcvideo.api.mediaPlayer.SimpleMediaPlayer;
import dev.polv.vlcvideo.internal.SimpleTextRenderer;
import dev.polv.vlcvideo.internal.Util;
import dev.polv.vlcvideo.api.DynamicResourceLocation;
import dev.polv.vlcvideo.api.eventbus.EventPriority;

public record VLCVideoEvents() {

    public static final DynamicResourceLocation fallback = new DynamicResourceLocation(Constants.MOD_ID, "fallback");

    public static final String UNSUPPORTED = "It seems you're using an unsupported platform.";
    public static final String UNSUPPORTED2 = "Head to bit.ly/vlcBeta and try installing the latest version for your OS.";

    @VLCVideoEvent(priority = EventPriority.SURPREME)
    @SuppressWarnings("unused")
    public static void addDefaultPlayer(PlayerRegistryEvent.AddPlayerEvent event) {
        event.handler().registerPlayerOnFreeResLoc(fallback, SimpleMediaPlayer.class);
        IntegerBuffer2D buffer = Util.injectableTextureFromJar("VLCMissing.png", VLCVideoEvent.class.getClassLoader(), 1024);

        buffer.bulkPut(SimpleTextRenderer.greatestSizedText(UNSUPPORTED, 1024, 310, -1), 0, 0, true); //1024 * 120
        buffer.bulkPut(SimpleTextRenderer.greatestSizedText(UNSUPPORTED2, 1024, 310, -1), 0, 710, true);

        ((SimpleMediaPlayer) event.handler().getMediaPlayer(fallback)).setIntBuffer(buffer);
        ((SimpleMediaPlayer) event.handler().getMediaPlayer(fallback)).renderToResourceLocation();
    }
}
