package net.epoxide.elysian.world.chunk;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;

import java.util.List;
import java.util.Random;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.world.biome.BiomeGenElysian;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderElysian implements IChunkProvider {
	private Random rand;

	/** A NoiseGeneratorOctaves used in generating nether terrain, now in ours */
	private NoiseGeneratorOctaves elysianNoiseGen1;
	private NoiseGeneratorOctaves elysianNoiseGen2;
	private NoiseGeneratorOctaves elysianNoiseGen3;

	/** Determines whether topBlocks can be generated at a location */
	private NoiseGeneratorOctaves topBlockNoiseGen; //noise 4
	/** Determines whether something other than the fillerblock can be generated at a location */
	private NoiseGeneratorOctaves fillerBlockNoiseGen; //noise 5

	public NoiseGeneratorOctaves elysianNoiseGen6;
	public NoiseGeneratorOctaves elysianNoiseGen7;
	/** Is the world that the elysian is getting generated in. */
	private World worldObj;
	private double[] noiseField;
	/**
	 * Holds the noise used to determine whether something other than topBlocks can be generated at a location
	 */
	private double[] exclusivelyFillerNoise = new double[256];

	private MapGenBase caveGenerator = new MapGenCavesHell();
	/** Holds the noise used to determine whether topBlocks can be generated at a location */
	private double[] topBlockNoise = new double[256];
	/** Holds the noise used to determine whether fillerBlocks can be generated at a location */
	private double[] fillerNoise = new double[256];
	/**contains the noise data for elysianNoiseGen1*/
	double[] noiseData1;
	/**contains the noise data for elysianNoiseGen2*/
	double[] noiseData2;
	/**contains the noise data for elysianNoiseGen3*/
	double[] noiseData3;
	/**contains the noise data for elysianNoiseGen6*/
	double[] noiseData6;
	/**contains the noise data for elysianNoiseGen7*/
	double[] noiseData7;

	/** The biomes that are used to generate the chunk */
	private BiomeGenBase[] biomesForGeneration;

	private Object theBiomeDecorator;

	/**Block Base for fillers. will be replaced by biome's filler later*/
	private Block generalFiller = BlockHandler.dirt;
	/**Block Base for fluid. will be replaced by biome's fluid later (if implemented)*/
	private Block generalWater = Blocks.water;

	private static final String __OBFID = "CL_00000392";
	{
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, NETHER_CAVE);
	}

	public ChunkProviderElysian(World world, long seed) {

		this.worldObj = world;
		this.rand = new Random(seed);
		this.elysianNoiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.elysianNoiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.elysianNoiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.topBlockNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
		this.fillerBlockNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
		this.elysianNoiseGen6 = new NoiseGeneratorOctaves(this.rand, 10);
		this.elysianNoiseGen7 = new NoiseGeneratorOctaves(this.rand, 16);

		NoiseGenerator[] noiseGens = { elysianNoiseGen1, elysianNoiseGen2, elysianNoiseGen3, topBlockNoiseGen, fillerBlockNoiseGen, elysianNoiseGen6, elysianNoiseGen7 };
		noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.rand, noiseGens);
		this.elysianNoiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
		this.elysianNoiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
		this.elysianNoiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
		this.topBlockNoiseGen = (NoiseGeneratorOctaves) noiseGens[3];
		this.fillerBlockNoiseGen = (NoiseGeneratorOctaves) noiseGens[4];
		this.elysianNoiseGen6 = (NoiseGeneratorOctaves) noiseGens[5];
		this.elysianNoiseGen7 = (NoiseGeneratorOctaves) noiseGens[6];
	}

	/**
	 * initializes noisefields, calculates size of the noisefields with other noisefields, uses
	 * default blocks to start generation
	 * */
	public void prepareChunk (int posX, int posZ, Block[] chunkBlocks) {

		byte four = 4;
		byte waterLevel = 70;
		int five_a = four + 1;
		byte sevenTeen = 33;
		int five_b = four + 1;

		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(five_a + 16, five_b + 16);

		BiomeGenElysian biome = null;

		if (biomegenbase instanceof BiomeGenElysian)
			biome = (BiomeGenElysian) biomegenbase;

		if (biome == null) {
			System.out.println("skipped " + five_a + " " + five_b + " chunk because biome wasnt our elysian biome");
			return;
		}

		float t = biomegenbase.getFloatTemperature(five_a, 16, five_b);
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, posX * 4 - 2, posZ * 4 - 2, 10, 10);
		/**takes as argument empty noise aray, position, and size*/

		this.noiseField = this.initializeNoiseField(this.noiseField, posX * four, 0, posZ * four, five_a, sevenTeen, five_b);

		// 4*4 = 16 ! it's a chunk size
		for (int xSize = 0; xSize < four; ++xSize) {
			for (int zSize = 0; zSize < four; ++zSize) {
				for (int chunkSize = 0; chunkSize < 32; ++chunkSize) {

					double d = 0.125D;
					double sizeNoise1 = this.noiseField[((xSize + 0) * five_b + zSize + 0) * sevenTeen + chunkSize + 0];
					double sizeNoise2 = this.noiseField[((xSize + 0) * five_b + zSize + 1) * sevenTeen + chunkSize + 0];
					double sizeNoise3 = this.noiseField[((xSize + 1) * five_b + zSize + 0) * sevenTeen + chunkSize + 0];
					double sizeNoise4 = this.noiseField[((xSize + 1) * five_b + zSize + 1) * sevenTeen + chunkSize + 0];
					double sizeNoise5 = (this.noiseField[((xSize + 0) * five_b + zSize + 0) * sevenTeen + chunkSize + 1] - sizeNoise1) * d;
					double sizeNoise6 = (this.noiseField[((xSize + 0) * five_b + zSize + 1) * sevenTeen + chunkSize + 1] - sizeNoise2) * d;
					double sizeNoise7 = (this.noiseField[((xSize + 1) * five_b + zSize + 0) * sevenTeen + chunkSize + 1] - sizeNoise3) * d;
					double sizeNoise8 = (this.noiseField[((xSize + 1) * five_b + zSize + 1) * sevenTeen + chunkSize + 1] - sizeNoise4) * d;

					for (int loopNr = 0; loopNr < 8; ++loopNr) {

						double d9 = 0.25D;
						double sN1 = sizeNoise1;
						double sN2 = sizeNoise2;
						double compactNoiseDouble3n1 = (sizeNoise3 - sizeNoise1) * d9;
						double compactNoiseDouble4n2 = (sizeNoise4 - sizeNoise2) * d9;

						for (int xSize_bis = 0; xSize_bis < 4; ++xSize_bis) {

							int zSise_bis = xSize_bis + xSize * 4 << 12 | 0 + zSize * 4 << 8 | chunkSize * 8 + loopNr;
							short worldHeight = 256; //total world height
							double d14 = 0.25D;
							double sn1_bis = sN1;
							double d16 = (sN2 - sN1) * d14; // TODO name these !

							for (int i = 0; i < 4; ++i) {

								Block block = null;

								if (chunkSize * 8 + loopNr < waterLevel)
									block = generalWater; 

								if (sn1_bis > 0.0D)
									block = generalFiller; 

								chunkBlocks[zSise_bis] = block;
								zSise_bis += worldHeight;
								sn1_bis += d16;
							}

							sN1 += compactNoiseDouble3n1;
							sN2 += compactNoiseDouble4n2;
						}

						sizeNoise1 += sizeNoise5;
						sizeNoise2 += sizeNoise6;
						sizeNoise3 += sizeNoise7;
						sizeNoise4 += sizeNoise8;
					}
				}
			}
		}
	}

	/**
	 * replaces blocks for biomes they are in currently contains a check for blocks bellow
	 * water and above water
	 * */
	public void replaceBiomeBlocks (int posX, int posZ, Block[] chunkBlocks, byte[] meta, BiomeGenBase[] biomes) {

		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, posX, posZ, chunkBlocks, meta, biomes, this.worldObj);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return;

		byte waterLevel = 70;
		int totalWorldHeight = 256;

		double noiseScale = 0.03125D;
		this.topBlockNoise = this.topBlockNoiseGen.generateNoiseOctaves(this.topBlockNoise, posX * 16, posZ * 16, 0, 16, 16, 1, noiseScale, noiseScale, 1.0D);
		this.fillerNoise = this.topBlockNoiseGen.generateNoiseOctaves(this.fillerNoise, posX * 16, 109, posZ * 16, 16, 1, 16, noiseScale, 1.0D, noiseScale);
		this.exclusivelyFillerNoise = this.fillerBlockNoiseGen.generateNoiseOctaves(this.exclusivelyFillerNoise, posX * 16, posZ * 16, 0, 16, 16, 1, noiseScale * 2.0D, noiseScale * 2.0D, noiseScale * 2.0D);

		for (int chunkX = 0; chunkX < 16; ++chunkX) { // Z and X could be other way around ...
			for (int chunkZ = 0; chunkZ < 16; ++chunkZ) {

				BiomeGenBase biomegenbase = biomesForGeneration[chunkZ + chunkX * 16];

				BiomeGenElysian biome = null;

				if (biomegenbase instanceof BiomeGenElysian)
					biome = (BiomeGenElysian) biomegenbase;

				if (biome == null) {
					System.out.println("skipped " + chunkX + " " + chunkZ + " chunk because biome wasnt our elysian biome");
					return;
				}

				boolean canTopReplace = this.topBlockNoise[chunkX + chunkZ * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
				boolean canFillerReplace = this.fillerNoise[chunkX + chunkZ * 16] + this.rand.nextDouble() * 0.2D > 0.0D;

				int fillerExclusiveNoise = (int) (this.exclusivelyFillerNoise[chunkX + chunkZ * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
				int minusOne = -1;

				Block block = biomegenbase.topBlock; // above water
				Block block1 = biomegenbase.fillerBlock; // bellow water

				for (int height = totalWorldHeight-1; height >= 0; --height) {

					int chunkSize = (chunkZ * 16 + chunkX) * totalWorldHeight + height;

					if (height < totalWorldHeight && height > 0) {

						Block block2 = chunkBlocks[chunkSize];

						if (block2 != null && block2.getMaterial() != Material.air) {

							generateDifferentWaterSources();

							if (block2 == generalFiller) {
								if (minusOne == -1) {
									if (fillerExclusiveNoise <= 0) {
										block = null;
										block1 = biome.fillerBlock;
									}
									else if (height >= waterLevel - 4 && height <= waterLevel + 1) {
										block = biomegenbase.topBlock;
										block1 = biomegenbase.fillerBlock;

										if (canFillerReplace) {
											block = biomegenbase.topBlock;
											block1 = biomegenbase.fillerBlock;
										}

										if (canTopReplace) {
											block = biomegenbase.topBlock;
											block1 = biomegenbase.fillerBlock;
										}
									}

									minusOne = fillerExclusiveNoise;

									if (height >= waterLevel - 1)
										chunkBlocks[chunkSize] = block;
									else
										chunkBlocks[chunkSize] = block1;
								}
								else if (minusOne > 0) {
									--minusOne;
									chunkBlocks[chunkSize] = block1;
								}
								/**this is a fix for the topblock under the barrier block on the upper edge of the world*/
								if (height == totalWorldHeight-1) {
									int chunkSize2 = (chunkZ * 16 + chunkX) * (totalWorldHeight) + (height-1);
									chunkBlocks[chunkSize2] = biome.fillerBlock;
								}
							}
						}
						else
							minusOne = -1;
					}
					else if (height == totalWorldHeight || height == 0) {
						chunkBlocks[chunkSize] = biome.barrier;
					}
				}
			}
		}
	}

	@Deprecated
	/**allows for multi watersources, one per biome. suffers from extreme lag. has to be improved*/
	private void generateDifferentWaterSources () {

		// TODO : feature > uncheck this if we want multi fluids to be enabled
		// WARNING/!\ it lags a shit ton and doen't allow us to properly use the dimension
		// if(block2 == generalWater){
		// if (j1 == -1){
		// if(k1 <= waterLevel)
		// chunkBlocks[l1] = biome.fluid;
		// }
		// else if (j1 > 0){
		// --j1;
		// chunkBlocks[l1] = biome.fluid;
		// }
		// }
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	@Override
	public Chunk loadChunk (int posX, int posZ) {

		return this.provideChunk(posX, posZ);
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates
	 * all the blocks for the specified chunk from the map seed and chunk seed
	 */
	@Override
	public Chunk provideChunk (int chunkX, int chunkZ) {

		this.rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
		Block[] ablock = new Block[65536];
		byte[] meta = new byte[ablock.length];
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		this.prepareChunk(chunkX, chunkZ, ablock);
		this.replaceBiomeBlocks(chunkX, chunkZ, ablock, meta, this.biomesForGeneration);
		this.caveGenerator.func_151539_a(this, this.worldObj, chunkX, chunkZ, ablock);
		Chunk chunk = new Chunk(this.worldObj, ablock, meta, chunkX, chunkZ);
		byte[] abyte = chunk.getBiomeArray();

		for (int k = 0; k < abyte.length; ++k) {
			abyte[k] = (byte) biomesForGeneration[k].biomeID;
		}

		chunk.resetRelightChecks();
		return chunk;
	}

	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise
	 * array, the position, and the size.
	 */
	private double[] initializeNoiseField (double[] noiseArray, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ) {

		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, noiseArray, posX, posY, posZ, sizeX, sizeY, sizeZ);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.getResult() == Result.DENY)
			return event.noisefield;

		if (noiseArray == null)
			noiseArray = new double[sizeX * sizeY * sizeZ];

		double noiseScaleZX = 684.412D;
		double noiseScaleY = 2053.236D;

		this.noiseData1 = this.elysianNoiseGen3.generateNoiseOctaves(this.noiseData1, posX, posY, posZ, sizeX, sizeY, sizeZ, noiseScaleZX / 80.0D, noiseScaleY / 60.0D, noiseScaleZX / 80.0D);
		this.noiseData2 = this.elysianNoiseGen1.generateNoiseOctaves(this.noiseData2, posX, posY, posZ, sizeX, sizeY, sizeZ, noiseScaleZX, noiseScaleY, noiseScaleZX);
		this.noiseData3 = this.elysianNoiseGen2.generateNoiseOctaves(this.noiseData3, posX, posY, posZ, sizeX, sizeY, sizeZ, noiseScaleZX, noiseScaleY, noiseScaleZX);
		this.noiseData6 = this.elysianNoiseGen6.generateNoiseOctaves(this.noiseData6, posX, posY, posZ, sizeX, 1, sizeZ, 1.0D, 0.0D, 1.0D);
		this.noiseData7 = this.elysianNoiseGen7.generateNoiseOctaves(this.noiseData7, posX, posY, posZ, sizeX, 1, sizeZ, 100.0D, 0.0D, 100.0D);

		int noiseIndex_1_2_3 = 0;
		int noiseIndex_6_7 = 0;
		
		double[] chunkHeight = new double[sizeY];

		for (int indexHeight = 0; indexHeight < sizeY; ++indexHeight) {
			chunkHeight[indexHeight] = Math.cos(indexHeight * Math.PI * 6.0D / sizeY) * 2.0D;
			double index_D = indexHeight;

			if (indexHeight > sizeY / 2)
				index_D = sizeY - 1 - indexHeight;

			if (index_D < 4.0D) {
				index_D = 4.0D - index_D;
				chunkHeight[indexHeight] -= index_D * index_D * index_D * 10.0D;
			}
		}

		for (int indexHeight = 0; indexHeight < sizeX; ++indexHeight) {
			for (int z = 0; z < sizeZ; ++z) {
				
				double noiseData_6 = (this.noiseData6[noiseIndex_6_7] + 256.0D) / 1024.0D;

				if (noiseData_6 > 1.0D) 
					noiseData_6 = 1.0D;

				double d4 = 0.0D;
				double noiseData_7 = this.noiseData7[noiseIndex_6_7] / 8000.0D;

				if (noiseData_7 < 0.0D) {
					noiseData_7 = -noiseData_7;
				}

				noiseData_7 = noiseData_7 * 3.0D - 3.0D;

				if (noiseData_7 < 0.0D) {
					noiseData_7 /= 2.0D;

					if (noiseData_7 < -1.0D) {
						noiseData_7 = -1.0D;
					}

					noiseData_7 /= 1.4D;
					noiseData_7 /= 2.0D;
					noiseData_6 = 0.0D;
				}
				else {
					if (noiseData_7 > 1.0D) {
						noiseData_7 = 1.0D;
					}

					noiseData_7 /= 6.0D;
				}

				noiseData_6 += 0.5D;
				noiseData_7 = noiseData_7 * sizeY / 16.0D;
				++noiseIndex_6_7;

				for (int y = 0; y < sizeY; ++y) {
					double adouble = 0.0D;
					double chunkHeightIndex = chunkHeight[y];
					double noiseData_2 = this.noiseData2[noiseIndex_1_2_3] / 1024.0D; //from 512 to 1024. both
					double noiseData_3 = this.noiseData3[noiseIndex_1_2_3] / 1024.0D;
					double noiseData_1 = (this.noiseData1[noiseIndex_1_2_3] / 10.0D + 1.0D) / 2.0D;

					if (noiseData_1 < 0.0D) {
						adouble = noiseData_2;
					}
					else if (noiseData_1 > 1.0D) {
						adouble = noiseData_3;
					}
					else {
						adouble = noiseData_2 + (noiseData_3 - noiseData_2) * noiseData_1;
					}

					adouble -= chunkHeightIndex;
					double d11;

					if (y > sizeY - 4) {
						d11 = (y - (sizeY - 4)) / 3.0F;
						adouble = adouble * (1.0D - d11) + -10.0D * d11;
					}

					if (y < d4) {
						d11 = (d4 - y) / 4.0D;

						if (d11 < 0.0D) {
							d11 = 0.0D;
						}

						if (d11 > 1.0D) {
							d11 = 1.0D;
						}

						adouble = adouble * (1.0D - d11) + -10.0D * d11;
					}

					noiseArray[noiseIndex_1_2_3] = adouble;
					++noiseIndex_1_2_3;
				}
			}
		}

		return noiseArray;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	@Override
	public boolean chunkExists (int posX, int posZ) {

		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate (IChunkProvider provider, int posX, int posZ) {

		BlockFalling.fallInstantly = true;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, worldObj, rand, posX, posZ, false));

		int x = posX * 16;
		int z = posZ * 16;
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(x + 16, z + 16);

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, rand, x, z));

		//mineables here
		
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, rand, x, z));
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, worldObj, rand, posX, posZ, false));

		BlockFalling.fallInstantly = false;
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If passed false, save
	 * up to two chunks. Return true if all chunks have been saved.
	 */
	@Override
	public boolean saveChunks (boolean flag, IProgressUpdate progressUpdate) {

		return true;
	}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave, only during
	 * world unload. Currently unimplemented.
	 */
	@Override
	public void saveExtraData () {

	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every
	 * such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks () {

		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave () {

		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString () {

		return "SoulForestLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	@Override
	public List getPossibleCreatures (EnumCreatureType creatureType, int posX, int posY, int posZ) {

		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(posX, posZ);
		return biomegenbase == null ? null : biomegenbase.getSpawnableList(creatureType);
	}

	@Override
	public int getLoadedChunkCount () {

		return 0;
	}

	@Override
	public void recreateStructures (int chunkX, int chunkZ) {

	}

	@Override
	public ChunkPosition func_147416_a(World world, String someString, int x, int y, int z) {
		return null;
	}
}