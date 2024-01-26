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

import dev.polv.vlcvideo.config.SimpleConfig;

import java.io.File;
import java.util.Arrays;

public class VLCVideoConfig extends SimpleConfig {

    public VLCVideoConfig() {
        super(new File("config", "vlcvideoapi.cfg"));
        setProperty("dllVersion", String.valueOf(Constants.DLL_VERSION), "DO NOT MODIFY THIS! (Set it to -1 to regenerate your DLLs, but otherwise DO NOT TOUCH!)", ">= -1", s -> {
            try {
                if (Integer.parseInt(s) >= -1) {
                    return true;
                }
            } catch (NumberFormatException ignored) {
                // Ignored
            }
            return false;
        });
        // TODO
        // setProperty("debugLog", String.valueOf(false), "Enable debug logging. Disables the ModLauncher log filter. This cause massive log spam! Only activate this when you're told to!", "true / false", s -> Arrays.asList("true", "false").contains(s));
        setProperty("example", String.valueOf(false), "Activate the debug/showcase mode. Access it by pressing the Options Button in Main Menu.", "true / false", s -> Arrays.asList("true", "false").contains(s));

        read();
        write();
    }
}
