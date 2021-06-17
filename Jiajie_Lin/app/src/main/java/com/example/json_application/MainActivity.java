package com.example.json_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//Jiajie Lin
//lin.jiajie88@gmail.com
public class MainActivity extends AppCompatActivity {
    Button click;
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = findViewById(R.id.button);
        data = findViewById(R.id.fetchdata);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonData fetcheddata = new JsonData();
                fetcheddata.execute();

            }
        });
    }
}
