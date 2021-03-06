package com.deaboy.amber.util;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Projectile;
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
		data += world.getName() + div1;
		
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
		data += world.getDifficulty().getValue() + div1;
		data += world.getPVP() + div1;
		
		return data;
	}

	/**
	 * Returns a serialized version of a blockState object. World, x, y, z,
	 * type, damage.
	 * @param block The block state to serialize
	 * @return Serialized version of the block
	 */
	public static String serializeBlockState(BlockState block)
	{
		String data = Constants.prefixBlock;
		
		data += block.getTypeId() + div1;
		data += block.getData().getData() + div1;
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
		case DROPPER:		data += Constants.prefixInventory;
							data += serializeItemStack(((Dropper) block).getInventory().getContents());
							break;
		case HOPPER:		data += Constants.prefixInventory;
							data += serializeItemStack(((Hopper) block).getInventory().getContents());
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
		case WALL_SIGN:
		case SIGN_POST:		data += ((Sign) block).getLine(0) + div1;
							data += ((Sign) block).getLine(1) + div1;
							data += ((Sign) block).getLine(2) + div1;
							data += ((Sign) block).getLine(3) + div1;
							data += " " + div1; // This space prevents Java from ignoring any blank lines.
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
		data += entity.getFireTicks() + div1;			// 11
		data += entity.getFallDistance() + div1;		// 12
		
		if (entity.getType() == EntityType.DROPPED_ITEM)
		{
			data += Constants.prefixInventory;
			data += serializeItemStack(((Item) entity).getItemStack()); // 13 +
		}
		else if (entity instanceof LivingEntity)
		{
			data += ((LivingEntity) entity).getHealth() + div1; // 13
			data += ((LivingEntity) entity).getRemainingAir() + div1; // 14
			
			switch (entity.getType())
			{
			// HOSTILE
			case CREEPER:	data += ((Creeper) entity).isPowered() + div1; // 15
							break;
			case ENDERMAN:	data += ((Enderman) entity).getCarriedMaterial().getItemTypeId() + div1; // 15
							data += ((Enderman) entity).getCarriedMaterial().getData() + div1; // 16
							break;
			case SLIME:		data += ((Slime) entity).getSize() + div1; // 15
							break;
			// NETHER
			case MAGMA_CUBE:data += ((MagmaCube) entity).getSize() + div1; // 15
							break;
			// PASSIVE
			case PIG:		data += ((Pig) entity).getAge() + div1; // 15
							data += ((Pig) entity).canBreed() + div1; // 16
							data += ((Pig) entity).hasSaddle() + div1; // 17
							break;
			case COW:		data += ((Cow) entity).getAge() + div1; // 15
							data += ((Cow) entity).canBreed() + div1; // 16
							break;
			case MUSHROOM_COW:data += ((MushroomCow) entity).getAge() + div1; // 15
							data += ((MushroomCow) entity).canBreed() + div1; // 16
							break;
			case CHICKEN:	data += ((Chicken) entity).getAge() + div1; // 15
							data += ((Chicken) entity).canBreed() + div1; // 16
							break;
			case SHEEP:		data += ((Sheep) entity).getAge() + div1; // 15
							data += ((Sheep) entity).canBreed() + div1; // 16
							data += ((Sheep) entity).getColor().getDyeData() + div1; // 17
							break;
			case WOLF:		data += ((Wolf) entity).getAge() + div1; // 15
							data += ((Wolf) entity).canBreed() + div1; // 16
							data += (((Wolf) entity).getOwner() == null ? "null" : ((Wolf) entity).getOwner().getName()) + div1; // 17
							data += ((Wolf) entity).isSitting() + div1; // 18
							break;
			case OCELOT:	data += ((Ocelot) entity).getAge() + div1; // 15
							data += ((Ocelot) entity).canBreed() + div1; // 16
							data += ((Ocelot) entity).getCatType().getId() + div1; // 17
							data += (((Ocelot) entity).getOwner() == null ? "null" : ((Ocelot) entity).getOwner().getName()) + div1; // 18
							data += ((Ocelot) entity).isSitting() + div1; // 19
							break;
			case VILLAGER:	data += ((Villager) entity).getAge() + div1; // 15
							data += ((Villager) entity).canBreed() + div1; // 16
							data += ((Villager) entity).getProfession().getId() + div1; // 17
							break;
							
			default:		break;
			
			}
			
			data += serializePotionEffects(((LivingEntity) entity).getActivePotionEffects()) + div1; // ?
		}
		else if (entity instanceof Projectile)
		{
			data += ((Projectile) entity).doesBounce(); // 15
		}
		else
		{
			switch (entity.getType())
			{
			case PAINTING:	data += ((Painting) entity).getArt().getId() + div1; // 15
							data += ((Painting) entity).getAttachedFace().name() + div1; // 16
							break;
			case PRIMED_TNT:data += ((TNTPrimed) entity).getFuseTicks() + div1; // 15
							break;
			default:		break;
			}
		}
		
		return data;
	}


	public static String serializeItemStack(ItemStack ... stack)
	{
		String data = new String();
		
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
