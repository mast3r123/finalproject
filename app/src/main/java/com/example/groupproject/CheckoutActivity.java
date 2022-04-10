package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckoutActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        btnBuy.setOnClickListener(view -> {
            validateUserDetails();
            if (!(rGroup.getCheckedRadioButtonId() == R.id.radio_cash)) {
                validateCardDetails();
            }

            if (isValid) {
                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("cart");
                dbNode.removeValue();
                moveBackActivity();
                Toast.makeText(this, "Successfully placed order", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void moveBackActivity() {
        Intent newIntent = new Intent(this, EventList.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);
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
}