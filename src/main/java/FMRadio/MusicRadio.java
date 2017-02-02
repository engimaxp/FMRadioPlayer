package FMRadio;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.List;

public class MusicRadio {
    private MusicResult result;
    private String code;
    private String message;

    private int currentPlay=0;

    public List<Music> getSongs() {
        return result.getTracks();
    }
    public Music getASong() throws Exception {
        Music playmusic = new Music();
        try{
            if (result == null || currentPlay > result.getTracks().size()) {
                String radioUrl = String.format("http://music.163.com/api/playlist/detail?id=%d",37880978);
                String json = Request.Get(radioUrl).execute().returnContent().asString();
                Gson gson = new Gson();
                MusicRadio jsonObj = gson.fromJson(json, MusicRadio.class);
                result = jsonObj.result;
                currentPlay = 0;
            }
            playmusic = this.getSongs().get(currentPlay);
            currentPlay++;
        } catch (IOException e) {
            throw new Exception("Parse Json Wrong");
        }
        return playmusic;
    }
}
