package com.busbooking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewReservationDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {


	 String from;
	 String to,date;
	private List<BusDetailsView> cList = new ArrayList<BusDetailsView>();
	private ListView listView;
	private ProgressDialog pDialog1, pDialog2;
	private BusListAdapter2 adapter;
	SQLiteDatabase db;
	String unm;
	TextView tv;
	String sno;
	int count=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_bus);

		Bundle b = getIntent().getExtras();
		unm = b.getString("unm");


		setTitle("Reservation Details");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		db = openOrCreateDatabase( "bus_booking", android.content.Context.MODE_PRIVATE, null );
		db.execSQL( "create table if not exists booking_details(sno varchar,bus_no varchar,bus_from varchar,bus_to varchar,deprt varchar,fare varchar,seasts varchar,pname varchar,page varchar,pgen varchar,userid varchar)" );

		tv=(TextView)findViewById(R.id.textView);
		tv.setVisibility(View.GONE);

		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);


		new listofBus().execute(  );


	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		BusDetailsView p = (BusDetailsView) cList.get(position);

	sno = p.getSno();


		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewReservationDetails.this);
		alertDialog.setMessage("Are you sure, Want to Cancel this Ticket?");
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new delete().execute();

			}
		});
		alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertDialog.show();


	}

	class listofBus extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog( ViewReservationDetails.this);
			pDialog1.setMessage("Loading ...");
			pDialog1.setIndeterminate(false);
			pDialog1.setCancelable(false);
			pDialog1.show();
		}
		protected String doInBackground(String... args) {


			String selectQuery = "SELECT * FROM booking_details where userid='"+unm+"' ";
			Cursor cursor = db.rawQuery(selectQuery, null);
			cList.clear();
			while (cursor.moveToNext()) {
				count=1;
				BusDetailsView item = new BusDetailsView();
				item.setSno( cursor.getString( cursor.getColumnIndexOrThrow( "sno" ) ) );
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

					adapter = new BusListAdapter2( ViewReservationDetails.this, cList );
					listView.setAdapter( adapter );
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

	class delete extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog2 = new ProgressDialog(ViewReservationDetails.this);
			pDialog2.setMessage("Processing ....");
			pDialog2.setIndeterminate(false);
			pDialog2.setCancelable(false);
			pDialog2.show();
		}

		protected String doInBackground(String... args) {
			// Building Parameters


			db.execSQL("delete from booking_details where sno='" + sno + "' ");


			return null;
		}

		protected void onPostExecute(String file_url) {


			Toast.makeText(getApplicationContext(), "Bus Ticket Cancelled Successfully..!", Toast.LENGTH_SHORT).show();

			Intent i = new Intent(ViewReservationDetails.this, UserHome.class);
			i.putExtra("unm", unm);
			startActivity(i);
			finish();


			pDialog2.dismiss();

		}


	}

}
