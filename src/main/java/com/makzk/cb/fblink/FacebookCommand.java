package com.makzk.cb.fblink;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * FacebookLink plugin main command
 * 
 * @author makzk <me@makzk.com>
 */
public class FacebookCommand implements CommandExecutor {
	private FacebookLink p = FacebookLink.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(!p.fbIsLoaded()) {
			sender.sendMessage("Can't access Facebook!");
		} else if(!sender.hasPermission("fb.command")) {
			sender.sendMessage("You don't have the permission to use this command!");
		} else {
			sender.sendMessage("FacebookLink WIP");
		}
		
		return false;
	}

}
