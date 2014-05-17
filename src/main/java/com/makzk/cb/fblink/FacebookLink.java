package com.makzk.cb.fblink;

import org.bukkit.plugin.java.JavaPlugin;

public class FacebookLink extends JavaPlugin {
	private static FacebookLink instance;
	public Configuration conf;

	@Override
	public void onEnable() {
		instance = this;
		
		conf = new Configuration(instance, "config.yml");
		conf.saveDefaultConfig();
		
		getLogger().info("FacebookLink WIP!");
	}
	
	@Override
	public void onDisable() {
		conf.saveConfig();
	}
	
	public static FacebookLink getInstance() {
		return instance;
	}
}
