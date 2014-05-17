package com.makzk.cb.fblink;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.makzk.apilinks.Facebook;

/**
 * A CraftBukkit plugin that allows to connect to a Facebook Page
 * via the Facebook Open Graph API. The server administrator requires
 * a Facebook Application to get the AppId and AppSecret from the 
 * Developers panel, to access the API.
 * 
 * @author makzk <me@makzk.com>
 *
 */
public class FacebookLink extends JavaPlugin {
	private static FacebookLink instance;
	public Configuration conf;
	private Facebook facebook;

	@Override
	public void onEnable() {
		instance = this;
		
		if(!loadConfig()) {
			getPluginLoader().disablePlugin(this);
		} else {
			facebook = new Facebook(conf.string("appId"), conf.string("appSecret"));
			Bukkit.getScheduler().runTaskAsynchronously(this, new FacebookLinkCheck(this, facebook));
			
			getCommand("facebook").setExecutor(new FacebookCommand());
		}
	}
	
	@Override
	public void onDisable() {
		conf.saveConfig();
	}
	
	/**
	 * Gets a plugin instance to access its functions
	 * @return The plugin instance
	 */
	public static FacebookLink getInstance() {
		return instance;
	}
	
	/**
	 * Loads and checks the configuration.
	 * @return true or false, if the configuration is OK or not.
	 */
	public boolean loadConfig() {
		conf = new Configuration(instance, "config.yml");
		
		if(conf == null) {
			getLogger().severe("Could not load configuration!");
			return false;
		} else {
			conf.saveDefaultConfig();
			if(conf.string("appId").isEmpty() ||
					conf.string("appSecret").isEmpty() ||
					conf.string("pageId").isEmpty()) {
				getLogger().severe("Plugin is not (fully) configured");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if the plugin can access Facebook Graph API
	 * @return true or false, if the API can be accessed or not.
	 */
	public boolean fbIsLoaded() {
		return facebook.canAccessApi();
	}
}
