package com.busbooking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ViewBusDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {


	 String from;
	 String to,date;
	private List<BusDetailsView> cList = new ArrayList<BusDetailsView>();
	private ListView listView;
	private ProgressDialog  pDialog1;
	private BusListAdapter adapter;
	SQLiteDatabase db;
	String unm;

	TextView tv;
	final String[] option = {"1","2","3","4","5"};
	int count;
	String dept_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_bus);

		Bundle b = getIntent().getExtras();
		unm = b.getString("unm");
		from = b.getString("from");
		to = b.getString("to");
		date = b.getString("date");


		setTitle(from+" --> "+to);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		db = openOrCreateDatabase( "bus_booking", android.content.Context.MODE_PRIVATE, null );
		db.execSQL( "create table if not exists bus_details(bus_frm varchar,bus_to varchar,bus_no varchar,cost varchar,seats varchar,time varchar)" );

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

		String busno = p.getBusno();
		String tot_seats=p.getSeats().split(":")[1];
		dept_time=p.getDepttime();
		String amount = p.getFare();


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewBusDetails.this,android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(ViewBusDetails.this);
		builder.setTitle("Select No.of Seats");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				String seats=option[i];

					Intent intent=new Intent(ViewBusDetails.this, ConfirmBooking.class);
				intent.putExtra( "from", from );
				intent.putExtra( "to", to );
				intent.putExtra( "date_time",date+" "+dept_time );
				intent.putExtra( "unm",unm );
				intent.putExtra( "busno",busno );
				intent.putExtra( "seats",seats );
				intent.putExtra( "fare",amount );
				intent.putExtra( "tot_seats",tot_seats );
					startActivity(intent);

			}
		});


		final  AlertDialog a = builder.create();
		a.show();


		}

	class listofBus extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog(ViewBusDetails.this);
			pDialog1.setMessage("Loading ...");
			pDialog1.setIndeterminate(false);
			pDialog1.setCancelable(false);
			pDialog1.show();
		}

		protected String doInBackground(String... args) {


			String selectQuery = "SELECT * FROM bus_details where bus_frm='"+from+"' and bus_to='"+to+"' ";
			Cursor cursor = db.rawQuery(selectQuery, null);
			cList.clear();
			while (cursor.moveToNext()) {

				String depart_name=date+" "+ cursor.getString( cursor.getColumnIndexOrThrow( "time" ) );
				String busno=cursor.getString( cursor.getColumnIndexOrThrow( "bus_no" ));


				String sql = "SELECT *FROM booking_details where bus_no='"+busno+"' and deprt='"+depart_name+"' ";
				Cursor cursor2= db.rawQuery(sql, null);
				int seats_cnt=0;
				while (cursor2.moveToNext()) {
					seats_cnt=seats_cnt+Integer.parseInt( cursor2.getString( cursor2.getColumnIndexOrThrow( "seasts"
					)) );

				}
				int ava_seats=Integer.parseInt( cursor.getString( cursor.getColumnIndexOrThrow( "seats" ) ))-seats_cnt;

				BusDetailsView item = new BusDetailsView();
				item.setBusno( cursor.getString( cursor.getColumnIndexOrThrow( "bus_no" ) ) );
				item.setSeats( "Seats Available:" + ava_seats );
				item.setTime( "DepartTime:" + cursor.getString( cursor.getColumnIndexOrThrow( "time" ) ) );
				item.setAmount("₹"+cursor.getString( cursor.getColumnIndexOrThrow( "cost" ) ) );
				item.setFare(cursor.getString( cursor.getColumnIndexOrThrow( "cost" ) )  );
				item.setDepttime( cursor.getString( cursor.getColumnIndexOrThrow( "time" ) ) );

				cList.add( item );
			}



			return null;
		}

		protected void onPostExecute(String file_url) {


				adapter = new BusListAdapter(ViewBusDetails.this, cList);
				listView.setAdapter(adapter);

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
