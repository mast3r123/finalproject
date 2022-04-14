package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView txtTotal;
    LinearLayout cardDetails;
    RadioGroup rGroup;
    TextView personName;
    TextView personEmail;
    TextView personAddress;
    EditText cardNumber;
    EditText cardExpiry;
    EditText cardCVV;
    Button btnBuy;
    Boolean isValid = true;

    ArrayList<String> orderItemStrings;
    List<OrderItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_checkout);
        txtTotal = findViewById(R.id.textView21);
        cardDetails = findViewById(R.id.card_details);
        rGroup = findViewById(R.id.radio_group);

        personName = findViewById(R.id.editTextTextPersonName);
        personEmail = findViewById(R.id.editTextTextEmailAddress);
        personAddress = findViewById(R.id.editTextTextPostalAddress);
        cardNumber = findViewById(R.id.editTextCardNumber);
        cardExpiry = findViewById(R.id.editTextExpiryNumber);
        cardCVV = findViewById(R.id.editTextCVVNumber);

        btnBuy = findViewById(R.id.button8);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = rGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.radio_cash:
                        cardDetails.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.radio_credit:
                    case R.id.radio_debit:
                        cardDetails.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

        Intent intent = getIntent();
        String total = intent.getStringExtra("total");
        txtTotal.setText(total);

        orderItems = new ArrayList<>();
        final Bundle extra = intent.getExtras();
        orderItemStrings = extra.getStringArrayList("orderitems");

        for (String orderItemString : orderItemStrings)
        {
            String[] items = orderItemString.split("\\|");
            OrderItem orderItem = new OrderItem(items[0], items[1], items[2], items[3]);
            orderItems.add(orderItem);
        }

        btnBuy.setOnClickListener(view -> {
            validateUserDetails();
            if (!(rGroup.getCheckedRadioButtonId() == R.id.radio_cash)) {
                validateCardDetails();
            }

            if (isValid) {
                processOrder();
                clearCart();
            }

        });
    }

    private void validateUserDetails() {
        if (personName.getText().toString().equals("")) {
            personName.setError("Name cannot be empty");
            personName.requestFocus();
            isValid = false;
            return;
        }

        if (personEmail.getText().toString().equals("")) {
            personEmail.setError("Email address cannot be empty");
            personEmail.requestFocus();
            isValid = false;
            return;
        }

        if (personAddress.getText().toString().equals("")) {
            personAddress.setError("Postal address cannot be empty");
            personAddress.requestFocus();
            isValid = false;
            return;
        }
        isValid = true;
    }

    private void validateCardDetails() {
        if (cardNumber.getText().toString().equals("")) {
            cardNumber.setError("Card number cannot be empty");
            cardNumber.requestFocus();
            isValid = false;
            return;
        }

        if (cardExpiry.getText().toString().equals("")) {
            cardExpiry.setError("Card expiry cannot be empty");
            cardExpiry.requestFocus();
            isValid = false;
            return;
        }

        if (cardCVV.getText().toString().equals("")) {
            cardCVV.setError("Card CVV cannot be empty");
            cardCVV.requestFocus();
            isValid = false;
            return;
        }
        isValid = true;
    }

    private void processOrder(){
        Intent orderDetailIntent = new Intent(this, OrderDetail.class);
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("orders");
        Order order;
        String paymentInfo = "";

        int id = rGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radio_cash:
                paymentInfo = "Payed by Cash";
                break;
            case R.id.radio_credit:
                paymentInfo = "Credit Card: " + cardNumber.getText().toString() + " (" + cardExpiry.getText().toString() + ")" ;
                break;
            case R.id.radio_debit:
                paymentInfo = "Debit Card: " + cardNumber.getText().toString() + " (" + cardExpiry.getText().toString() + ")" ;
                break;
            default:
                break;
        }

        try {
            order = new Order(
                mAuth.getCurrentUser().getUid(),
                orderItems,
                personName.getText().toString(),
                personEmail.getText().toString(),
                personAddress.getText().toString(),
                paymentInfo,
                txtTotal.getText().toString(),
                DateFormat.getDateTimeInstance().format(new Date())
            );

            orderRef.push().setValue(order, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference ref) {
                    if (databaseError != null) {
                        // Error!
                        Log.i("databaseError", databaseError.toString());

                    } else {
                        // Go to Order Detail Activity
                        orderDetailIntent.putExtra("orderdetail_username", order.getUsername());
                        orderDetailIntent.putStringArrayListExtra("orderdetail_orderitems", orderItemStrings);
                        orderDetailIntent.putExtra("orderdetail_email", order.getEmail());
                        orderDetailIntent.putExtra("orderdetail_address", order.getAddress());

                        orderDetailIntent.putExtra("orderdetail_totalprice", order.getTotalprice());
                        orderDetailIntent.putExtra("orderdetail_paymentinfo", order.getPaymentinfo());
                        orderDetailIntent.putExtra("orderdetail_orderdate", order.getOrderdate());
                        startActivity(orderDetailIntent);
                    }
                }
            });
        }
        catch (IllegalArgumentException ex) {
            // Invalid form data.
            Log.i("IllegalArgumentException", ex.toString());
        }
    }

    private void clearCart(){
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("cart");
        dbNode.removeValue();
    }
}