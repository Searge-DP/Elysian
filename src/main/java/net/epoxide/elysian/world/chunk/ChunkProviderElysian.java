package net.epoxide.elysian.world.chunk;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;

import java.util.List;
import java.util.Random;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.world.biome.BiomeGenElysian;
import net.minecraft.block.Block;
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
import net.minecraft.world.gen.structure.MapGenNetherBridge;
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

		double d0 = 0.03125D;
		this.topBlockNoise = this.topBlockNoiseGen.generateNoiseOctaves(this.topBlockNoise, posX * 16, posZ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
		this.fillerNoise = this.topBlockNoiseGen.generateNoiseOctaves(this.fillerNoise, posX * 16, 109, posZ * 16, 16, 1, 16, d0, 1.0D, d0);
		this.exclusivelyFillerNoise = this.fillerBlockNoiseGen.generateNoiseOctaves(this.exclusivelyFillerNoise, posX * 16, posZ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

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

				boolean flag = this.topBlockNoise[chunkX + chunkZ * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
				boolean flag1 = this.fillerNoise[chunkX + chunkZ * 16] + this.rand.nextDouble() * 0.2D > 0.0D;

				int i1 = (int) (this.exclusivelyFillerNoise[chunkX + chunkZ * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
				int j1 = -1;

				Block block = biomegenbase.topBlock; // above water
				Block block1 = biomegenbase.fillerBlock; // bellow water

				for (int height = totalWorldHeight-1; height >= 0; --height) {

					int chunkSize = (chunkZ * 16 + chunkX) * totalWorldHeight + height;

					if (height < totalWorldHeight && height > 0) {

						Block block2 = chunkBlocks[chunkSize];

						if (block2 != null && block2.getMaterial() != Material.air) {

							generateDifferentWaterSources();

							if (block2 == generalFiller) {
								if (j1 == -1) {
									if (i1 <= 0) {
										block = null;
										block1 = biome.fillerBlock;
									}
									else if (height >= waterLevel - 4 && height <= waterLevel + 1) {
										block = biomegenbase.topBlock;
										block1 = biomegenbase.fillerBlock;

										if (flag1) {
											block = biomegenbase.topBlock;
											block1 = biomegenbase.fillerBlock;
										}

										if (flag) {
											block = biomegenbase.topBlock;
											block1 = biomegenbase.fillerBlock;
										}
									}

									j1 = i1;

									if (height >= waterLevel - 1)
										chunkBlocks[chunkSize] = block;
									else
										chunkBlocks[chunkSize] = block1;
								}
								else if (j1 > 0) {
									--j1;
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
							j1 = -1;
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
	public Chunk loadChunk (int p_73158_1_, int p_73158_2_) {

		return this.provideChunk(p_73158_1_, p_73158_2_);
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates
	 * all the blocks for the specified chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk (int chunkX, int chunkZ) {

		this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
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

		double d0 = 684.412D;
		double d1 = 2053.236D;

		this.noiseData1 = this.elysianNoiseGen3.generateNoiseOctaves(this.noiseData1, posX, posY, posZ, sizeX, sizeY, sizeZ, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
		this.noiseData2 = this.elysianNoiseGen1.generateNoiseOctaves(this.noiseData2, posX, posY, posZ, sizeX, sizeY, sizeZ, d0, d1, d0);
		this.noiseData3 = this.elysianNoiseGen2.generateNoiseOctaves(this.noiseData3, posX, posY, posZ, sizeX, sizeY, sizeZ, d0, d1, d0);
		this.noiseData6 = this.elysianNoiseGen6.generateNoiseOctaves(this.noiseData6, posX, posY, posZ, sizeX, 1, sizeZ, 1.0D, 0.0D, 1.0D);
		this.noiseData7 = this.elysianNoiseGen7.generateNoiseOctaves(this.noiseData7, posX, posY, posZ, sizeX, 1, sizeZ, 100.0D, 0.0D, 100.0D);

		int k1 = 0;
		int l1 = 0;
		double[] chunkHeight = new double[sizeY];
		int i; // just and index for the loop

		for (i = 0; i < sizeY; ++i) {
			chunkHeight[i] = Math.cos((double) i * Math.PI * 6.0D / (double) sizeY) * 2.0D;
			double d2 = (double) i;

			if (i > sizeY / 2)
				d2 = (double) (sizeY - 1 - i);

			if (d2 < 4.0D) {
				d2 = 4.0D - d2;
				chunkHeight[i] -= d2 * d2 * d2 * 10.0D;
			}
		}

		for (i = 0; i < sizeX; ++i) {
			for (int k2 = 0; k2 < sizeZ; ++k2) {
				double d3 = (this.noiseData6[l1] + 256.0D) / 1024.0D; //edited this from 128 / 512 to 512 / 1024

				if (d3 > 1.0D) {
					d3 = 1.0D;
				}

				double d4 = 0.0D;
				double d5 = this.noiseData7[l1] / 8000.0D;

				if (d5 < 0.0D) {
					d5 = -d5;
				}

				d5 = d5 * 3.0D - 3.0D;

				if (d5 < 0.0D) {
					d5 /= 2.0D;

					if (d5 < -1.0D) {
						d5 = -1.0D;
					}

					d5 /= 1.4D;
					d5 /= 2.0D;
					d3 = 0.0D;
				}
				else {
					if (d5 > 1.0D) {
						d5 = 1.0D;
					}

					d5 /= 6.0D;
				}

				d3 += 0.5D;
				d5 = d5 * (double) sizeY / 16.0D;
				++l1;

				for (int j2 = 0; j2 < sizeY; ++j2) {
					double d6 = 0.0D;
					double d7 = chunkHeight[j2];
					double d8 = this.noiseData2[k1] / 1024.0D; //from 512 to 1024. both
					double d9 = this.noiseData3[k1] / 1024.0D;
					double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

					if (d10 < 0.0D) {
						d6 = d8;
					}
					else if (d10 > 1.0D) {
						d6 = d9;
					}
					else {
						d6 = d8 + (d9 - d8) * d10;
					}

					d6 -= d7;
					double d11;

					if (j2 > sizeY - 4) {
						d11 = (double) ((float) (j2 - (sizeY - 4)) / 3.0F);
						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					if ((double) j2 < d4) {
						d11 = (d4 - (double) j2) / 4.0D;

						if (d11 < 0.0D) {
							d11 = 0.0D;
						}

						if (d11 > 1.0D) {
							d11 = 1.0D;
						}

						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					noiseArray[k1] = d6;
					++k1;
				}
			}
		}

		return noiseArray;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	public boolean chunkExists (int p_73149_1_, int p_73149_2_) {

		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	public void populate (IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {

		BlockSand.fallInstantly = true;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, false));

		int k = p_73153_2_ * 16;
		int l = p_73153_3_ * 16;
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
		int i1;
		int j1;
		int k1;
		int l1 = 0;
		int j2;

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, rand, k, l));

		i1 = this.rand.nextInt(this.rand.nextInt(10) + 1) + 1;
		int i2;

		// doGen = TerrainGen.decorate(worldObj, soulRNG, k, l, SHROOM);
		// Everything not biome related

		// #region Ore Gen
		// WorldGenMinable worldgenminable;

		// for (int i = 0; i < 5; i++){
		// int randPosX = k + rand.nextInt(16);
		// int randPosY = rand.nextInt(128);
		// int randPosZ = l + rand.nextInt(16);
		// (new WorldGenMinable(SoulBlocks.Bauxite.get(), 30,
		// SoulBlocks.Porphyry.get())).generate(worldObj, rand, randPosX, randPosY, randPosZ);
		// }
		//
		// for (int i = 0; i < 10; i++){
		// int randPosX = k + rand.nextInt(16);
		// int randPosY = rand.nextInt(128);
		// int randPosZ = l + rand.nextInt(16);
		// (new WorldGenMinable(SoulBlocks.Slate.get(), 50,
		// SoulBlocks.Porphyry.get())).generate(worldObj, rand, randPosX, randPosY, randPosZ);
		// }
		// for (int i = 0; i < 15; i++){
		// int randPosX = k + rand.nextInt(16);
		// int randPosY = rand.nextInt(128);
		// int randPosZ = l + rand.nextInt(16);
		// (new WorldGenMinable(SoulBlocks.DarkPorphyry.get(), 20,
		// SoulBlocks.Porphyry.get())).generate(worldObj, rand, randPosX, randPosY, randPosZ);
		// }

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, rand, k, l));
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, false));

		BlockSand.fallInstantly = false;
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If passed false, save
	 * up to two chunks. Return true if all chunks have been saved.
	 */
	public boolean saveChunks (boolean p_73151_1_, IProgressUpdate p_73151_2_) {

		return true;
	}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave, only during
	 * world unload. Currently unimplemented.
	 */
	public void saveExtraData () {

	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every
	 * such chunk.
	 */
	public boolean unloadQueuedChunks () {

		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	public boolean canSave () {

		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	public String makeString () {

		return "SoulForestLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	public List getPossibleCreatures (EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {

		BiomeGenBase biomegenbase = (BiomeGenBase) this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return biomegenbase == null ? null : biomegenbase.getSpawnableList(p_73155_1_);
	}

	public ChunkPosition func_147416_a (World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {

		return null;
	}

	public int getLoadedChunkCount () {

		return 0;
	}

	@Override
	public void recreateStructures (int p_82695_1_, int p_82695_2_) {

	}
}