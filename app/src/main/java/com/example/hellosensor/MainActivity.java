package com.example.hellosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startCompass(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    public void startAcc(View view) {
        Intent intent = new Intent(this, AccActivity.class);
        startActivity(intent);
    }
}
