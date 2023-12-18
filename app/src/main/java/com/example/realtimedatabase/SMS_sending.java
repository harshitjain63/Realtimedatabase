package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMS_sending extends AppCompatActivity {

    EditText number, text;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_sending);
        number = findViewById(R.id.idEdtEmployeePhoneNumber);
        text = findViewById(R.id.idEdtEmployeeName);
        button = findViewById(R.id.idBtnSendData);



        if (ContextCompat.checkSelfPermission(SMS_sending.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            // if permission is not granted then we are requesting for the permissions on below line.
            ActivityCompat.requestPermissions(SMS_sending.this, new String[]{Manifest.permission.SEND_SMS}, 100);
        } else {
            // if permission is already granted then we are displaying a toast message as permission granted.
           // Toast.makeText(SMS_sending.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eNumber = number.getText().toString();
                String eText = text.getText().toString();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(eNumber, null, eText, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Some fields is Empty or SMS permission not given", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // on below line we are checking for the request code.
        if (requestCode == 100) {
            // on below line we are checking if permissions are granted.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // displaying a toast message when permission is granted.
                Toast.makeText(SMS_sending.this, "Read SMS Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                // displaying a toast message when permission is denied.
                Toast.makeText(SMS_sending.this, "Read SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

