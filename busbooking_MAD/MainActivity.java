package com.busbooking;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    CardView admin,user;
    Intent i1,i2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        admin = (CardView) findViewById( R.id.admin );
        user = (CardView) findViewById( R.id.user );

        i1= new Intent(MainActivity.this,Admin.class);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });

        i2= new Intent(MainActivity.this,User.class);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });


    }




}