package com.study.mp3player;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.ViewHolder> {
    private ArrayList<MusicFile> listMusic;
    private Context context;

    public ListMusicAdapter(ArrayList<MusicFile> listMusic, Context context) {
        this.listMusic = listMusic;
        this.context = context;
    }


    @NonNull
    @Override
    public ListMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_music, parent, false);
        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMusicAdapter.ViewHolder holder, int position) {
        MusicFile mMusicFile = listMusic.get(position);
        System.out.println(mMusicFile);
        holder.mArtist.setText(mMusicFile.getArtist());
        holder.mTitle.setText(mMusicFile.getTitle());
        byte[] img = getPhoto(mMusicFile.getPath());
        Glide.with(context).asBitmap().load(img).into(holder.mImageSong);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AudioPlayer.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }
    public byte [] getPhoto(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
    @Override
    public int getItemCount() {
        return listMusic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mArtist;
        private ImageView mImageSong;
        private ListMusicAdapter listMusicAdapter;

        public ViewHolder(View view,ListMusicAdapter listMusicAdapter) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.txtSongName);
            mArtist = (TextView) view.findViewById(R.id.txtArtist);
            mImageSong = (ImageView) view.findViewById(R.id.imageSong);
            this.listMusicAdapter = listMusicAdapter;
        }
    }
}
