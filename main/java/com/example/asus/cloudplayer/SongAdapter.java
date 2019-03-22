package com.example.asus.cloudplayer;
/*
* 把歌曲信息映射到RecyclerView的映射器
* */
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    public static List<Song> mSong;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    static Song song;
    static int songPosition;
    class ViewHolder extends RecyclerView.ViewHolder{
        View SongView;
        TextView songName;
        TextView artist;
        public ViewHolder(View view){
            super(view);
            SongView = view;
            songName = view.findViewById(R.id.song_song);
            artist = view.findViewById(R.id.song_artist);
        }
    }

    public SongAdapter(List<Song> songs){
        mSong = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_text, parent,
                false);
        final ViewHolder holder = new ViewHolder(view);
        holder.SongView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songPosition = holder.getAdapterPosition();
                song = mSong.get(songPosition);
                MainActivity.seekBar.setMax(song.duration);
                Log.d("SongAdapter","position is " + songPosition);
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(song.path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = mSong.get(position);
        holder.songName.setText(song.songName);
        holder.artist.setText(song.artist);
    }

    @Override
    public int getItemCount() {
        return mSong.size();
    }
}
