package com.example.projektfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    public static List<Fixture> fixtureList = new ArrayList<>();
//    public static boolean[] addressValidation = new boolean[512];

    public int dmxAddress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences preferences = getSharedPreferences("DMXAddressKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("dmx_addr",dmxAddress);
        editor.apply();

        //Zapodanie listy


        //Buttons
        final Button btnAddHundreds = findViewById(R.id.addHundreds);
        final Button btnAddDecimals = findViewById(R.id.addDecimals);
        final Button btnAddUnits = findViewById(R.id.addUnits);

        final Button btnDecHundreds = findViewById(R.id.decHundreds);
        final Button btnDecDecimals = findViewById(R.id.decDecimals);
        final Button btnDecUnits = findViewById(R.id.decUnits);

        final Button btnHundreds = findViewById(R.id.btnHundreds);
        final Button btnDecimals = findViewById(R.id.btnDecimals);
        final Button btnUnits = findViewById(R.id.btnUnits);

        final Button btnAddStep = findViewById(R.id.addStep);
        final Button btnDecStep = findViewById(R.id.decStep);

        final Button btnChngStep = findViewById(R.id.changeStepButton);

        final TextInputEditText tiChngStep = findViewById(R.id.stepTextInput);


        //Listeners

        btnAddHundreds.setOnClickListener(view -> {
//                if(dmxAddress >= 500){
//                    showMessage("Przekroczono maksymalną wartość");
//                }
//                else {
//
//                    dmxAddress += 100;
//                    String newAddress = String.valueOf(dmxAddress);
//
//                    btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
//                    btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
//                    btnUnits.setText(String.valueOf(newAddress.charAt(2)));
//
//                }
//
//                calculateDipSwitch();
            try {
                writeToFile("text", getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnAddDecimals.setOnClickListener(view -> {
//                if (dmxAddress >= 510) {
//                    showMessage("Przekroczono maksymalną wartość");
//                } else {
//
//                    dmxAddress += 10;
//                    String newAddress = String.valueOf(dmxAddress);
//
//                    if(dmxAddress > 100) {
//                        btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
//                        btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
//                        btnUnits.setText(String.valueOf(newAddress.charAt(2)));
//                    }
//                    else{
//                        btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
//                        btnUnits.setText(String.valueOf(newAddress.charAt(1)));
//                    }
//                    calculateDipSwitch();
//                }
            Intent intent = new Intent(getApplicationContext(), ActivityFixtureList.class);
            startActivity(intent);
        });

        btnAddUnits.setOnClickListener(view -> {
//            if(dmxAddress >= 511){
//                showMessage("Przekroczono maksymalną wartość");
//            }
//            else {
//                dmxAddress += 1;
//                String newAddress = String.valueOf(dmxAddress);
//
//                if(dmxAddress > 100) {
//                    btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
//                    btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
//                    btnUnits.setText(String.valueOf(newAddress.charAt(2)));
//                }
//                else if(dmxAddress > 10){
//                    btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
//                    btnUnits.setText(String.valueOf(newAddress.charAt(1)));
//                }
//                else{
//                    btnUnits.setText(String.valueOf(newAddress.charAt(0)));
//                }
//            }
//
//            calculateDipSwitch();

            Intent intent = new Intent(getApplicationContext(), ActivityFixtureBuilder.class);
            startActivity(intent);
        });

        btnDecHundreds.setOnClickListener(view -> {
            if(dmxAddress > 0){
                int hundredsValue = Integer.parseInt(btnHundreds.getText().toString()) - 1;
                if (hundredsValue >= 0) {
                    btnHundreds.setText(String.valueOf(hundredsValue));
                    dmxAddress -= 100;
                }
            }
        });

        btnDecDecimals.setOnClickListener(view -> {
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
        });

        btnDecUnits.setOnClickListener(view -> {
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
        });


    btnAddStep.setOnClickListener(view -> {
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
            calculateDipSwitch();
        }
    });


        btnDecStep.setOnClickListener(view -> {
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
        });

    btnChngStep.setOnClickListener(view -> {
        String step = Objects.requireNonNull(tiChngStep.getText()).toString();
        String add = "+" + step;
        String dec = "-" + step;
        btnDecStep.setText(dec);
        btnAddStep.setText(add);

    });

    }//Ten ładny nawias kończy onCreate()




    public void calculateDipSwitch(){
        SwitchMaterial[] tab = new SwitchMaterial[9];
        tab[0] = findViewById(R.id.switch9);
        tab[1] = findViewById(R.id.switch8);
        tab[2] = findViewById(R.id.switch7);
        tab[3] = findViewById(R.id.switch6);
        tab[4] = findViewById(R.id.switch5);
        tab[5] = findViewById(R.id.switch4);
        tab[6] = findViewById(R.id.switch3);
        tab[7] = findViewById(R.id.switch2);
        tab[8] = findViewById(R.id.switch1);


        String binaryNumber = Integer.toBinaryString(dmxAddress);
        String padding = String.format("%9s", binaryNumber).replace(' ', '0');

         for(int i = 8; i >= 0; i--)
             tab[i].setChecked(padding.charAt(i) == '1');

        }



    public void showMessage(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();


    }

    private void writeToFile(String data,Context context) throws IOException {

        String fileName = "file.txt";
        File path = getApplicationContext().getFilesDir();
        System.out.println(path.toString());

        FileOutputStream writer = new FileOutputStream(new File(path, fileName));
        writer.write(data.getBytes());
        writer.close();

        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

//        File path = context.getFilesDir();
//        File path2 = context.getExternalFilesDir(null);
//        File file = new File(path, "my-file-name.txt");
//        File file2 = new File(path2, "my-file-name.txt");
//
//        FileOutputStream stream = new FileOutputStream(file);
//        try {
//            stream.write("text-to-write".getBytes());
//        } finally {
//            stream.close();
//        }
//
//        FileOutputStream stream2 = new FileOutputStream(file2);
//        try {
//            stream.write("text-to-write".getBytes());
//        } finally {
//            stream.close();
//        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPreferences = getSharedPreferences("DMXAddressKey", Context.MODE_PRIVATE);
        dmxAddress = sharedPreferences.getInt("dmx_addr", dmxAddress);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("DMXAddressKey", Context.MODE_PRIVATE);
        dmxAddress = sharedPreferences.getInt("dmx_addr", dmxAddress);
    }
}