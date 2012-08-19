package com.deaboy.amber.util;

import org.bukkit.block.Block;

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

}
