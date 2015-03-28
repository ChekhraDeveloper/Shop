package com.shop.mobile;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeListView extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_list_view);
		//ActionBar actionBar = getSupportActionBar();
		//actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        
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
					view.animate().setDuration(2000).alpha(0)
							.withEndAction(new Runnable() {
								@Override
								public void run() {
									list.remove(category);
									adapter.notifyDataSetChanged();
									view.setAlpha(1);
								}
							});
				}
				Log.i("position", position + "");
				Log.i("category", category);
				intent.putExtra("category", category);
				startActivity(intent);

			}
		});
		listView.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
