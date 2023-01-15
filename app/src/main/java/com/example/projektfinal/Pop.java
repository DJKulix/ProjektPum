package com.example.projektfinal;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Pop extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.popattrwindow);

                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                int width = dm.widthPixels;
                int height = dm.heightPixels;


                getWindow().setLayout((int)(width*.8), (int)(height*.6));
                Button dimmerButton = (Button) findViewById(R.id.buttonDim);
                Button shutterButton = (Button) findViewById(R.id.buttonShu);
                Button redButton = (Button) findViewById(R.id.buttonRed);
                Button greenButton = (Button) findViewById(R.id.buttonGre);
                Button blueButton = (Button) findViewById(R.id.buttonBlu);
                Button whiteButton = (Button) findViewById(R.id.buttonWht);
                Button amberButton = (Button) findViewById(R.id.buttonAmb);



                dimmerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                FixtureBuilder.addAttr("Dimmer");
                                finish();
                        }
                });

                shutterButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                FixtureBuilder.addAttr("Shutter");
                                finish();
                        }
                });
                redButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                FixtureBuilder.addAttr("Red");
                                finish();
                        }
                });
                greenButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                FixtureBuilder.addAttr("Green");
                                finish();
                        }
                });
                blueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                FixtureBuilder.addAttr("Blue");
                                finish();
                        }
                });
                whiteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                FixtureBuilder.addAttr("White");
                                finish();
                        }
                });
                amberButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                FixtureBuilder.addAttr("Amber");
                                finish();
                        }
                });

        }
}
