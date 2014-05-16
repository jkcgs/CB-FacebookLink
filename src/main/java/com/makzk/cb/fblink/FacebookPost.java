package com.makzk.cb.fblink;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.makzk.apilinks.Bitly;

public class FacebookPost {
	private String content;
	private String type;
	private String link;
	private Date date;
	
	/**
	 * Fills the post information from a Facebook Graph API URL
	 * @param url The URL to retrieve
	 * @return true if the content were successfully retrieved, or false in contrary case
	 */
	public boolean createFromURL(URL url) {
		try {
			// create JSON Object to parse OpenGraph data
			JSONObject root = new JSONObject(new JSONTokener(url.openStream()));
			root = root.getJSONArray("data").getJSONObject(0);
		
			// Check if data was retrieved correctly
			if(root.isNull("message") || root.isNull("updated_time") || root.isNull("type")) {
				return false;
			} else {
				content = root.getString("message");
				type = root.getString("type");
				date = new Date(root.getLong("updated_time"));
				link = !root.isNull("link") ? root.getString("link") : "";
				
				return true;
			}
		} catch (JSONException | IOException e) {
			return false;
		}
	}
	
	/**
	 * Retrieves the last post from a page, and fills the post information
	 * using the Page ID and the access token from a Facebook App
	 * @param pageId The page ID to read
	 * @param token The access token that allows to read the content
	 * @return true if the content were successfully retrieved, or false in contrary case
	 */
	public boolean createFromPage(String pageId, String token) {
		// create the URL link to get the post
		String fields = "message,type,updated_time";
		String targetURL = "https://graph.facebook.com/%s/posts?fields=%s&limit=1&date_format=U&token=%s";
		try {
			// Create the URL format and call the another method
			URL url = new URL(String.format(targetURL, pageId, fields, token));
			return createFromURL(url);
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	/**
	 * Gets the post content, without satinization (line breaks, special characters, etc)
	 * @return The post content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Gets the sanitized post, for general usage
	 * @param max The max characters to return
	 * @return The sanitized post
	 */
	public String getSanitizedContent(int max) {
		return content.trim().replaceAll("\n", " ");
	}
	
	/**
	 * Gets the type of the actual post
	 * @return The type of the post
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the link of the post if there is one
	 * @return An empty string if there's no link on the post, else, the link is returned.
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * A shortned version of the post's link
	 * @param token The token to access the Bitly system
	 * @return The shortned link, if there's one. Else, an empty string.
	 * @see Bitly 
	 */
	public String getBitlyLink(String token) {
		if(!link.isEmpty()) {
			Bitly shortner = new Bitly();
			shortner.setAccessToken(token);
			return shortner.canAccessApi() ? shortner.getShortenedLink(link) : "";
		} else {
			return "";
		}
	}
	
	/**
	 * The post's date as an object.
	 * @return The post's date object
	 */
	public Date getDate() {
		return date;
	}
}
