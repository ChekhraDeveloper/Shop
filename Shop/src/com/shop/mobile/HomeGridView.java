package com.shop.mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeGridView extends ActionBarActivity{
	protected Cursor mCursor;
	protected int columnIndex;
	protected int descIndex;
	protected GridView mGridView;
	protected ImageAdapter mAdapter;
	TextView checkoutCount  = null;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_grid_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
        	actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeAsUpIndicator(R.drawable.arrow);
		}
        Intent intent = getIntent();
        //Bundle extras = intent.getExtras();
        String category = intent.getStringExtra("category");
        //TODO after we get item. We have to fetch JSON related to this Category
        /*String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
        mGridView = (GridView) findViewById(R.id.gridView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeGridView.this, "Selected Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mGridView.setAdapter(mAdapter);*/
        String[] projection = {
        		MediaStore.Images.Thumbnails._ID,
        		MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Thumbnails.IMAGE_ID
        };
     
        mCursor = getContentResolver().query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Thumbnails.IMAGE_ID + " DESC"
        );
     
        columnIndex = mCursor.getColumnIndexOrThrow(projection[0]);
        descIndex = mCursor.getColumnIndexOrThrow(projection[1]);
        
        // Get the GridView layout
        mGridView = (GridView) findViewById(R.id.gridView);
        mAdapter = new ImageAdapter(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeGridView.this, "Selected Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mGridView.setAdapter(mAdapter);
        
	}
	public void onCheckout(MenuItem item) {
		Toast.makeText(HomeGridView.this, "Check Out", Toast.LENGTH_SHORT).show();
	}
	
	@SuppressLint("NewApi") @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		/*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.badge).getActionView();
			checkoutCount= (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
			checkoutCount.setText("0");
		}*/
		/*final Menu m = menu;
        final MenuItem item = menu.findItem(R.id.badge);
        item.getActionView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {   
            	Toast.makeText(HomeGridView.this, "Check Out", Toast.LENGTH_SHORT).show();
            }
        });*/
		
		
		return true;
		
	}


	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		switch (item.getItemId()) {
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	@SuppressLint("NewApi") public void addtocart(final View v){
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
			v.animate().setDuration(1000).alpha(0)
					.withEndAction(new Runnable() {
						@Override
						public void run() {
							v.setAlpha(1);
						}
					});
		}
		LinearLayout parent = (LinearLayout) v.getParent();
    	int childcount = parent.getChildCount();
    	ImageView image =(ImageView) parent.getChildAt(0);
    	TextView desc =(TextView) parent.getChildAt(2);
    	String path = desc.getText().toString();
    	Log.i("parent",parent.toString());
		Toast.makeText(HomeGridView.this, "Added to cart", Toast.LENGTH_SHORT).show();
		SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", 0);
		String cart = sharedpreferences.getString("cart", null);
		if(cart==null){
			cart=path;
		}else{
			cart+=","+path;
		}
		
		checkoutCount.setText((Integer.parseInt(checkoutCount.getText().toString())+1)+"");
        sharedpreferences.edit().putString("cart",cart);
    	Log.i("path",path);
    }
	
	class ImageAdapter extends BaseAdapter {
		 
	    private Context mContext;
	 
	    public ImageAdapter(Context context) {
	        mContext = context;
	    }
	 
	    @Override
	    public int getCount() {
	        return mCursor.getCount();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return null;
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return 0;
	    }
	 
	    // Convert DP to PX
	    // Source: http://stackoverflow.com/a/8490361
	    public int dpToPx(int dps) {
	        final float scale = getResources().getDisplayMetrics().density;
	        int pixels = (int) (dps * scale + 0.5f);
	 
	        return pixels;
	    }
	 
	    @SuppressLint("NewApi") @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        TextView textview;
	        Button title;
	        int imageID = 0;
	        String desc ="";
	        // Want the width/height of the items
	        // to be 120dp
	        int wPixel = dpToPx(120);
	        int hPixel = dpToPx(120);
	 
	        // Move cursor to current position
	        mCursor.moveToPosition(position);
	        // Get the current value for the requested column
	        imageID = mCursor.getInt(columnIndex);
	        desc = mCursor.getString(descIndex);
	 
	        if (convertView == null) {
	            // If convertView is null then inflate the appropriate layout file
	            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, null);
	        }
	        else {
	 
	        }
	        textview = (TextView)convertView.findViewById(R.id.description);
	        title = (Button)convertView.findViewById(R.id.title);
	        imageView = (ImageView) convertView.findViewById(R.id.imageView);
	        
	        // Set height and width constraints for the image view
	        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(wPixel, hPixel);
	        lp.setMargins(16, 12, 12, 16);
	        imageView.setLayoutParams(lp);*/
	        //textview.setLayoutParams(new LinearLayout.LayoutParams(wPixel, hPixel));
	        // Set the content of the image based on the provided URI
	        //SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", 0);
	        //sharedpreferences.edit().putString();
	        
	        imageView.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID)
	        );
	        textview.setText(desc);
	        title.setText("Add To Cart");
	        // Image should be cropped towards the center
	        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	 
	        // Set Padding for images
	        //imageView.setPadding(8, 8, 8, 8);
	        //textview.setPadding(8, 8, 8, 8);
	        // Crop the image to fit within its padding
	        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
	        	imageView.setCropToPadding(true);
	        }
	        return convertView;
	    }
	}

}

