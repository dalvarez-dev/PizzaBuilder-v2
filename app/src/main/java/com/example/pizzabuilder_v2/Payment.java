package com.example.pizzabuilder_v2;



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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Payment extends AppCompatActivity {

    double mtotal_price = MainActivity.getTotal_price();

    TextView subtotaldata;

    EditText addressEditText;

    TextView addressValidationMessage;

    Handler addressvalidationHandler;

    Runnable addressValidatioRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        subtotaldata = findViewById(R.id.subtotaldata);

        addressEditText = findViewById(R.id.given_address_edit_text);
        addressValidationMessage = findViewById(R.id.addressValidationMessage);

        addressvalidationHandler = new Handler();
        addressValidatioRunnable = this::validateAddress;

        addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                addressvalidationHandler.removeCallbacks(addressValidatioRunnable);

                addressvalidationHandler.postDelayed(addressValidatioRunnable, 5000);
            }
        });

    }

    private void validateAddress(){
        String address= addressEditText.getText().toString();
        new GeocodingTask().execute(address);
    }

    private class GeocodingTask extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... addresses) {
            if(addresses.length == 0 || addresses[0].trim().isEmpty()) return false;

            String address = addresses[0].trim();
            String apiKey = "AIzaSyBRiOJkdzKIKHBRdBrJ63Hn0t7xsq1dn40";

            try{
                URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key" + apiKey);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream in = urlConnection.getInputStream();
                    Scanner sc = new Scanner(in);
                    sc.useDelimiter("\\A");

                    if(sc.hasNext()) {
                        String response = sc.next();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray results = jsonObject.getJSONArray("results");

                        if(results.length() > 0) {
                            JSONArray addressComponents = results.getJSONObject(0).getJSONArray("address_components");

                            boolean hasStreet = false;
                            boolean hasCity = false;

                            for(int i = 0; i < addressComponents.length(); i++){
                                JSONObject component = addressComponents.getJSONObject(i);
                                JSONArray types = component.getJSONArray("types");

                                for(int j = 0; j< types.length(); j++){
                                    String type = types.getString(j);

                                    if(type.equals("street_number") || type.equals("route")){
                                        hasStreet= true;

                                        }
                                    else if(type.equals("locality")){
                                        hasCity = true;
                                    }
                                    }
                                }
                            return hasStreet && hasCity;
                        }
                        }
                    return false;
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException | JSONException e){
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean isValid){
            if(isValid){
                addressValidationMessage.setText("Address valid");
            } else{
                addressValidationMessage.setText("Please enter a valid address");
            }
        }
    }








}//end class
