package com.example.youtubedownloader;



import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class jsonparser {
    public ArrayList<String> JsonParser(String str, ArrayList<String> map,Context context) throws JSONException {
        JSONObject Jobject = new JSONObject(str);
        Log.d("Sda",Jobject.toString());
        JSONArray Jarray;
        try {
             Jarray = Jobject.getJSONArray("items");
        }catch (Exception e){
            return null;
        }


        for (int i = 0; i < Jarray.length(); i++) {
            String id = ((JSONObject) Jarray.get(i)).getJSONObject("snippet")
                    .getJSONObject("resourceId").getString("videoId").toString();
            String title = ((JSONObject) Jarray.get(i)).getJSONObject("snippet").getString("title");
            Log.d("S1221", "https://www.youtube.com/watch?v="+id);
            map.add("https://www.youtube.com/watch?v="+id);
        }
        return map;
    }


}
