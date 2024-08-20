package com.busbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminHome extends AppCompatActivity {

    CardView addbus,regstr_users,busbkstatus;
    Intent i1,i2,i3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        setTitle("AdminHome");

        addbus = (CardView) findViewById(R.id.addbus);
        regstr_users = (CardView) findViewById(R.id.regstr_users);
        busbkstatus = (CardView) findViewById(R.id.busbkstatus);

        i1= new Intent(AdminHome.this,AddBusInformation.class);
        addbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });

        i2= new Intent(AdminHome.this,ViewBookingStatus.class);
        busbkstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });

        i3= new Intent(AdminHome.this,ViewUserDetails.class);
        regstr_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i3);
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
