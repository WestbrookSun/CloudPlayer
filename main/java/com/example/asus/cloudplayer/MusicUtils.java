package com.example.asus.cloudplayer;
/*
* MusicUtils
* 工具类，用于扫描所有本地文件，将扫描到的音乐文件放到一个容器里，
* 以便之后播放音乐和显示音乐时使用，返回这个容器。
* */
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {


    public static List<Song> getMusic(Context context){
        List<Song> list = new ArrayList<>();
        /*Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                null,null,MediaStore.Audio.AudioColumns.IS_MUSIC);*/
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if(cursor != null){
            Log.d("cursor", "cursor not null");
            Song song;
            while (cursor.moveToNext()){
                song = new Song();
                song.songName = cursor.getString(cursor.getColumnIndexOrThrow((MediaStore.Audio.Media.DISPLAY_NAME)));
                song.artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                Log.d("song", "getMusic: " + song.songName);
                if (true) {
                    if (song.songName.contains("-")) {
                        String[] str = song.songName.split("-");
                        song.artist = str[0];
                        song.songName = str[1];
                    }
                    list.add(song);
                }
            }
            cursor.close();
        }
        return list;
    }

    public static String formatTime(int time){
        if(((time / 1000) % 60) < 10){
            String tt = (time / 1000) / 60 + ":0" + (time / 1000) % 60;
            return tt;
        }else {
            String tt = (time / 1000) / 60 + ":" + (time / 1000) % 60;
            return  tt;
        }
    }

}
