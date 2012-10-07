package com.deaboy.amber.record;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Tiny, simple class for saving a block's location.
 * @author Deaboy
 *
 */
public class TinyBlockLoc
{
	public final int x;
	public final int y;
	public final int z;
	
	public TinyBlockLoc(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public TinyBlockLoc(Location loc)
	{
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
	}
	
	public boolean equals(TinyBlockLoc loc)
	{
		return (x == loc.x && y == loc.y && z == loc.z);
	}
	
	public boolean equals(Location loc)
	{
		return (x == loc.getBlockX() && y == loc.getBlockY() && z == loc.getBlockZ());
	}
	
	public Location toLocation(World world)
	{
		return new Location(world, x, y, z);
	}
}
