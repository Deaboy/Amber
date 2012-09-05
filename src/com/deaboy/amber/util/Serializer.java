package com.deaboy.amber.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
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
import org.bukkit.inventory.ItemStack;


public class Serializer
{
	public static final String div1 = "|";
	public static final String div2 = ":";
	public static final String div3 = ";";
	public static final String div4 = ",";
	public static final String divInv = "/inv/";
	
	/**
	 * Serializes the important information in the world
	 * @param world
	 * @return
	 */
	public static String serializeWorld(World world)
	{
		String data = new String();
		// WORLD NAME
		data += world.getName();
		
		//WORLD TIME
		data += world.getFullTime() + div1;
		
		//WORLD SPAWN
		data += world.getSpawnLocation().getX() + div2;
		data += world.getSpawnLocation().getY() + div2;
		data += world.getSpawnLocation().getZ() + div1;
		
		//WORLD WEATHER
		data += world.getWeatherDuration() + div2;
		data += world.getThunderDuration() + div2;
		data += world.isThundering() + div1;
		
		//WORLD MOB SETTINGS
		data += world.getMonsterSpawnLimit() + div1;
		data += world.getTicksPerMonsterSpawns() + div1;
		data += world.getAnimalSpawnLimit() + div1;
		data += world.getTicksPerAnimalSpawns() + div1;
		data += world.getWaterAnimalSpawnLimit() + div1;
		
		//WORLD SETTINGS
		data += world.getDifficulty() + div1;
		data += world.getPVP() + div1;
		
		return data;
	}

	/**
	 * Returns a serialized version of a blockState object. World, x, y, z,
	 * type, damage.
	 * @param block The block state to serialize
	 * @return Serialized version of the block
	 */
	public static String serializeBlock(Block block)
	{
		String data = new String();
		
		data += block.getTypeId() + div1;
		data += block.getData() + div1;
		data += block.getWorld().getName() + div1;
		data += block.getX() + div1;
		data += block.getY() + div1;
		data += block.getZ() + div1;
		
		switch (block.getType())
		{
		case BREWING_STAND:	data += ((BrewingStand) block).getBrewingTime() + div1;
							data += divInv + serializeItemStack(((BrewingStand) block).getInventory().getContents()); //serializeItemStack includes a div1
							break;
		case CHEST:			data += divInv + serializeItemStack(((Chest) block).getBlockInventory().getContents()); //serializeItemStack includes a div1
							break;
		case MOB_SPAWNER:	data += ((CreatureSpawner) block).getSpawnedType().getTypeId() + div1;
							data += ((CreatureSpawner) block).getDelay() + div1;
							break;
		case DISPENSER:		data += divInv + serializeItemStack(((Dispenser) block).getInventory().getContents()); //serializeItemStack includes a div1
							break;
		case FURNACE:		data += ((Furnace) block).getBurnTime() + div1;
							data += ((Furnace) block).getCookTime() + div1;
							data += divInv + serializeItemStack(((Furnace) block).getInventory().getContents());
							break;
		case JUKEBOX:		data += ((Jukebox) block).getPlaying().getId() + div1;
							break;
		case NOTE_BLOCK:	data += ((NoteBlock) block).getRawNote() + div1;;
							break;
		case SIGN:			data += ((Sign) block).getLine(0) + div1;
							data += ((Sign) block).getLine(1) + div1;
							data += ((Sign) block).getLine(2) + div1;
							data += ((Sign) block).getLine(3) + div1;
							break;
		default:			break;
		}
		
		return data;
	}

	public static String serializeEntity(Entity entity)
	{
		String data = new String();
		
		data += entity.getEntityId() + ";";
		data += entity.getType().getTypeId() + ";";
		data += entity.getWorld().getName() + ";";
		data += entity.getLocation().getX() + ";";
		data += entity.getLocation().getY() + ";";
		data += entity.getLocation().getZ() + ";";
		data += entity.getLocation().getYaw() + ";";
		data += entity.getLocation().getPitch() + ";";
		
		return data;
	}

	public static String serializeItemStack(ItemStack[] stack)
	{
		String data = new String();
		
		for (int slot = 0; slot < stack.length; slot++)
		{
			String item = new String();
			try
			{
				item += stack[slot].getAmount();
				item += div2;
				item += stack[slot].getTypeId();
				item += div2;
				item += stack[slot].getDurability();
				item += div2;
				for (Enchantment enchantment : stack[slot].getEnchantments().keySet())
				{
					item += enchantment.getId();
					item += div4;
					item += stack[slot].getEnchantments().get(enchantment);
					item += div3;
				}
			}
			catch (Exception e)
			{
				item = new String();
			}
			data += item + div1;
		}
		
		return data;
	}

	public static String serializeLocation(Location loc)
	{
		String data = new String();
		
		data += loc.getWorld() + div1;
		data += loc.getX() + div1;
		data += loc.getY() + div1;
		data += loc.getZ() + div1;
		data += loc.getYaw() + div1;
		data += loc.getPitch() + div1;
		
		return data;
	}

}
