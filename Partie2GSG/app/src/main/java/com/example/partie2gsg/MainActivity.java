package com.example.partie2gsg;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    TextView TxtPrix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinnerPizza = findViewById(R.id.pizza);
        TxtPrix = findViewById(R.id.prix);
        TextView TxtNumCom = findViewById(R.id.num_com);
        EditText EditNom = findViewById(R.id.nom);
        EditText EditPrenom = findViewById(R.id.prenom);
        EditText EditAdresse = findViewById(R.id.adresses);
        EditText EditCodePostal = findViewById(R.id.code_postal);
        EditText EditVille = findViewById(R.id.ville);
        EditText EditTel = findViewById(R.id.tel);


        TxtNumCom.setText(String.valueOf((int)(Math.random() * 101)));

        Button btnMap = findViewById(R.id.btnMap);
        Button btnEnvoyer = findViewById(R.id.btnEnvoyer);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.pizza,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPizza.setAdapter(adapter);
        spinnerPizza.setOnItemSelectedListener(this);


        btnEnvoyer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsFragment.class);
            startActivity(intent);
        });


        btnEnvoyer.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Commande Envoyé")
                    .setMessage("Votre commande va être livrée par la succursale \n Numéro de téléphone de cette succursale.")
                    .setPositiveButton("OK", null)
                    .show();
        });
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    MapsFragment.class
            );
            startActivity(intent);
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        double prix_d = i * 10;
        TxtPrix.setText(String.valueOf(prix_d));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}