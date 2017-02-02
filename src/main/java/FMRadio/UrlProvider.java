package FMRadio;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Wang on 2017/1/19.
 */
public class UrlProvider {
    private LinkedList<URL> audioFileUrl;
    private URL currentURL;
    public UrlProvider(){
        audioFileUrl = new LinkedList<URL>();
    }
    public void addNextUrl(URL url){
        audioFileUrl.push(url);
    }
    public URL getNextUrl(){
//        if(audioFileUrl.isEmpty())
//            return null;
//        currentURL = audioFileUrl.pop();
//        return currentURL;
        try {
            return new URL("file:/C:/Users/Wang/Music/Magnet.mp3");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
