package com.j.topmoviesfromitunes.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * Application model class
 * @author j
 *
 */
public class MovieModel {

	/**
	 * Tag for logging
	 */
	private static final String TAG = MovieModel.class.getSimpleName();

	/**
	 * Singleton instance
	 */
	private static MovieModel instance = null;

	/**
	 * Timestamp of last update
	 */
	private Long lastUpdateTimestamp = null;
	
	/**
	 * List of movies
	 */
	private List<ITunesMovieDetails> movies = new ArrayList<ITunesMovieDetails>();
	
	/**
	 * Singleton constructor
	 */
	private MovieModel() {
		//reset movies list
		movies = new ArrayList<ITunesMovieDetails>();
		
		Log.d(TAG, "MovieModel created");
	};
	
	/**
	 * Get model instance
	 * @return Singleton model instance
	 */
	public synchronized static MovieModel getInstance() {
		//lazy instantiation
		//if no instance has been created
		if (null == instance) {
			//create new model instance
			instance = new MovieModel();
		}
		//
		
		//return singleton instance
		return instance;
	}	

	/**
	 * Get timestamp of last update
	 * @return Timestamp of last update
	 */
	public Long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	/**
	 * Set last update timestamp
	 * @param lastUpdateTimestamp Timestamp of last update
	 */
	public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	/**
	 * Get movies list
	 * @return The list of movies
	 */
	public List<ITunesMovieDetails> getMovies() {
		//return a copy of the movie list (note: movie list must be read only for the layout)
		return (new ArrayList<ITunesMovieDetails>(movies));
	}

	/**
	 * Set movies
	 * @param movies The list of movies
	 */
	public void setMovies(ArrayList<ITunesMovieDetails> movies) {
		this.movies = movies;
	}
	
	/**
	 * Get movie by its movie list position 
	 * @param position The movie list position of the movie
	 * @return The movie if found by position, null otherwise
	 */
	public ITunesMovieDetails getMovieByPosition(int position) {
		//return movie
		return movies.get(position);
	}

	/**
	 * Get movie by its movie id
	 * @param id The id of the movie
	 * @return The movie if found by id, null otherwise
	 */
	public ITunesMovieDetails getMovieById(long id) {
		
		//process each movie
		for (int movieIndex = 0; movieIndex < movies.size(); movieIndex += 1) {
			//get next movie
			ITunesMovieDetails movie = movies.get(movieIndex);
			//if movie id matches
			if (id == movie.getId()) {
				return movie;
			}//if
		}//for
		
		//movie not found, return null
		return null;
	}
	
}
