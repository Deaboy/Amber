package com.deaboy.amber.record;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.entity.*;

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
	
	public void onEvent(Event e)
	{
		if (e instanceof PlayerEvent)
		{
			if (((PlayerEvent) e).getPlayer().getWorld() != world)
			{
				return;
			}
		}
		else if (e instanceof BlockEvent)
		{
			if (((BlockEvent) e).getBlock().getWorld() != world)
			{
				return;
			}
		}
		else if (e instanceof InventoryPickupItemEvent)
		{
			if (!(((InventoryPickupItemEvent) e).getInventory().getHolder() instanceof BlockState)
				|| ((BlockState) ((InventoryPickupItemEvent) e).getInventory().getHolder()).getWorld() != world)
			{
				return;
			}
		}
		else if (e instanceof InventoryMoveItemEvent)
		{
			if ((
					!(((InventoryMoveItemEvent) e).getSource().getHolder() instanceof BlockState)
					|| ((BlockState) ((InventoryMoveItemEvent) e).getSource().getHolder()).getWorld() != world
				) && (
					!(((InventoryMoveItemEvent) e).getDestination().getHolder() instanceof BlockState)
					|| ((BlockState) ((InventoryMoveItemEvent) e).getDestination().getHolder()).getWorld() != world
					))
				return;
		}
		else
		{
			return;
		}
		if (Amber.getWorldRecorder(world).isRecording())
		{
			BlockState block = null;
			if (e instanceof BlockEvent)
				block = ((BlockEvent) e).getBlock().getState();
			if (e instanceof BlockPlaceEvent)
				block = ((BlockPlaceEvent) e).getBlockReplacedState();
			if (e instanceof BlockFromToEvent)
				block = ((BlockFromToEvent) e).getToBlock().getState();
			if (e instanceof BlockPhysicsEvent)
				block = ((BlockPhysicsEvent) e).getBlock().getState();
			if (e instanceof PlayerBucketEmptyEvent)
				block = ((PlayerBucketEmptyEvent) e).getBlockClicked().getRelative(((PlayerBucketEmptyEvent) e).getBlockFace()).getState();
			if (e instanceof PlayerBucketFillEvent)
				block = ((PlayerBucketFillEvent) e).getBlockClicked().getState();
			if (e instanceof EntityInteractEvent)
				block = ((EntityInteractEvent) e).getBlock().getState();
			if (e instanceof PlayerInteractEvent)
				if (((PlayerInteractEvent) e).getClickedBlock() == null)
					return;
				else
					block = ((PlayerInteractEvent) e).getClickedBlock().getState();
			if (e instanceof BlockPistonExtendEvent)
			{
				Block b = block.getBlock();
				for (int i = 0; i <= ((BlockPistonExtendEvent) e).getLength(); i++)
				{
					b = b.getRelative(((BlockPistonExtendEvent) e).getDirection());
					Amber.getWorldRecorder(world).saveBlock(b.getState());
				}
			}
			if (e instanceof BlockPistonRetractEvent)
			{

				Block b = block.getBlock();
				for (int i = 0; i < 2; i++)
				{
					b = b.getRelative(((BlockPistonRetractEvent) e).getDirection());
					Amber.getWorldRecorder(world).saveBlock(b.getState());
				}
			}
			if (e instanceof InventoryPickupItemEvent)
			{
				if (((InventoryPickupItemEvent) e).getInventory().getHolder() instanceof BlockState)
					block = (BlockState) ((InventoryPickupItemEvent) e).getInventory().getHolder();
			}
			if (e instanceof InventoryMoveItemEvent)
			{
				if (((InventoryMoveItemEvent) e).getSource().getHolder() instanceof BlockState)
				{
					block = (BlockState) ((InventoryMoveItemEvent) e).getSource().getHolder();
					Amber.getWorldRecorder(world).saveBlock(block);
				}
				if (((InventoryMoveItemEvent) e).getDestination().getHolder() instanceof BlockState)
				{
					block = (BlockState) ((InventoryMoveItemEvent) e).getDestination().getHolder();
					Amber.getWorldRecorder(world).saveBlock(block);
				}
			}
			if (block == null)
			{
				return;
			}
			if (Amber.getWorldRecorder(world).saveBlock(block))
			{
				//Bukkit.getLogger().log(Level.INFO, "Saved block: " + block.getType().name());
				//Bukkit.getLogger().log(Level.INFO, "   event: " + e.getEventName());
			}
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
		onEvent(e);
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockDispense(BlockDispenseEvent e)
	{
		onEvent(e);
	}
	
	@EventHandler
	public void onBlockFade(BlockFadeEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockGrow(BlockGrowEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockPhysicsEvent(BlockPhysicsEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockPistonExtend(BlockPistonExtendEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockPistonRetract(BlockPistonRetractEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onInventoryMoveItem(InventoryMoveItemEvent e)
	{
		onEvent(e);
	}
	
	@EventHandler
	public void onInventoryPickupItem(InventoryPickupItemEvent e)
	{
		onEvent(e);
	}
	
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onBrew(BrewEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onFurnaceBurn(FurnaceBurnEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e)
	{
		onEvent(e);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e)
	{
		onEvent(e);
	}

	@EventHandler
	public void onPlayerBucketFill(PlayerBucketFillEvent e)
	{
		onEvent(e);
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e)
	{
		for (Block block : e.blockList())
		{
			if (Amber.getWorldRecorder(world).saveBlock(block.getState()))
			{
				//Bukkit.getLogger().log(Level.INFO, "Saved block: " + block.getType().name());
				//Bukkit.getLogger().log(Level.INFO, "   event: " + e.getEventName());
			}
		}
	}
	
	@EventHandler
	public void onEntityInteract(EntityInteractEvent e)
	{
		onEvent(e);
	}
}
