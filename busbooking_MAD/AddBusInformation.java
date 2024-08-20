package com.busbooking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;


public class AddBusInformation extends AppCompatActivity {
    AutoCompleteTextView from, to;
    SQLiteDatabase db;
    TextInputEditText bus_no, cost, seats, time;
    String from1, to1, bus_no1, cost1, seats1, time1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bus_info);
        setTitle("Add Bus Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = openOrCreateDatabase("bus_booking", android.content.Context.MODE_PRIVATE, null);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        bus_no = findViewById(R.id.bus_no);
        cost = findViewById(R.id.cost);
        seats = findViewById(R.id.seats);
        time = findViewById(R.id.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBusInformation.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_arrays));
        from.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(AddBusInformation.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_arrays));
        to.setAdapter(adapter2);
    }

    public void submit(View view) {

        db.execSQL("create table if not exists bus_details(bus_frm varchar,bus_to varchar,bus_no varchar,cost varchar,seats varchar,time varchar)");
        from1 = from.getText().toString();
        to1 = to.getText().toString();
        bus_no1 = bus_no.getText().toString();
        cost1 = cost.getText().toString();
        seats1 = seats.getText().toString();
        time1 = time.getText().toString();


        if (null == from1 || from1.trim().length() == 0) {
            from.setError("Please enter From");
            from.requestFocus();
        } else if (null == to1 || to1.trim().length() == 0) {
            to.setError("Please enter To");
            to.requestFocus();
        } else if (null == bus_no1 || bus_no1.trim().length() == 0) {
            bus_no.setError("Please enter Bus Number");
            bus_no.requestFocus();
        } else if (null == seats1 || seats1.trim().length() == 0) {
            seats.setError("Please enter Number of Seats");
            seats.requestFocus();
        } else if (null == cost1 || cost1.trim().length() == 0) {
            cost.setError("Please enter Cost");
            cost.requestFocus();
        } else if (null == time1 || time1.trim().length() == 0) {
            time.setError("Please enter Time");
            time.requestFocus();
        } else if (from1.equals(to1)) {
            Toast.makeText(AddBusInformation.this, "Name of From and To Bus Routes should not be same", Toast.LENGTH_LONG).show();
        } else {
            Cursor cursor = db.rawQuery("select *from bus_details where bus_no='" + bus_no1 + "'", null);
            int count = 0;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        count++;
                    } while (cursor.moveToNext());
                }
                if (count > 0) {
                    Toast.makeText(AddBusInformation.this, "Bus number already exists", Toast.LENGTH_LONG).show();
                } else {

                    db.execSQL("insert into bus_details values ('" + from1 + "','" + to1 + "','" + bus_no1 + "','" + cost1 + "','" + seats1 + "','" + time1 + "')");
                    Toast.makeText(getApplicationContext(), "Bus details added successfully..!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddBusInformation.this, AddBusInformation.class);
                    startActivity(i);
                    finish();
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