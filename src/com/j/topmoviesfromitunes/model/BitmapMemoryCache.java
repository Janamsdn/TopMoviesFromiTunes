package com.j.topmoviesfromitunes.model;


import com.j.topmoviesfromitunes.ConfigSettings;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Bitmap cache class for caching bitmaps in an LRU style memory cache
 * @author j
 *
 */
public class BitmapMemoryCache {

	/**
	 * Tag for logging
	 */
	private static final String TAG = BitmapMemoryCache.class.getSimpleName();

	/**
	 * Singleton instance
	 */
	private static BitmapMemoryCache instance = null;
	
	/**
	 * Bitmap (LRU) cache table
	 */
	private LruCache<String, Bitmap> cacheTable = new LruCache<String, Bitmap>(ConfigSettings.getBitmapCacheSize());
	
	/**
	 * Singleton constructor
	 */
	private BitmapMemoryCache() {
		Log.d(TAG, "Bitmap cache created");
	}
	
	/**
	 * Get model instance
	 * @return Singleton model instance
	 */
	public synchronized static BitmapMemoryCache getInstance() {
		//lazy instantiation
		//if no instance has been created
		if (null == instance) {
			//create new cache instance
			instance = new BitmapMemoryCache();
		}
		//
		
		//return singleton instance
		return instance;
	}	
	
	/**
	 * Get file path for given key
	 * @param context The context
	 * @param key The key for the cached file
	 * @return The file path to the cached file
	 */
	public Bitmap get(String key) {
	
		//get cached value
		return cacheTable.get(key);
	}
	
	/**
	 * Save data to the cache
	 * @param context The context
	 * @param key The key for the data
	 * @param dataStream The input stream of the data
	 */
	public void put(String key, Bitmap bitmap) {
		
		//store value in cache
		cacheTable.put(key, bitmap);
	}
}
