package com.busbooking;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class UserHome extends AppCompatActivity {

    CardView bus_booking,ticket_cancellation;

    Intent i1,i2;
    String unm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
        setTitle("UserHome");

        bus_booking = (CardView) findViewById(R.id.bus_booking);

        ticket_cancellation = (CardView) findViewById(R.id.cancel);

        Bundle b = getIntent().getExtras();
        unm = b.getString("unm");



        i1= new Intent(UserHome.this,BusTicketBooking.class);
        bus_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1.putExtra( "unm",unm);
                startActivity(i1);
            }
        });

        i2= new Intent(UserHome.this,ViewReservationDetails.class);
        ticket_cancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra( "unm",unm);
                startActivity(i2);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;

            default:
                break;

        }


        return true;
    }
}
