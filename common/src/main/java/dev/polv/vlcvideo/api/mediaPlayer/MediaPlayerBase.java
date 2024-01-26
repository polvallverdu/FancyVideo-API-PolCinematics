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

import com.mojang.blaze3d.platform.NativeImage;
import dev.polv.vlcvideo.api.internal.MediaPlayerCallback;
import dev.polv.vlcvideo.api.internal.SelfCleaningDynamicTexture;
import dev.polv.vlcvideo.api.internal.utils.IntegerBuffer2D;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import dev.polv.vlcvideo.Constants;
import dev.polv.vlcvideo.api.DynamicResourceLocation;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * For most custom implementation this should be enough to override.
 *
 * @see AbstractMediaPlayer
 * @since 0.2.0.0
 */
@SuppressWarnings("unused")
public class MediaPlayerBase extends AbstractMediaPlayer {

    /**
     * Last available Frame, stored as a {@link DynamicResourceLocation}
     **/
    public final DynamicResourceLocation dynamicResourceLocation;
    // MediaPlayerCallback
    public final MediaPlayerCallback callback = new MediaPlayerCallback(0, this);
    /**
     * Last available Frame, stored as a {@link NativeImage}
     **/
    protected NativeImage image = new NativeImage(1, 1, true); //TODO This redundant initializer seems to be important?
    /**
     * Last available Frame, stored as a {@link SelfCleaningDynamicTexture}
     **/
    protected SelfCleaningDynamicTexture dynamicTexture = new SelfCleaningDynamicTexture(image);

    public MediaPlayerBase(DynamicResourceLocation resourceLocation) {
        image = new NativeImage(1, 1, true);
        image.setPixelRGBA(0, 0, 0);
        dynamicTexture.setPixels(image);
        dynamicResourceLocation = resourceLocation;
        Minecraft.getInstance().getTextureManager().register(resourceLocation.toWorkingString().replace(':', '.'), dynamicTexture);
        Constants.LOG.debug("TextureLocation is '{}'", dynamicResourceLocation);
    }

    /**
     * Template methode. <br>
     * Overrides should always return a valid {@link EmbeddedMediaPlayer}
     *
     * @return null
     * @since 0.2.0.0
     */
    @Override
    public MediaPlayer api() {
        return null;
    }

    @Override
    public void markToRemove() {
        // Template methode.
    }

    @Override
    public void cleanup() {
        // Template methode.
    }

    /**
     * Template methode. <br>
     * This returns the current video frame as an int[] suitable for drawing to a {@link com.mojang.blaze3d.vertex.PoseStack}.
     * Use {@link #getWidth()} to get the buffer width.
     *
     * @since 0.2.0.0
     */
    public int[] getIntFrame() {
        return new int[0];
    }

    /**
     * Template methode. <br>
     * This returns the width of the current video frame.
     *
     * @since 0.2.0.0
     */
    public int getWidth() {
        return 0; //NOSONAR
    }

    /**
     * Template methode. <br>
     * Invoked by the callback to set a new frame. Should only be used by the callback, or if you want to inject custom frames.
     *
     * @since 0.2.0.0
     */
    public void setIntBuffer(IntegerBuffer2D in) {
        // Template methode.
    }

    /**
     * Template methode. <br>
     *
     * @since 0.2.0.0
     */
    public IntegerBuffer2D getIntBuffer() {
        return new IntegerBuffer2D(1, 1);
    }

    /**
     * Template methode. <br>
     * Renders the current frame to a {@link ResourceLocation} for further use.
     *
     * @return The {@link ResourceLocation} rendered to.
     */
    public ResourceLocation renderToResourceLocation() {
        return dynamicResourceLocation;
    }
}
