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

package dev.polv.vlcvideo.api.eventbus; //NOSONAR

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A methode annotated with this will be considered a valid event listener.
 *
 * @see EventPhase
 * @see EventPriority
 * @since 0.2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface VLCVideoEvent {

    EventPriority priority() default EventPriority.NORMAL;

    EventPhase phase() default EventPhase.EVENT;
}
