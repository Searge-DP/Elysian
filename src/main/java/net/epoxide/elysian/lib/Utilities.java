package net.epoxide.elysian.lib;

import java.util.ArrayList;

import net.minecraft.world.biome.BiomeGenBase;

public class Utilities {

	private static ArrayList<Integer> foundBiomes = new ArrayList();

	/**
	 * Attempts to find a vacant biome ID. Does not guarantee that other mods
	 * won't use this same ID afterwards.
	 * 
	 * @return int: A biome id which is currently available.
	 */
	public static int getAvailableBiomeID() {

		for (int possibleID = 0; possibleID < BiomeGenBase.getBiomeGenArray().length; possibleID++)

			if (BiomeGenBase.getBiome(possibleID) == null
					&& !foundBiomes.contains(possibleID)) {

				foundBiomes.add(possibleID);
				return possibleID;
			}

		throw new OutOfBiomesException();
	}

	public static class OutOfBiomesException extends RuntimeException {

		/**
		 * A new exceptions which is thrown when all possible ids within the
		 * biomeList are occupied.
		 */
		public OutOfBiomesException() {

			super(
					"An attempt to find an available biome ID was made, however no IDs are available.");
		}
	}
}
