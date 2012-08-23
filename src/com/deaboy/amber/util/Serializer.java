package com.deaboy.amber.util;

import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

public class Serializer
{
	public static final String div1 = "|";
	public static final String div2 = ":";
	public static final String div3 = ",";
	
	/**
	 * Serializes the important information in the world
	 * @param world
	 * @return
	 */
	public static String serializeWorldData(World world)
	{
		String data = new String();
		
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
	public static String serializeBlockState(BlockState block)
	{
		String data = new String();
		
		data += block.getWorld().getName() + ";";
		data += block.getX() + ";";
		data += block.getY() + ";";
		data += block.getZ() + ";";
		data += block.getTypeId() + ";";
		data += block.getData() + ";";
		
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

}
