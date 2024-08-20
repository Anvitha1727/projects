package com.busbooking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;


public class Registration extends AppCompatActivity {



    SQLiteDatabase db;
    TextInputEditText name, uid, pwd, mno, email;
    String name1, uid1, pwd1, mno1, email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.user_register );
        setTitle("User Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = openOrCreateDatabase( "bus_booking", android.content.Context.MODE_PRIVATE, null );
        name = findViewById( R.id.name );
        uid = findViewById( R.id.uid );
        pwd = findViewById( R.id.pwd );
        mno = findViewById( R.id.mno );
        email = findViewById( R.id.email );

    }

    public void register(View view) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        db.execSQL( "create table if not exists register(name varchar,userid varchar,passwrd varchar,contact varchar,email varchar)" );
        name1 = name.getText().toString();
        uid1 = uid.getText().toString();
        pwd1 = pwd.getText().toString();
        mno1 = mno.getText().toString();
      email1 = email.getText().toString();



        if (null == name1 || name1.trim().length() == 0) {
            name.setError( "Enter name" );
            name.requestFocus();
        } else if (null == uid1 || uid1.trim().length() == 0) {
            uid.setError( "Enter User name " );
            uid.requestFocus();
        } else if (null == pwd1 || pwd1.trim().length() == 0) {
            pwd.setError( "Enter  Password" );
            pwd.requestFocus();
        } else if (null == mno1 || mno1.trim().length() == 0) {
            mno.setError( "Enter  Mobile number" );
            mno.requestFocus();
        } else if (mno1.length() != 10) {
            mno.setError("Invalid Mobile Number.");
            mno.requestFocus();
        }
        else if (null == email1 || email1.trim().length() == 0) {
            email.setError("Enter  Email address");
            email.requestFocus();
        }
        else if (!email.getText().toString().trim().matches(emailPattern)) {

            Toast.makeText(this,"Incorect Email address Entry ",Toast.LENGTH_LONG).show();
        }

        else {

            Cursor cursor = db.rawQuery( "select *from register where userid='" + uid1 + "' or email='"+email1+"'", null );
            int count = 0;
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        count++;
                    } while (cursor.moveToNext());

                }

                if (count > 0) {
                    Toast.makeText( Registration.this, "Username or email already exists", Toast.LENGTH_LONG ).show();

                }else{

                    db.execSQL("insert into register values ('" + name1 + "','" + uid1 + "','" + pwd1 + "','" + mno1 +"','" + email1 +"')");
                    Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(Registration.this,User.class);
                    startActivity(i);
                }
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();


            default:
                break;

        }
        return true;
    }

}