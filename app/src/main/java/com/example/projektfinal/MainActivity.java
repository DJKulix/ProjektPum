package com.example.projektfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    public static List<Fixture> fixtureList = new ArrayList<>();
    public int dmxAddress = 0;
    Button btnHundreds;
    Button btnDecimals;
    Button btnUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),ActivityFixtureList.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),ActivityFixtureBuilder.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        SharedPreferences preferences = getSharedPreferences("DMXAddressKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("dmx_addr", dmxAddress);
        editor.apply();



        //Buttons
        final Button btnAddHundreds = findViewById(R.id.addHundreds);
        final Button btnAddDecimals = findViewById(R.id.addDecimals);
        final Button btnAddUnits = findViewById(R.id.addUnits);

        final Button btnDecHundreds = findViewById(R.id.decHundreds);
        final Button btnDecDecimals = findViewById(R.id.decDecimals);
        final Button btnDecUnits = findViewById(R.id.decUnits);

        btnHundreds = findViewById(R.id.btnHundreds);
        btnDecimals = findViewById(R.id.btnDecimals);
        btnUnits = findViewById(R.id.btnUnits);

        final Button btnAddStep = findViewById(R.id.addStep);
        final Button btnDecStep = findViewById(R.id.decStep);

        final Button btnChngStep = findViewById(R.id.changeStepButton);

        final TextInputEditText tiChngStep = findViewById(R.id.stepTextInput);

        final Button btnRotateUpsideDown = findViewById(R.id.rotateUpDownBtn);
        final Button btnRotateLeftRight = findViewById(R.id.rotateLeftRightBtn);

        //Listeners

        btnRotateLeftRight.setOnClickListener(view -> {
            TableLayout tableLayout = findViewById(R.id.switchTL);
            if(tableLayout.getRotationX() == 0)
                tableLayout.setRotationX(180);
            else
                tableLayout.setRotationX(0);

        });

        btnRotateUpsideDown.setOnClickListener(view -> {
            TableLayout tableLayout = findViewById(R.id.switchTL);
            if(tableLayout.getRotationY() == 0)
                tableLayout.setRotationY(180);
            else
                tableLayout.setRotationY(0);
        });

        btnAddHundreds.setOnClickListener(view -> {
            if (dmxAddress >= 500) {
                showMessage("Przekroczono maksymalną wartość");
            } else {
                dmxAddress += 100;
                String newAddress = String.valueOf(dmxAddress);

                btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                btnUnits.setText(String.valueOf(newAddress.charAt(2)));

            }
            calculateDipSwitch();
        });


        btnAddDecimals.setOnClickListener(view -> {
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
        });

        btnAddUnits.setOnClickListener(view -> {
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
        });

        btnDecHundreds.setOnClickListener(view -> {
            if (dmxAddress > 0) {
                int hundredsValue = Integer.parseInt(btnHundreds.getText().toString()) - 1;
                if (hundredsValue >= 0) {
                    btnHundreds.setText(String.valueOf(hundredsValue));
                    dmxAddress -= 100;
                }
            }
        });

        btnDecDecimals.setOnClickListener(view -> {
            if (dmxAddress > 0) {
                int decimalsValue = Integer.parseInt(btnDecimals.getText().toString()) - 1;
                if (decimalsValue >= 0) {
                    btnDecimals.setText(String.valueOf(decimalsValue));

                } else {
                    btnDecimals.setText("9");
                    int hundredsValue = Integer.parseInt(btnHundreds.getText().toString()) - 1;
                    btnHundreds.setText(String.valueOf(hundredsValue));
                }
                dmxAddress -= 10;
            }
            calculateDipSwitch();
        });

        btnDecUnits.setOnClickListener(view -> {
            if (dmxAddress > 0) {
                int unitsValue = Integer.parseInt(btnUnits.getText().toString()) - 1;
                if (unitsValue >= 0) {
                    btnUnits.setText(String.valueOf(unitsValue));
                } else {
                    btnUnits.setText("9");
                    int decimalsValue = Integer.parseInt(btnDecimals.getText().toString()) - 1;
                    if (decimalsValue < 0) {
                        btnDecimals.setText("9");
                        int hundredsValue = Integer.parseInt(btnHundreds.getText().toString()) - 1;
                        btnHundreds.setText(String.valueOf(hundredsValue));
                    } else {
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
            if (dmxAddress + stepValue > 511) showMessage("Przekroczono maksymalną wartość");

            else {
                dmxAddress += stepValue;
                String newAddress = String.valueOf(dmxAddress);

                if (dmxAddress >= 100) {
                    btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                    btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                    btnUnits.setText(String.valueOf(newAddress.charAt(2)));
                } else if (dmxAddress >= 10) {
                    btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
                    btnUnits.setText(String.valueOf(newAddress.charAt(1)));
                } else {
                    btnUnits.setText(String.valueOf(newAddress.charAt(0)));
                }
                calculateDipSwitch();
            }
        });


        btnDecStep.setOnClickListener(view -> {
            String step = btnDecStep.getText().toString();
            step = step.substring(1);

            int stepValue = Integer.parseInt(step);

            if (dmxAddress - stepValue >= 0) {
                dmxAddress -= stepValue;
                String newAddress = String.valueOf(dmxAddress);
                if (dmxAddress >= 100) {
                    btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
                    btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
                    btnUnits.setText(String.valueOf(newAddress.charAt(2)));
                } else if (dmxAddress >= 10) {
                    btnHundreds.setText("0");
                    btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
                    btnUnits.setText(String.valueOf(newAddress.charAt(1)));
                } else {
                    btnHundreds.setText("0");
                    btnDecimals.setText("0");
                    btnUnits.setText(String.valueOf(newAddress.charAt(0)));
                }
            }
            else{
                showMessage("Kolejny skok spowoduje niepoprawny adres");
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


    public void calculateAddr(View view) {

        int tempAddr = 0;
        SwitchMaterial[] tab = new SwitchMaterial[9];
        tab[8] = findViewById(R.id.switch9);
        tab[7] = findViewById(R.id.switch8);
        tab[6] = findViewById(R.id.switch7);
        tab[5] = findViewById(R.id.switch6);
        tab[4] = findViewById(R.id.switch5);
        tab[3] = findViewById(R.id.switch4);
        tab[2] = findViewById(R.id.switch3);
        tab[1] = findViewById(R.id.switch2);
        tab[0] = findViewById(R.id.switch1);
        for (int i = 0; i < 9; i++) {
            if (tab[i].isChecked()) {
                tempAddr += Math.pow(2, i);
            }
        }
        dmxAddress = tempAddr;
        String newAddress = String.valueOf(dmxAddress);
        if (dmxAddress > 99) {
            btnHundreds.setText(String.valueOf(newAddress.charAt(0)));
            btnDecimals.setText(String.valueOf(newAddress.charAt(1)));
            btnUnits.setText(String.valueOf(newAddress.charAt(2)));
        } else if (dmxAddress < 10) {
            btnHundreds.setText("0");
            btnDecimals.setText("0");
            btnUnits.setText(String.valueOf(newAddress.charAt(0)));
        } else {
            btnHundreds.setText("0");
            btnDecimals.setText(String.valueOf(newAddress.charAt(0)));
            btnUnits.setText(String.valueOf(newAddress.charAt(1)));
        }
    }


    public void calculateDipSwitch() {
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

        for (int i = 8; i >= 0; i--)
            tab[i].setChecked(padding.charAt(i) == '1');

    }

    public void showMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();


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