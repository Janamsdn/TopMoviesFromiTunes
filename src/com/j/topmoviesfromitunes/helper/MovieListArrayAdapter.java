package com.j.topmoviesfromitunes.helper;


import com.j.topmoviesfromitunes.R;

import java.util.List;

import com.j.topmoviesfromitunes.model.ITunesMovieDetails;
import com.j.topmoviesfromitunes.model.MovieModel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * MovieList Array Adapter class for movie list
 * @author j
 *
 */
public class MovieListArrayAdapter extends ArrayAdapter<ITunesMovieDetails> {
    
	//Tag for logging
	private static final String TAG = MovieListArrayAdapter.class.getSimpleName();
	
	
	private final LayoutInflater mInflater;
    
	public MovieListArrayAdapter(Context context, int resource, int textViewResourceId, List<ITunesMovieDetails> objects) {
		super(context, resource, textViewResourceId, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	}
	
	//Set data in array adapter	and Movie data list
	public void setData(List<ITunesMovieDetails> data) {
		//clear current data
		clear();
		//if new data list is not null
		if (data != null) {
			//add all movie from new data list to array adapter
			addAll(data);
			Log.d(TAG, "Movie list updated: " + data.size() + " movie" + (1 < data.size() ? "s" : "") + " are in the updated list");
		} else {
			Log.d(TAG, "Movie list cleared");
		}
		
	} 
	  
	// Populate new items in the list.
    @Override public View getView(int position, View convertView, ViewGroup parent) {
    	
    	Log.d(TAG, "getView(" + position + ")");

    	//get model instance
        MovieModel movieModel = MovieModel.getInstance();
        //get movie by position
        ITunesMovieDetails movie = movieModel.getMovieByPosition(position);
    	
    	//view holder keeps reference to avoid further findViewbyId() calls
    	ViewHolder viewHolder;
    	
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.list_item_layout, parent, false);
            //create view holder for new view
            viewHolder = new ViewHolder();
            //set view holder fields from layout
            viewHolder.rankTextView = ((TextView)view.findViewById(R.id.rankTextView));
            viewHolder.movieSmallImageLoadingProgressBar = ((ProgressBar)view.findViewById(R.id.movieSmalleImageLoadingProgressBar));
            viewHolder.movieSmallImageView = ((ImageView)view.findViewById(R.id.movieSmallImageView));
            viewHolder.movieTitleTextView = ((TextView)view.findViewById(R.id.movieTitleTextView));
            viewHolder.movieCopyrightTextView = ((TextView)view.findViewById(R.id.movieCopyrightTextView));
            //
            //set view holder in view
            view.setTag(viewHolder);
        } else {
        	//use convert view
            view = convertView;

            //get view holder from view
            viewHolder = (ViewHolder) view.getTag();

            //this movie is ready
            if (movie.getId() == viewHolder.movieId) {
            	Log.d(TAG, "View is up to date, reusing...");
            	return view;
            }//if
            
        }//if

        Log.d(TAG, "Setting up view...");
        
        //update movie id
        viewHolder.movieId = movie.getId();
        
        //update movie rank
        viewHolder.rankTextView.setText("" + (1 + position));
        //
        
       
        //update movie image (if possible)
      
        if (null != movie.getSmallImageUrl()) {

        	//start background image loader (using multiple cores)
        	new ImageLoaderHelper(viewHolder.movieSmallImageView, viewHolder.movieSmallImageLoadingProgressBar, movie.getSmallImageUrl(), 60, 90).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	        
        }
        
        //update movie title
        viewHolder.movieTitleTextView.setText(movie.getTitle());
        
        //update movie copyright
        viewHolder.movieCopyrightTextView .setText(movie.getCopyright());

        return view;
    }
    
   // View holder for performance speedup
    static class ViewHolder {
    	Long movieId;
    	TextView rankTextView;
    	ProgressBar movieSmallImageLoadingProgressBar;
    	ImageView movieSmallImageView;
    	TextView movieTitleTextView;
    	TextView movieCopyrightTextView;
    }
}
