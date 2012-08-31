package com.deaboy.amber.record;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;

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
		Block block = e.getBlock();
		
		if (locationAlreadySaved(block.getLocation()))
		{
			return;
		}
		if (output == null)
		{
			return;
		}
		
		blockLocs.add(new TinyBlockLoc(block.getLocation()));
		
		String data = Serializer.serializeBlock(block);
		output.write(data);
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
	
	private enum State
	{
		RECORDING, IDLE, RESTORING;
	}
	
}