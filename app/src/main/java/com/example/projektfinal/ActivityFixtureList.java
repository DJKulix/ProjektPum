package com.example.projektfinal;


import static com.example.projektfinal.MainActivity.fixtureList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;

public class ActivityFixtureList extends AppCompatActivity {

    public int addr = 1;
    TableLayout tableLayout;


    public static void initialize() {
//        fixtureList.add();
    }

    public void updateTable() {
        tableLayout.removeAllViews();
        TableRow tableHeader = new TableRow(this);
        tableHeader.setPadding(25, 25, 25, 25);
        tableHeader.setBackgroundColor(Color.MAGENTA);


        TextView textViewHeaderName = new TextView(this);
        textViewHeaderName.setText("Nazwa");
        tableHeader.addView(textViewHeaderName);


        TextView textViewHeaderAddr = new TextView(this);
        textViewHeaderAddr.setPadding(0, 0, 25, 0);
        textViewHeaderAddr.setText("Adres");
        tableHeader.addView(textViewHeaderAddr);

        TextView textViewHeaderMode = new TextView(this);
        textViewHeaderMode.setText("Tryb");
        tableHeader.addView(textViewHeaderMode);

        TextView textViewHeaderCh = new TextView(this);
        textViewHeaderCh.setText("L. kanałów");
        tableHeader.addView(textViewHeaderCh);

        tableLayout.addView(tableHeader);
        if (!fixtureList.isEmpty()) {


            for (Fixture value : fixtureList) {
                TableRow row = new TableRow(getApplicationContext());
                row.setPadding(25, 25, 25, 25);
                if (!value.isId()) {
                    row.setBackgroundColor(Color.RED);
                }
                TextView textViewName = new TextView(getApplicationContext());
                textViewName.setText(value.getName());
                textViewName.setGravity(Gravity.CENTER);
                row.addView(textViewName);

                TextView textViewAddr = new TextView(getApplicationContext());
                textViewAddr.setText(String.valueOf(value.getAddress()));
                textViewAddr.setPadding(0, 0, 25, 0);
                textViewAddr.setGravity(Gravity.CENTER);
                row.addView(textViewAddr);

                TextView textViewMode = new TextView(getApplicationContext());
                textViewMode.setText(value.getMode());
                textViewMode.setGravity(Gravity.CENTER);
                row.addView(textViewMode);

                TextView textViewCh = new TextView(getApplicationContext());
                textViewCh.setText(String.valueOf(value.getChannels()));
                textViewCh.setGravity(Gravity.CENTER);
                row.addView(textViewCh);

                Button addToBuilder = new Button(getApplicationContext());
                addToBuilder.setText("Builder");

                addToBuilder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ActivityFixtureBuilder.class);
                        intent.putExtra("KEY_NAME", value);
                        startActivity(intent);
                    }
                });

                row.addView(addToBuilder);

                tableLayout.addView(row);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_list);

        Button addToListBtn = findViewById(R.id.addToListBtn);
        Button validateAddressBtn = findViewById(R.id.validateAddressBtn);
        Button autoAddressBtn = findViewById(R.id.autoAddress);

        tableLayout = findViewById(R.id.fixtureListTL);
        updateTable();

//        addToListBtn.setOnClickListener(view -> addFixtureToList());

        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityFixtureBuilder.class);
                startActivity(intent);
            }
        });
        validateAddressBtn.setOnClickListener(view -> validateAddress());

        autoAddressBtn.setOnClickListener(view -> autoAddress());

    }//Ten nawias kończy onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        validateAddress();
        updateTable();
    }

    public void addFixtureToList() {
        Fixture fixture = new Fixture("Par LED", 10, "Classic 10", 5);
        fixtureList.add(fixture);
        TableRow row = new TableRow(getApplicationContext());
        row.setPadding(25, 25, 25, 25);

        TextView textViewName = new TextView(getApplicationContext());
        textViewName.setText(fixture.getName());
        textViewName.setGravity(Gravity.CENTER);
        row.addView(textViewName);

        TextView textViewAddr = new TextView(getApplicationContext());
        textViewAddr.setText(String.valueOf(fixture.getAddress()));
        textViewAddr.setPadding(0, 0, 25, 0);
        textViewAddr.setGravity(Gravity.CENTER);
        row.addView(textViewAddr);

        TextView textViewMode = new TextView(getApplicationContext());
        textViewMode.setText(fixture.getMode());
        textViewMode.setGravity(Gravity.CENTER);
        row.addView(textViewMode);

        TextView textViewCh = new TextView(getApplicationContext());
        textViewCh.setText(String.valueOf(fixture.getChannels()));
        textViewCh.setGravity(Gravity.CENTER);
        row.addView(textViewCh);

        Button addToBuilder = new Button(getApplicationContext());
        addToBuilder.setText("Edycja");

        addToBuilder.setOnClickListener(view1 -> {
            Intent intent = new Intent(getApplicationContext(), ActivityFixtureBuilder.class);
            intent.putExtra("KEY_NAME", fixture);
            startActivity(intent);
        });

        row.addView(addToBuilder);
        tableLayout.addView(row);
    }

    public void autoAddress() {
        int addr = 1;
        for (Fixture fixture : fixtureList) {
            fixture.setAddress(addr);
            addr += fixture.getChannels() + 1;
            fixture.setId(true);
            if (addr > 512) showMessage("Przekroczono dopuszczalną liczbę adresów");
            updateTable();
        }
    }

    public void validateAddress() {
        boolean[] addressArray = new boolean[513];
        for (Fixture fixture : fixtureList) {
            int temp = 0;
            for (int i = fixture.getAddress(); i < fixture.getAddress() + fixture.getChannels(); i++) {
                if (addressArray[i]) {
                    temp += 1;
                }
            }
            if (temp > 0) {
                fixture.setId(false);
            } else {
                for (int i = fixture.getAddress(); i < fixture.getAddress() + fixture.getChannels(); i++) {
                    addressArray[i] = true;
                    fixture.setId(true);
                }
            }
    }
}

    public void showMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
