import java.net.URL;

/**
 * Created by Wang on 2017/1/19.
 */
public class UrlProvider {
    private URL audioFileUrl;
    public UrlProvider(URL url){
        audioFileUrl = url;
    }
    public void setNextUrl(URL url){
        audioFileUrl = url;
    }
    public URL getNextUrl(){
        return audioFileUrl;
    }
}
