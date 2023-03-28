package nick1st.fancyvideo.api.mediaPlayer;

import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.MediaPlayerHandler;
import nick1st.fancyvideo.api.internal.AudioPlayerCallback;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class AudioMediaPlayer extends AbstractMediaPlayer {

    private static final String FORMAT = "S16N";
    private static final int RATE = 44100;
    private static final int CHANNELS = 2;

    private final MediaPlayer mediaPlayer;

    private DynamicResourceLocation resourceLocation;

    public AudioMediaPlayer(DynamicResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        this.mediaPlayer = MediaPlayerHandler.getInstance().getFactory().mediaPlayers().newMediaPlayer();
        try {
            this.mediaPlayer.audio().callback(FORMAT, RATE, CHANNELS, new AudioPlayerCallback(FORMAT, RATE, CHANNELS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MediaPlayer api() {
        return mediaPlayer;
    }

    @Override
    public void markToRemove() {
        MediaPlayerHandler.getInstance().flagPlayerRemoval(this.resourceLocation);
    }

    @Override
    public void cleanup() {
        if (providesAPI()) {
            this.mediaPlayer.controls().stop();
            mediaPlayer.release();
        }
    }

    @Override
    public boolean providesAPI() {
        return true;
    }
}
