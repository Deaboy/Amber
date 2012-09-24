package com.deaboy.amber.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class Util
{
	public static boolean isSolid(Material type) {
		List<Material> types = new ArrayList<Material>();
				types.add(Material.STONE);
				types.add(Material.GRASS);
				types.add(Material.DIRT);
				types.add(Material.COBBLESTONE);
				types.add(Material.WOOD);
				types.add(Material.BEDROCK);
				types.add(Material.SAND);
				types.add(Material.GOLD_ORE);
				types.add(Material.IRON_ORE);
				types.add(Material.COAL_ORE);
				types.add(Material.LOG);
				types.add(Material.LEAVES);
				types.add(Material.SPONGE);
				types.add(Material.GLASS);
				types.add(Material.LAPIS_ORE);
				types.add(Material.LAPIS_BLOCK);
				types.add(Material.DISPENSER);
				types.add(Material.SANDSTONE);
				types.add(Material.NOTE_BLOCK);
				types.add(Material.PISTON_STICKY_BASE);
				types.add(Material.PISTON_BASE);
				types.add(Material.PISTON_EXTENSION);
				types.add(Material.WOOL);
				types.add(Material.GOLD_BLOCK);
				types.add(Material.IRON_BLOCK);
				types.add(Material.DOUBLE_STEP);
				types.add(Material.BRICK);
				types.add(Material.TNT);
				types.add(Material.BOOKSHELF);
				types.add(Material.MOSSY_COBBLESTONE);
				types.add(Material.OBSIDIAN);
				types.add(Material.MOB_SPAWNER);
				types.add(Material.CHEST);
				types.add(Material.DIAMOND_ORE);
				types.add(Material.DIAMOND_BLOCK);
				types.add(Material.WORKBENCH);
				types.add(Material.FURNACE);
				types.add(Material.BURNING_FURNACE);
				types.add(Material.REDSTONE_ORE);
				types.add(Material.GLOWING_REDSTONE_ORE);
				types.add(Material.ICE);
				types.add(Material.JUKEBOX);
				types.add(Material.FENCE);
				types.add(Material.PUMPKIN);
				types.add(Material.NETHERRACK);
				types.add(Material.SOUL_SAND);
				types.add(Material.GLOWSTONE);
				types.add(Material.JACK_O_LANTERN);
				types.add(Material.LOCKED_CHEST);
				types.add(Material.MONSTER_EGG);
				types.add(Material.SMOOTH_BRICK);
				types.add(Material.HUGE_MUSHROOM_1);
				types.add(Material.HUGE_MUSHROOM_2);
				types.add(Material.MELON);
				types.add(Material.FENCE_GATE);
				types.add(Material.MYCEL);
				types.add(Material.NETHER_BRICK);
				types.add(Material.NETHER_FENCE);
				types.add(Material.ENCHANTMENT_TABLE);
				types.add(Material.CAULDRON);
				types.add(Material.ENDER_PORTAL_FRAME);
				types.add(Material.ENDER_STONE);
				types.add(Material.REDSTONE_LAMP_OFF);
				types.add(Material.REDSTONE_LAMP_ON);
				types.add(Material.WOOD_DOUBLE_STEP);
				types.add(Material.EMERALD_ORE);
				types.add(Material.ENDER_CHEST);
				types.add(Material.EMERALD_BLOCK);
				//types.add(Material.COMMAND_BLOCK);
				//types.add(Material.BEACON);
				//types.add(Material.COBBLESTONE_WALL);
				//types.add(Material.);
		return (types.contains(type));
	}
}
