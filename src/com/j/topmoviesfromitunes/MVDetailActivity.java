package com.j.topmoviesfromitunes;

import com.j.topmoviesfromitunes.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * An activity display a single Movie detail screen. 
 * 
 */
public class MVDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_detail_activity);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity and using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putInt(MVDetailFragment.ARG_ITEM_ID, getIntent().getIntExtra(MVDetailFragment.ARG_ITEM_ID, -1));
			MVDetailFragment fragment = new MVDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container, fragment).commit();
		}
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
			NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
