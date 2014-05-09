package com.makzk.cb.fblink;

import org.bukkit.plugin.java.JavaPlugin;

public class FacebookLink extends JavaPlugin {
	private static FacebookLink instance;

	@Override
	public void onEnable() {
		instance = this;
		getLogger().info("FacebookLink WIP!");
		getServer().getPluginManager().disablePlugin(instance);
	}
	
	@Override
	public void onDisable() {
		
	}
}
