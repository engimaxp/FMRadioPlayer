import javax.media.*;
import java.io.IOException;
import java.net.URL;
/**
 * Created by Wang on 2017/1/17.
 */
public class AudioPlayer implements ControllerListener { // ControllerListener // 控制事件
    private Player player;
    private UrlProvider provider;
    AudioPlayer(UrlProvider urlprovider) {
        provider = urlprovider;
        createPlayer();
    }

    private void createPlayer() {
        try {
            player = Manager.createRealizedPlayer(provider.getNextUrl());
        } catch (CannotRealizeException ex) {
            System.out.println("Cannot Realize");
        } catch (NoPlayerException ex) {
            System.out.println("No Player");
        } catch (IOException ex) {
        }
    }

    public void start() {
        if (player == null) {
            return;
        }
        player.addControllerListener(this);
        player.prefetch();
    }
    public void stop(){
        if(player==null){
            return;
        }
        player.stop();
    }
    public void controllerUpdate(ControllerEvent e) {
        if (e instanceof EndOfMediaEvent) {
            player.deallocate();
            createPlayer();
            this.start();
            return;
        }
        if (e instanceof PrefetchCompleteEvent) {
            player.start();
            return;
        }
    }
}