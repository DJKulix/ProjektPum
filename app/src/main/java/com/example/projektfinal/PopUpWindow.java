package com.example.projektfinal;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

/**
 * Klasa używana do wyświetlenia pop-upu z listą atrybutów
 */
public class PopUpWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popattrwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;


        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        Button dimmerButton = findViewById(R.id.buttonDim);
        Button shutterButton = findViewById(R.id.buttonShu);
        Button redButton = findViewById(R.id.buttonRed);
        Button greenButton = findViewById(R.id.buttonGre);
        Button blueButton = findViewById(R.id.buttonBlu);
        Button whiteButton = findViewById(R.id.buttonWht);
        Button amberButton = findViewById(R.id.buttonAmb);


        dimmerButton.setOnClickListener(view -> {
            ActivityFixtureBuilder.addAttr("Dimmer");
            finish();
        });

        shutterButton.setOnClickListener(view -> {
            ActivityFixtureBuilder.addAttr("Shutter");
            finish();
        });
        redButton.setOnClickListener(view -> {
            ActivityFixtureBuilder.addAttr("Red");
            finish();
        });
        greenButton.setOnClickListener(view -> {
            ActivityFixtureBuilder.addAttr("Green");
            finish();
        });
        blueButton.setOnClickListener(view -> {
            ActivityFixtureBuilder.addAttr("Blue");
            finish();
        });
        whiteButton.setOnClickListener(view -> {
            ActivityFixtureBuilder.addAttr("White");
            finish();
        });
        amberButton.setOnClickListener(view -> {
            ActivityFixtureBuilder.addAttr("Amber");
            finish();
        });

    }
}
