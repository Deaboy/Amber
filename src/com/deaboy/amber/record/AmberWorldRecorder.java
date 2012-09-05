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
import com.deaboy.amber.util.Serializer;

public class AmberWorldRecorder implements Listener
{
	private final World world;

	private final AmberWorldRecorderFileOutput output;
	private final AmberWorldRecorderFileInput input;
	
	private State state;

	private List<TinyBlockLoc> blockLocs = new ArrayList<TinyBlockLoc>();

	public AmberWorldRecorder(World world)
	{
		this.world = world;
		
		output = new AmberWorldRecorderFileOutput(world.getName());
		input = new AmberWorldRecorderFileInput(world.getName());
		
		state = State.IDLE;
	}

	/*
	 * RECORDING METHODS 
	 */
	
	public void startRecording()
	{
		if (state == State.IDLE)
		{
			state = State.RECORDING;
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
		if (state == State.RECORDING)
		{
			state = State.IDLE;
		}
		else
		{
			return;
		}
		output.close();
		stopListening();
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
		if (!saveBlock(e.getBlock()))
		{
			return;
		}
		
		// CANCEL THE EVENT
		if (Cancellable.class.isInstance(e))
		{
			((Cancellable) e).setCancelled(true);
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
	
	private enum State
	{
		RECORDING, IDLE, RESTORING;
	}
	
}