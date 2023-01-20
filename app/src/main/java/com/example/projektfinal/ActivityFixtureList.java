package com.example.projektfinal;

import static com.example.projektfinal.MainActivity.fixtureList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class ActivityFixtureList extends AppCompatActivity {

    TableLayout tableLayout;
    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            File file = new File(uri.getPath());
            final String[] split = file.getPath().split(":");//split the path.
            String filePath = "storage/emulated/0/" + split[1];//assign it to a string(your choice).
            file = new File(filePath);
            showMessage(file.toString());
            try {
                Reader reader = new FileReader(filePath);
                Fixture[] tempArray = new Gson().fromJson(reader, Fixture[].class);
                fixtureList.addAll(Arrays.asList(tempArray));
                updateTable();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.dashboard:
                    return true;
                case R.id.about:
                    startActivity(new Intent(getApplicationContext(), ActivityFixtureBuilder.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        Button addToListBtn = findViewById(R.id.addToListBtn);
        Button validateAddressBtn = findViewById(R.id.validateAddressBtn);
        Button autoAddressBtn = findViewById(R.id.autoAddress);

        Button importToJsonBtn = findViewById(R.id.importJSONBtn);
        Button exportToJsonBtn = findViewById(R.id.exportJSONBtn);

        importToJsonBtn.setOnClickListener(view -> importJson());
        exportToJsonBtn.setOnClickListener(view -> {
            try {
                exportJson();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tableLayout = findViewById(R.id.fixtureListTL);
        updateTable();

        addToListBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ActivityFixtureBuilder.class);
            startActivity(intent);
        });
        validateAddressBtn.setOnClickListener(view -> validateAddress());
        autoAddressBtn.setOnClickListener(view -> autoAddress());
    }//Ten nawias kończy onCreate()

    @Override
    protected void onResume() {
        super.onResume();
//        validateAddress();
        updateTable();
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

    public static void exportJson() throws IOException {
        String json = new Gson().toJson(fixtureList);
        String fileName = "fixtureList.json";
        FileWriter fileWriter = new FileWriter(new File(Environment.getExternalStorageDirectory(), fileName));
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();
    }

    public void importJson() {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("application/json");
        data = Intent.createChooser(data, "Wybierz plik json");
        sActivityResultLauncher.launch(data);

    }

    public void showMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public void updateTable() {
        tableLayout.removeAllViews();
        TableRow tableHeader = new TableRow(this);
        tableHeader.setPadding(25, 25, 25, 25);
        tableHeader.setBackgroundColor(Color.MAGENTA);


        TextView textViewHeaderName = new TextView(this);
        textViewHeaderName.setText("Nazwa");
        textViewHeaderName.setWidth(275);
        textViewHeaderName.setGravity(Gravity.CENTER);
        tableHeader.addView(textViewHeaderName);


        TextView textViewHeaderAddr = new TextView(this);
        textViewHeaderAddr.setPadding(0, 0, 25, 0);
        textViewHeaderAddr.setText("Adres");
        textViewHeaderAddr.setWidth(150);

        tableHeader.addView(textViewHeaderAddr);

        TextView textViewHeaderMode = new TextView(this);
        textViewHeaderMode.setText("Tryb");
        textViewHeaderMode.setWidth(225);
        textViewHeaderMode.setGravity(Gravity.CENTER);
        tableHeader.addView(textViewHeaderMode);

        TextView textViewHeaderCh = new TextView(this);
        textViewHeaderCh.setText("L. kanałów");
        textViewHeaderCh.setWidth(150);
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
                addToBuilder.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_edit_24, 0, 0, 0);

                addToBuilder.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), ActivityFixtureBuilder.class);
                    intent.putExtra("KEY_NAME", value);
                    startActivity(intent);
                });

                row.addView(addToBuilder);

                tableLayout.addView(row);
            }
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

}
