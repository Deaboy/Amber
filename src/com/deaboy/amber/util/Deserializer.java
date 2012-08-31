package com.deaboy.amber.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class Deserializer
{
	public static final String div1 = Serializer.div1;
	public static final String div2 = Serializer.div2;
	public static final String div3 = Serializer.div3;
	public static final String div4 = Serializer.div4;

 	public static void deserializeWorld(String data)
	{
		String[] splitData = data.split(div1);
		
		try
		{
			// GET WORLD
			World world = Bukkit.getWorld(splitData[0]);
			
			//PARSE THE DATA
			long fullTime = Long.parseLong(splitData[1]);
			
			//SPAWN
			double spawnX = Double.parseDouble(splitData[2].split(div2)[0]);
			double spawnY = Double.parseDouble(splitData[2].split(div2)[1]);
			double spawnZ = Double.parseDouble(splitData[2].split(div2)[2]);
			
			//WEATHER
			int weatherDuration = Integer.parseInt(splitData[3].split(div2)[0]);
			int thunderDuration = Integer.parseInt(splitData[3].split(div2)[1]);
			boolean isThundering = Boolean.parseBoolean(splitData[3].split(div2)[2]);
			
			//MOBS
			int monsterLimit = Integer.parseInt(splitData[4]);
			long monsterSpawnTicks = Long.parseLong(splitData[5]);
			int animalLimit = Integer.parseInt(splitData[6]);
			long animalSpawnTicks = Long.parseLong(splitData[7]);
			int waterAnimalLimit = Integer.parseInt(splitData[8]);
			
			//SETTINGS
			int difficulty = Integer.parseInt(splitData[9]);
			boolean pvp = Boolean.parseBoolean(splitData[10]);
			
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
	public static void deserializeBlock(String data)
	{
		String[] parts = data.split(div2);
		
		World world = Bukkit.getWorld(parts[0]);
		if (world == null)
		{
			return;
		}
		Block block = world.getBlockAt(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
		if (block == null)
		{
			return;
		}
		
		BlockState backup = block.getState();
		
		try
		{
			block.setTypeId(Integer.parseInt(parts[4]));
			block.setData(Byte.parseByte(parts[5]));
		}
		catch (Exception e)
		{
			block.setTypeId(backup.getType().getId());
			block.setData(backup.getData().getData());
		}
	}

	public static Entity deserializeEntity(String data)
	{
		String[] parts = data.split(div1);
		
		try
		{
			EntityType type = EntityType.fromId(Integer.parseInt(parts[1]));
			
			Location location = new Location(
					Bukkit.getWorld(parts[2]),
					Double.parseDouble(parts[3]),
					Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]),
					Float.parseFloat(parts[6]),
					Float.parseFloat(parts[7]));
			
			Entity entity = Bukkit.getWorld(parts[2]).spawnEntity(location, type);
			
			return entity;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Deserializes a string into an item stack array
	 * @param data
	 * @return
	 */
	public static ItemStack[] deserializeItemStack(String data)
	{
		String[] splitData = data.split(div1);
		ItemStack[] items = new ItemStack[splitData.length];
		
		for (int slot = 0; slot < splitData.length; slot++)
		{
			String itemData = splitData[slot];
			
			try
			{
				String[] parts = itemData.split(div2);
				
				int amount = Integer.parseInt(parts[0]);
				int type = Integer.parseInt(parts[1]);
				short durability = Short.parseShort(parts[2]);
				
				Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
				
				if (parts.length > 3)
				{
					for (String enchantment : parts[3].split(div3))
					{
						enchantments.put(Enchantment.getById(Integer.parseInt(enchantment.split(",")[0])), Integer.parseInt(enchantment.split(",")[1]));
					}
				}
				
				ItemStack item = new ItemStack(Material.getMaterial(type));
				item.setAmount(amount);
				item.setDurability(durability);
				item.addEnchantments(enchantments);
				
				items[slot] = item;
			}
			catch (Exception e) {}
		}
		
		return items;
	}

	public static Location deserializeLocation(String data)
	{
		String[] parts = data.split(div1);
		try
		{
			Location loc = new Location(
					Bukkit.getWorld(parts[0]),
					Double.parseDouble(parts[1]),
					Double.parseDouble(parts[2]),
					Double.parseDouble(parts[3]),
					Float.parseFloat(parts[4]),
					Float.parseFloat(parts[5]));
		
			return loc;
		}
		catch (Exception e)
		{
			return null;
		}
	}

}
