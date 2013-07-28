package com.deaboy.amber;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.deaboy.amber.record.AmberWorldRecorder;

public class Amber
{
	private static Amber instance = null;
	private HashMap<World, AmberWorldRecorder> recorders = new HashMap<World, AmberWorldRecorder>();
	
	public static Amber getInstance()
	{
		if (instance == null)
		{
			instance = new Amber();
		}
		return instance;
	}
	
	
	// PUBLIC STATIC METHODS / API
	public static boolean startRecordingWorld(World world, Plugin plugin)
	{
		Amber amber = getInstance();
		
		if (!amber.recorders.containsKey(world))
		{
			amber.recorders.put(world, new AmberWorldRecorder(world));
		}
		
		AmberWorldRecorder recorder = amber.recorders.get(world);
		
		if (recorder.isIdle())
		{
			recorder.startRecording(plugin);
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean stopRecordingWorld(World world)
	{
		Amber amber = getInstance();
		
		if (!amber.recorders.containsKey(world))
		{
			amber.recorders.put(world, new AmberWorldRecorder(world));
		}
		
		AmberWorldRecorder recorder = amber.recorders.get(world);
		
		if (recorder.isRecording())
		{
			recorder.stopRecording();
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean startRestoringWorld(World world, Plugin plugin)
	{
		Amber amber = getInstance();
		
		if (!amber.recorders.containsKey(world))
		{
			return false;
		}
		
		AmberWorldRecorder recorder = amber.recorders.get(world);
		
		if (recorder.isIdle() || recorder.isRecording())
		{
			recorder.startRestoring(plugin);
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean stopRestoringWorld(World world)
	{
		Amber amber = getInstance();
		
		if (!amber.recorders.containsKey(world))
		{
			return false;
		}
		
		AmberWorldRecorder recorder = amber.recorders.get(world);
		
		if (recorder.isRestoring())
		{
			recorder.stopRestoring();
			return true;
		}
		else
		{
			return false;
		}
	}
	public static AmberWorldRecorder getWorldRecorder(World world)
	{
		Amber amber = getInstance();
		
		if (!amber.recorders.containsKey(world))
		{
			amber.recorders.put(world, new AmberWorldRecorder(world));
		}
		
		return amber.recorders.get(world);
	}
	
	
	
}
