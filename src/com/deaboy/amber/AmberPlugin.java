package com.deaboy.amber;

import org.bukkit.plugin.java.JavaPlugin;

public class AmberPlugin extends JavaPlugin
{
	private static AmberPlugin instance = null;
	
	@Override
	public void onEnable()
	{
		instance = this;
		Amber.getInstance();
	}
	
	public static AmberPlugin getInstance()
	{
		return instance;
	}
}
