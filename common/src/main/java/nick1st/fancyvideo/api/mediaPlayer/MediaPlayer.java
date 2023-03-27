package nick1st.fancyvideo.api.mediaPlayer;

import nick1st.fancyvideo.Constants;
import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.MediaPlayerHandler;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;

public abstract class MediaPlayer {

    public static final MediaPlayerFactory MEDIA_PLAYER_FACTORY = new MediaPlayerFactory();
    protected final uk.co.caprica.vlcj.player.base.MediaPlayer mediaPlayer;

    private final boolean audioOnly;
    private final DynamicResourceLocation resourceLocation;

    public MediaPlayer(DynamicResourceLocation resourceLocation, boolean audioOnly) {
        this.resourceLocation = resourceLocation;
        this.audioOnly = audioOnly;

        if (Constants.NO_LIBRARY_MODE) {
            this.mediaPlayer = null;
        } else {
            this.mediaPlayer = audioOnly ?
                    MEDIA_PLAYER_FACTORY.mediaPlayers().newMediaPlayer() :
                    MEDIA_PLAYER_FACTORY.mediaPlayers().newEmbeddedMediaPlayer();
        }
    }

    public abstract void init();

    public void changeUrl(String url) {
        this.mediaPlayer.media().prepare(url);
    }

    public uk.co.caprica.vlcj.player.base.MediaPlayer api() {
        return Constants.NO_LIBRARY_MODE ? null : this.mediaPlayer;
    }

    public boolean isAudioOnly() {
        return audioOnly;
    }

    /**
     * Call this to remove (/unregister) your MediaPlayer.
     *
     * @since 0.2.0.0
     */
    public void markToRemove() {
        MediaPlayerHandler.getInstance().flagPlayerRemoval(this.resourceLocation);
    }

    /**
     * This is forcefully called when the MediaPlayer is removed (/unregistered).
     * Implement custom cleanup behavior by overriding this methode.
     *
     * @since 0.2.0.0
     */
    public void cleanup() {
        if (providesAPI()) {
            this.mediaPlayer.controls().stop();
            mediaPlayer.release();
        }
    }

    public boolean providesAPI() {
        return !Constants.NO_LIBRARY_MODE;
    }
}
