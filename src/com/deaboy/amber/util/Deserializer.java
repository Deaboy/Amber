package com.deaboy.amber.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Jukebox;
import org.bukkit.block.NoteBlock;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Deserializer
{
	private static final String div1 = Constants.div1;
	private static final String div2 = Constants.div2;
	private static final String div3 = Constants.div3;
	@SuppressWarnings("unused")
	private static final String div4 = Constants.div4;
	
	/**
	 * Deserializes data into world settings.
	 * @param data
	 */
	public static void deserializeWorld(String data)
	{
		data = data.substring(Constants.prefixWorld.length());
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
		data = data.substring(Constants.prefixBlock.length());
		String[] parts = data.split(div2);
		
		try
		{
			World world = Bukkit.getWorld(parts[2]);
			if (world == null)
			{
				return;
			}
			Block block = world.getBlockAt(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
			if (block == null)
			{
				return;
			}
			
			BlockState backup = block.getState();
			
			try
			{
				Material type = Material.getMaterial(Integer.parseInt(parts[0]));
				
				block.setType(type);
				block.setData(Byte.parseByte(parts[1]));
				
				// USE ONLY PARTS VALUES > 5
				
				switch (block.getType())
				{
				case BREWING_STAND:	((BrewingStand) block).setBrewingTime(Integer.parseInt(parts[6]));
									((BrewingStand) block).getInventory().setContents(deserializeItemStack(data.substring(data.indexOf(Constants.prefixInventory))));
									break;
				case CHEST:			((Chest) block).getBlockInventory().setContents(deserializeItemStack(data.substring(data.indexOf(Constants.prefixInventory))));
									break;
				case MOB_SPAWNER:	((CreatureSpawner) block).setSpawnedType(EntityType.fromId(Integer.parseInt(parts[6])));
									((CreatureSpawner) block).setDelay(Integer.parseInt(parts[7]));
									break;
				case DISPENSER:		((Dispenser) block).getInventory().setContents(deserializeItemStack(data.substring(data.indexOf(Constants.prefixInventory))));
									break;
				case FURNACE:		((Furnace) block).setBurnTime(Short.parseShort(parts[6]));
									((Furnace) block).setCookTime(Short.parseShort(parts[7]));
									((Furnace) block).getInventory().setContents(deserializeItemStack(data.substring(data.indexOf(Constants.prefixInventory))));
									break;
				case JUKEBOX:		((Jukebox) block).setPlaying(Material.getMaterial(Integer.parseInt(parts[6])));
									break;
				case NOTE_BLOCK:	((NoteBlock) block).setRawNote(Byte.parseByte(parts[6]));
									break;
				case SIGN:			((Sign) block).setLine(0, parts[6]);
									((Sign) block).setLine(1, parts[7]);
									((Sign) block).setLine(2, parts[8]);
									((Sign) block).setLine(3, parts[9]);
									break;
				default:			break;
				}
			}
			catch (Exception e)
			{
				block.setTypeId(backup.getType().getId());
				block.setData(backup.getData().getData());
			}
		}
		catch (Exception e)
		{
			Bukkit.getLogger().log(Level.SEVERE, e.toString());
			return;
		}
	}

	public static Entity deserializeEntity(String data)
	{
		data = data.substring(Constants.prefixEntity.length());
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
			
			entity.setVelocity(new Vector(Double.parseDouble(parts[8]), Double.parseDouble(parts[9]), Double.parseDouble(parts[10])));
			
			//TODO DESERIALIZE DIFFERENT TYPES
			
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
		data = data.substring(Constants.prefixInventory.length());
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
		data = data.substring(Constants.prefixLocation.length());
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

	public static void deserializePotionEffects(String data)
	{
		
	}

}
