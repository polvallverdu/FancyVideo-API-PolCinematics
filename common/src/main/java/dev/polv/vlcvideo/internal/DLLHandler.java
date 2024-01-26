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

package dev.polv.vlcvideo.internal; //NOSONAR

import dev.polv.vlcvideo.Constants;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;

import static dev.polv.vlcvideo.Constants.PLUGINSDIR;

public class DLLHandler {

    private DLLHandler() {}

    public static void clearDLL() {
        try {
            new File(LibraryMapping.libVLC.getByOS(Constants.OS)).delete();
            new File(LibraryMapping.libVLCCore.getByOS(Constants.OS)).delete();
            FileUtils.deleteDirectory(new File(PLUGINSDIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean unpack(ClassLoader loader) {
        if (Constants.DEBUG_NO_LIBRARY_MODE) {
            Constants.LOG.warn("Debugging NO_LIBRARY_MODE; VLC will be unavailable.");
            return false;
        }
        // Check if we package this os and arch
        String path = "vlc-bin/" + Constants.OS + "/" + Constants.ARCH + "/";
        Constants.LOG.debug(path);
        if (Constants.OS == null || loader.getResource(path) == null) {
            return false;
        }

        // Extract natives
        for (LibraryMapping mapping : LibraryMapping.values()) {
            String file;
            switch (Constants.OS) {
                case ("linux"):
                    file = mapping.linuxName;
                    break;
                case ("mac"):
                    file = mapping.macName;
                    break;
                case ("windows"):
                    file = mapping.windowsName;
                    break;
                default:
                    return false;
            }
            try {
                //noinspection ResultOfMethodCallIgnored
                new File(PLUGINSDIR).mkdir();
                extract(loader, path, file, mapping.isPlugin);
            } catch (IOException e) {
                Constants.LOG.error("An error occurred whilst trying to unpack natives ", e);
            }
        }
        return true;
    }

    private static void extract(ClassLoader loader, String path, String file, boolean isPlugin) throws IOException {
        if (isPlugin) {
            //noinspection ResultOfMethodCallIgnored
            new File(PLUGINSDIR + file).getParentFile().mkdirs();
        }
        InputStream in = isPlugin ? loader.getResourceAsStream(path + PLUGINSDIR + file) : loader.getResourceAsStream(path + file);
        OutputStream out = isPlugin ? new FileOutputStream(PLUGINSDIR + file) : new FileOutputStream(file);
        IOUtils.copy(in, out);
        in.close();
        out.flush();
        out.close();
    }
}
