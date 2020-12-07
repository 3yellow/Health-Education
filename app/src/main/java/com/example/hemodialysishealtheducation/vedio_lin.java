package com.example.hemodialysishealtheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class vedio_lin extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_key = "AIzaSyD07V93XTujLIEDZMoJ_aoFdrlktHHL0to";
    public static final String Video_id = "1bT36G17FS8";//https://youtu.be/1bT36G17FS8 磷結合劑的運用
    YouTubePlayerView youTubePlayerView;
    int flag=0;
    String count,score,nurseID,pid,exam_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_lin);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_key,this);

        Intent i=this.getIntent();
        count=i.getStringExtra("count");
        score=i.getStringExtra("score");
        nurseID=i.getStringExtra("nurseID");
        pid=i.getStringExtra("id");
        exam_id=i.getStringExtra("exam_id");
        flag=i.getIntExtra("flag",0);
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {


        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if(!b){
            youTubePlayer.cueVideo(Video_id);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"Failured to Initialize",Toast.LENGTH_SHORT).show();
    }

    public void onnext(View v) {
        if(flag==0){        //回到影片頁
            Intent intent = new Intent(vedio_lin.this, choice_vedio.class);

            intent.putExtra("count",count);
            intent.putExtra("score",score);
            intent.putExtra("nurseID",nurseID);
            intent.putExtra("id",pid);
            intent.putExtra("exam_id",exam_id);

            startActivity(intent);
        }else{              //繼續衛教
            Intent intent = new Intent(vedio_lin.this, backtest.class);

            intent.putExtra("count",count);
            intent.putExtra("score",score);
            intent.putExtra("nurseID",nurseID);
            intent.putExtra("id",pid);
            intent.putExtra("exam_id",exam_id);

            startActivity(intent);
        }
    }
}