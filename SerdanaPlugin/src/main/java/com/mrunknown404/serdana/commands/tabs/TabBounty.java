package main.java.com.mrunknown404.serdana.commands.tabs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabBounty implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			List<String> r = new ArrayList<String>();
			List<String> finalResults = new ArrayList<String>();
			
			r.add("create");
			r.add("cancel");
			
			for (int i = 0; i < r.size(); i++) {
				if (r.get(i).toLowerCase().contains(args[0].toLowerCase())) {
					finalResults.add(r.get(i));
				}
			}
			
			return finalResults;
		}
		
		return null;
	}
}
