import javax.media.*;
import java.io.IOException;
import java.net.URL;
/**
 * Created by Wang on 2017/1/17.
 */
public class AudioPlayer implements ControllerListener { // ControllerListener // 控制事件
    private Player player;
    AudioPlayer(URL srcurl) {
    	try {
    		player = Manager.createRealizedPlayer(srcurl);
    	} catch(javax.media.CannotRealizeException ex) {
            System.out.println("Cannot Realize");
        } catch(NoPlayerException ex) {
            System.out.println("No Player");
        } catch(IOException ex) {
        }
    }
    public void start() {
        if (player == null) {
            return;
        }
        player.addControllerListener(this);
        player.prefetch();
    }
    public void controllerUpdate(ControllerEvent e) {
        if (e instanceof EndOfMediaEvent) {
            return;
        }
        if (e instanceof PrefetchCompleteEvent) {
            player.start();
            return;
        }
    }
}