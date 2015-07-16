package net.epoxide.elysian.world.gen;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;

import java.util.List;
import java.util.Random;

import net.epoxide.elysian.world.biome.BiomeGenElysian;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
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
	private World worldObj;
	private MapGenBase elysianCaveGenerator = new MapGenCaveElysian(); //update : this has nothing to do with the noise that generates the terrain ... (derp me)
	private BiomeGenBase[] biomesForGeneration;

	private double[] noiseField;

	// TODO map noises
	private NoiseGeneratorOctaves noiseA;
	private NoiseGeneratorOctaves noiseB;
	private NoiseGeneratorOctaves noiseC;
	public NoiseGeneratorOctaves noiseD;
	public NoiseGeneratorOctaves noiseE;

	double[] noiseDataA;
	double[] noiseDataB;
	double[] noiseDataC;
	double[] noiseDataD;
	double[] noiseDataE;

	{
		elysianCaveGenerator = TerrainGen.getModdedMapGen(elysianCaveGenerator, NETHER_CAVE);
	}

	public ChunkProviderElysian(World world, long seed) {

		this.worldObj = world;
		this.rand = new Random(seed);
		this.noiseA = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseB = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseC = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseD = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseE = new NoiseGeneratorOctaves(this.rand, 16);

		NoiseGenerator[] noiseGens = { noiseA, noiseB, noiseC, noiseD, noiseE };
		noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.rand, noiseGens);

		this.noiseA = (NoiseGeneratorOctaves) noiseGens[0];
		this.noiseB = (NoiseGeneratorOctaves) noiseGens[1];
		this.noiseC = (NoiseGeneratorOctaves) noiseGens[2];
		this.noiseD = (NoiseGeneratorOctaves) noiseGens[3];
		this.noiseE = (NoiseGeneratorOctaves) noiseGens[4];
	}

	public void prepareChunk (int chunkX, int chunkZ, Block[] chunkBlocks) {

		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);

		byte b0 = 4;
		byte b1 = 32;
		int k = b0 + 1;
		byte b2 = 17;
		int l = b0 + 1;
		this.noiseField = this.initializeNoiseField(this.noiseField, chunkX * b0, 0, chunkZ * b0, k, b2, l);

		//TODO make a proper check for our biome
		BiomeGenElysian biome = (BiomeGenElysian)this.worldObj.getBiomeGenForCoords(chunkX, chunkZ);

		for (int i1 = 0; i1 < b0; ++i1) {
			for (int j1 = 0; j1 < b0; ++j1) {
				for (int k1 = 0; k1 < 16; ++k1) {
					double d0 = 0.125D;
					double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
					double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
					double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
					double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
					double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
					double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
					double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
					double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;
					for (int l1 = 0; l1 < 8; ++l1) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i2 = 0; i2 < 4; ++i2) {
							int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
							short short1 = 128;
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int k2 = 0; k2 < 4; ++k2) {
								Block block = null;

								if (k1 * 8 + l1 < b1) { // b1 == 32
									block = biome.fluid;
								}

								if (d15 > 0.0D) {
									block = biome.fillerBlock; 
								}

								chunkBlocks[j2] = block;

								j2 += short1;
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;							
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	// TODO map variables
	public void replaceBiomeBlocks (int chunkX, int chunkZ, Block[] chunkBlocks, byte[] chunkMetas, BiomeGenBase[] possibleBiomes) {

		//TODO topblock !
		//hint : topblocks get done in cave generation !
		//we should modify our hell generator !
		//or implement digBlock method from the mapGenCaves class

		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, chunkBlocks, chunkMetas, possibleBiomes, this.worldObj);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return;

		byte waterLevel = 32;
		double d0 = 0.03125D;

		//TODO make a proper check for our biome
		BiomeGenElysian biome = (BiomeGenElysian)this.worldObj.getBiomeGenForCoords(chunkX, chunkZ);

		for (int y = 0; y < 16; ++y) {
			for (int x = 0; x < 16; ++x) {
				int minusOne = -1;
				Block block = biome.fillerBlock;// TODO custom block // this block is above sea level
				Block block1 = biome.fillerBlock;// TODO custom block // this block is under sea level

				for (int posY = 127; posY >= 0; --posY) { //top down to ground level
					int blockPos = (x * 16 + y) * 128 + posY;

					if (posY < 127 - this.rand.nextInt(5) && posY > 0 + this.rand.nextInt(5)) {
						Block block2 = chunkBlocks[blockPos];
						if (block2 != null && block2.getMaterial() != Material.air) { //if not air
							if (block2 == biome.fillerBlock) 
							{
								if (minusOne == -1){
									if (posY >= waterLevel - 4 && posY <= waterLevel + 1) {
										block = biome.fillerBlock;
										block1 = biome.fillerBlock;
									}

									if (posY < waterLevel && (block == null || block.getMaterial() == Material.air)) {
										block = biome.fluid; 
									}

									if (posY >= waterLevel - 1) {
										chunkBlocks[blockPos] = block;
									}
									else {
										chunkBlocks[blockPos] = block1;
									}
								}
								else if (minusOne > 0) {
									--minusOne;
									chunkBlocks[blockPos] = block1;
								}
							}else if(chunkBlocks[blockPos].getMaterial() == Material.air || chunkBlocks[blockPos] == null){
								if(chunkBlocks[blockPos -1] != null && chunkBlocks[blockPos -1] == biome.fillerBlock)
									chunkBlocks[blockPos-1] = biome.topBlock;
							}
						}
						else {
							minusOne = -1;
						}
					}
					else {
						chunkBlocks[blockPos] = biome.barrier;
					}
				}
			}
		}
	}

	@Override
	public Chunk loadChunk (int posX, int posZ) {

		return this.provideChunk(posX, posZ);
	}

	@Override
	public Chunk provideChunk (int posX, int posZ) {

		this.rand.setSeed((long) posX * 341873128712L + (long) posZ * 132897987541L);
		Block[] chunkBlocks = new Block[32768];
		byte[] chunkMetas = new byte[chunkBlocks.length];
		this.prepareChunk(posX, posZ, chunkBlocks);
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, posX * 16, posZ * 16, 16, 16);
		this.replaceBiomeBlocks(posX, posZ, chunkBlocks, chunkMetas, this.biomesForGeneration);
		this.elysianCaveGenerator.func_151539_a(this, this.worldObj, posX, posZ, chunkBlocks);
		Chunk chunk = new Chunk(this.worldObj, chunkBlocks, chunkMetas, posX, posZ);
		byte[] chunkBiomes = chunk.getBiomeArray();

		for (int currentBiomePos = 0; currentBiomePos < chunkBiomes.length; ++currentBiomePos)
			chunkBiomes[currentBiomePos] = (byte) this.biomesForGeneration[currentBiomePos].biomeID;

		chunk.resetRelightChecks();
		return chunk;
	}

	// TODO Add an appropriate Javadoc
	private double[] initializeNoiseField (double[] noiseField, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ) {

		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, noiseField, posX, posY, posZ, sizeX, sizeY, sizeZ);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return event.noisefield;

		if (noiseField == null)
			noiseField = new double[sizeX * sizeY * sizeZ];

		double d0 = 684.412D;
		double d1 = 2053.236D;
		this.noiseDataD = this.noiseD.generateNoiseOctaves(this.noiseDataD, posX, posY, posZ, sizeX, 1, sizeZ, 1.0D, 0.0D, 1.0D);
		this.noiseDataE = this.noiseE.generateNoiseOctaves(this.noiseDataE, posX, posY, posZ, sizeX, 1, sizeZ, 100.0D, 0.0D, 100.0D);
		this.noiseDataA = this.noiseC.generateNoiseOctaves(this.noiseDataA, posX, posY, posZ, sizeX, sizeY, sizeZ, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
		this.noiseDataB = this.noiseA.generateNoiseOctaves(this.noiseDataB, posX, posY, posZ, sizeX, sizeY, sizeZ, d0, d1, d0);
		this.noiseDataC = this.noiseB.generateNoiseOctaves(this.noiseDataC, posX, posY, posZ, sizeX, sizeY, sizeZ, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;
		double[] adouble1 = new double[sizeY];

		int i2;

		for (i2 = 0; i2 < sizeY; ++i2) {
			adouble1[i2] = Math.cos((double) i2 * Math.PI * 6.0D / (double) sizeY) * 2.0D;
			double d2 = (double) i2;

			if (i2 > sizeY / 2) {
				d2 = (double) (sizeY - 1 - i2);
			}

			if (d2 < 4.0D) {
				d2 = 4.0D - d2;
				adouble1[i2] -= d2 * d2 * d2 * 10.0D;
			}
		}

		for (i2 = 0; i2 < sizeX; ++i2) {
			for (int k2 = 0; k2 < sizeZ; ++k2) {
				double d3 = (this.noiseDataD[l1] + 256.0D) / 512.0D;

				if (d3 > 1.0D) {
					d3 = 1.0D;
				}

				double d4 = 0.0D;
				double d5 = this.noiseDataE[l1] / 8000.0D;

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
					double d7 = adouble1[j2];
					double d8 = this.noiseDataB[k1] / 512.0D;
					double d9 = this.noiseDataC[k1] / 512.0D;
					double d10 = (this.noiseDataA[k1] / 10.0D + 1.0D) / 2.0D;

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

					noiseField[k1] = d6;
					++k1;
				}
			}
		}

		return noiseField;
	}

	@Override
	public boolean chunkExists (int posX, int posY) {

		return true;
	}

	@Override
	public void populate (IChunkProvider chunkProvider, int posX, int posY) {

		BlockFalling.fallInstantly = true;

		int chunkX = posX * 16;
		int chunkY = posY * 16;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, posX, posY, false));
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, rand, chunkX, chunkY));
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, rand, chunkX, chunkY));
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, posX, posY, false));

		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean saveChunks (boolean saved, IProgressUpdate progress) {

		return true;
	}

	@Override
	public void saveExtraData () {

	}

	@Override
	public boolean unloadQueuedChunks () {

		return false;
	}

	@Override
	public boolean canSave () {

		return true;
	}

	@Override
	public String makeString () {

		return "ELysianRandomLevelSource";
	}

	@Override
	public List getPossibleCreatures (EnumCreatureType creatureType, int posX, int posY, int posZ) {

		return this.worldObj.getBiomeGenForCoords(posX, posZ).getSpawnableList(creatureType);
	}

	@Override
	public ChunkPosition func_147416_a (World world, String structureName, int posX, int posY, int posZ) {

		return null;
	}

	@Override
	public int getLoadedChunkCount () {

		return 0;
	}

	@Override
	public void recreateStructures (int posX, int posY) {

	}

	//Determine if the block at the specified location is the top block for the biome, we take into account
	//Vanilla bugs to make sure that we generate the map the same way vanilla does.
	private boolean isTopBlock(Block data, int x, int y, int z, int chunkX, int chunkZ)
	{
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		return data == biome.topBlock;
	}

	/**
	 * Digs out the current block, default implementation removes stone, filler, and top block
	 * Sets the block to lava if y is less then 10, and air other wise.
	 * If setting to air, it also checks to see if we've broken the surface and if so 
	 * tries to make the floor the biome's top block
	 * 
	 * @param data Block data array
	 * @param index Pre-calculated index into block data
	 * @param x local X position
	 * @param y local Y position
	 * @param z local Z position
	 * @param chunkX Chunk X position
	 * @param chunkZ Chunk Y position
	 * @param foundTop True if we've encountered the biome's top block. Ideally if we've broken the surface.
	 */
	protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
	{
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		Block top    = biome.topBlock;
		Block filler = biome.fillerBlock;
		Block block  = data[index];

		if (block == Blocks.stone || block == filler || block == top)
		{
			data[index] = null;

			if (foundTop && data[index - 1] == filler)
			{
				data[index - 1] = top;
			}
		}
	}
}