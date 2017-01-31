package FMRadio;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.List;

public class MusicRadio {
    private String r;
    private String is_show_quick_start;
    private List<Music> song;

    public List<Music> getSong() {
        return song;
    }
    public static Music getASong() throws Exception {
        Music playmusic = new Music();
        try{
            String radioUrl = String.format("http://douban.fm/j/mine/playlist?type=n&channel=%d&from=mainsite",1);
            String json = Request.Get(radioUrl).execute().returnContent().asString();
            Gson gson = new Gson();
            MusicRadio jsonObj = gson.fromJson(json, MusicRadio.class);
            playmusic = jsonObj.getSong().get(0);
        } catch (IOException e) {
            throw new Exception("Parse Json Wrong");
        }
        return playmusic;
    }
}
