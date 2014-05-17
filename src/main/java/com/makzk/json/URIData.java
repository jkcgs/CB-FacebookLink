package com.makzk.json;

import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

public class URIData {
	public JSONObject data = null;
	
	public URIData(String uri) {
		try {
			URL url = new URL(uri);
			
			// create JSON Object to parse data
			data = (new JSONObject(new JSONTokener(url.openStream())));
			
		} catch (Exception e) { }
	}
}
