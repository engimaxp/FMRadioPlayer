package FMRadio;


import javax.media.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Wang on 2017/1/17.
 */
public class AudioPlayer implements ControllerListener,Subject { // ControllerListener // 控制事件
    private Player player;
    private UrlProvider provider;
    private boolean paused;
    AudioPlayer(UrlProvider urlprovider) {
        provider = urlprovider;
        paused = false;
    }
    private void createPlayer() {
            URL url = provider.getNextUrl();
            if(url == null) return;
        try {
            player = Manager.createRealizedPlayer(provider.getNextUrl());
        } catch (CannotRealizeException ex) {
            System.out.println("Cannot Realize");
        } catch (NoPlayerException ex) {
            System.out.println("No Player");
        } catch (IOException ex) {
        }
    }
    private Time resume;
    public void pause(){
        if(player == null) {
            return;
        }
        if(paused){
            player.setMediaTime(resume);
            player.start();
            paused = false;
        } else {
            resume = player.getMediaTime();
            player.stop();
            paused = true;
        }
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
    public void controllerUpdate(ControllerEvent e) {
        if (e instanceof EndOfMediaEvent) {
            player.deallocate();
            player = null;
            this.start();
            return;
        }
        if (e instanceof PrefetchCompleteEvent) {
            player.start();
            return;
        }
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