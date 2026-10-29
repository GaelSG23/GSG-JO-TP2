package com.example.partie2gsg;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.partie2gsg.DataBase.SuccursaleDataSource;
import com.example.partie2gsg.DataBase.SuccursaleDomino;
import com.example.partie2gsg.DataBase.SuccursaleImporter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private SuccursaleDataSource datasource;
    List<SuccursaleDomino> valeurs;
    TextView TxtPrix;
    EditText EditAddresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datasource = new SuccursaleDataSource(this);

        SuccursaleImporter importer =
                new SuccursaleImporter(this, datasource);


        datasource.open();
        valeurs = datasource.getAllSuccursales();

        Log.d("Succursales", "Nombre : " + valeurs.size());
        for (SuccursaleDomino s : valeurs) {
            Log.d("Succursales", s.getId() + " | " + s.getAddress() + " | " + s.getTel()
                    + " | " + s.getLatitude() + ", " + s.getLongitude());
        }

        Spinner spinnerPizza = findViewById(R.id.pizza);
        TxtPrix = findViewById(R.id.prix);
        TextView TxtNumCom = findViewById(R.id.num_com);
        EditAddresses = findViewById(R.id.adresses);

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


            SuccursaleDomino succursale = succursaleCheck();
            if (succursale == null) {
                Toast.makeText(this, "Adresse introuvable", Toast.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Commande Envoyé")
                    .setMessage("Votre commande va être livrée par la succursale " + succursale.getAddress() + "\n Numéro de téléphone de cette succursale " + succursale.getTel())
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
    public SuccursaleDomino succursaleCheck() {

        String address = EditAddresses.getText().toString().trim();

        if (address.isEmpty() || valeurs == null || valeurs.isEmpty()) {
            return null;
        }



        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses =
                    geocoder.getFromLocationName(address, 1);

            if (addresses == null || addresses.isEmpty()) {
                return null;
            }

            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();

            SuccursaleDomino plusProche = null;
            float distanceMin = Float.MAX_VALUE;

            for (SuccursaleDomino s : valeurs) {

                if (s.getAddress() == null || s.getAddress().trim().isEmpty() || s.getTel() == null || s.getTel().trim().isEmpty()) {
                    continue;
                }

                float[] distance = new float[1];

                Location.distanceBetween(
                        latitude,
                        longitude,
                        s.getLatitude(),
                        s.getLongitude(),
                        distance
                );

                if (distance[0] < distanceMin) {
                    distanceMin = distance[0];
                    plusProche = s;
                }
            }

            return plusProche;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}