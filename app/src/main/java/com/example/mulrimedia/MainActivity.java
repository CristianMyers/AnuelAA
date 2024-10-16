package com.example.mulrimedia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    private MediaPlayer mediaPlayer;
    private boolean isAudioPlaying = false;
    private boolean isVideoPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de componentes
        ImageView imageView = findViewById(R.id.imageView);
        Button playAudioButton = findViewById(R.id.playAudioButton);
        VideoView videoView = findViewById(R.id.myVideoView);
        Button playVideoButton = findViewById(R.id.playVideoButton);
        Button animateButton = findViewById(R.id.animateButton);

        // Reproducción de audio
        playAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.spotifyheadshot);
                }

                if (isAudioPlaying) {
                    mediaPlayer.pause();
                    isAudioPlaying = false;
                    playAudioButton.setText("Reproducir Audio");
                } else {
                    mediaPlayer.start();
                    isAudioPlaying = true;
                    playAudioButton.setText("Pausar Audio");
                }
            }
        });

        // Reproducción de video
        playVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoheadshot);
                videoView.setVideoURI(videoUri);

                if (isVideoPlaying) {
                    videoView.pause();
                    isVideoPlaying = false;
                    playVideoButton.setText("Reproducir Video");
                } else {
                    videoView.start();
                    isVideoPlaying = true;
                    playVideoButton.setText("Pausar Video");
                }
            }
        });

        // Animación de la imagen de anuel1
        animateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeInAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
                imageView.startAnimation(fadeInAnimation);
            }
        });

        // Comprobar y solicitar permisos si es necesario
        checkAndRequestPermissions();
    }

    // Verificar si los permisos están concedidos
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                // Permiso denegado
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del MediaPlayer cuando la actividad se destruya
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}