package com.deaboy.amber.util;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Jukebox;
import org.bukkit.block.NoteBlock;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Slime;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;


public class Serializer
{
	private static final String div1 = Constants.div1;
	private static final String div2 = Constants.div2;
	private static final String div3 = Constants.div3;
	private static final String div4 = Constants.div4;
	
	/**
	 * Serializes the important information in the world
	 * @param world
	 * @return
	 */
	public static String serializeWorld(World world)
	{
		String data = Constants.prefixWorld;
		// WORLD NAME
		data += world.getName();
		
		//WORLD TIME
		data += world.getFullTime() + div1;
		
		//WORLD SPAWN
		data += world.getSpawnLocation().getX() + div2;
		data += world.getSpawnLocation().getY() + div2;
		data += world.getSpawnLocation().getZ() + div1;
		
		//WORLD WEATHER
		data += world.getWeatherDuration() + div2;
		data += world.getThunderDuration() + div2;
		data += world.isThundering() + div1;
		
		//WORLD MOB SETTINGS
		data += world.getMonsterSpawnLimit() + div1;
		data += world.getTicksPerMonsterSpawns() + div1;
		data += world.getAnimalSpawnLimit() + div1;
		data += world.getTicksPerAnimalSpawns() + div1;
		data += world.getWaterAnimalSpawnLimit() + div1;
		
		//WORLD SETTINGS
		data += world.getDifficulty() + div1;
		data += world.getPVP() + div1;
		
		return data;
	}

	/**
	 * Returns a serialized version of a blockState object. World, x, y, z,
	 * type, damage.
	 * @param block The block state to serialize
	 * @return Serialized version of the block
	 */
	public static String serializeBlock(Block block)
	{
		String data = Constants.prefixBlock;
		
		data += block.getTypeId() + div1;
		data += block.getData() + div1;
		data += block.getWorld().getName() + div1;
		data += block.getX() + div1;
		data += block.getY() + div1;
		data += block.getZ() + div1;
		
		switch (block.getType())
		{
		case BREWING_STAND:	data += ((BrewingStand) block).getBrewingTime() + div1;
							data += Constants.prefixInventory;
							data += serializeItemStack(((BrewingStand) block).getInventory().getContents()); //serializeItemStack includes a div1
							break;
		case CHEST:			data += Constants.prefixInventory;
							data += serializeItemStack(((Chest) block).getBlockInventory().getContents()); //serializeItemStack includes a div1
							break;
		case MOB_SPAWNER:	data += ((CreatureSpawner) block).getSpawnedType().getTypeId() + div1;
							data += ((CreatureSpawner) block).getDelay() + div1;
							break;
		case DISPENSER:		data += Constants.prefixInventory;
							data += serializeItemStack(((Dispenser) block).getInventory().getContents()); //serializeItemStack includes a div1
							break;
		case FURNACE:		data += ((Furnace) block).getBurnTime() + div1;
							data += ((Furnace) block).getCookTime() + div1;
							data += Constants.prefixInventory;
							data += serializeItemStack(((Furnace) block).getInventory().getContents());
							break;
		case JUKEBOX:		data += ((Jukebox) block).getPlaying().getId() + div1;
							break;
		case NOTE_BLOCK:	data += ((NoteBlock) block).getRawNote() + div1;;
							break;
		case SIGN:			data += ((Sign) block).getLine(0) + div1;
							data += ((Sign) block).getLine(1) + div1;
							data += ((Sign) block).getLine(2) + div1;
							data += ((Sign) block).getLine(3) + div1;
							break;
		default:			break;
		}
		
		return data;
	}

	public static String serializeEntity(Entity entity)
	{
		String data = Constants.prefixEntity;
		
		data += entity.getEntityId() + div1;			// 0
		data += entity.getType().getTypeId() + div1;	// 1
		data += entity.getWorld().getName() + div1;		// 2
		data += entity.getLocation().getX() + div1;		// 3
		data += entity.getLocation().getY() + div1;		// 4
		data += entity.getLocation().getZ() + div1;		// 5
		data += entity.getLocation().getYaw() + div1;	// 6
		data += entity.getLocation().getPitch() + div1;	// 7
		data += entity.getVelocity().getX() + div1;		// 8
		data += entity.getVelocity().getY() + div1;		// 9
		data += entity.getVelocity().getZ() + div1;		// 10
		
		if (entity instanceof LivingEntity)
		{
			data += ((LivingEntity) entity).getHealth() + div1; // 11
			data += ((LivingEntity) entity).getRemainingAir() + div1; // 12
			data += ((LivingEntity) entity).getFallDistance() + div1; // 13
			
			switch (entity.getType())
			{
			// HOSTILE
			case CREEPER:	data += ((Creeper) entity).isPowered() + div1; // 14
							break;
			case ENDERMAN:	data += ((Enderman) entity).getCarriedMaterial() + div1; // 14
							break;
			case SLIME:		data += ((Slime) entity).getSize() + div1; // 14
							break;
			// NETHER
			case MAGMA_CUBE:data += ((MagmaCube) entity).getSize() + div1; // 14
							break;
			// PASSIVE
			case PIG:		data += ((Pig) entity).isAdult() + div1; // 14
							data += ((Pig) entity).canBreed() + div1; // 15
							data += ((Pig) entity).hasSaddle() + div1; // 16
							break;
			case COW:		data += ((Cow) entity).isAdult() + div1; // 14
							data += ((Cow) entity).canBreed() + div1; // 15
							break;
			case MUSHROOM_COW:data += ((MushroomCow) entity).isAdult() + div1; // 14
							data += ((MushroomCow) entity).canBreed() + div1; // 15
							break;
			case CHICKEN:	data += ((Chicken) entity).isAdult() + div1; // 14
							data += ((Chicken) entity).canBreed() + div1; // 15
							break;
			case SHEEP:		data += ((Sheep) entity).isAdult() + div1; // 14
							data += ((Sheep) entity).canBreed() + div1; // 15
							data += ((Sheep) entity).getColor().name() + div1; // 16
							break;
			case WOLF:		data += ((Wolf) entity).isAdult() + div1; // 14
							data += ((Wolf) entity).canBreed() + div1; // 15
							data += ((Wolf) entity).isTamed() + div1; // 16
							data += ((Wolf) entity).getOwner().getName() + div1; //17
							data += ((Wolf) entity).isSitting() + div1; // 18
							break;
			case OCELOT:	data += ((Ocelot) entity).isAdult() + div1; // 14
							data += ((Ocelot) entity).canBreed() + div1; // 15
							data += ((Ocelot) entity).getCatType().getId() + div1; // 16
							data += ((Ocelot) entity).isTamed() + div1; // 17
							data += ((Ocelot) entity).getOwner().getName() + div1; // 18
							data += ((Ocelot) entity).isSitting() + div1; // 19
							break;
			case VILLAGER:	data += ((Villager) entity).isAdult() + div1; // 14
							data += ((Villager) entity).canBreed() + div1; // 15
							data += ((Villager) entity).getProfession().getId() + div1; // 16
							break;
							
			default:		break;
			
			}
			
			data += serializePotionEffects(((LivingEntity) entity).getActivePotionEffects()) + div1; // ?
		}
		else
		{
			switch (entity.getType())
			{
			case PAINTING:	data += ((Painting) entity).getArt().getId() + div1; // 14
							data += ((Painting) entity).getAttachedFace().name() + div1; // 15
							break;
			case PRIMED_TNT:data += ((TNTPrimed) entity).getFireTicks() + div1;
							break;
			default:		break;
			}
		}
		
		return data;
	}

	public static String serializeItemStack(ItemStack ... stack)
	{
		String data = Constants.prefixInventory;
		
		for (int slot = 0; slot < stack.length; slot++)
		{
			String item = new String();
			try
			{
				item += stack[slot].getAmount();
				item += div2;
				item += stack[slot].getTypeId();
				item += div2;
				item += stack[slot].getDurability();
				item += div2;
				for (Enchantment enchantment : stack[slot].getEnchantments().keySet())
				{
					item += enchantment.getId();
					item += div4;
					item += stack[slot].getEnchantments().get(enchantment);
					item += div3;
				}
			}
			catch (Exception e)
			{
				item = new String();
			}
			data += item + div1;
		}
		
		return data;
	}

	public static String serializeLocation(Location loc)
	{
		String data = Constants.prefixLocation;
		
		data += loc.getWorld() + div1;
		data += loc.getX() + div1;
		data += loc.getY() + div1;
		data += loc.getZ() + div1;
		data += loc.getYaw() + div1;
		data += loc.getPitch() + div1;
		
		return data;
	}

	public static String serializePotionEffects(Collection<PotionEffect> effects)
	{
		String data = Constants.prefixEffects;
		
		for (PotionEffect effect : effects)
		{
			data += effect.getType().getId() + div2;
			data += effect.getAmplifier() + div2;
			data += effect.getDuration() + div1;
		}
		
		return data;
	}

}
