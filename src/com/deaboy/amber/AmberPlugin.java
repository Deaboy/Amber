package com.deaboy.amber;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class AmberPlugin extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		for (Entity e : Bukkit.getWorlds().get(0).getEntities())
		{
			String output = e.toString() + ":  ";
			output += "Entity Type ID: " + e.getType().getTypeId() + "  ";
			if (e.getType() == EntityType.DROPPED_ITEM)
			{
				output += "Material: " + ((CraftItem) e).getItemStack().getType();
			}
			Bukkit.getLogger().log(Level.INFO, output);
		}
	}

	@Override
	public void onDisable()
	{
		
	}

}
