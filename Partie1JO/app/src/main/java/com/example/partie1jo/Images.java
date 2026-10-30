package com.example.partie1jo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Random;

public class Images extends AppCompatActivity {

    int[] images = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
    };
    Random random = new Random();

    //tableau des position d'images
    boolean[] clicked = {false, false, false, false};

    ImageView imageSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_images);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView[] champsImages = {
                findViewById(R.id.imageView1),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
        };
        LinearLayout[] champsBordureImages = {
                findViewById(R.id.image1_border),
                findViewById(R.id.image2_border),
                findViewById(R.id.image3_border),
                findViewById(R.id.image4_border),
        };

        View imageView = findViewById(R.id.main);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherImagesAleatoires(champsImages);
            }
        });
        int count=0;

        //boucle qui vérifie pour chaque layout qui contient l'image si il a été cliqué si oui on mets les bordures en rouge
        for (LinearLayout champBordureImage:champsBordureImages) {
            int position = count;
            champBordureImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position <= images.length){
                        if (clicked[position]){
                            clicked[position] = false;
                            imageSelected = null;
                            champsBordureImages[position].setBackgroundColor(Color.TRANSPARENT);

                        } else {
                            if (!imgAlreadySelected()){
                                clicked[position] = true;
                                imageSelected = champsImages[position];
                                champsBordureImages[position].setBackgroundColor(Color.RED);
                            }
                        }
                    }

                }
            });
            count++;
        }

        Button suivant = findViewById(R.id.btn_suivant);
        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected != null && imgAlreadySelected() && imageSelected.getTag() != null){
                    int id = (int) imageSelected.getTag();
                    Intent intent = new Intent(Images.this, SelectedImage.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });



    }

    //parcours le tableau de position d'images pour voir si une image a déaja ete selectionné
    private boolean imgAlreadySelected(){
        boolean anImageIsAlreadySelected = false;
        for (boolean instanceClicked: clicked) {
            if (instanceClicked){
                anImageIsAlreadySelected= true;
            }
        }
        return anImageIsAlreadySelected;
    }

    private void afficherImagesAleatoires(ImageView[] champsImages){
        for (ImageView imageView: champsImages) {
            int position = random.nextInt(images.length);
            imageView.setImageResource(images[position]);
            imageView.setTag(images[position]);
        }
    }
}