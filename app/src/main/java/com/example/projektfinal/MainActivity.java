package com.example.projektfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    public int dmxAddress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Buttons
        final Button btnAddHundreds = (Button) findViewById(R.id.addHundreds);
        final Button btnAddDecimals = (Button) findViewById(R.id.addDecimals);
        final Button btnAddUnits = (Button) findViewById(R.id.addUnits);

        final Button btnDecHundreds = (Button) findViewById(R.id.decHundreds);
        final Button btnDecDecimals = (Button) findViewById(R.id.decDecimals);
        final Button btnDecUnits = (Button) findViewById(R.id.decUnits);

        final Button btnHundreds = (Button) findViewById(R.id.btnHundreds);
        final Button btnDecimals = (Button) findViewById(R.id.btnDecimals);
        final Button btnUnits = (Button) findViewById(R.id.btnUnits);

        final Button btnAddStep = (Button) findViewById(R.id.addStep);
        final Button btnDecStep = (Button) findViewById(R.id.decStep);


        //Listeners

        btnAddHundreds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dmxAddress >= 500){
                    showMessage("Przekroczono maksymalną wartość");
                }
                else {

                    dmxAddress += 100;
                    String newAddress = String.valueOf(dmxAddress);

                    btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                    btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                    btnUnits.setText(String.valueOf(newAddress.charAt(2)));

                }

                calculateDipSwitch();
            }
        });

        btnAddDecimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dmxAddress >= 510) {
                    showMessage("Przekroczono maksymalną wartość");
                } else {

                    dmxAddress += 10;
                    String newAddress = String.valueOf(dmxAddress);

                    if(dmxAddress > 100) {
                        btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                        btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                        btnUnits.setText(String.valueOf(newAddress.charAt(2)));
                    }
                    else{
                        btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
                        btnUnits.setText(String.valueOf(newAddress.charAt(1)));
                    }
                    calculateDipSwitch();
                }
            }
        });

        btnAddUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dmxAddress >= 511){
                    showMessage("Przekroczono maksymalną wartość");
                }
                else {
                    dmxAddress += 1;
                    String newAddress = String.valueOf(dmxAddress);

                    if(dmxAddress > 100) {
                        btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                        btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                        btnUnits.setText(String.valueOf(newAddress.charAt(2)));
                    }
                    else if(dmxAddress > 10){
                        btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
                        btnUnits.setText(String.valueOf(newAddress.charAt(1)));
                    }
                    else{
                        btnUnits.setText(String.valueOf(newAddress.charAt(0)));
                    }
                }

                calculateDipSwitch();
            }
        });

        btnDecHundreds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dmxAddress > 0){
                    int hundredsValue = Integer.parseInt(btnHundreds.getText().toString()) - 1;
                    if (hundredsValue >= 0) {
                        btnHundreds.setText(String.valueOf(hundredsValue));
                        dmxAddress -= 100;
                    }
                }
            }
        });

        btnDecDecimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dmxAddress > 0){
                    int decimalsValue = Integer.parseInt(btnDecimals.getText().toString()) - 1;
                    if (decimalsValue >= 0) {
                        btnDecimals.setText(String.valueOf(decimalsValue));

                    }
                    else{
                        btnDecimals.setText("9");
                        int hundredsValue = Integer.parseInt(btnHundreds.getText().toString()) - 1;
                        btnHundreds.setText(String.valueOf(hundredsValue));
                    }
                    dmxAddress -= 10;
                }

                calculateDipSwitch();
            }
        });

        btnDecUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dmxAddress > 0){
                    int unitsValue = Integer.parseInt(btnUnits.getText().toString()) - 1;
                    if (unitsValue >= 0) {
                        btnUnits.setText(String.valueOf(unitsValue));
                    }

                    else{
                        btnUnits.setText("9");
//                        int hundredsValue = Integer.parseInt(btnHundreds.getText().toString()) - 1;
//                        btnHundreds.setText(String.valueOf(hundredsValue));
                        int decimalsValue = Integer.parseInt(btnDecimals.getText().toString()) -1;
                        if(decimalsValue < 0){
                            btnDecimals.setText("9");
                            int hundredsValue = Integer.parseInt(btnHundreds.getText().toString())-1;
                            btnHundreds.setText(String.valueOf(hundredsValue));
                        }
                        else{
                            btnDecimals.setText(String.valueOf(decimalsValue));
                        }
                    }
                    dmxAddress -= 1;
                }
                calculateDipSwitch();
            }
        });


    btnAddStep.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String step = btnAddStep.getText().toString();
            step = step.substring(1);

            int stepValue = Integer.parseInt(step);
            if(dmxAddress + stepValue > 511)
                showMessage("Przekroczono maksymalną wartość");

            else{
                dmxAddress += stepValue;
                String newAddress = String.valueOf(dmxAddress);
                showMessage(String.valueOf(newAddress.charAt(0)));

                if(dmxAddress >= 100) {
                    btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                    btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                    btnUnits.setText(String.valueOf(newAddress.charAt(2)));
                }
                else if(dmxAddress >= 10){
                    btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
                    btnUnits.setText(String.valueOf(newAddress.charAt(1)));
                }
                else{
                    btnUnits.setText(String.valueOf(newAddress.charAt(0)));
                }

            }
        }
    });


        btnDecStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String step = btnDecStep.getText().toString();
                step = step.substring(1);

                int stepValue = Integer.parseInt(step);

                if(dmxAddress - stepValue >= 0){
                    dmxAddress += stepValue;
                    String newAddress = String.valueOf(dmxAddress);
                    showMessage(String.valueOf(newAddress.charAt(0)));

                    if(dmxAddress >= 100) {
                        btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                        btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                        btnUnits.setText(String.valueOf(newAddress.charAt(2)));
                    }
                    else if(dmxAddress >= 10){
                        btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
                        btnUnits.setText(String.valueOf(newAddress.charAt(1)));
                    }
                    else{
                        btnUnits.setText(String.valueOf(newAddress.charAt(0)));
                    }

                }
            }
        });



    }//Ten ładny nawias kończy onCreate()




    public void calculateDipSwitch(){
        SwitchMaterial[] tab = new SwitchMaterial[9];
        tab[0] = (SwitchMaterial) findViewById(R.id.switch9);
        tab[1] = (SwitchMaterial) findViewById(R.id.switch8);
        tab[2] = (SwitchMaterial) findViewById(R.id.switch7);
        tab[3] = (SwitchMaterial) findViewById(R.id.switch6);
        tab[4] = (SwitchMaterial) findViewById(R.id.switch5);
        tab[5] = (SwitchMaterial) findViewById(R.id.switch4);
        tab[6] = (SwitchMaterial) findViewById(R.id.switch3);
        tab[7] = (SwitchMaterial) findViewById(R.id.switch2);
        tab[8] = (SwitchMaterial) findViewById(R.id.switch1);


        String binaryNumber = Integer.toBinaryString(dmxAddress);
        String padding = String.format("%9s", binaryNumber).replace(' ', '0');

         for(int i = 8; i >= 0; i--){
            if(padding.charAt(i) == '1'){
                tab[i].setChecked(true);
            }
            else
                tab[i].setChecked(false);
        }
//        showMessage(padding);
        }



    public void showMessage(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();


    }


}