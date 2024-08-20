package com.busbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BusTicketBooking extends AppCompatActivity   {


    AutoCompleteTextView from,to;
    SQLiteDatabase db;
    TextInputEditText date, cost, seats, time;
    String from1, to1, date1, unm, seats1, time1;

    Calendar calendar ;
    DatePickerDialog datePickerDialog ;

    private ListView listView;
    int mdate, mMonth, yYear ;
    int count=0;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.bus_booking );
        setTitle("Bus Ticket Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = openOrCreateDatabase( "bus_booking", android.content.Context.MODE_PRIVATE, null );
        db.execSQL( "create table if not exists bus_details(bus_frm varchar,bus_to varchar,bus_no varchar,cost varchar,seats varchar,time varchar)" );

        from = findViewById( R.id.from );
        to = findViewById( R.id.to );
        tv=(TextView)findViewById(R.id.textView);
        tv.setVisibility(View.GONE);


        Bundle b = getIntent().getExtras();
        unm = b.getString("unm");

        final Calendar cal=Calendar.getInstance();
        mdate=cal.get(Calendar.DATE);
        mMonth=cal.get(Calendar.MONTH);
        yYear=cal.get(Calendar.YEAR);

        date = findViewById(R.id.date);

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {



                    DatePickerDialog dpd=new DatePickerDialog( BusTicketBooking.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            date.setText( day+"/"+(month+1)+"/"+year );
                        }
                    },yYear,mMonth,mdate);
                    dpd.show();
                }


            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>( BusTicketBooking.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_arrays));
        from.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>( BusTicketBooking.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city_arrays));
        to.setAdapter(adapter2);
    }
    public void search(View view) {

        from1 = from.getText().toString();
        to1 = to.getText().toString();
        date1 = date.getText().toString();

        if (null == from1 || from1.trim().length() == 0) {
            from.requestFocus();
        } else if (null == to1 || to1.trim().length() == 0) {
            to.requestFocus();
        }
        else if (null == date1 || date1.trim().length() == 0) {
            date.requestFocus();
        }
        else if (from1.equals( to1 )) {
            Toast.makeText( BusTicketBooking.this,"End Place cannot be same as Start Place.",Toast.LENGTH_LONG ).show();
        }else {


            Cursor cursor = db.rawQuery( "SELECT * FROM bus_details where bus_frm='"+from1+"' and bus_to='"+to1+"' ", null );

            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        count++;
                    } while (cursor.moveToNext());

                }
            }
                if (count > 0) {
                    Intent i = new Intent( BusTicketBooking.this, ViewBusDetails.class );
                    i.putExtra( "from", from1 );
                    i.putExtra( "to", to1 );
                    i.putExtra( "date",date1 );
                    i.putExtra( "unm",unm );
                    startActivity( i );

                } else {
                    Toast.makeText( BusTicketBooking.this,"No Buses Available for this Route",Toast.LENGTH_LONG ).show();
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