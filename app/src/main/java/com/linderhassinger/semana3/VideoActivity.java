package com.linderhassinger.semana3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    private VideoView video;
    private Button orden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video = (VideoView) findViewById(R.id.video);
        orden = (Button) findViewById(R.id.orden);
        video.setMediaController(new MediaController(this));
        video.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_pizza));
        video.start();

        orden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoActivity.this,OrdenActivity.class);
                startActivity(intent);
            }
        });
    }
}
