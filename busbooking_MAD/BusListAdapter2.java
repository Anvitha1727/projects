package com.busbooking;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BusListAdapter2 extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<BusDetailsView> Items;


	public BusListAdapter2(Activity activity, List<BusDetailsView> Items) {
		this.activity = activity;
		this.Items = Items;
	}

	@Override
	public int getCount() {
		return Items.size();
	}

	@Override
	public Object getItem(int location) {
		return Items.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.reservation_list, null);


		TextView busno = (TextView) convertView.findViewById(R.id.busno);
		TextView seats = (TextView) convertView.findViewById(R.id.seats);
		TextView depart = (TextView) convertView.findViewById(R.id.depart);
		TextView route = (TextView) convertView.findViewById(R.id.route);
		TextView fare = (TextView) convertView.findViewById(R.id.fare);
		TextView pname = (TextView) convertView.findViewById(R.id.pname);
		TextView page = (TextView) convertView.findViewById(R.id.page);
		TextView pgen = (TextView) convertView.findViewById(R.id.pgen);

		BusDetailsView m = Items.get(position);

		busno.setText(m.getBusno());
		seats.setText(m.getSeats());
		depart.setText(m.getTime());
		fare.setText(m.getAmount());
		route.setText( m.getBurroute() );
		pname.setText( m.getName() );
		page.setText( m.getAge() );
		pgen.setText( m.getGender() );

		return convertView;
	}

}