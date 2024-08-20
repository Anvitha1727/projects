package com.busbooking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Random;


public class ConfirmBooking extends AppCompatActivity {
	private ArrayList<String> itemPricelist = new ArrayList<String>();


	String from;
	String to,date_time,busno,seats,tot_seats,fare1;

	String unm,pname1,page1,gen1;

	AutoCompleteTextView gen;
	TextInputEditText pname,page;

int tot_fare;
	SQLiteDatabase db;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_book);

		db = openOrCreateDatabase( "bus_booking", android.content.Context.MODE_PRIVATE, null );
		db.execSQL( "create table if not exists bus_details(bus_frm varchar,bus_to varchar,bus_no varchar,cost varchar,seats varchar,time varchar)" );


		Bundle b = getIntent().getExtras();
		unm = b.getString("unm");
		from = b.getString("from");
		to = b.getString("to");
		date_time = b.getString("date_time");
		busno = b.getString("busno");
		seats = b.getString("seats");
		tot_seats = b.getString("tot_seats");
		fare1 = b.getString("fare");


		setTitle("Booking Confirmation");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		TextView bus_no= (TextView) findViewById(R.id.bus_no);

		TextView bus_from= (TextView) findViewById(R.id.bus_from);

		TextView bus_to = (TextView) findViewById(R.id.bus_to);

		TextView bus_seats = (TextView) findViewById(R.id.seats);

		TextView bus_fare = (TextView) findViewById(R.id.fare);

		TextView depart = (TextView) findViewById(R.id.depart);


		tot_fare=Integer.parseInt( fare1)*Integer.parseInt( seats );

		pname = findViewById( R.id.pname );
		page = findViewById( R.id.page );
		gen = findViewById( R.id.gen );
		ArrayAdapter<String> adapter = new ArrayAdapter<>(ConfirmBooking.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.gen_arrays));
		//Set adapter
		gen.setAdapter(adapter);


		bus_no.setText("Bus no.: "+busno);
		bus_from.setText("From: "+from);
		bus_to.setText("To: "+to);
		bus_seats.setText("No.of Seats: "+seats);
		bus_fare.setText("Seats Fare: ₹"+new Integer(tot_fare).toString() );
		depart.setText("Departure On: "+date_time );
			}

public void confirm_bkng(View view){

	Random rno=new Random();
	int sno = rno.nextInt(1000);

	db.execSQL( "create table if not exists booking_details(sno varchar,bus_no varchar,bus_from varchar,bus_to varchar,deprt varchar,fare varchar,seasts varchar,pname varchar,page varchar,pgen varchar,userid varchar)" );

	pname1 = pname.getText().toString();
	page1 = page.getText().toString();
	gen1 = gen.getText().toString();

	if (null == pname1 || pname1.trim().length() == 0) {
		pname.requestFocus();
	} else if (null == page1 || page1.trim().length() == 0) {
		page.requestFocus();
	} else if (null == gen1 || gen1.trim().length() == 0) {
		gen.requestFocus();
	}else{

		db.execSQL("insert into booking_details values ('" + sno + "','" + busno + "','" + from + "','" + to +"','" + date_time +"','"+tot_fare+"','"+seats+"','"+pname1+"','"+page1+"','"+gen1+"','"+unm+"') ");
		Toast.makeText(getApplicationContext(), "Bus Ticket Booked Successfully..!", Toast.LENGTH_SHORT).show();
		Intent i=new Intent(ConfirmBooking.this,UserHome.class);
		i.putExtra( "unm",unm );
		startActivity(i);
		finish();
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
