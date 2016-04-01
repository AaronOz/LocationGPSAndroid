package com.example.aaron.locationgps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


//import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showMapAction(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
}
