package com.j.topmoviesfromitunes.model;

import java.net.URL;

/**
 * Data class for iTunes movie
 * @author j
 *
 */
public class ITunesMovieDetails {

	/**
	 * Id 
	 */
	Long id;
	
	/**
	 *  title
	 */
	String title;
	
	/**
	 * Copyright 
	 */
	String copyright;
	
	/**
	 *  small image
	 */
	URL smallImageUrl;
	
	/**
	 * Movie image
	 */
	URL imageUrl;
	
	/**
	 * Movie summary
	 */
	String summary;
	
	/**
	 * Get movie id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set movie id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get movie title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set movie title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get movie copyright notice or null if no copyright notice found for movie
	 * @return Movie copyright notice
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * Set movie copyright notice
	 * @param copyright The copyright notice of the movie
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * Get movie small image URL
	 * @return Movie small image URL or null if no small image found for movie
	 */
	public URL getSmallImageUrl() {
		return smallImageUrl;
	}

	/**
	 * Set movie small image URL
	 * @param smallImageUrl The small image URL of the movie
	 */
	public void setSmallImageUrl(URL smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	/**
	 * Get movie image URL
	 * @return Movie image URL or null if no image found for movie
	 */
	public URL getImageUrl() {
		return imageUrl;
	}

	/**
	 * Set movie image URL
	 * @param imageUrl The image URL of the movie
	 */
	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Get movie summary
	 * @return The summary text of the movie
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Set movie summary
	 * @param summary The summary of the movie
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
