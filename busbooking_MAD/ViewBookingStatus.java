package com.busbooking;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingStatus extends AppCompatActivity  {


	private List<BusDetailsView> cList = new ArrayList<BusDetailsView>();
	private ListView listView;
	private ProgressDialog  pDialog1;
	private BusListAdapter2 adapter;
	SQLiteDatabase db;

	TextView tv;

	int count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_bus);
		setTitle("Reservation Details");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		db = openOrCreateDatabase( "bus_booking", android.content.Context.MODE_PRIVATE, null );
		db.execSQL( "create table if not exists booking_details(sno varchar,bus_no varchar,bus_from varchar,bus_to varchar,deprt varchar,fare varchar,seasts varchar,pname varchar,page varchar,pgen varchar,userid varchar)" );

		tv=(TextView)findViewById(R.id.textView);
		tv.setVisibility(View.GONE);

		listView = (ListView) findViewById(R.id.list);



		new listofBus().execute(  );


	}




	class listofBus extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog( ViewBookingStatus.this);
			pDialog1.setMessage("Loading ...");
			pDialog1.setIndeterminate(false);
			pDialog1.setCancelable(false);
			pDialog1.show();
		}

		protected String doInBackground(String... args) {


			String selectQuery = "SELECT * FROM booking_details";
			Cursor cursor = db.rawQuery(selectQuery, null);
			cList.clear();
			while (cursor.moveToNext()) {
				count=1;
				BusDetailsView item = new BusDetailsView();
				item.setBusno( cursor.getString( cursor.getColumnIndexOrThrow( "bus_no" ) ) );
				item.setBurroute( cursor.getString( cursor.getColumnIndexOrThrow( "bus_from" ) )+"-->"+cursor.getString( cursor.getColumnIndexOrThrow( "bus_to" ) ) );
				item.setSeats( "No. of Seats: " + cursor.getString( cursor.getColumnIndexOrThrow( "seasts" ) ) );
				item.setTime( "DepartTime: " + cursor.getString( cursor.getColumnIndexOrThrow( "deprt" ) ) );
				item.setAmount("Fare: ₹"+cursor.getString( cursor.getColumnIndexOrThrow( "fare" ) ) );
				item.setName("Name: "+cursor.getString( cursor.getColumnIndexOrThrow( "pname" ) ) );
				item.setAge("Age: "+cursor.getString( cursor.getColumnIndexOrThrow( "page" ) ) );
				item.setGender("Gender: "+cursor.getString( cursor.getColumnIndexOrThrow( "pgen" ) ) );

				cList.add( item );
			}



			return null;
		}

		protected void onPostExecute(String file_url) {



			if(count>0) {


				adapter = new BusListAdapter2( ViewBookingStatus.this, cList);
				listView.setAdapter(adapter);
			}else{

				tv.setVisibility(View.VISIBLE);
				tv.setText( "No Records Found" );
			}

			pDialog1.dismiss();

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
