package com.deaboy.amber.record;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.World;

public class AmberWorldCopier
{
	private static String directory = "plugins/Amber/backups/";
	
	private final World world;
	private final File fromFile;
	private final File toFile;
	
	public AmberWorldCopier(World world)
	{
		this.world = world;
		this.fromFile = world.getWorldFolder();
		
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyLocalizedPattern("yyMMddHHmmss");
		
		this.toFile = new File(directory + world.getName() + "-" + formatter.format(new Date()));
	}
	
	public void copyFolder()
	{
		
	}
}
