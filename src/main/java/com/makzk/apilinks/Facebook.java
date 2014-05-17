package com.makzk.apilinks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.makzk.json.URIData;

/**
 * Access to a Facebook application
 * @author makzk <me@makzk.com>
 *
 */
public class Facebook {
	private String appId;
	private String appSecret;
	private String accessToken;
	
	/**
	 * Creates a link to a Facebook application
	 * @param appId The application ID
	 * @param appSecret The application secret, from the Facebook Developer App panel
	 */
	public Facebook(String appId, String appSecret) {
		this.appId = appId;
		this.appSecret = appSecret;
		accessToken = "";
	}
	
	/**
	 * Gets the an access token from the Facebook Graph api
	 * If the class methods are not working, use this to renew the access token
	 */
	public void getAccessToken() {
		String token = "";
		String con = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&grant_type=client_credentials";
	    try {
	    	URL url = new URL(String.format(con, appId, appSecret));
			URLConnection urlc = url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) {
	        	if(!inputLine.trim().isEmpty() && token.isEmpty()) {
	        		token = inputLine;
	        		break;
	        	}
	        }
	        in.close();
	    } catch(Exception e) {
	    	return;
	    }
	    
		if(token.matches("access_token=.*")) {
			accessToken = token.substring(token.indexOf("=")+ 1 );
		} else {
			accessToken = "";
		}
	}
	
	/**
	 * Gets the posts as JSON from the page selected
	 * @param pageId The page ID to check content
	 * @param limit The maximum amount of posts to retrieve
	 * @return A JSONObject that have the posts.
	 */
	public JSONArray getPostsFromPage(String pageId, int limit) {
		String url = "https://graph.facebook.com/%s/posts?fields=message,type,updated_time,link&date_format=U&limit=%s&access_token=%s";
		URIData json = new URIData(String.format(url, pageId, limit, accessToken));
		
		return json.data.getJSONArray("data");
	}
	
	/**
	 * Shorthand to get the last post of a page by its id
	 * @param pageId The page id to retrieve its post
	 * @return A JSONObject with the post data
	 */
	public JSONObject getLastPostFromPage(String pageId) {
		return getPostsFromPage(pageId, 1).getJSONObject(0);
	}
	
	/**
	 * Checks if there is an available access token
	 * @return true or false if there is an access token
	 */
	public boolean canAccessApi() {
		return !accessToken.isEmpty();
	}
}
