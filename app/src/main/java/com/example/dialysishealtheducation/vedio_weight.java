package com.example.dialysishealtheducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class vedio_weight extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_key = "AIzaSyD07V93XTujLIEDZMoJ_aoFdrlktHHL0to";
    public static final String Video_id = "1jGFssRayD8";//https://youtu.be/1jGFssRayD8 乾體重
    YouTubePlayerView youTubePlayerView;
    int flag=0;
    String count,score,nurseID,pid,exam_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_weight);

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
            Intent intent = new Intent(vedio_weight.this, choice_vedio.class);

            intent.putExtra("count",count);
            intent.putExtra("score",score);
            intent.putExtra("nurseID",nurseID);
            intent.putExtra("id",pid);
            intent.putExtra("exam_id",exam_id);

            startActivity(intent);
        }else{              //繼續衛教
            Intent intent = new Intent(vedio_weight.this, backtest.class);

            intent.putExtra("count",count);
            intent.putExtra("score",score);
            intent.putExtra("nurseID",nurseID);
            intent.putExtra("id",pid);
            intent.putExtra("exam_id",exam_id);

            startActivity(intent);
        }

        /*AlertDialog.Builder builder = new AlertDialog.Builder(vedio_weight.this);
        builder.setMessage("請選擇您要返回的畫面？");
        builder.setPositiveButton("影片列表", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(vedio_weight.this, choice_vedio.class);

                intent.putExtra("count",count);
                intent.putExtra("score",score);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("id",pid);
                intent.putExtra("exam_id",exam_id);


                startActivity(intent);
            }
        });
        builder.setNegativeButton("繼續測驗", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();*/
    }
}
