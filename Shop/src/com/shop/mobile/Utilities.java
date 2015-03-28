package com.shop.mobile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class Utilities {
	public void fetchJSON(final String urlString) {
		Log.i("Url", urlString);
		if (urlString == null) {
			return;
		}
			Thread thread = new Thread(new Runnable() {
				@SuppressWarnings("unused")
				@Override
				public void run() {
					try {
						String data = null;
						InputStream stream = null;
						URL url = new URL(urlString);
						if (url == null) {
							Log.e("fetch JSON","Unable to Make URL");
							return;
						}
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						if (conn != null) {
							conn.setReadTimeout(10000); /* milliseconds */
							conn.setConnectTimeout(15000); /* milliseconds */
							conn.setRequestMethod("GET");
							conn.setDoInput(true);
							conn.connect();
							stream = conn.getInputStream();
						}
						if (stream != null) {
							data = convertStreamToString(stream);
							Log.i("Data : ", data);
						}
						if (stream != null)
							stream.close();
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			});
			if (thread != null) {
				thread.start();
			}
	}

	@SuppressWarnings("resource")
	public String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner scanner = null;
		if (is != null) {
			scanner = new java.util.Scanner(is);
		}
		if (scanner != null) {
			scanner = scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		}
		return null;

	}
}
