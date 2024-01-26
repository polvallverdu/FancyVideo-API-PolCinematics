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

package dev.polv.vlcvideo.fabric;

import dev.polv.vlcvideo.config.SimpleConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import dev.polv.vlcvideo.Constants;
import dev.polv.vlcvideo.VLCVideoAPI;
import net.fabricmc.api.ModInitializer;
import dev.polv.vlcvideo.VLCVideoConfig;

public class VLCVideoAPIFabric implements ModInitializer {

    private static VLCVideoAPIFabric instance;

    public VLCVideoAPIFabric() {
        if (instance == null) {
            instance = this;
        } else {
            Constants.LOG.error("Called VLCVideoAPI constructor a second time! This will cause serious problems!");
        }
    }

    // Config Holder
    public SimpleConfig config;

    @Override
    public void onInitialize() {
        // Init Config
        config = new VLCVideoConfig();
        VLCVideoAPI.init(config);
    }

    public static VLCVideoAPIFabric getInstance() {
        return instance;
    }

    public void firstRenderTick() {
        // Ensure this only runs on the client (Not sure if this is required)
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && !Constants.renderTick) {
            VLCVideoAPI.apiSetup();
            Constants.renderTick = true;
        }
    }
}
