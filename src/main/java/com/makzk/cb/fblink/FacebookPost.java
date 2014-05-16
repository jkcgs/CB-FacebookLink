package com.makzk.cb.fblink;

import java.util.Date;

public class FacebookPost {
	private String content;
	private String type;
	private String link;
	private Date date;
	
	public String getContent() {
		return content;
	}
	
	public String getSanitizedContent(int max) {
		return content.trim().replaceAll("\n", " ");
	}
	
	public String getType() {
		return type;
	}
	
	public String getLink() {
		return link;
	}
	
	public Date getDate() {
		return date;
	}
}
