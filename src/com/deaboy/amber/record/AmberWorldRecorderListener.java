package com.deaboy.amber.record;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.*;

import com.deaboy.amber.Amber;

public class AmberWorldRecorderListener implements Listener
{
	private final World world;
	
	public AmberWorldRecorderListener(World world)
	{
		this.world = world;
	}
	
	public void startListening()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, Amber.getInstance());
	}

	public void stopListening()
	{
		HandlerList.unregisterAll(this);
	}
	
	public void onBlockEvent(BlockEvent e)
	{
		if (e.getBlock().getWorld() != world)
		{
			return;
		}
		if (Amber.getWorldRecorder(world).isRecording())
		{
			Amber.getWorldRecorder(world).saveBlock(e.getBlock());
		}
		else if (Amber.getWorldRecorder(world).isRestoring())
		{
			if (e instanceof Cancellable)
			{
				((Cancellable) e).setCancelled(true);
			}
		}
		else
		{
			return;
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockDispense(BlockDispenseEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockFade(BlockFadeEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockGrow(BlockGrowEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockPhysicsEvent(BlockPhysicsEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockPiston(BlockPistonEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onBrew(BrewEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onFurnaceBurn(FurnaceBurnEvent e)
	{
		onBlockEvent(e);
	}

	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e)
	{
		onBlockEvent(e);
	}

}
