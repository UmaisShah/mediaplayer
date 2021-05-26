package com.sam.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button play,stop,pause;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=findViewById(R.id.button);
        stop=findViewById(R.id.button2);
        pause=findViewById(R.id.button3);
        seekBar=findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.diamonds);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                    play();

                break;
            case R.id.button2:
            stop();
                break;
            case R.id.button3:
               pause();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            mediaPlayer.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
        mediaPlayer = null;
        }

    void play(){
        seekBar.setProgress(1);

        seekBar.setMax(mediaPlayer.getDuration());
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                    //null the global MediaPlayer object
                }
            });
        }
        }
    void pause(){
        if (mediaPlayer.isPlaying()==true && mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
    void stop(){
        if (mediaPlayer!=null && mediaPlayer.isPlaying()==true) {
            mediaPlayer.stop();
            mediaPlayer.release();
//                  mediaPlayer.reset();
            mediaPlayer = null;
            //onStop();

        }
    }

}