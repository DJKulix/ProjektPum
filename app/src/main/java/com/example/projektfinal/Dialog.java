package com.example.projektfinal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Dialog extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_builder);

        final TextView nameTV = findViewById(R.id.nameTIBuilder);
        final TextView addressTV = findViewById(R.id.addressTIBuilder);
        final TextView modeTV = findViewById(R.id.modeTIBuilder);
        final TextView descriptionTV = findViewById(R.id.channelsTIBuilder);

        final Button saveBtn = (Button) findViewById(R.id.buildFixtureButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fixture fixture = new Fixture(
//                        MainMenu.fixtureQuantity +1,
                        nameTV.getText().toString(),
                        Integer.parseInt(addressTV.getText().toString()),
                        modeTV.getText().toString()
//                        descriptionTV.getText().toString()
                );
//                MainMenu.fixtureQuantity++;
//                MainMenu.fixtureList.add(fixture);
            }
        });
    }
}
