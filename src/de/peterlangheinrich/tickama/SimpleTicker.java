package de.peterlangheinrich.tickama;

import de.peterlangheinrich.tickama.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

// Twitter-Test BOF
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
// Twitter-Test EOF

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
		
		// Twitter-Test BOF
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("1FLcEWVVDMiR5yWq4l0nsQ")
		  .setOAuthConsumerSecret("nxm3etPzLSzWM1aNfhikJ2RL7kb16OSJQGlIKJO30")
		  .setOAuthAccessToken("15127195-CW9ZFSsbo8BpcW3Wo8bnegCEXkvsSSwepoU4lXotk")
		  .setOAuthAccessTokenSecret("C1rI3zwiJrcOsYZaOzJfYvgCB5bTKgGdNluFSylew");
		try {
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Query query = new Query("Apple");
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					// TODO
					txtLabel.setText("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
				}
			} while ((query = result.nextQuery()) != null);	
		} catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
		// Twitter-Test EOF
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
