package com.example.pizzabuilder_v2;



import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class Payment extends AppCompatActivity {


    MainActivity main = new MainActivity();

    int msize = MainActivity.getSize();

    private final double taxvalue = .07;

    double subtotal, finaltotal, tip, tipvalue;

    View vbutton;
    TextView subtotaldata, taxdata, finaldata, order, address_hc, tipdata;
    EditText address_edit, card_edit, expiration_edit, CVV_edit;

    EditText addressInput;

    TextView validationMessage;

    private static final String API_KEY = "AIzaSyBRiOJkdzKIKHBRdBrJ63Hn0t7xsq1dn40";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        address_hc = findViewById(R.id.address_hc);
        order = findViewById(R.id.order);
        subtotaldata = findViewById(R.id.subtotaldata);
        addressInput = findViewById(R.id.given_address_edit_text);
        //validationMessage = findViewById(R.id.addressValidationMessage);
        taxdata = findViewById(R.id.taxdata);
        tipdata = findViewById(R.id.tipdata);
        finaldata = findViewById(R.id.finaldata);
        address_edit = findViewById(R.id.given_address_edit_text);
        card_edit = findViewById(R.id.card_edit_text);
        expiration_edit = findViewById(R.id.exp_edit_text);
        CVV_edit = findViewById(R.id.CVV_edit_text);
        vbutton = findViewById(R.id.validateButton);


        address_edit.setVisibility(View.INVISIBLE);
        address_hc.setVisibility(View.INVISIBLE);
        vbutton.setVisibility(View.INVISIBLE);

        if (MainActivity.getDelivery()) {
            address_edit.setVisibility(View.VISIBLE);
            address_hc.setVisibility(View.VISIBLE);
            vbutton.setVisibility(View.VISIBLE);}


        Button finalbutton = (Button) findViewById(R.id.finalize);

        finalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // click handling code
                Toast.makeText(Payment.this, "Order Placed!", Toast.LENGTH_LONG).show();
            }
        });


        subtotal = MainActivity.getSubtotal();
        tip = 0.00;
        finaltotal = subtotal + (subtotal * taxvalue) + tipvalue;


        subtotaldata.setText("$" + String.format("%.2f", subtotal));
        taxdata.setText("$" + String.format("%.2f", subtotal * taxvalue));
        tipdata.setText("$" + String.format("%.2f", tip));
        finaldata.setText("$" + String.format("%.2f", finaltotal));

        if (msize != 0) {
            switch (msize) {

                case 1:
                    order.setText("Small Pizza");
                    break;
                case 2:
                    order.setText("Medium Pizza");
                    break;
                case 3:
                    order.setText("Large Pizza");
                    break;
            }//end switch
        }//end if

        Button validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAddress(addressInput.getText().toString());
            }
        });



    }

    public void radioPick(View view) {
        // Is button selected?
        boolean selected = ((RadioButton) view).isChecked();
        // check which button was selected
        if (view.getId() == R.id.fifteen) {
            if (selected)
                tip = .15;
        } else if (view.getId() == R.id.twenty) {
            if (selected)
                tip = .20;
        } else if (view.getId() == R.id.ten) {
            if (selected)
                tip = .10;


        }
        updateTip();
        updateTotal();
    }

    public void updateTip() {
        tipvalue = subtotal * tip;
        tipdata.setText("$" + String.format("%.2f", tipvalue));

    }//end updateTip

    public void updateTotal() {
        finaltotal = subtotal + (subtotal * taxvalue) + tipvalue;
        finaldata.setText("$" + String.format("%.2f", finaltotal));

    }







    private void validateAddress(String userAddress){
        Geocoder geocoder = new Geocoder(this);
        try{
            List<Address> addresses = geocoder.getFromLocationName(userAddress, 1);
            if(addresses.isEmpty()){
                Toast.makeText(this, "Address not found, please enter a valid address", Toast.LENGTH_SHORT).show();
            }

            Address address = addresses.get(0);
            double lat = address.getLatitude();
            double lon = address.getLongitude();

            List<Address> reverseGeo = geocoder.getFromLocation(lat, lon, 1);

            if(reverseGeo.isEmpty()){
                Toast.makeText(this, "Error, Could not validate address", Toast.LENGTH_SHORT).show();
            }

            Address reverseAddy = reverseGeo.get(0);

            String makeStandardized = getStandardizedAddress(reverseAddy);

            if (userAddress.equalsIgnoreCase(makeStandardized)){
                validationMessage = findViewById(R.id.addressValidationMessage);
                validationMessage.setText("Address is Valid");
            } else{
                validationMessage = findViewById(R.id.addressValidationMessage);
                validationMessage.setText("Address invalid, you might have meant: " + makeStandardized);
                addressInput.setText(makeStandardized);
            }
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Error occured while trying to validate address", Toast.LENGTH_SHORT).show();
        }
    }

    private String getStandardizedAddress(Address address){
        StringBuilder stringBuilder = new StringBuilder();
        String addressL1 = address.getAddressLine(0);
        String addressL2 = address.getAddressLine(1);
        String locality = address.getLocality();
        String adminArea = address.getAdminArea();
        String postalCode = address.getPostalCode();

        if (addressL1 != null){
            stringBuilder.append(addressL1);
        }
        if(addressL2 != null){
            stringBuilder.append(", ").append(addressL2);
        }
        if(locality != null){
            stringBuilder.append(", ").append(locality);
        }
        if(adminArea != null){
            stringBuilder.append(", ").append(adminArea);
        }
        if(postalCode != null){
            stringBuilder.append(", ").append(postalCode);
        }
        return stringBuilder.toString().trim();
    }

    }







//end class

