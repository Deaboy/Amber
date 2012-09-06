package com.deaboy.amber;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.deaboy.amber.record.AmberWorldRecorder;

public class AmberPlugin extends JavaPlugin
{
	private static AmberPlugin instance;
	
	private HashMap<World, AmberWorldRecorder> recorders = new HashMap<World, AmberWorldRecorder>();

	@Override
	public void onEnable()
	{
		AmberPlugin.instance = this;
		
		new AmberCommands();
	}

	@Override
	public void onDisable()
	{
		instance = null;
	}
	
	public static AmberPlugin getInstance()
	{
		return instance;
	}
	
	// PUBLIC STATIC METHODS / API
	
	public static boolean startRecordingWorld(World world)
	{
		AmberPlugin plugin = getInstance();
		
		if (!plugin.recorders.containsKey(world))
		{
			plugin.recorders.put(world, new AmberWorldRecorder(world));
		}
		
		AmberWorldRecorder recorder = plugin.recorders.get(world);
		
		if (recorder.isIdle())
		{
			plugin.recorders.get(world).startRecording();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean stopRecordingWorld(World world)
	{
		AmberPlugin plugin = getInstance();
		
		if (!plugin.recorders.containsKey(world))
		{
			plugin.recorders.put(world, new AmberWorldRecorder(world));
		}
		
		AmberWorldRecorder recorder = plugin.recorders.get(world);
		
		if (recorder.isRecording())
		{
			plugin.recorders.get(world).stopRecording();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean startRestoringWorld(World world)
	{
		AmberPlugin plugin = getInstance();
		
		if (!plugin.recorders.containsKey(world))
		{
			return false;
		}
		
		plugin.recorders.get(world);
		
		AmberWorldRecorder recorder = plugin.recorders.get(world);
		
		if (recorder.isIdle())
		{
			plugin.recorders.get(world).startRestoring();
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean stopRestoringWorld(World world)
	{
		AmberPlugin plugin = getInstance();
		
		if (!plugin.recorders.containsKey(world))
		{
			return false;
		}
		
		plugin.recorders.get(world);
		
		AmberWorldRecorder recorder = plugin.recorders.get(world);
		
		if (recorder.isRestoring())
		{
			plugin.recorders.get(world).stopRestoring();
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
