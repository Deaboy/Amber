package com.deaboy.amber.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Deserializer
{

	/**
	 * Deserializes a block's data from a string to the block.
	 * @param block The block to deserialize to
	 * @param data The data to deserialize from
	 */
	public static void deserializeBlockState(Block block, String data)
	{
		String[] splitData = data.split(";");
		
		try
		{
			block.setTypeId(Integer.parseInt(splitData[4]));
			block.setData(Byte.parseByte(splitData[5]));
		}
		catch (Exception e)
		{
		}
	}

	public static Entity deserializeEntity(String data)
	{
		String[] splitData = data.split(";");
		
		try
		{
			Location location = new Location(Bukkit.getWorld(splitData[2]), Double.parseDouble(splitData[3]), Double.parseDouble(splitData[4]), Double.parseDouble(splitData[5]), Float.parseFloat(splitData[6]), Float.parseFloat(splitData[7]));
			
			Entity entity = Bukkit.getWorld(splitData[2]).spawnEntity(location, EntityType.fromId(Integer.parseInt(splitData[1])));
			
			return entity;
		}
		catch (Exception e)
		{
			return null;
		}
	}

}
