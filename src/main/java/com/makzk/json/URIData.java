package com.makzk.json;

import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads JSON Data from a URI
 * 
 * @author makzk <me@makzk.com>
 */
public class URIData {
	public JSONObject data = null;
	
	/**
	 * Loads the JSON data from an URI
	 * @param uri The URI to load
	 */
	public URIData(String uri) {
		try {
			URL url = new URL(uri);
			
			data = (new JSONObject(new JSONTokener(url.openStream())));
			
		} catch (Exception e) {}
	}
}
