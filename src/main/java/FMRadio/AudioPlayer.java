package FMRadio;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Wang on 2017/1/17.
 */
public class AudioPlayer implements Subject { // ControllerListener // 控制事件
    private EmbeddedMediaPlayer player;
    private UrlProvider provider;
    private boolean paused;
    private static final String NATIVE_LIBRARY_SEARCH_PATH = "D:\\Program Files (x86)\\VideoLAN\\VLC";
    AudioPlayer(UrlProvider urlprovider) {
        provider = urlprovider;
        paused = false;
        boolean found = new NativeDiscovery().discover();
        System.out.println(found);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), NATIVE_LIBRARY_SEARCH_PATH);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());
    }
    private void createPlayer() {
            URL url = provider.getNextUrl();
            if(url == null) return;
            MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
            player = mediaPlayerFactory.newEmbeddedMediaPlayer();

            // Add a component to be notified of player events
            player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                @Override
                public void finished(MediaPlayer mediaPlayer) {
                    super.finished(mediaPlayer);
                    player.release();
                    mediaPlayerFactory.release();
                    notifyObservers();
                    start();
                }
            });

            // Play a particular item, with options if necessary
            String mediaPath = url.toString();
            player.playMedia(mediaPath);
    }
    public void pause(){
        if(player == null) {
            return;
        }
        player.setPause(!paused);
        paused = !paused;
    }
    public void start() {
        if (player == null) {
            createPlayer();
        }
    }
    public void stop(){
        if(player==null){
            return;
        }
        player.stop();
    }

    private ArrayList<Observer> observers = new ArrayList<>();
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.update();
        }
    }
}