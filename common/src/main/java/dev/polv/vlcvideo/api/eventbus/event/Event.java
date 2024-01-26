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

package dev.polv.vlcvideo.api.eventbus.event; //NOSONAR

import dev.polv.vlcvideo.Constants;
import dev.polv.vlcvideo.api.eventbus.EventException;

/**
 * Base class of all events, extend this if you want an event without phases.
 *
 * @see PhasedEvent
 * @since 0.2.0.0
 */
@SuppressWarnings("unused")
public abstract class Event {
    private boolean canceled = false;

    /**
     * Template Methode. <br>
     * Override this if your event should be cancelable.
     *
     * @see #setCanceled()
     */
    public abstract boolean isCancelable();

    /**
     * All Events having Phases should be a subclass of {@link PhasedEvent}
     *
     * @return False, as this doesn't have phases
     */
    public boolean hasPhases() {
        return false;
    }

    /**
     * Calling this will cancel the event.
     * Events that are cancelled aren't send to lower priority listeners nor get later phases fired. <br>
     * Trying to cancel a non-cancelable event will throw an {@link EventException.EventCancellationException}.
     */
    public void setCanceled() {
        try {
            if (!this.isCancelable()) {
                Constants.LOG.error("################# ERROR #################");
                Constants.LOG.error("Tried to cancel non-cancelable event: {}", this.getClass().getName());
                Constants.LOG.error("#########################################");
                throw new EventException.EventCancellationException("Event not cancelable: " + this.getClass().getName());
            } else {
                this.canceled = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return True if the event is canceled, false otherwise.
     */
    public boolean isCanceled() {
        return this.canceled;
    }

    /**
     * Template Methode. <br>
     * Override this if you need to do something after your event has run.
     */
    public abstract void onFinished();
}
