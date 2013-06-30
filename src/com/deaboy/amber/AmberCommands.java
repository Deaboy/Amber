package com.deaboy.amber;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AmberCommands implements CommandExecutor
{
	public AmberCommands()
	{
		Bukkit.getPluginCommand("amber").setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args)
	{
		if (!cmd.equalsIgnoreCase("amber"))
		{
			return false;
		}
		
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Only players can do that.");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (args.length == 0)
		{
			sender.sendMessage("record, stoprecording, restore, stoprestoring");
			return true;
		}
		if (args[0].equalsIgnoreCase("record"))
		{
			if (Amber.startRecordingWorld(p.getWorld()))
			{
				sender.sendMessage("Recording world");
			}
			else
			{
				sender.sendMessage("There was an error");
			}
		}
		else if (args[0].equalsIgnoreCase("stoprecording"))
		{
			if (Amber.stopRecordingWorld(p.getWorld()))
			{
				sender.sendMessage("Stopping recording");
			}
			else
			{
				sender.sendMessage("There was an error");
			}
		}
		else if (args[0].equalsIgnoreCase("restore"))
		{
			if (Amber.startRestoringWorld(p.getWorld()))
			{
				sender.sendMessage("Restoring world");
			}
			else
			{
				sender.sendMessage("There was an error");
			}
		}
		else if (args[0].equalsIgnoreCase("stoprestoring"))
		{
			if (Amber.stopRestoringWorld(p.getWorld()))
			{
				sender.sendMessage("Stopping restoration.");
			}
			else
			{
				sender.sendMessage("There was an error");
			}
		}
		
		return true;
	}
}
