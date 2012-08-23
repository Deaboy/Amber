package com.deaboy.amber.util;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Deserializer
{
	public static final String div1 = Serializer.div1;
	public static final String div2 = Serializer.div2;
	public static final String div3 = Serializer.div3;

	public static void deserializeWorldData(World world, String data)
	{
		String[] splitData = data.split(div1);
		
		try
		{
			//PARSE THE DATA
			long fullTime = Long.parseLong(splitData[0]);
			
			//SPAWN
			double spawnX = Double.parseDouble(splitData[1].split(div2)[0]);
			double spawnY = Double.parseDouble(splitData[1].split(div2)[1]);
			double spawnZ = Double.parseDouble(splitData[1].split(div2)[2]);
			
			//WEATHER
			int weatherDuration = Integer.parseInt(splitData[2].split(div2)[0]);
			int thunderDuration = Integer.parseInt(splitData[2].split(div2)[1]);
			boolean isThundering = Boolean.parseBoolean(splitData[2].split(div2)[2]);
			
			//MOBS
			int monsterLimit = Integer.parseInt(splitData[3]);
			long monsterSpawnTicks = Long.parseLong(splitData[4]);
			int animalLimit = Integer.parseInt(splitData[5]);
			long animalSpawnTicks = Long.parseLong(splitData[6]);
			int waterAnimalLimit = Integer.parseInt(splitData[7]);
			
			//SETTINGS
			int difficulty = Integer.parseInt(splitData[8]);
			boolean pvp = Boolean.parseBoolean(splitData[9]);
			
			//SAVE DATA TO WORLD
			world.setFullTime(fullTime);
			
			//SPAWN
			world.setSpawnLocation((int) spawnX, (int) spawnY, (int) spawnZ);
			
			//WEATEHR
			world.setWeatherDuration(weatherDuration);
			world.setThunderDuration(thunderDuration);
			world.setThundering(isThundering);
			
			//MOBS
			world.setMonsterSpawnLimit(monsterLimit);
			world.setTicksPerMonsterSpawns((int) monsterSpawnTicks);
			world.setAnimalSpawnLimit(animalLimit);
			world.setTicksPerAnimalSpawns((int) animalSpawnTicks);
			world.setWaterAnimalSpawnLimit(waterAnimalLimit);
			
			//SETTINGS
			world.setDifficulty(Difficulty.getByValue(difficulty));
			world.setPVP(pvp);
		}
		catch (Exception e)
		{
			return;
		}
	}

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
