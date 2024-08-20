package com.busbooking;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<UsersView> Items;


	public UserListAdapter(Activity activity, List<UsersView> Items) {
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
			convertView = inflater.inflate(R.layout.list_row2, null);


		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView mno = (TextView) convertView.findViewById(R.id.mno);
		TextView email = (TextView) convertView.findViewById(R.id.email);



		UsersView m = Items.get(position);

		name.setText(m.getName());
		mno.setText(m.getMno());
		email.setText(m.getEmail());


		return convertView;
	}

}