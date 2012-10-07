package com.deaboy.amber.util;

import org.bukkit.Material;

public class Util
{
	public static boolean isSolid(Material type)
	{
		switch (type)
		{
		//case BEACON:
		case BEDROCK:
		case BOOKSHELF:
		case BRICK:
		case BURNING_FURNACE:
		case CAULDRON:
		case CHEST:
		case COAL_ORE:
		//case COBBLESTONE_WALL:
		case COBBLESTONE:
		//case COMMAND_BLOCK:
		case DIAMOND_ORE:
		case DIAMOND_BLOCK:
		case DIRT:
		case DISPENSER:
		case DOUBLE_STEP:
		case EMERALD_BLOCK:
		case EMERALD_ORE:
		case ENCHANTMENT_TABLE:
		case ENDER_CHEST:
		case ENDER_PORTAL_FRAME:
		case ENDER_STONE:
		case FENCE:
		case FENCE_GATE:
		case FURNACE:
		case GLASS:
		case GLOWSTONE:
		case GLOWING_REDSTONE_ORE:
		case GOLD_BLOCK:
		case GOLD_ORE:
		case GRASS:
		case GRAVEL:
		case HUGE_MUSHROOM_1:
		case HUGE_MUSHROOM_2:
		case ICE:
		case IRON_BLOCK:
		case IRON_ORE:
		case JACK_O_LANTERN:
		case JUKEBOX:
		case LAPIS_BLOCK:
		case LAPIS_ORE:
		case LEAVES:
		case LOCKED_CHEST:
		case LOG:
		case MELON_BLOCK:
		case MOB_SPAWNER:
		case MONSTER_EGG:
		case MOSSY_COBBLESTONE:
		case MYCEL:
		case NETHER_BRICK:
		case NETHER_FENCE:
		case NETHERRACK:
		case NOTE_BLOCK:
		case OBSIDIAN:
		case PISTON_BASE:
		case PISTON_EXTENSION:
		case PISTON_STICKY_BASE:
		case PUMPKIN:
		case REDSTONE_LAMP_OFF:
		case REDSTONE_LAMP_ON:
		case REDSTONE_ORE:
		case SAND:
		case SANDSTONE:
		case SMOOTH_BRICK:
		case SPONGE:
		case STONE:
		case SOUL_SAND:
		case TNT:
		case WOOD:
		case WOOD_DOUBLE_STEP:
		case WOOL:
		case WORKBENCH:
		//case 
			return true;
		default:
			return false;
		}
	}
}
