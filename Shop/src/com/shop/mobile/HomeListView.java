package com.shop.mobile;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeListView extends ActionBarActivity {
	TextView checkoutCount  = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_list_view);
		
		// List of items
		// TODO Get these items from fetchJSON
		final String[] categories = { "Category 1", "Category 2", "Category 3", "Category 4",
				"Category 5", "Category 6", "Category 7", "Category 8", "Category 9", "Category 10",
				"Category 11", "Category 12", "Category 13", "Category 14", "Category 15" };
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < categories.length; ++i) {
			list.add(categories[i]);
		}
		// Create an array adapter
		final ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, categories);

		// Create a ListView object
		ListView listView = (ListView) findViewById(R.id.listview);
		// set the adapter to the view
		listView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi") @Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long arg) {
				Intent intent = new Intent(HomeListView.this,
						HomeGridView.class);
				final String category = (String) parent.getItemAtPosition(position);
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
					view.animate().setDuration(1000).alpha(0)
							.withEndAction(new Runnable() {
								@Override
								public void run() {
									list.remove(category);
									adapter.notifyDataSetChanged();
									view.setAlpha(1);
								}
							});
				}
				Log.i("category", category);
				intent.putExtra("category", category);
				startActivity(intent);

			}
		});
		listView.setAdapter(adapter);

	}

	public void onCheckout(MenuItem item) {
		Toast.makeText(HomeListView.this, "Check Out", Toast.LENGTH_SHORT)
				.show();
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		/*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(
					R.id.badge).getActionView();
			checkoutCount = (TextView) badgeLayout
					.findViewById(R.id.actionbar_notifcation_textview);
			checkoutCount.setText("0");
		}*/
		/*final Menu m = menu;
		final MenuItem item = menu.findItem(R.id.badge);
		item.getActionView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(HomeListView.this, "Check Out",
						Toast.LENGTH_SHORT).show();
			}
		});*/

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Toast.makeText(HomeListView.this, "Check Out", Toast.LENGTH_SHORT).show();
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
