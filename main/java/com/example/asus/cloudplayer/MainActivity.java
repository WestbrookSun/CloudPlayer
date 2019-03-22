package com.example.asus.cloudplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static SeekBar seekBar;
    private RecyclerView recyclerView;
    private List<Song> list;
    Handler handler = new Handler();

    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        Button buttonSongPlay = findViewById(R.id.play_and_stop);
        Button buttonSongActChange = findViewById(R.id.play_act_change);
        Button buttonSongNext = findViewById(R.id.next_song);
        Button buttonSongPrev = findViewById(R.id.previous_song);
        buttonSongNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongAdapter.mediaPlayer.stop();
                SongAdapter.mediaPlayer.reset();
                SongAdapter.songPosition++;
                SongAdapter.song = SongAdapter.mSong.get(SongAdapter.songPosition);
                MainActivity.seekBar.setMax(SongAdapter.song.duration);
                Log.d("SongAdapter","position is " + SongAdapter.songPosition);
                try {
                    SongAdapter.mediaPlayer.reset();
                    SongAdapter.mediaPlayer.setDataSource(SongAdapter.song.path);
                    SongAdapter.mediaPlayer.prepare();
                    SongAdapter.mediaPlayer.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        buttonSongPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongAdapter.mediaPlayer.stop();
                SongAdapter.mediaPlayer.reset();
                SongAdapter.songPosition--;
                SongAdapter.song = SongAdapter.mSong.get(SongAdapter.songPosition);
                MainActivity.seekBar.setMax(SongAdapter.song.duration);
                Log.d("SongAdapter","position is " + SongAdapter.songPosition);
                try {
                    SongAdapter.mediaPlayer.reset();
                    SongAdapter.mediaPlayer.setDataSource(SongAdapter.song.path);
                    SongAdapter.mediaPlayer.prepare();
                    SongAdapter.mediaPlayer.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        buttonSongPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SongAdapter.mediaPlayer.isPlaying()){
                    SongAdapter.mediaPlayer.start();
                }else if(SongAdapter.mediaPlayer.isPlaying()){
                    SongAdapter.mediaPlayer.pause();
                }
            }
        });
        buttonSongActChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SongAdapter.mediaPlayer.isLooping()){
                    SongAdapter.mediaPlayer.setLooping(true);
                }else if(SongAdapter.mediaPlayer.isLooping()){
                    SongAdapter.mediaPlayer.setLooping(false);
                }
            }
        });
        requestPermission();
        init();

        Log.d(TAG, "onCreate: "+ list.size());
    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        recyclerView = findViewById(R.id.song_list);
        list = new ArrayList<>();
        list = MusicUtils.getMusic(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SongAdapter songAdapter = new SongAdapter(list);
        recyclerView.setAdapter(songAdapter);

    }

    private void requestPermission() {
        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!(hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED)) {
            //拥有权限，执行操作
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }



    public static void initSeekBar(){
        MainActivity.seekBar.setMax(SongAdapter.song.duration);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SongAdapter.mediaPlayer.seekTo(MainActivity.seekBar.getProgress());
            }
        });
    }
}
