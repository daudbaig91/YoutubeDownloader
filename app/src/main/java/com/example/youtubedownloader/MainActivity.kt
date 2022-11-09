package com.example.youtubedownloader

import android.R.id.message
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autocompleteTV.setAdapter( ArrayAdapter(this, R.layout.dropdownmenu, resources.getStringArray(R.array.formats)))
        
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.auto2)
        val adapter = ArrayAdapter(this, R.layout.dropdownmenu, resources.getStringArray(R.array.type))
        autocompleteTV2.setAdapter(adapter)

        val namegroup = findViewById<TextInputLayout>(R.id.name)
        val menugroup = findViewById<TextInputLayout>(R.id.menu)

        (autocompleteTV2 as AutoCompleteTextView).onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->

                if(position==1){
                    namegroup.visibility = View.INVISIBLE
                    menugroup.visibility = View.INVISIBLE
                }else{
                    namegroup.visibility = View.VISIBLE
                    menugroup.visibility = View.VISIBLE
                }
            }
    }


    fun playlist(){
        Thread {
            val textview = findViewById<AutoCompleteTextView>(R.id.UrlText).text.toString()
            val uri = Uri.parse(textview)
            val listUrl: String? = uri.getQueryParameter("list")
            val list = YoutubePlayList()
            Log.d("sdasd",listUrl.toString())
            val arr = list.getData(listUrl,this)
            if(arr != null){
            for (str in arr) {
                val download = VideoDownloader()
                val strname: String? = null
                download.getVideo(str, this, "mp3",strname)
                Thread.sleep(100)
            }}else{
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Error parsing Playlist. Make sure playlist is public",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }.start()
    }
    fun download(view: View){


        var titlename :String? = null
        val filename = findViewById<AutoCompleteTextView>(R.id.nameText)
        if(filename.text.toString().isNotEmpty()){
            titlename = filename.text.toString()
        }
        val textview = findViewById<AutoCompleteTextView>(R.id.UrlText)
        val format = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView).text.toString()
        val vidorlist = findViewById<AutoCompleteTextView>(R.id.auto2).text.toString()


        if(!vidorlist.equals("Video")) {
            if(!hasId(textview.text.toString(),"list")){
                Toast.makeText(this,"Wrong Url, Please enter Playlist Url",Toast.LENGTH_LONG).show()
                return;
            }
            playlist()
        }else {
            if(!hasId(textview.text.toString(),"v")){
                Toast.makeText(this,"Wrong Url, Please enter valid Video Url",Toast.LENGTH_LONG).show()
                return
            }
            var type = "mp3"
            if(format.contains("MP4-High Res")){
                type = "mp4-h"
            }else if(format.contains("MP4-Low Res")){
                type = "mp4-l"
            }
            Log.d("sdasd",type)
            val download = VideoDownloader()
            download.getVideo(textview.text.toString(), this, type,titlename)
            textview.setText("")
            filename.setText("")

        }
    }


    fun hasId(url:String,param:String):Boolean{
        if(param.equals("v")){
            if(url.contains("youtu")){
                return true
            }
        }
        val uri = Uri.parse(url)
        val listUrl: String? = uri.getQueryParameter(param)
        if(listUrl!=null){
            return true
        }
        return false
    }



    fun log(str:String){
        Log.d("tst1",str)
    }
}