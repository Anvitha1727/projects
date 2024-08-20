package com.busbooking;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin);
        setTitle("Admin Login");
    }
    public void adminlogin(View view) {

        EditText username = (EditText) findViewById(R.id.uid);
        EditText pwd = (EditText) findViewById(R.id.pwd);

        String unm1 = username.getText().toString();
        String pwd1 = pwd.getText().toString();
        if (null == unm1 || unm1.trim().length() == 0) {
            username.setError("Enter  UserName");
            username.requestFocus();
        } else if (null == pwd1 || pwd1.trim().length() == 0) {
            pwd.setError("Enter  Password");
            pwd.requestFocus();
        } else {
            if (unm1.equals("anvitha") && pwd1.equals("anvitha")) {
                Intent i = new Intent(Admin.this, AdminHome.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(Admin.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
