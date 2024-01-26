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

package dev.polv.vlcvideo.api.internal; //NOSONAR

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.renderer.texture.DynamicTexture;
import dev.polv.vlcvideo.Constants;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of a self-cleaning DynamicTexture. Allows for high frame rate uploads.
 *
 * @since 0.1.0.0
 */
public class SelfCleaningDynamicTexture extends DynamicTexture {
    public SelfCleaningDynamicTexture(NativeImage nativeImage) {
        super(nativeImage);
    }

    @Override
    public void setPixels(@NotNull NativeImage nativeImage) {
        super.setPixels(nativeImage);
        if (this.getPixels() != null) {
            TextureUtil.prepareImage(this.getId(), this.getPixels().getWidth(), this.getPixels().getHeight());
            this.upload();
        } else {
            Constants.LOG.error("Called setPixels in {} with NativeImage.getPixels == null", this.getClass().getName());
        }
    }

}
