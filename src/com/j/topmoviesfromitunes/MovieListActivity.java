package com.j.topmoviesfromitunes;

import com.j.topmoviesfromitunes.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;


/**
 * An activity representing a list of Movies. This activity presentation for handset
 * This activity also implements the required
 * {@link MovieListFragment.Callbacks} interface to listen for item selections.
 */
public class MovieListActivity extends FragmentActivity implements
		MovieListFragment.Callbacks {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_list_activity);	
	}

	/**
	 * Callback method from {@link MovieListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(int id) {
		
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, MVDetailActivity.class);
			detailIntent.putExtra(MVDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	
}
