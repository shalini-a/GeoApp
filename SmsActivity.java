package com.example.shagniho.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends AppCompatActivity {

    private EditText body , phone;
    private Button send ;
    private final int SEND_SMS_REQUEST_CODE = 0;
    String phoneNo, message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        body = (EditText)findViewById(R.id.body);
        phone = (EditText)findViewById(R.id.phone);
        send = (Button) findViewById(R.id.send);
        String location = getIntent().getStringExtra("Location");
        body.setText(location);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            sendMessage();

            }
        });
    }


    private void sendMessage()
    {
       phoneNo = phone.getText().toString();
        message = body.getText().toString();
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.SEND_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.SEND_SMS} , SEND_SMS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case SEND_SMS_REQUEST_CODE :
            {
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    SmsManager mgr = SmsManager.getDefault();
                    mgr.sendTextMessage(phoneNo , null , message , null , null);
                    Toast.makeText(this , "SMS sent" , Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this , "SMS sending failed" , Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
