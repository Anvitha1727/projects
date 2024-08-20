package com.busbooking;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

public class BusListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<BusDetailsView> Items;


	public BusListAdapter(Activity activity, List<BusDetailsView> Items) {
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
			convertView = inflater.inflate(R.layout.list_row, null);


		TextView busno = (TextView) convertView.findViewById(R.id.busno);
		TextView seats = (TextView) convertView.findViewById(R.id.seats);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		TextView fare = (TextView) convertView.findViewById(R.id.cost);


		BusDetailsView m = Items.get(position);

		busno.setText(m.getBusno());
		seats.setText(m.getSeats());
		time.setText(m.getTime());
		fare.setText(m.getAmount());

		return convertView;
	}

}