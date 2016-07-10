//package com.example.android.justjava;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = quantity * 5;

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        Log.v("MainActivity", "Has chocolate: " + hasChocolate);

        EditText textPersonName = (EditText) findViewById(R.id.text_person_name);
        String personName = textPersonName.getText().toString();
        Log.v("MainActivity", "Name: " + personName);

        String priceMessage = createOrderSummary(personName, price, hasWhippedCream, hasChocolate);

        // displayMessage(priceMessage);
        String[] mailtoEmails = new String[]{"google@gmail.com", "microsoft@hotmail.com"};
        String subject = getString(R.string.JustJavaOrderFor) + personName;
        String bodyText = priceMessage;
        // composeEmail(mailtoEmails, subject, bodyText);
        composeEmail(null, subject, bodyText);
    }

    private String createOrderSummary(String personName, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = getString(R.string.order_summary_name, personName);
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + hasWhippedCream;
        priceMessage += "\n" + getString(R.string.add_chocolate) + hasChocolate;

        if (hasWhippedCream) {
            price = price + (quantity * 2);
        }
        if (hasChocolate) {
            price = price + (quantity * 1);
        }

        priceMessage += "\n" + getString(R.string.Quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity >= 10) {
            quantity = 10;
            Toast.makeText(getApplicationContext(), "Maximum quantity = 10", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity <= 0) {
            quantity = 0;
            Toast.makeText(getApplicationContext(), "Minimum quantity = 0", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    // developer.android.com/guide/components/intents-common.html#Email
    public void composeEmail(String[] addresses, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
