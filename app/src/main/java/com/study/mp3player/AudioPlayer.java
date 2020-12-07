package com.study.mp3player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.study.mp3player.MainActivity.musicFiles;

public class AudioPlayer extends AppCompatActivity {
    private TextView songName,artistName,durationPlay,durationTotal;
    private ImageView nextBtn,preBtn,backBtn;
    private FloatingActionButton playPauseBtn;
    private SeekBar seekBar;
    private int position = -1;
    static ArrayList<MusicFile> listSong = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        initLayout();
        getIntentData();
        AudioPlayer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mCurrentDuration = mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(mCurrentDuration);
                    durationPlay.setText(formattedTime(mCurrentDuration));
                }
                handler.postDelayed(this,100);
            }
        });

    }

    private String formattedTime(int mCurrentDuration) {
        String totalout="";
        String totalNew="";
        String ss=String.valueOf(mCurrentDuration % 60);
        String mm=String.valueOf(mCurrentDuration / 60);
        totalout = mm + ":" + ss;
        totalNew = mm + ":"+"0" + ss;
        if (ss.length()==1){
            return totalNew;
        }else{
            return totalout;
        }
    }

    private void getIntentData() {
        position = getIntent().getIntExtra("position",-1);
        listSong = musicFiles;

        if(listSong!=null){
            songName.setText(listSong.get(position).getTitle());
            artistName.setText(listSong.get(position).getArtist());
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSong.get(position).getPath());
            if (mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
            }else{
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
            }
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            durationTotal.setText(formattedTime(mediaPlayer.getDuration()/1000));
        }
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                preBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1) <0 ?(listSong.size() -1): (position-1));
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSong.get(position).getTitle());
            artistName.setText(listSong.get(position).getArtist());
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentDuration = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentDuration);
                    }
                    handler.postDelayed(this,100);
                }
            });
            durationTotal.setText(formattedTime(mediaPlayer.getDuration()/1000));
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1) % listSong.size());
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSong.get(position).getTitle());
            artistName.setText(listSong.get(position).getArtist());
            playPauseBtn.setImageResource(R.drawable.ic_play);
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentDuration = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentDuration);
                    }
                    handler.postDelayed(this,100);
                }
            });
            durationTotal.setText(formattedTime(mediaPlayer.getDuration()/1000));
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position+1) % listSong.size());
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSong.get(position).getTitle());
            artistName.setText(listSong.get(position).getArtist());
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentDuration = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentDuration);
                    }
                    handler.postDelayed(this,100);
                }
            });
            durationTotal.setText(formattedTime(mediaPlayer.getDuration()/1000));
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position+1) % listSong.size());
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSong.get(position).getTitle());
            artistName.setText(listSong.get(position).getArtist());
            playPauseBtn.setImageResource(R.drawable.ic_play);
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentDuration = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentDuration);
                    }
                    handler.postDelayed(this,100);
                }
            });
            durationTotal.setText(formattedTime(mediaPlayer.getDuration()/1000));
        }
    }


    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if(mediaPlayer.isPlaying()){
            playPauseBtn.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentDuration = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentDuration);
                    }
                    handler.postDelayed(this,100);
                }
            });
        }else{
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            AudioPlayer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentDuration = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentDuration);
                    }
                    handler.postDelayed(this,100);
                }
            });
        }
    }

    private void initLayout() {
        songName = findViewById(R.id.SongName_txt);
        artistName = findViewById(R.id.ArtistName_txt);
        durationPlay = findViewById(R.id.durationPlay_txt);
        durationTotal = findViewById(R.id.duration_txt);
        nextBtn = findViewById(R.id.next_btn);
        preBtn = findViewById(R.id.pre_btn);
        backBtn = findViewById(R.id.back_btn);
        playPauseBtn = findViewById(R.id.play_pause_btn);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AudioPlayer.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}