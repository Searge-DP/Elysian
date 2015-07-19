package net.epoxide.elysian.world.gen;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;

import java.util.List;
import java.util.Random;

import net.epoxide.elysian.LogElysian;
import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.world.biome.BiomeGenElysian;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
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

public class ChunkProviderElysian implements IChunkProvider
{
	private Random rand;
	/** A NoiseGeneratorOctaves used in generating nether terrain */
	private NoiseGeneratorOctaves netherNoiseGen1;
	private NoiseGeneratorOctaves netherNoiseGen2;
	private NoiseGeneratorOctaves netherNoiseGen3;
	/** Determines whether slowsand or gravel can be generated at a location */
	private NoiseGeneratorOctaves lateriteGrassPorphyryNoise;
	/** Determines whether something other than nettherack can be generated at a location */
	private NoiseGeneratorOctaves porphyryExclusivityNoiseGen;
	public NoiseGeneratorOctaves netherNoiseGen6;
	public NoiseGeneratorOctaves netherNoiseGen7;
	/** Is the world that the nether is getting generated. */
	private World worldObj;
	private double[] noiseField;
	public MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
	/** Holds the noise used to determine whether slowsand can be generated at a location */
	private double[] topBlockNoise = new double[256];
	private double[] fillerNoise = new double[256];
	/** Holds the noise used to determine whether something other than netherrack can be generated at a location */
	private double[] exclusivelyFillerNoise = new double[256];
	//	private MapGenBase soulForestCaveGenerator = new WorldGenCavesSoulForest();
	private MapGenBase soulForestCaveGenerator = new MapGenCavesHell();
	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;

	/** The biomes that are used to generate the chunk */
	private BiomeGenBase[] biomesForGeneration;

	private Object theBiomeDecorator;

	private Block generalFiller = BlockHandler.dirt;
	private Block generalWater = BlockHandler.water;

	private static final String __OBFID = "CL_00000392";{
		genNetherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(genNetherBridge, NETHER_BRIDGE);
		soulForestCaveGenerator = TerrainGen.getModdedMapGen(soulForestCaveGenerator, NETHER_CAVE);
	}

	public ChunkProviderElysian(World p_i2005_1_, long p_i2005_2_){
		this.worldObj = p_i2005_1_;
		this.rand = new Random(p_i2005_2_);
		this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.lateriteGrassPorphyryNoise = new NoiseGeneratorOctaves(this.rand, 4);
		this.porphyryExclusivityNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
		this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.rand, 10);
		this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.rand, 16);

		NoiseGenerator[] noiseGens = {netherNoiseGen1, netherNoiseGen2, netherNoiseGen3, lateriteGrassPorphyryNoise, porphyryExclusivityNoiseGen, netherNoiseGen6, netherNoiseGen7};
		noiseGens = TerrainGen.getModdedNoiseGenerators(p_i2005_1_, this.rand, noiseGens);
		this.netherNoiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
		this.netherNoiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
		this.netherNoiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
		this.lateriteGrassPorphyryNoise = (NoiseGeneratorOctaves)noiseGens[3];
		this.porphyryExclusivityNoiseGen = (NoiseGeneratorOctaves)noiseGens[4];
		this.netherNoiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
		this.netherNoiseGen7 = (NoiseGeneratorOctaves)noiseGens[6];
	}

	/**initializes noisefields
	 * calculates size of the noisefields with other noisefields
	 * uses only biome found at spawn to place blocks
	 * */
	public void prepareChunk(int posX, int posZ, Block[] chunkBlocks){

		byte four = 4;
		byte waterLevel = 32;
		int five_a = four + 1;
		byte sevenTeen = 17;
		int five_b = four + 1;

		//these coordinates take only the biome from spawn !
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(five_a + 16, five_b + 16);

		//this should be better
		//but odly, this makes it (run hyper slowly)/crash and/or bug out
		//BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(posX,posZ);

		BiomeGenElysian biome = null;

		if(biomegenbase instanceof BiomeGenElysian)
			biome = (BiomeGenElysian)biomegenbase;

		if(biome == null){
			System.out.println("skipped " + five_a +" " + five_b + " chunk because biome wasnt our elysian biome");
			return;
		}

		LogElysian.info("preparing chunk ... at " + posX + " " + posZ);
		LogElysian.info("preparing chunk ... biome is " +biome.biomeName);
		LogElysian.info("preparing chunk ... biome at location is realy " + this.worldObj.getBiomeGenForCoords(posX, posZ).biomeName);

		float t = biomegenbase.getFloatTemperature(five_a, 16, five_b);
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, posX * 4 - 2, posZ * 4 - 2, five_a + 5, five_b + 5);

		this.noiseField = this.initializeNoiseField(this.noiseField, posX * four, 0, posZ * four, five_a, sevenTeen, five_b);//empty nosie array, position, size

		//4*4 = 16 ! it's a chunk size
		for (int xSize = 0; xSize < four; ++xSize){
			for (int zSize = 0; zSize < four; ++zSize){
				for (int chunkSize = 0; chunkSize < 16; ++chunkSize){

					double d = 0.125D;
					double sizeNoise1 = this.noiseField[((xSize + 0) * five_b + zSize + 0) * sevenTeen + chunkSize + 0];
					double sizeNoise2 = this.noiseField[((xSize + 0) * five_b + zSize + 1) * sevenTeen + chunkSize + 0];
					double sizeNoise3 = this.noiseField[((xSize + 1) * five_b + zSize + 0) * sevenTeen + chunkSize + 0];
					double sizeNoise4 = this.noiseField[((xSize + 1) * five_b + zSize + 1) * sevenTeen + chunkSize + 0];
					double sizeNoise5 = (this.noiseField[((xSize + 0) * five_b + zSize + 0) * sevenTeen + chunkSize + 1] - sizeNoise1) * d;
					double sizeNoise6 = (this.noiseField[((xSize + 0) * five_b + zSize + 1) * sevenTeen + chunkSize + 1] - sizeNoise2) * d;
					double sizeNoise7 = (this.noiseField[((xSize + 1) * five_b + zSize + 0) * sevenTeen + chunkSize + 1] - sizeNoise3) * d;
					double sizeNoise8 = (this.noiseField[((xSize + 1) * five_b + zSize + 1) * sevenTeen + chunkSize + 1] - sizeNoise4) * d;

					for (int l1 = 0; l1 < 8; ++l1){

						double d9 = 0.25D;
						double sN1 = sizeNoise1;
						double sN2 = sizeNoise2;
						double d12 = (sizeNoise3 - sizeNoise1) * d9; //TODO name these !
						double d13 = (sizeNoise4 - sizeNoise2) * d9; //TODO name these !

						for (int xSize_bis = 0; xSize_bis < 4; ++xSize_bis){
							int zSise_bis = xSize_bis + xSize * 4 << 11 | 0 + zSize * 4 << 7 | chunkSize * 8 + l1;
							short short1 = 128;
							double d14 = 0.25D;
							double sn1_bis = sN1;
							double d16 = (sN2 - sN1) * d14; //TODO name these !

							for (int i = 0; i < 4; ++i){

								Block block = null;

								//water and filler are the same everywhere
								//this is done in overworld generation too
								if (chunkSize * 8 + l1 < waterLevel)					
									block = generalWater; // fluid is the same everywhere
								if (sn1_bis > 0.0D)
									block = generalFiller; //filelr is the sam everywhere. 


								chunkBlocks[zSise_bis] = block;
								zSise_bis += short1;
								sn1_bis += d16;
							}

							sN1 += d12;
							sN2 += d13;
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

	/**replaces blocks for biomes they are in
	 * currently contains a check for blocks bellow water and above water
	 * */
	public void replaceBiomeBlocks(int posX, int posZ, Block[] chunkBlocks, byte[] meta, BiomeGenBase[] biomes)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, posX, posZ, chunkBlocks, meta, biomes, this.worldObj);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return;

		byte waterLevel = 32;
		double d0 = 0.03125D;
		this.topBlockNoise = this.lateriteGrassPorphyryNoise.generateNoiseOctaves(this.topBlockNoise, posX * 16, posZ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
		this.fillerNoise = this.lateriteGrassPorphyryNoise.generateNoiseOctaves(this.fillerNoise, posX * 16, 109, posZ * 16, 16, 1, 16, d0, 1.0D, d0);
		this.exclusivelyFillerNoise = this.porphyryExclusivityNoiseGen.generateNoiseOctaves(this.exclusivelyFillerNoise, posX * 16, posZ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

		for (int chunkX = 0; chunkX < 16; ++chunkX){ //Z and X could be other way around ...
			for (int chunkZ = 0; chunkZ < 16; ++chunkZ){

				BiomeGenBase biomegenbase = biomesForGeneration[chunkZ + chunkX * 16];

				BiomeGenElysian biome = null;

				if(biomegenbase instanceof BiomeGenElysian)
					biome = (BiomeGenElysian)biomegenbase;

				if(biome == null){
					System.out.println("skipped " + chunkX +" " + chunkZ + " chunk because biome wasnt our elysian biome");
					return;
				}

				boolean flag = this.topBlockNoise[chunkX + chunkZ * 16]  + this.rand.nextDouble() * 0.2D > 0.0D;
				boolean flag1 = this.fillerNoise[chunkX + chunkZ * 16] + this.rand.nextDouble() * 0.2D > 0.0D;

				int i1 = (int)(this.exclusivelyFillerNoise[chunkX + chunkZ * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
				int j1 = -1;

				Block block = biomegenbase.topBlock; //above water
				Block block1 = biomegenbase.fillerBlock; //bellow water

				//TODO change waterblock so multiple liquids can be supported

				for (int k1 = 127; k1 >= 0; --k1){
					int l1 = (chunkZ * 16 + chunkX) * 128 + k1;

					if (k1 < 127 - this.rand.nextInt(5) && k1 > 0 + this.rand.nextInt(5)){
						Block block2 = chunkBlocks[l1];

						if (block2 != null && block2.getMaterial() != Material.air){
							//uncheck this if we want multi fluids to be enabled
							//							if(block2 == generalWater){
							//								if (j1 == -1){
							//									if(k1 <= waterLevel)
							//										chunkBlocks[l1] = biome.fluid;
							//								}
							//								else if (j1 > 0){
							//									--j1;
							//									chunkBlocks[l1] = biome.fluid;
							//								}
							//							}

							if (block2 == generalFiller){
								if (j1 == -1){
									if (i1 <= 0){
										block = null;
										block1 = biome.fillerBlock;  
									}
									else if (k1 >= 0 - 4 && k1 <= waterLevel + 1){
										block = biomegenbase.topBlock;
										block1 = biomegenbase.fillerBlock;

										if (flag1){
											block = biomegenbase.topBlock;   
											block1 = biomegenbase.fillerBlock;
										}

										if (flag){
											block = biomegenbase.topBlock;
											block1 = biomegenbase.fillerBlock;
										}
									}

									j1 = i1;

									if (k1 >= waterLevel - 1)
										chunkBlocks[l1] = block; 
									else
										chunkBlocks[l1] = block1;
								}
								else if (j1 > 0)
								{
									--j1;
									chunkBlocks[l1] = block1;
								}
							}
						}
						else
							j1 = -1;
					}
					else
						chunkBlocks[l1] = biome.barrier;
				}
			}
		}
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		return this.provideChunk(p_73158_1_, p_73158_2_);
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
	{
		this.rand.setSeed((long)p_73154_1_ * 341873128712L + (long)p_73154_2_ * 132897987541L);
		Block[] ablock = new Block[32768];
		byte[] meta = new byte[ablock.length];
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16); //Forge Move up to allow for passing to replaceBiomeBlocks
		this.prepareChunk(p_73154_1_, p_73154_2_, ablock);
		this.replaceBiomeBlocks(p_73154_1_, p_73154_2_, ablock, meta, this.biomesForGeneration);
		this.soulForestCaveGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, ablock);
		//this.genNetherBridge.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, ablock);
		Chunk chunk = new Chunk(this.worldObj, ablock, meta, p_73154_1_, p_73154_2_);
		byte[] abyte = chunk.getBiomeArray();

		for (int k = 0; k < abyte.length; ++k){
			abyte[k] = (byte)biomesForGeneration[k].biomeID;
		}

		chunk.resetRelightChecks();
		return chunk;
	}

	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseField(double[] p_73164_1_, int p_73164_2_, int p_73164_3_, int p_73164_4_, int p_73164_5_, int p_73164_6_, int p_73164_7_)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, p_73164_1_, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return event.noisefield;

		if (p_73164_1_ == null)
		{
			p_73164_1_ = new double[p_73164_5_ * p_73164_6_ * p_73164_7_];
		}

		double d0 = 684.412D;
		double d1 = 2053.236D;
		this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 1.0D, 0.0D, 1.0D);
		this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 100.0D, 0.0D, 100.0D);
		this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
		this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
		this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;
		double[] adouble1 = new double[p_73164_6_];
		int i2;

		for (i2 = 0; i2 < p_73164_6_; ++i2)
		{
			adouble1[i2] = Math.cos((double)i2 * Math.PI * 6.0D / (double)p_73164_6_) * 2.0D;
			double d2 = (double)i2;

			if (i2 > p_73164_6_ / 2)
			{
				d2 = (double)(p_73164_6_ - 1 - i2);
			}

			if (d2 < 4.0D)
			{
				d2 = 4.0D - d2;
				adouble1[i2] -= d2 * d2 * d2 * 10.0D;
			}
		}

		for (i2 = 0; i2 < p_73164_5_; ++i2)
		{
			for (int k2 = 0; k2 < p_73164_7_; ++k2)
			{
				double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;

				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}

				double d4 = 0.0D;
				double d5 = this.noiseData5[l1] / 8000.0D;

				if (d5 < 0.0D)
				{
					d5 = -d5;
				}

				d5 = d5 * 3.0D - 3.0D;

				if (d5 < 0.0D)
				{
					d5 /= 2.0D;

					if (d5 < -1.0D)
					{
						d5 = -1.0D;
					}

					d5 /= 1.4D;
					d5 /= 2.0D;
					d3 = 0.0D;
				}
				else
				{
					if (d5 > 1.0D)
					{
						d5 = 1.0D;
					}

					d5 /= 6.0D;
				}

				d3 += 0.5D;
				d5 = d5 * (double)p_73164_6_ / 16.0D;
				++l1;

				for (int j2 = 0; j2 < p_73164_6_; ++j2)
				{
					double d6 = 0.0D;
					double d7 = adouble1[j2];
					double d8 = this.noiseData2[k1] / 512.0D;
					double d9 = this.noiseData3[k1] / 512.0D;
					double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

					if (d10 < 0.0D)
					{
						d6 = d8;
					}
					else if (d10 > 1.0D)
					{
						d6 = d9;
					}
					else
					{
						d6 = d8 + (d9 - d8) * d10;
					}

					d6 -= d7;
					double d11;

					if (j2 > p_73164_6_ - 4)
					{
						d11 = (double)((float)(j2 - (p_73164_6_ - 4)) / 3.0F);
						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					if ((double)j2 < d4)
					{
						d11 = (d4 - (double)j2) / 4.0D;

						if (d11 < 0.0D)
						{
							d11 = 0.0D;
						}

						if (d11 > 1.0D)
						{
							d11 = 1.0D;
						}

						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					p_73164_1_[k1] = d6;
					++k1;
				}
			}
		}

		return p_73164_1_;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		BlockSand.fallInstantly = true;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, false));

		int k = p_73153_2_ * 16;
		int l = p_73153_3_ * 16;
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
		//this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.soulRNG, par2, par3);
		int i1;
		int j1;
		int k1;
		int l1 = 0;
		int j2;

		biomegenbase.decorate(this.worldObj, this.rand, k, l);
		//boolean doGen = TerrainGen.populate(p_73153_1_, worldObj, soulRNG, p_73153_2_, p_73153_3_, false, NETHER_LAVA);

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, rand, k, l));

		i1 = this.rand.nextInt(this.rand.nextInt(10) + 1) + 1;
		int i2;

		//doGen = TerrainGen.decorate(worldObj, soulRNG, k, l, SHROOM);
		// Everything not biome related       

		// #region Ore Gen
		//WorldGenMinable worldgenminable;

		//		for (int i = 0; i < 5; i++){
		//			int randPosX = k + rand.nextInt(16);
		//			int randPosY = rand.nextInt(128);
		//			int randPosZ = l + rand.nextInt(16);
		//			(new WorldGenMinable(SoulBlocks.Bauxite.get(), 30, SoulBlocks.Porphyry.get())).generate(worldObj, rand, randPosX, randPosY, randPosZ);
		//		}	
		//
		//		for (int i = 0; i < 10; i++){
		//			int randPosX = k + rand.nextInt(16);
		//			int randPosY = rand.nextInt(128);
		//			int randPosZ = l + rand.nextInt(16);
		//			(new WorldGenMinable(SoulBlocks.Slate.get(), 50, SoulBlocks.Porphyry.get())).generate(worldObj, rand, randPosX, randPosY, randPosZ);
		//		}	
		//		for (int i = 0; i < 15; i++){
		//			int randPosX = k + rand.nextInt(16);
		//			int randPosY = rand.nextInt(128);
		//			int randPosZ = l + rand.nextInt(16);
		//			(new WorldGenMinable(SoulBlocks.DarkPorphyry.get(), 20, SoulBlocks.Porphyry.get())).generate(worldObj, rand, randPosX, randPosY, randPosZ);
		//		}	

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, rand, k, l));
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, false));

		BlockSand.fallInstantly = false;
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
	 * Return true if all chunks have been saved.
	 */
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_){
		return true;
	}

	/**
	 * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
	 * unimplemented.
	 */
	public void saveExtraData() {}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
	 */
	public boolean unloadQueuedChunks(){
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	public boolean canSave(){
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	public String makeString(){
		return "SoulForestLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_){
		BiomeGenBase biomegenbase = (BiomeGenBase) this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return biomegenbase == null ? null : biomegenbase.getSpawnableList(p_73155_1_);
	}

	public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_){
		return null;
	}

	public int getLoadedChunkCount(){
		return 0;
	}

	@Override
	public void recreateStructures(int p_82695_1_, int p_82695_2_) {
	}
}