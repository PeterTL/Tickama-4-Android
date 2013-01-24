package de.peterlangheinrich.tickama;

import de.peterlangheinrich.tickama.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

// Twitter-API (Test) BOF
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
// Twitter-API (Test) EOF

public class SimpleTicker extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_simple_ticker);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_simple_ticker);
		
		// Hier geht der eigentliche Code los
		TextView txtLabel = (TextView) this.findViewById(R.id.statusField);
		if (this.isOnline())
			txtLabel.setText("ICH BIN ONLINE.");
		else
			txtLabel.setText("ICH BIN NICHT ONLINE.");
		
		// Twitter-API (Test) BOF
		try {
			InputStream is = new URL("https://graph.facebook.com/19292868552").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			String jsonText = sb.toString();
			JSONObject json = new JSONObject(jsonText);
//			System.out.println(json.toString());
//			System.out.println(json.get("id"));
//			Log.v("Tickama", json.toString());
//			Log.v("Tickama", json.get("id").toString());
			txtLabel.setText(json.get("about").toString());
		} catch (Exception e) {
			txtLabel.setText(e.getMessage());
			e.printStackTrace();
		}
		// Twitter-API (Test) EOF
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Zusätzliche Methoden, die man so braucht
	public boolean isOnline() {
		try {
		    ConnectivityManager cm =
			        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo netInfo = cm.getActiveNetworkInfo();
			    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			        return true;
			    }
			    return false;	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
