package com.j.topmoviesfromitunes.helper;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.j.topmoviesfromitunes.ConfigSettings;
import com.j.topmoviesfromitunes.model.ITunesMovieDetails;
import com.j.topmoviesfromitunes.model.MovieModel;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Async task Movie List Loader class for background loading movie list
 * @author j
 *
 */
public class MovieListLoader extends AsyncTaskLoader<List<ITunesMovieDetails>> {

	/**
	 * Tag for logging
	 */
	private static final String TAG = MovieListLoader.class.getSimpleName();

	public MovieListLoader(Context context) {
		super(context);
	}

	@Override
	protected void onStartLoading() {
		
		Log.d(TAG, "onStartLoading()");

		super.onStartLoading();
		
		//get model
		MovieModel movieModel = MovieModel.getInstance();
		//get movies from model
		List<ITunesMovieDetails> movies = movieModel.getMovies();
		
		//if movie list is empty
		if (0 == movies.size()) {
			Log.d(TAG, "No movie found in model, downloading top list from iTunes...");
			//start downloading movie list from iTunes
			forceLoad();
		} else {
			Log.d(TAG, "Delivering movies...");
			//deliver movies to adapter
			deliverResult(movies);
		}
	}

	@Override
	public List<ITunesMovieDetails> loadInBackground() {
		
		Log.d(TAG, "loadInBackground()");
		
		//get target URL 
		String urlString = ConfigSettings.getITunesTopMoviesListUrlString();
		
    	//content data string
    	String contentDataString = null;

		//create HTTP client
        DefaultHttpClient client = new DefaultHttpClient();
        //create GET request with target URL
        HttpGet httpGet = new HttpGet(urlString);
    	
    	Log.d(TAG, "Downloading movie list from iTunes...");

    	//downloading content data
        try {
        	//get HTTP response
        	HttpResponse execute = client.execute(httpGet);
        	//get response entity input stream
        	InputStream content = execute.getEntity().getContent();
        	//open buffered reader to the input stream
        	BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
          
        	//stringbuffer for entity content data
        	StringBuffer contentDataBuffer = new StringBuffer();
        	//string for next entity data line
        	String line = null;
        	//read entity content data to content data buffer
        	while ((line = buffer.readLine()) != null) {
        		//add next data line to entity content data buffer
        		contentDataBuffer.append(line + "\n");
        	}

        	//get content data from buffer
        	contentDataString = contentDataBuffer.toString();
        	
         } catch (Exception e) {
        	//handle exceptions
        	Log.e(TAG, "Error downloading iTunes movie list", e);
			//send 'operation failed' signal
        	return null;
        }
      
        //set timestamp of last update
        Long modelLastUpdateTimestamp = System.currentTimeMillis();
        
        //create movie list for model
        ArrayList<ITunesMovieDetails> modelMovies = new ArrayList<ITunesMovieDetails>();
        //id for next movie
        long modelMovieId = 0;
        Log.d(TAG, "Parsing downloaded movie list...");
        
    	//parse content data
        try {
        	//create JSON object from content data string
        	JSONObject contentDataJson = new JSONObject(contentDataString);
        	
        	//get iTunes movie list items
        	JSONArray movieListItems = contentDataJson.getJSONObject("feed").getJSONArray("entry");
        	
        	//if movie list not found
        	if (null == movieListItems) {
        		Log.e(TAG, "iTunes movie list not found in response");
    			//send 'operation failed' signal
        		return null;
        	}//if
        	
        	//process each movie list item
        	for (int movieListItemIndex = 0; movieListItemIndex < movieListItems.length(); movieListItemIndex += 1) {

                //get next movie list itme
        		JSONObject movieListItem = movieListItems.getJSONObject(movieListItemIndex);
       	
        		//get movie title
        		String movieTitle = null;
        		try {
        			movieTitle = movieListItem.getJSONObject("title").getString("label");
        		} catch (JSONException je) {
        			Log.w(TAG, "No title value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
        		
        		//get movie copyright notice
        		String movieCopyright = null;
        		try {
        			movieCopyright = movieListItem.getJSONObject("rights").getString("label");
        		} catch (JSONException je) {
        			Log.w(TAG, "No copyright value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
        		
        		//get movie images
        		URL movieSmallImageUrl = null;
        		URL movieImageUrl = null;
        		try {
        			
        			//get movie images
        			JSONArray movieImages = movieListItem.getJSONArray("im:image");

        			//process all images for small image
        			for (int movieImageIndex = 0; movieImageIndex < movieImages.length(); movieImageIndex += 1) {
        				//get next image
        				JSONObject movieImageJson = movieImages.getJSONObject(movieImageIndex);
        				//get next image height
        				String movieImageHeight = movieImageJson.getJSONObject("attributes").getString("height");
        				//if this image is a small image
        				if (ConfigSettings.getSmallImageHeight() == Integer.valueOf(movieImageHeight)) {
        					//set movie small URL
        					movieSmallImageUrl = new URL(movieImageJson.getString("label"));
        					//skip further processing
        					break;
        				}//if
        			}//for

        			//process all images for image
        			for (int movieImageIndex = 0; movieImageIndex < movieImages.length(); movieImageIndex += 1) {
        				//get next image
        				JSONObject movieImageJson = movieImages.getJSONObject(movieImageIndex);
        				//get next image height
        				String movieImageHeight = movieImageJson.getJSONObject("attributes").getString("height");
        				//if this image is an image
        				if (ConfigSettings.getImageHeight() == Integer.valueOf(movieImageHeight)) {
        					//set movie URL
        					movieImageUrl = new URL(movieImageJson.getString("label"));
        					//skip further processing
        					break;
        				}//if
        			}//for

        		} catch (JSONException je) {
        			Log.w(TAG, "No copyright value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
           		
        		//get movie summary
        		String movieSummary = null;
        		try {
        			movieSummary = movieListItem.getJSONObject("summary").getString("label");
        		} catch (JSONException je) {
        			Log.w(TAG, "No summary value for movie, list item index: " + movieListItemIndex);
        		}//try
        		//
 
        		//create new movie instance
        		ITunesMovieDetails movie = new ITunesMovieDetails();
        		//set movie id
        		movie.setId(modelMovieId++);
        		//set movie title
        		movie.setTitle(movieTitle);
        		//set movie copyright notice
        		movie.setCopyright(movieCopyright);
        		//set movie small image URL
        		movie.setSmallImageUrl(movieSmallImageUrl);
        		//set movie image URL
        		movie.setImageUrl(movieImageUrl);
        		//set movie summary
        		movie.setSummary(movieSummary);
        		
        		//add movie to movie list
        		modelMovies.add(movie);
        		
        	}//for
        	
        	//reset model
        	MovieModel movieModel = MovieModel.getInstance();
        	//reset last update timestamp
        	movieModel.setLastUpdateTimestamp(modelLastUpdateTimestamp);
        	//reset movies
        	movieModel.setMovies(modelMovies);
        	//
        	
        	return modelMovies;
        } catch (Exception e) {
        	//handle exceptions
        	Log.e(TAG, "Error parsing iTunes movie list", e);
			//send 'operation failed' signal
        	return null;
        }
      }

}
