package com.deaboy.amber.util;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

public class Serializer
{

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
