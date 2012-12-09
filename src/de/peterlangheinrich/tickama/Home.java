package de.peterlangheinrich.tickama;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Home extends Activity {
	
	public final static String SEARCH_TERM = "de.peterlangheinrich.tickama.SEARCH_TERM";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_home);
		
		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void startMagic(View view) {
		TextView searchField = (TextView) this.findViewById(R.id.searchField);
		String sSearchTerm = searchField.getText().toString();
		if (sSearchTerm.matches("")) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Ne search term provided");
			alertDialog.setMessage("The search field is empty. Please provide at least one one word to search Twitter for.");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
			      // here you can add functions
			   }
			});
			alertDialog.show();
			return;
		}
		Intent intent = new Intent(this, SimpleTicker.class);
		intent.putExtra(SEARCH_TERM, sSearchTerm);
		startActivity(intent);
	}
}
