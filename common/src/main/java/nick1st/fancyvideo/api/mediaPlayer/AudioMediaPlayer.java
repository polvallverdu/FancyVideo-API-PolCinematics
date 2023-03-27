package nick1st.fancyvideo.api.mediaPlayer;

import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.internal.AudioPlayerCallback;

public class AudioMediaPlayer extends MediaPlayer {

    private static final String FORMAT = "S16N";

    private static final int RATE = 44100;

    private static final int CHANNELS = 2;

    public AudioMediaPlayer(DynamicResourceLocation resourceLocation) {
        super(resourceLocation, true);
    }

    @Override
    public void init() {
        try {
            this.mediaPlayer.audio().callback(FORMAT, RATE, CHANNELS, new AudioPlayerCallback(FORMAT, RATE, CHANNELS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
