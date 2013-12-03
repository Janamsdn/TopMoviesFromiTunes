package com.j.topmoviesfromitunes;

import com.j.topmoviesfromitunes.helper.ImageLoaderHelper;
import com.j.topmoviesfromitunes.model.ITunesMovieDetails;
import com.j.topmoviesfromitunes.model.MovieModel;

import com.j.topmoviesfromitunes.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A fragment representing a single Movie detail screen. 
 */
public class MVDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * Tag for logging
	 */
	private static final String TAG = MVDetailFragment.class.getSimpleName();

	/**
	 * The dummy content this fragment is presenting.
	 */
	private ITunesMovieDetails mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MVDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate");

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			
			MovieModel movieModel = MovieModel.getInstance();
			
			// Load the content specified by the fragment arguments. 
			mItem = movieModel.getMovieByPosition(getArguments().getInt(ARG_ITEM_ID));
			//if no valid movie selected by the arguments and there is at least one movie
			if (null == mItem && 0 < movieModel.getMovies().size()) {
				//select the first movie from the movies list
				Log.d(TAG, "Selecting the first available movie...");
				mItem = movieModel.getMovieByPosition(0);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreateView");	
		//set movie title
		this.getActivity().setTitle(mItem.getTitle());

		View rootView = inflater.inflate(R.layout.movie_detail_fragment,
				container, false);

		if (null == mItem) {
			Log.w(TAG, "Movie item is null");
			return rootView;
		}
		
		//get movie details image loading progress bar
		ProgressBar movieImageLoadingProgressBar = (ProgressBar)rootView.findViewById(R.id.movieDetailsImageLoadingProgressBar);
		//get movie details image view
		ImageView movieImage = (ImageView)rootView.findViewById(R.id.movieDetailsImageView);
   
        //set movie image (if possible)
        if (null != mItem.getImageUrl()) {

        	//start background image loader (using multiple cores)
        	new ImageLoaderHelper(movieImage, movieImageLoadingProgressBar, mItem.getImageUrl(), 113, 170).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);        
        }	
        //set movie summary
		((TextView) rootView.findViewById(R.id.movieDetailsSummaryTextView)).setText(mItem.getSummary());

		return rootView;
	}

	
}
