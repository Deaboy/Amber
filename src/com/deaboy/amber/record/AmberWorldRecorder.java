package com.deaboy.amber.record;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.Cancellable;

import com.deaboy.amber.AmberPlugin;
import com.deaboy.amber.util.Constants;
import com.deaboy.amber.util.Deserializer;
import com.deaboy.amber.util.Serializer;

public class AmberWorldRecorder implements Listener
{
	private final World world;

	private final AmberWorldRecorderFileOutput output;
	private final AmberWorldRecorderFileInput input;
	
	private Status status;
	
	private int schedule;
	private static final short apt = 500; // ACTIONS PER TICK

	private List<TinyBlockLoc> blockLocs = new ArrayList<TinyBlockLoc>();

	public AmberWorldRecorder(World world)
	{
		this.world = world;
		
		output = new AmberWorldRecorderFileOutput(world.getName());
		input = new AmberWorldRecorderFileInput(world.getName());
		
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
		startListening();
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
		stopListening();
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
		startListening();
		
		for (Entity e : world.getEntities())
		{
			e.remove();
		}
		
		schedule = Bukkit.getScheduler().scheduleSyncRepeatingTask(AmberPlugin.getInstance(), new Runnable()
		{
			public void run()
			{
				restoreStep();
			}
		}, 0, 0);
	}
	
	public void stopRestoring()
	{
		if (status == Status.RESTORING)
		{
			status = Status.IDLE;
		}
		else
		{
			return;
		}
		input.close();
		stopListening();
		
		Bukkit.getScheduler().cancelTask(schedule);
	}
	
	public void restoreStep()
	{
		short step = apt;
		
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
				Deserializer.deserializeWorld(data);
				step--;
			}
			else if (data.startsWith(Constants.prefixEntity))
			{
				Deserializer.deserializeEntity(data);
				step--;
			}
			else if (data.startsWith(Constants.prefixBlock))
			{
				Deserializer.deserializeBlock(data);
				step--;
			}
			else
			{
				continue;
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
	public void onBlock(BlockEvent e)
	{
		if (status == Status.RECORDING)
		{
			if (!saveBlock(e.getBlock()))
			{
				return;
			}
		}
		else if (status == Status.RESTORING)
		{
			if (Cancellable.class.isInstance(e))
			{
				((Cancellable) e).setCancelled(true);
			}
		}
		else
		{
			return;
		}
	}
	
	/*
	 * HELPER METHODS
	 */
	
	public boolean saveBlock(Block block)
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
			Block block2;
			
			if ((block2 = world.getBlockAt(block.getX()+1, block.getY(), block.getZ())).getType() == Material.CHEST
					|| (block2 = world.getBlockAt(block.getX()-1, block.getY(), block.getZ())).getType() == Material.CHEST
					|| (block2 = world.getBlockAt(block.getX(), block.getY(), block.getZ()+1)).getType() == Material.CHEST
					|| (block2 = world.getBlockAt(block.getX(), block.getY(), block.getZ()-1)).getType() == Material.CHEST)
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