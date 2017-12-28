package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=  2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        String priceMessage=createOrderSummary(price);
       // displayMessage(priceMessage);
        composeEmail(getString(R.string.name) +": " +getNameEditText(),priceMessage);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void decrement(View view) {
        if (quantity-1 >= 1){
            quantity = quantity -1;
        }
        else {
            Toast toast = Toast.makeText(this,getString(R.string.quantity_maus_be_greater_than) + 0, Toast.LENGTH_LONG);
            toast.show();

        }

        displayQuantity(quantity);

    }

    public void increment(View view) {
        if(quantity+1 <= 100){
            quantity = quantity + 1;
        }
        else {
            Toast toast = Toast.makeText(this,getString(R.string.quantity_must_be_lower_than) +100, Toast.LENGTH_LONG);
            toast.show();
        }
        displayQuantity(quantity);
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    private int calculatePrice(){
        int price = 5;
        if (hasWhippedCream()) price += 1;
        if (hasChocolate()) price += 2;
        price *= quantity ;
        return price;

    }

    private Boolean hasWhippedCream(){
        CheckBox hwc = (CheckBox) findViewById(R.id.hasWhippedCream);
        return hwc.isChecked();
    }
    private Boolean hasChocolate(){
        CheckBox hc = (CheckBox) findViewById(R.id.hasChocolate);
        return hc.isChecked();
    }
    private String getNameEditText(){
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        return "" + nameEditText.getText();
    }
    private String createOrderSummary(int price){
        String orderSummary = "";
        orderSummary += getString(R.string.name) +": " +getNameEditText()+"\n";
        orderSummary += getString(R.string.add_whipped_cream)+hasWhippedCream()+"\n";
        orderSummary += getString(R.string.add_chocolate)+hasChocolate()+"\n";
        orderSummary += getString(R.string.quantity)+": "+ quantity + "\n";
        orderSummary += getString(R.string.total) + price + "\n";
        orderSummary += getString(R.string.thank_you);
        return  orderSummary;
    }
    public void composeEmail( String subject, String messageBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, messageBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}