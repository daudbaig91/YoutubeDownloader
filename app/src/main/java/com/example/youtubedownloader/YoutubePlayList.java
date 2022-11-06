package com.example.youtubedownloader;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class YoutubePlayList {
    public ArrayList<String> getData(String url, Context context) throws JSONException {

        okhttphandler http = new okhttphandler();

        ArrayList<String> list = new ArrayList<String>();
        jsonparser parser = new jsonparser();
        String s = http.doInBackground2("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=114&playlistId=" + url +"&key=" + BuildConfig.Key_Google);
        list = parser.JsonParser(s, list,context);




        return list;
    }
}
