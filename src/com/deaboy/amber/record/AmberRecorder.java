package com.deaboy.amber.record;

import java.io.File;
import java.io.IOException;

import org.bukkit.World;

public class AmberRecorder
{
	private final String directoryPath = "plugins/Amber/Recordings";
	private final String recordPath;
	
	private final File directory;
	private final File record;
	private final World world;
	
	public AmberRecorder(World world)
	{
		this.world = world;
		
		this.recordPath = "Recording_" + this.world.getName() + ".awr"; //AWR = Amber World Recording
		
		this.directory = new File(directoryPath);
		if (!directory.exists() || !directory.isDirectory())
		{
			directory.mkdir();
		}
		
		this.record = new File(recordPath);
		if (record.exists())
		{
			record.delete();
		}
		
		try
		{
			record.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
