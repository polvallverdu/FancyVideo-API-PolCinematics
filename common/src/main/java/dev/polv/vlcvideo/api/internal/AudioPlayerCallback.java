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

package dev.polv.vlcvideo.api.internal;

import com.sun.jna.Pointer;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.callback.AudioCallbackAdapter;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;

public class AudioPlayerCallback extends AudioCallbackAdapter {

    private static final int BLOCK_SIZE = 4;

    private static final int SAMPLE_BITS = 16; // BLOCK_SIZE * 8 / channels ???

    private final AudioFormat audioFormat;

    private final Info info;

    private final SourceDataLine dataLine;

    public AudioPlayerCallback(String format, int rate, int channels) throws Exception {
        this.audioFormat = new AudioFormat(rate, SAMPLE_BITS, channels, true, false);
        this.info = new Info(SourceDataLine.class, audioFormat);
        this.dataLine = (SourceDataLine)AudioSystem.getLine(info);
        start();
    }

    private void start() throws Exception {
        System.out.println("start()");
        dataLine.open(audioFormat);
        dataLine.start();
    }

    private void stop() {
        System.out.println("stop()");
        dataLine.close();
    }

    @Override
    public void play(MediaPlayer mediaPlayer, Pointer samples, int sampleCount, long pts) {
        // There may be more efficient ways to do this...
        int bufferSize = sampleCount * BLOCK_SIZE;
        // You could process these samples in some way before playing them...
        byte[] data = samples.getByteArray(0, bufferSize);
        dataLine.write(data, 0, bufferSize);
    }

    @Override
    public void drain(MediaPlayer mediaPlayer) {
        System.out.println("drain()");
        dataLine.drain();
    }

}
