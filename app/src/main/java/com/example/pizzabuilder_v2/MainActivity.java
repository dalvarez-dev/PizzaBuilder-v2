package com.example.pizzabuilder_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static Pizza pizza;

    static boolean deliveryselected=false;
    TextView total;
    static double total_price;
    TextView delivery;

    static int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pizza = new Pizza();
        total = findViewById(R.id.total);
        delivery = findViewById(R.id.deliveryNote);

        Button donebutton= (Button)findViewById(R.id.donebuilding);
        donebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                Intent intent = new Intent(MainActivity.this, Payment.class);
                startActivity(intent);
            }
        });
    }
    public void toppingRandomizer(View view) {
            CheckBox topping1 = findViewById(R.id.checkBox1);
            CheckBox topping2 = findViewById(R.id.checkBox2);
            CheckBox topping3 = findViewById(R.id.checkBox3);

            topping1.setChecked(false);
            topping2.setChecked(false);
            topping3.setChecked(false);

            int randomTopping = new Random().nextInt(3);

            for (int i = 0; i < randomTopping; i++) {
                int randomCheckbox = new Random().nextInt(3);

                switch (randomCheckbox) {
                    case 0:
                        topping1.setChecked(true);
                        onCheckboxSelect(topping1);
                        break;

                    case 1:
                        topping2.setChecked(true);
                        onCheckboxSelect(topping2);
                        break;

                    case 2:
                        topping3.setChecked(true);
                        onCheckboxSelect(topping3);
                        break;
                }
            }
    }
    public void radioSelect(View view) {
        // Is button selected?
        boolean selected = ((RadioButton) view).isChecked();
        // check which button was selected
        if (view.getId() == R.id.radioButton1) {
            if (selected)
                pizza.setPizza_size_price(8); size=1;
        } else if (view.getId() == R.id.radioButton2) {
            if (selected)
                pizza.setPizza_size_price(10); size=2;
        } else if (view.getId() == R.id.radioButton3) {
            if (selected)
                pizza.setPizza_size_price(12); size=3;
        }
        // total.setText("Total Price: $" + String.format("%.2f", pizza.getPizza_size_price()));
        updateTotalPrice(); // this method replaces the statement above to calculate the cost correctly

    }

    public void onCheckboxSelect(View view) {
        boolean selected = ((CheckBox) view).isChecked();

        if (view.getId() == R.id.checkBox3) {
            if (selected)
                pizza.setMeat_price(2);
            else
                pizza.setMeat_price(0);
        } else if (view.getId() == R.id.checkBox2) {
            if (selected)
                pizza.setVeggies_price(2);
            else
                pizza.setVeggies_price(0);
        } else if (view.getId() == R.id.checkBox1) {
            if (selected)
                pizza.setCheese_price(1);
            else
                pizza.setCheese_price(0);
        }

        // total.setText("Total Price: $" + String.format("%.2f", calculate_total()));
        updateTotalPrice();
    }

    // updates the total price whenever the size or toppings change
    private void updateTotalPrice() {
        total.setText("Total Price: $" + String.format("%.2f", calculate_total()));
    }

    public static double calculate_total() {
        total_price = pizza.getPizza_size_price() + pizza.getCheese_price() + pizza.getMeat_price()
                + pizza.getVeggies_price();
        return total_price;
    }


    public void onSwitchSelect(View view) {

        Switch switchButton = (Switch) view;
        if (switchButton.isChecked()){
            delivery.setText("Delivery required!");
            deliveryselected=true;}
        else
            delivery.setText("");
        }

        public static double getSubtotal() {
            double subtotal = calculate_total();

            return subtotal;
        }

        public static int getSize(){
            return size;
        }

        public static boolean getDelivery(){
            return deliveryselected;}




}//end class