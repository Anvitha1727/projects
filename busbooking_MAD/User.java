package com.busbooking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {

    SQLiteDatabase db;
    ImageButton reg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        setTitle("User Login");
        db = openOrCreateDatabase("bus_booking", android.content.Context.MODE_PRIVATE, null);
        reg = (ImageButton) findViewById(R.id.fab);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(User.this, Registration.class);
                startActivity(i);
            }
        });
    }

    public void userlogin(View view) {
        db.execSQL("create table if not exists register(name varchar,userid varchar,passwrd varchar,contact varchar,email varchar)");

        EditText unm = (EditText) findViewById(R.id.unm);
        EditText pwd = (EditText) findViewById(R.id.pwd);

        String unm1 = unm.getText().toString();
        String pwd1 = pwd.getText().toString();
        if (null == unm1 || unm1.trim().length() == 0) {
            unm.setError("Enter UserName");
            unm.requestFocus();
        } else if (null == pwd1 || pwd1.trim().length() == 0) {
            pwd.setError("Enter Password");
            pwd.requestFocus();
        } else {
            int count = 0;
            Cursor cursor = db.rawQuery("select *from register where  userid='" + unm1 + "' and passwrd='" + pwd1 + "'", null);

            if (cursor != null) {
                count = cursor.getCount();

                if (count > 0) {
                    Intent i = new Intent(User.this, UserHome.class);
                    i.putExtra("unm", unm1);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(User.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        }
    }
}
