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

package dev.polv.vlcvideo.forge;

import dev.polv.vlcvideo.config.SimpleConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.EventSubclassTransformer;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import dev.polv.vlcvideo.Constants;
import dev.polv.vlcvideo.VLCVideoAPI;
import net.minecraftforge.fml.common.Mod;
import dev.polv.vlcvideo.VLCVideoConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import java.util.Arrays;

@Mod(VLCVideoAPI.MOD_ID)
public class VLCVideoAPIForge {

    // Config Holder
    private SimpleConfig config;

    public VLCVideoAPIForge() {
        // Client only
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, ()-> new IExtensionPoint.DisplayTest(() -> "ANY", (a, b) -> true));
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            Constants.LOG.warn("## WARNING ## 'VLCVideoAPI' is a client mod and has no effect when loaded on a server!");
            return;
        }

        // Init Config
        config = new VLCVideoConfig();

        // Ignore the silly NullPointers caused by ModLauncher // TODO Make this actually STOP the error
        if (LogManager.getLogger(EventSubclassTransformer.class) instanceof org.apache.logging.log4j.core.Logger && !config.getAsBool("debugLog")) {
            org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger(EventSubclassTransformer.class);
            logger.warn("## WARNING ## 'VLCVideoAPI' is modifying this log! Disable this behavior in its config BEFORE reporting bugs!");
            logger.addFilter(new AbstractFilter() {
                @Override
                public Result filter(LogEvent event) {
                    if (event.getMessage() != null && event.getThrown() != null && event.getMarker() != null) {
                        if (event.getMarker().getName().equals("EVENTBUS") && event.getMessage().getFormattedMessage().equals("An error occurred building event handler")) {
                            if (Arrays.stream(event.getThrown().getStackTrace()).anyMatch(sTE -> sTE.getClassName().startsWith("uk.co.caprica.vlcj."))) {
                                return Result.DENY;
                            }
                        }
                    }
                    return Result.NEUTRAL;
                }
            });
        }

        MinecraftForge.EVENT_BUS.addListener(this::firstRenderTick);
        VLCVideoAPI.init(config);
    }

    public void firstRenderTick(TickEvent.RenderTickEvent event) {
        if (!Constants.renderTick) {
            VLCVideoAPI.apiSetup();
            Constants.renderTick = true;
        }
    }
}
