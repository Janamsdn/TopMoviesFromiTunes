package com.j.topmoviesfromitunes;

import com.j.topmoviesfromitunes.R;

import java.util.List;

import com.j.topmoviesfromitunes.helper.MovieListArrayAdapter;
import com.j.topmoviesfromitunes.helper.MovieListLoader;
import com.j.topmoviesfromitunes.model.ITunesMovieDetails;
import com.j.topmoviesfromitunes.model.MovieModel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * A list fragment representing a list of Movies.
 * This helps indicate which item is currently being viewed in a
 * Activities containing this fragment MUST implement the Callbacks.
 */
public class MovieListFragment extends ListFragment implements LoaderCallbacks<List<ITunesMovieDetails>> {

	/**
	 * Tag for logging
	 */
	public static final String TAG = MovieListFragment.class.getSimpleName();

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * Adapter for list data
	 */
	private MovieListArrayAdapter listAdapter;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(int itemIndex);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(int itemIndex) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MovieListFragment() {
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		Log.d(TAG, "onActivityCreated");

		//create parent
		super.onCreate(savedInstanceState);
		
        // Give some text to display if there is no data. 
        setEmptyText(getString(R.string.movie_list_is_empty));

		// get model instance
		MovieModel movieModel = MovieModel.getInstance();

		// create list adapter for fragment
		listAdapter = new MovieListArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, android.R.id.text1,
				movieModel.getMovies());
		
		// set list adapter for fragment
		setListAdapter(listAdapter);

		// Start out with a progress indicator.
        setListShown(false);

		// Prepare the data loader.
		getLoaderManager().initLoader(0, null, this);
	}

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(position);
	}
	//
	// LoaderCallbacks<List<ITunesMovieDetails>>
	//

	@Override
	public Loader<List<ITunesMovieDetails>> onCreateLoader(int arg0, Bundle arg1) {
		Log.d(TAG, "onCreateLoader");
		// return new loader
		return new MovieListLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<List<ITunesMovieDetails>> loader, List<ITunesMovieDetails> movies) {

		Log.d(TAG, "onLoadFinished");
		
		if (null != movies) {
			Log.d(TAG, movies.size() + " movie" + (1 < movies.size() ? "s" : "") + " loaded");
		}//if
		
		//set data in movie list adapter
		listAdapter.setData(movies);
        
		// The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }  
	}

	@Override
	public void onLoaderReset(Loader<List<ITunesMovieDetails>> arg0) {

		Log.d(TAG, "onLoaderReset");
		//clear data in movie list adapter
		listAdapter.setData(null);

	}

}
