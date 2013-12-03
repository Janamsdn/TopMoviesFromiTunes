package com.j.topmoviesfromitunes;

/**
 * ConfigSettings settings
 * @author j
 *
 */
public class ConfigSettings {
	
	/**
	 * URL for top movies 
	 */
	private static String ITUNES_TOP_MOVIES_LIST_URL = "https://itunes.apple.com/gb/rss/topmovies/limit=25/json";
	
	/**
	 *  Small image height
	 */
	private static final int SMALL_IMAGE_HEIGHT = 60;

	/**
	 *  image height
	 */
	private static final int IMAGE_HEIGHT = 170;
	
	/**
	 * bitmap cache size
	 */
	private static final int BITMAP_CACHE_SIZE = 4 * 1024 * 1024;
	
	/**
	 * Get URL string
	 */
	public static String getITunesTopMoviesListUrlString() {
    	return ITUNES_TOP_MOVIES_LIST_URL;
	}
	
	/**
	 * Get small movie image height
	 */
	public static int getSmallImageHeight() {
		return SMALL_IMAGE_HEIGHT;
	}

	/**
	 * Get movie image height
	 */
	public static int getImageHeight() {
		return IMAGE_HEIGHT;
	}

	/**
	 * Get bitmap cache size
	 */

	public static int getBitmapCacheSize() {
		return BITMAP_CACHE_SIZE;
	}
}
