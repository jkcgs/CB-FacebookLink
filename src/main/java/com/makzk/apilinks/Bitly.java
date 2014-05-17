package com.makzk.apilinks;

import org.yaml.snakeyaml.util.UriEncoder;

import com.makzk.json.URIData;

/**
 * Connects to the Bitly API to get shortened links.
 * 
 * @author makzk <me@makzk.com
 */
public class Bitly {
	private String accessToken;
	private boolean useSSL = true;
	
	public Bitly(String token) {
		accessToken = token;
	}
	
	/**
	 * Gets the Bitly API domain in usage
	 * @return The Bitly API domain to use
	 */
	private String getDomain() {
		return (useSSL ? "https://api-ssl" : "http://api") + ".bitly.com";
	}
	
	/**
	 * Checks if the Bitly API is accessible
	 * @return true or false, depending on the accessibility
	 */
	public boolean canAccessApi() {
		String urlckeck = "%s/v3/shorten?access_token=%s&longUrl=http://www.google.com";
		URIData json = new URIData(String.format(urlckeck, getDomain(), accessToken));
		
		return json.data.has("status_code") && json.data.getInt("status_code") == 200;
	}
	
	/**
	 * Retrieves the shortened version of a link
	 * @param longLink The full link
	 * @return A shortened version of a link, via Bitly
	 */
	public String getShortenedLink(String longLink) {
		String urlEncoded = UriEncoder.encode(longLink);
		String urlBitly = "%s/v3/shorten?access_token=%s&longUrl=%s";
		
		URIData json = new URIData(String.format(urlBitly, getDomain(), accessToken, urlEncoded));
		
		return canAccessApi() && json.data.getJSONObject("data").has("url") ? 
				json.data.getJSONObject("data").getString("url") : "";
	}
}
