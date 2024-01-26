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

package dev.polv.vlcvideo.api; //NOSONAR

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * ResourceLocation implementation for easier pre- and postfix handling.
 *
 * @since 0.2.0.0
 */
public class DynamicResourceLocation extends ResourceLocation {

    private final String playerNamespace;
    private final String playerPath;

    /**
     * This will construct a ResourceLocation that takes care about the name pre- and postfix minecraft adds to dynamic textures.
     *
     * @param namespace Should be your modid
     * @param path      Should be a unique identifier for this player
     * @since 0.2.0.0
     */
    public DynamicResourceLocation(@NotNull String namespace, @NotNull String path) {
        super("minecraft:dynamic/" + namespace + "." + path + "_1");
        playerNamespace = namespace;
        playerPath = path;
    }

    /**
     * See {@link #toWorkingString()} to get the internal name
     *
     * @return The true ResourceLocation, containing pre- and postfix.
     * @since 0.2.0.0
     */
    @Override
    public @NotNull String toString() {
        return super.toString();
    }

    /**
     * See {@link #toString()} to get the name containing pre- and postfix
     *
     * @return The VLCVideoAPI ResourceLocation, <b>NOT</b> containing pre- and postfix.
     * @since 0.2.0.0
     */
    public @NotNull String toWorkingString() {
        return playerNamespace + ":" + playerPath;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
