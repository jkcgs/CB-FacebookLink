package com.makzk.cb.fblink;

import java.util.Date;

import org.json.JSONObject;

import com.makzk.apilinks.Bitly;
import com.makzk.apilinks.Facebook;

public class FacebookPost {
	private String content;
	private String type;
	private String link;
	private String shortnedLink;
	private Date date;
	private Facebook fb;
	private FacebookLink p;
	
	public FacebookPost() {
		p = FacebookLink.getInstance();
		shortnedLink = "";
		fb = new Facebook(p.conf.string("appId"), p.conf.string("appSecret"));
	}
	
	/**
	 * Fills the post information from a Facebook Graph API URL.
	 * ALERT! Do this asynchronously with the server or it will freeze!
	 * @param url The URL to retrieve
	 * @return true if the content were successfully retrieved, or false in contrary case
	 */
	public boolean createFromPage() {
		try {
			// TODO
			fb.getAccessToken();
			if(!fb.canAccessApi()) {
				throw new Exception("Unable to access Facebook Graph API");
			}
			
			JSONObject root = fb.getLastPostFromPage(p.conf.string("pageId"));
		
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
		} catch (Exception e) {
			p.getLogger().severe("CreateFromURL Error: " + e.getMessage());
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
		if(!link.isEmpty() && shortnedLink.isEmpty()) {
			Bitly shortner = new Bitly(token);
			shortnedLink = shortner.canAccessApi() ? shortner.getShortenedLink(link) : "";
		}

		return shortnedLink;
	}
	
	/**
	 * The post's date as an object.
	 * @return The post's date object
	 */
	public Date getDate() {
		return date;
	}
}
