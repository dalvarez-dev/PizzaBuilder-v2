package com.example.pizzabuilder_v2;



import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Payment extends AppCompatActivity {

    double mtotal_price = MainActivity.getTotal_price();

    TextView subtotaldata;

    EditText addressInput;

    TextView validationMessage;

    private static final String API_KEY = "AIzaSyBRiOJkdzKIKHBRdBrJ63Hn0t7xsq1dn40";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        subtotaldata = findViewById(R.id.subtotaldata);
        addressInput = findViewById(R.id.given_address_edit_text);
        validationMessage = findViewById(R.id.addressValidationMessage);

        Button validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAddress(addressInput.getText().toString());
            }
        });


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
