package com.deaboy.amber.record;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;

import com.deaboy.amber.Amber;
import com.deaboy.amber.util.Constants;
import com.deaboy.amber.util.Deserializer;
import com.deaboy.amber.util.Serializer;

public class AmberWorldRecorder implements Listener
{
	public final World world;

	private final AmberWorldRecorderFileOutput output;
	private final AmberWorldRecorderFileInput input;
	private final AmberWorldRecorderListener listener;
	
	private Status status;
	
	private int schedule;
	private static final int apt = 500; // ACTIONS PER TICK

	private List<TinyBlockLoc> blockLocs = new ArrayList<TinyBlockLoc>();

	public AmberWorldRecorder(World world)
	{
		this.world = world;
		
		output = new AmberWorldRecorderFileOutput(world.getName());
		input = new AmberWorldRecorderFileInput(world.getName());
		listener = new AmberWorldRecorderListener(world);
		
		status = Status.IDLE;
	}

	/*
	 * RECORDING METHODS 
	 */
	
	public void startRecording()
	{
		if (status == Status.IDLE)
		{
			status = Status.RECORDING;
		}
		else
		{
			return;
		}
		output.open();
		listener.startListening();
		saveAllEntities();
	}

	public void stopRecording()
	{
		if (status == Status.RECORDING)
		{
			status = Status.IDLE;
		}
		else
		{
			return;
		}
		output.close();
		listener.stopListening();
		blockLocs.clear();
	}
	
	private void saveAllEntities()
	{
		if (output == null)
		{
			return;
		}
		
		for (Entity e : world.getEntities())
		{
			if (e.getType() == EntityType.PLAYER)
			{
				continue;
			}
			String data = Serializer.serializeEntity(e);
			output.write(data);
		}
	}
	
	/*
	 * RESTORING METHODS
	 */
	
	public void startRestoring()
	{
		if (status == Status.IDLE)
		{
			status = Status.RESTORING;
		}
		else
		{
			return;
		}
		input.open();
		listener.startListening();
		
		for (Entity e : world.getEntities())
		{
			if (e.getType() == EntityType.PLAYER)
			{
				continue;
			}
			e.remove();
		}
		
		schedule = Bukkit.getScheduler().scheduleSyncRepeatingTask(Amber.getInstance(), new Runnable()
		{
			public void run()
			{
				restoreStep();
			}
		}, 0, 0);
	}
	
	public void stopRestoring()
	{
		Bukkit.getLogger().log(Level.INFO, "stopping restoring...");
		if (status == Status.RESTORING)
		{
			status = Status.IDLE;
		}
		else
		{
			return;
		}
		input.close();
		listener.stopListening();
		
		Bukkit.getScheduler().cancelTask(schedule);
		Bukkit.getLogger().log(Level.INFO, "restoration complete");
	}
	
	public void restoreStep()
	{
		int step = apt;
		
		while (step > 0)
		{
			String data = input.read();
			
			if (data == null)
			{
				stopRestoring();
				return;
			}
			
			if (data.startsWith(Constants.prefixWorld))
			{
				Bukkit.getLogger().log(Level.INFO, "Restoring World");
				Deserializer.deserializeWorld(data);
				step--;
			}
			else if (data.startsWith(Constants.prefixEntity))
			{
				Bukkit.getLogger().log(Level.INFO, "Restoring entity");
				Deserializer.deserializeEntity(data);
				step--;
			}
			else if (data.startsWith(Constants.prefixBlock))
			{
				Bukkit.getLogger().log(Level.INFO, "Restoring block");
				Deserializer.deserializeBlock(data);
				step--;
			}
			else
			{
				continue;
			}
			
		}
	}
	
	public boolean saveBlock(BlockState block)
	{
		if (output == null)
		{
			return false;
		}
		if (locationAlreadySaved(block.getLocation()))
		{
			return false;
		}
		
		blockLocs.add(new TinyBlockLoc(block.getLocation()));
		
		String data = Serializer.serializeBlock(block);
		output.write(data);
		
		if (block.getType() == Material.CHEST) // Check if double chest, then save the other one too.
		{
			BlockState block2;
			
			if ((block2 = world.getBlockAt(block.getX()+1, block.getY(), block.getZ()).getState()).getType() == Material.CHEST
					|| (block2 = world.getBlockAt(block.getX()-1, block.getY(), block.getZ()).getState()).getType() == Material.CHEST
					|| (block2 = world.getBlockAt(block.getX(), block.getY(), block.getZ()+1).getState()).getType() == Material.CHEST
					|| (block2 = world.getBlockAt(block.getX(), block.getY(), block.getZ()-1).getState()).getType() == Material.CHEST)
				saveBlock(block2);
		}
		
		return true;
	}
	
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
	
	public boolean isIdle()
	{
		return status == Status.IDLE;
	}
	
	public boolean isRecording()
	{
		return status == Status.RECORDING;
	}
	
	public boolean isRestoring()
	{
		return status == Status.RESTORING;
	}
	
	private enum Status
	{
		RECORDING, IDLE, RESTORING;
	}
	
}