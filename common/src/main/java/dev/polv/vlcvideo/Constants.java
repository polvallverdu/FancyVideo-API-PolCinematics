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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Constants() {
    //DLL Version
    public static final int DLL_VERSION = 2;
    public static final String PLUGINSDIR = "plugins/";

    // Mod info
    public static final String MOD_ID = "vlcvideoapi";
    public static final String MOD_NAME = "VLCVideoAPI";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // System info
    public static String OS;
    public static String ARCH;

    // First render Tick
    public static boolean renderTick;

    // String constants
    public static final String AUDIO_OUTPUT = "audio_output";
    public static final String VIDEO_FILTER = "video_filter";
    public static final String ACCESS = "access";

    // No_Library_Mode
    public static boolean NO_LIBRARY_MODE;
    public static final boolean DEBUG_NO_LIBRARY_MODE = false;
}
