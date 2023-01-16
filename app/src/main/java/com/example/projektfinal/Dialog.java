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

        final TextView nameTV = (TextView) findViewById(R.id.nameTextInput);
        final TextView addressTV = (TextView) findViewById(R.id.addressTextInput);
        final TextView modeTV = (TextView) findViewById(R.id.modeTextInput);
        final TextView descriptionTV = (TextView) findViewById(R.id.descriptionTextInput);

        final Button saveBtn = (Button) findViewById(R.id.saveFixtureButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fixture fixture = new Fixture(
                        MainMenu.fixtureQuantity +1,
                        nameTV.getText().toString(),
                        Integer.parseInt(addressTV.getText().toString()),
                        modeTV.getText().toString(),
                        descriptionTV.getText().toString()
                );
                MainMenu.fixtureQuantity++;
                MainMenu.fixtureList.add(fixture);
            }
        });
    }
}
