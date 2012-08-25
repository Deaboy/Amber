package com.deaboy.amber.record;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;

import com.deaboy.amber.AmberPlugin;

public class AmberWorldRecorder implements Listener
{
	private final String directoryPath = "plugins/Amber/Recordings";
	private String recordPath;
	
	private List<TinyBlockLoc> blockLocs = new ArrayList<TinyBlockLoc>();
	
	private File directory = null;
	private File record = null;
	private FileOutputStream output = null;
	
	private final World world;
	
	public AmberWorldRecorder(World world)
	{
		this.world = world;
	}
	
	public void startRecording()
	{
		initializeFile();
		openFile();
		startListening();
	}
	
	public void stopRecording()
	{
		stopListening();
		closeFile();
	}
	
	/*
	 * FILE METHODS
	 */

	private void initializeFile()
	{	
		this.recordPath = this.world.getName() + ".awr"; //AWR = Amber World Recording
		
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
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void openFile()
	{
		try
		{
			output = new FileOutputStream(record);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private void closeFile()
	{
		if (output != null)
		{
			try
			{
				output.close();
				output = null;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * LISTENING METHODS
	 */

	private void startListening()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, AmberPlugin.getInstance());
	}

	private void stopListening()
	{
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler
	public void onBlockEvent(BlockEvent e)
	{
		
	}
	
	/*
	 * HELPER METHODS
	 */
	
	public boolean locationAlreadySaved(Location loc)
	{
		for (TinyBlockLoc bLoc : blockLocs)
		{
			if (bLoc.equals(loc))
			{
				return true;
			}
		}
		return false;
	}
	
}