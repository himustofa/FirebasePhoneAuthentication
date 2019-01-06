package com.apps.firebasephoneauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class PhoneActivity extends AppCompatActivity {

    private EditText phone;
    private Button submit;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        phone = findViewById(R.id.phone_number);
        submit = findViewById(R.id.submit_button);
        spinner = findViewById(R.id.country_spinner);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ConstantKey.countryAreaCodes);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition("+880")); //set item by default

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = spinner.getSelectedItem().toString();
                String mobile = phone.getText().toString().trim();
                if (TextUtils.isEmpty(mobile) || mobile.length() < 10) {
                    phone.setError("Enter a valid mobile");
                    phone.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneActivity.this, VerifyPhoneActivity.class);
                intent.putExtra(ConstantKey.PHONE_NUMBER, code+mobile);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //====================================================| To Check Firebase Authentication
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //For login to clear this screen for that did not back this screen

            startActivity(intent);
        }
    }
}
