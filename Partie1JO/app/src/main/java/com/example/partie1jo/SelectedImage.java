package com.example.partie1jo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SelectedImage extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    int[] songs = {
            R.raw.musique1,
            R.raw.musique2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selected_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Random random = new Random();
        int position_musique = random.nextInt(songs.length);
        mediaPlayer = MediaPlayer.create(this, songs[position_musique]);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        //aller cherver l'identifiant de photo choisie que on a mis dans le intent trouver la ohoto et l'associer à L'imageView
        int id = getIntent().getIntExtra("id", 0);
        ImageView imageView = findViewById(R.id.img_selected);
        if (id != 0){
            imageView.setImageResource(id);
        }
        Button btn_retour = findViewById(R.id.btn_retour);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedImage.this, Images.class);
                startActivity(intent);
            }
        });

    }

    //quand L'activité arret on arrette la musique
    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }
}