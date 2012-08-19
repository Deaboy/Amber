package com.deaboy.amber.util;

import org.bukkit.block.BlockState;

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

}
