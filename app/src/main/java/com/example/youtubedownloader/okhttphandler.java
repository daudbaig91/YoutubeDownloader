package com.example.youtubedownloader;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class okhttphandler extends AsyncTask {

    OkHttpClient client = new OkHttpClient();
    String stringcheck = "testtest";
    public String doInBackground2(String url) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();

        //
        try {

            Response response = client.newCall(request).execute();
            String str = response.body().string();

            return str;
        }catch (Exception e){

            e.printStackTrace();
        }
        return "null/error";
    }
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
