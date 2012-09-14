package com.deaboy.amber.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.DyeColor;
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
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Slime;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Deserializer
{
	private static final String div1 = "\\" + Constants.div1;
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
		data = new String(data.substring(Constants.prefixWorld.length()));
		String[] parts = data.split(div1).clone();
		
		for (int i = 0; i < parts.length; i++)
		{
			parts[i] = new String(parts[i]);
		}
		
		try
		{
			// GET WORLD
			World world = Bukkit.getWorld(parts[0]);
			
			//PARSE THE DATA
			long fullTime = Long.parseLong(parts[1]);
			
			//SPAWN
			double spawnX = Double.parseDouble(parts[2].split(div2)[0]);
			double spawnY = Double.parseDouble(parts[2].split(div2)[1]);
			double spawnZ = Double.parseDouble(parts[2].split(div2)[2]);
			
			//WEATHER
			int weatherDuration = Integer.parseInt(parts[3].split(div2)[0]);
			int thunderDuration = Integer.parseInt(parts[3].split(div2)[1]);
			boolean isThundering = Boolean.parseBoolean(parts[3].split(div2)[2]);
			
			//MOBS
			int monsterLimit = Integer.parseInt(parts[4]);
			long monsterSpawnTicks = Long.parseLong(parts[5]);
			int animalLimit = Integer.parseInt(parts[6]);
			long animalSpawnTicks = Long.parseLong(parts[7]);
			int waterAnimalLimit = Integer.parseInt(parts[8]);
			
			//SETTINGS
			int difficulty = Integer.parseInt(parts[9]);
			boolean pvp = Boolean.parseBoolean(parts[10]);
			
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
		data = new String(data.substring(Constants.prefixBlock.length()));
		String[] parts = data.split(div1).clone();
		
		for (int i = 0; i < parts.length; i++)
		{
			parts[i] = new String(parts[i]);
		}
		
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
				Bukkit.getLogger().log(Level.SEVERE, e.toString());
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
		Entity entity = null;
		
		try
		{
			data = new String(data.substring(Constants.prefixEntity.length()));
			String[] parts = new String[data.split(div1).clone().length];
			
			for (int i = 0; i < parts.length; i++)
			{
				parts[i] = new String(data.split(div1).clone()[i]);
			}
			
			EntityType type = EntityType.fromId(Integer.parseInt(parts[1]));
			
			if (type == null)
			{
				return null;
			}
			
			Location location = new Location(
					Bukkit.getWorld(parts[2]),
					Double.parseDouble(parts[3]),
					Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]),
					Float.parseFloat(parts[6]),
					Float.parseFloat(parts[7]));
			
			entity = Bukkit.getWorld(parts[2]).spawnEntity(location, type);
			entity.setVelocity(new Vector(Double.parseDouble(parts[8]), Double.parseDouble(parts[9]), Double.parseDouble(parts[10])));
			
			if (entity instanceof LivingEntity)
			{
				((LivingEntity) entity).setHealth(Integer.parseInt(parts[11]));
				((LivingEntity) entity).setRemainingAir(Integer.parseInt(parts[12]));
				((LivingEntity) entity).setFallDistance(Float.parseFloat(parts[13]));
				
				switch (entity.getType())
				{
				// HOSTILE
				case CREEPER:	((Creeper) entity).setPowered(Boolean.parseBoolean(parts[14])); // 14
								break;
				case ENDERMAN:	((Enderman) entity).setCarriedMaterial(new MaterialData(Integer.parseInt(parts[14]), Byte.parseByte(parts[15]))); // 14
								break;
				case SLIME:		((Slime) entity).setSize(Integer.parseInt(parts[14])); // 14
								break;
				// NETHER
				case MAGMA_CUBE:((MagmaCube) entity).setSize(Integer.parseInt(parts[14])); // 14
								break;
				// PASSIVE
				case PIG:		((Pig) entity).setAge(Integer.parseInt(parts[14])); // 14
								((Pig) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								((Pig) entity).setSaddle(Boolean.parseBoolean(parts[16])); // 16
								break;
				case COW:		((Cow) entity).setAge(Integer.parseInt(parts[14])); // 14
								((Cow) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								break;
				case MUSHROOM_COW:((MushroomCow) entity).setAge(Integer.parseInt(parts[14])); // 14
								((MushroomCow) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								break;
				case CHICKEN:	((Chicken) entity).setAge(Integer.parseInt(parts[14])); // 14
								((Chicken) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								break;
				case SHEEP:		((Sheep) entity).setAge(Integer.parseInt(parts[14])); // 14
								((Sheep) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								((Sheep) entity).setColor(DyeColor.getByData(Byte.parseByte(parts[16]))); // 16
								break;
				case WOLF:		((Wolf) entity).setAge(Integer.parseInt(parts[14])); // 14
								((Wolf) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								((Wolf) entity).setOwner(Bukkit.getOfflinePlayer(parts[17])); // 16
								((Wolf) entity).setSitting(Boolean.parseBoolean(parts[18])); // 17
								break;
				case OCELOT:	((Ocelot) entity).setAge(Integer.parseInt(parts[14])); // 14
								((Ocelot) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								((Ocelot) entity).setCatType(Type.getType(Integer.parseInt(parts[16]))); // 16
								((Ocelot) entity).setOwner(Bukkit.getOfflinePlayer(parts[17])); // 17
								((Ocelot) entity).setSitting(Boolean.parseBoolean(parts[18])); // 18
								break;
				case VILLAGER:	((Villager) entity).setAge(Integer.parseInt(parts[14])); // 14
								((Villager) entity).setBreed(Boolean.parseBoolean(parts[15])); // 15
								((Villager) entity).setProfession(Profession.getProfession(Integer.parseInt(parts[16]))); // 16
								break;
								
				default:		break;
				
				}
				
				((LivingEntity) entity).addPotionEffects(deserializePotionEffects(data.substring(data.indexOf(Constants.prefixEffects)))); // ?
			}
			else
			{
				switch (entity.getType())
				{
				case PAINTING:	data += ((Painting) entity).getArt().getId() + div1; // 14
								data += ((Painting) entity).getAttachedFace().name() + div1; // 15
								break;
				case PRIMED_TNT:data += ((TNTPrimed) entity).getFireTicks() + div1;
								break;
				default:		break;
				}
			}
			
			return entity;
		}
		catch (Exception e)
		{
			Bukkit.getLogger().log(Level.SEVERE, e.toString());
			if (entity != null)
			{
				entity.remove();
			}
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
		data = new String(data.substring(Constants.prefixInventory.length()));
		String[] parts = data.split(div1).clone();
		
		for (int i = 0; i < parts.length; i++)
		{
			parts[i] = new String(parts[i]);
		}
		ItemStack[] items = new ItemStack[parts.length];
		
		for (int slot = 0; slot < parts.length; slot++)
		{
			String itemData = parts[slot];
			
			try
			{
				String[] itemParts = itemData.split(div2);
				
				int amount = Integer.parseInt(itemParts[0]);
				int type = Integer.parseInt(itemParts[1]);
				short durability = Short.parseShort(itemParts[2]);
				
				Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
				
				if (itemParts.length > 3)
				{
					for (String enchantment : itemParts[3].split(div3))
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
			catch (Exception e) {
				Bukkit.getLogger().log(Level.SEVERE, e.toString());
				return null;
			}
		}
		
		return items;
	}

	public static Location deserializeLocation(String data)
	{
		try
		{
			data = new String(data.substring(Constants.prefixLocation.length()));
			String[] parts = data.split(div1).clone();
			
			for (int i = 0; i < parts.length; i++)
			{
				parts[i] = new String(parts[i]);
			}
			
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
			Bukkit.getLogger().log(Level.SEVERE, e.toString());
			return null;
		}
	}

	public static Collection<PotionEffect> deserializePotionEffects(String data)
	{
		Collection<PotionEffect> effects = new ArrayList<PotionEffect>();
		
		try
		{
			data = data.substring(Constants.prefixEffects.length());
			String[] parts = data.split(div1).clone();
			
			for (int i = 0; i < parts.length; i++)
			{
				parts[i] = new String(parts[i]);
			}
			
			for (String effectdata : parts)
			{
				String[] effectparts = effectdata.split(div2);
				effects.add(new PotionEffect(PotionEffectType.getById(Integer.parseInt(effectparts[0])), Integer.parseInt(effectparts[1]), Integer.parseInt(effectparts[2])));
			}
		}
		catch (Exception e)
		{
			Bukkit.getLogger().log(Level.SEVERE, e.toString());
			effects.clear();
		}
		
		return effects;
	}

}
