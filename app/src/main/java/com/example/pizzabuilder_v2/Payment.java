package com.example.pizzabuilder_v2;



import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Payment extends AppCompatActivity {

    double mtotal_price = MainActivity.getTotal_price();

    TextView subtotaldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        subtotaldata = findViewById(R.id.subtotaldata);
    }








}//end class
