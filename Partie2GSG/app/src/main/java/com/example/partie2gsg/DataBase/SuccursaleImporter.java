package com.example.partie2gsg.DataBase;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SuccursaleImporter {

    private final Context context;
    private final SuccursaleDataSource datasource;

    public SuccursaleImporter(Context context,
                              SuccursaleDataSource datasource) {
        this.context = context;
        this.datasource = datasource;
    }
    //Import la liste de succursale a portir d'un dossier csv
    public void importerCSV() {
        try (InputStream input =
                     context.getAssets().open("dominos.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(input, StandardCharsets.UTF_8))) {

            String ligne;
            boolean premiereLigne = true;
            int nombreImporte = 0;

            datasource.open();

            try {
                while ((ligne = reader.readLine()) != null) {
                    String[] colonnes = ligne.split(",", -1);

                    if (colonnes.length < 5) {
                        Log.w("CSVImporter", "Ligne invalide : " + ligne);
                        continue;
                    }

                    for (int i = 0; i < colonnes.length; i++) {
                        colonnes[i] = colonnes[i].replace("\"", "").trim();
                    }

                    try {
                        String numero = colonnes[0];
                        String rue = colonnes[1];
                        String tel = colonnes[2];
                        String address = (numero + " " + rue).trim();

                        double latitude = Double.parseDouble(colonnes[3]);
                        double longitude = Double.parseDouble(colonnes[4]);

                        SuccursaleDomino succursale = datasource.createSuccursale(address, tel, latitude, longitude);
                        if (succursale != null) {
                            nombreImporte++;
                        }
                    } catch (NumberFormatException e) {
                        Log.w("CSVImporter", "Coordonnées invalides : " + ligne);
                    }
                }
            } finally {
                datasource.close();
            }

            Log.i("CSVImporter",
                    "Succursales importées : " + nombreImporte);

        } catch (IOException | NumberFormatException e) {
            Log.e("CSVImporter",
                    "Erreur lors de l'importation CSV", e);
        }
    }
}