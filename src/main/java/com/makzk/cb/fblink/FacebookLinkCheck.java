package com.makzk.cb.fblink;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.makzk.apilinks.Facebook;

/**
 * Checks the actual link to the Facebook Graph API.
 * As it could freeze the server, it should be done asynchronously.
 * 
 * @author makzk <me@makzk.com>
 */
public class FacebookLinkCheck extends BukkitRunnable {

	private final JavaPlugin p;
	private Facebook fb;
	 
    public FacebookLinkCheck(JavaPlugin plugin, Facebook facebook) {
        p = plugin;
        fb = facebook;
    }
    
	@Override
	public void run() {
		fb.getAccessToken();
		if(!fb.canAccessApi()) {
			p.getLogger().severe("Could not connect to Facebook Graph API!");
			p.getPluginLoader().disablePlugin(p);
		} else {
			p.getLogger().info("Settings checked OK!");
		}
	}

}
