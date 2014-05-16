package com.makzk.apilinks;

import org.yaml.snakeyaml.util.UriEncoder;

import com.makzk.json.URIData;

public class Bitly {
	private String accessToken;
	private boolean useSSL;
	
	public void setAccessToken(String token) {
		accessToken = token;
	}
	
	private String getDomain() {
		return (useSSL ? "https://api-ssl" : "http://api") + ".bitly.com";
	}
	
	public boolean canAccessApi() {
		String urlckeck = "s/v3/shorten?access_token=%s&longUrl=http%3A%2F%2Fgoogle.com%2F";
		URIData json = new URIData(String.format(urlckeck, getDomain(), accessToken));
		
		return !json.isNull("status_code") && json.getInt("status_code") == 200;
	}
	
	public String getShortenedLink(String longLink) {
		String urlEncoded = UriEncoder.encode(longLink);
		String urlBitly = "%s/v3/shorten?access_token=%s&longUrl=%s";
		
		URIData json = new URIData(String.format(urlBitly, getDomain(), urlEncoded, accessToken));
		
		return canAccessApi() && !json.isNull("url") ? json.getString("url") : "";
	}
}
