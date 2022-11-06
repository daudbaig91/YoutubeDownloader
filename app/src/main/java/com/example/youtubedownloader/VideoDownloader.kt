package com.example.youtubedownloader

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.webkit.URLUtil
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

class VideoDownloader {

    lateinit var context:Context

    public fun getVideo(url: String,context: Context,type: String,filename:String?){
        this.context = context
        if(filename != null){
            Download(filename, url, type)
        }
        else{
            getTitle(url,type)
        }
    }

    private fun getTitle(youtubeUrl:String,type:String){
        thread {
            try {
                val x = URL("https://www.youtube.com/oembed?url=$youtubeUrl&format=json").readText()
                Download(JSONObject(x).get("title").toString(), youtubeUrl, type)
            }catch (e:Exception){

            }
        }
    }


    private fun Download(title: String,url: String,type: String){


        var typemp = "mp3"
        var typeurl = BuildConfig.key_azure_mp3
        if(type.equals("mp4-h")){
             typemp = "mp4"
             typeurl = BuildConfig.key_azure_mp4L
        }else if(type.equals("mp4-l")){
             typemp = "mp4"
             typeurl = BuildConfig.key_azure_mp4L
        }
        val url = BuildConfig.key_azure_url+"$typeurl?name=$url"
        val fileName = "$title.$typemp"
        val request = DownloadManager.Request(Uri.parse(url.toString() + ""))
        request.setTitle(fileName)
        request.setMimeType("application/$typemp")
        request.allowScanningByMediaScanner()
        request.setAllowedOverMetered(true)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        val dm = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }


}