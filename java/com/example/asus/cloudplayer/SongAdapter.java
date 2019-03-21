package com.example.asus.cloudplayer;
/*
* 把歌曲信息映射到RecyclerView的映射器
* */
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> mSong;

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView songName;
        TextView artist;

        public ViewHolder(View view){
            super(view);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_text, parent, false);
        ViewHolder holder = new ViewHolder(view);
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
