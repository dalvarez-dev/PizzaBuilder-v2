package com.example.pizzabuilder_v2;



import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Payment extends AppCompatActivity {


    MainActivity main = new MainActivity();

    int msize= MainActivity.getSize();

    private final double taxvalue=.07;

    double subtotal,finaltotal;
    TextView subtotaldata,taxdata, finaldata;
    EditText address, card, expiration, CVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        subtotaldata = findViewById(R.id.subtotaldata);
        taxdata = findViewById(R.id.taxdata);
        finaldata = findViewById(R.id.finaldata);
        address = findViewById(R.id.given_address_edit_text);
        card = findViewById(R.id.card_edit_text);
        expiration = findViewById(R.id.exp_edit_text);
        CVV = findViewById(R.id.CVV_edit_text);


        subtotal = MainActivity.getSubtotal();
        subtotaldata.setText("$"+String.format("%.2f", subtotal));

        taxdata.setText("$"+ subtotal*taxvalue);

        finaltotal= subtotal+(subtotal*taxvalue);
        finaldata.setText("$"+ finaltotal);

        switch(msize){

            case 0:


        }//end switch

    }





}//end class
