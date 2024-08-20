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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewUserDetails extends AppCompatActivity  {



	private List<UsersView> cList = new ArrayList<UsersView>();
	private ListView listView;
	private ProgressDialog  pDialog1;
	private UserListAdapter adapter;
	SQLiteDatabase db;


	TextView tv;
int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_bus);

		Bundle b = getIntent().getExtras();


		setTitle("Registered Users");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		db = openOrCreateDatabase( "bus_booking", android.content.Context.MODE_PRIVATE, null );
		db.execSQL( "create table if not exists register(name varchar,userid varchar,passwrd varchar,contact varchar,email varchar)" );

		tv=(TextView)findViewById(R.id.textView);
		tv.setVisibility(View.GONE);

		listView = (ListView) findViewById(R.id.list);


		new listofusers().execute(  );


	}



	class listofusers extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog( ViewUserDetails.this);
			pDialog1.setMessage("Loading ...");
			pDialog1.setIndeterminate(false);
			pDialog1.setCancelable(false);
			pDialog1.show();
		}
		protected String doInBackground(String... args) {


			String selectQuery = "SELECT * FROM register ";
			Cursor cursor = db.rawQuery(selectQuery, null);
			cList.clear();
			while (cursor.moveToNext()) {
				count=1;
				UsersView item = new UsersView();
				item.setName( cursor.getString( cursor.getColumnIndexOrThrow( "name" ) ) );
				item.setMno( "Contact No.:" + cursor.getString( cursor.getColumnIndexOrThrow( "contact" ) ) );
				item.setEmail("Email Id:" + cursor.getString( cursor.getColumnIndexOrThrow( "email" ) ) );
				cList.add(item);
			}



			return null;
		}
		protected void onPostExecute(String file_url) {




			if(count>0) {

				adapter = new UserListAdapter( ViewUserDetails.this, cList);
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
