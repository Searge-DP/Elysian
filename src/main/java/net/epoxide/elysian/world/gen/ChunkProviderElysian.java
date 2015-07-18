package net.epoxide.elysian.world.gen;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;

import java.util.List;
import java.util.Random;

import net.epoxide.elysian.world.biome.BiomeGenElysian;
import net.epoxide.elysian.world.biome.BiomeHandler;
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
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderElysian implements IChunkProvider
{
	//NETHER SOUL FOREST IDEA	
	
	private Random rand;

    /** A NoiseGeneratorOctaves used in generating nether terrain */
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    public NoiseGeneratorOctaves mobSpawnerNoise;

    private NoiseGeneratorOctaves topBlockNoiceForDifferentBiomes;
    private NoiseGeneratorOctaves fillerBlockNoiceForDifferentBiomes;
    
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;

    /** The biomes that are used to generate the chunk */
    private BiomeGenBase[] biomesForGeneration;
   
    /** Is the world that the nether is getting generated. */
    private World worldObj;
    private double[] noiseField;

    /**
     * Holds the noise used to determine whether lateriteGrass can be generated at a location
     */
    private double[] lateriteGrassNoise = new double[256];
    private double[] porphyryNoise = new double[256];

    /**
     * Holds the noise used to determine whether something other than porphyry can be generated at a location
     */
    private double[] porphyryExclusivityNoise = new double[256];
    private MapGenBase netherCaveGenerator = new MapGenCavesHell();
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

	private Object theBiomeDecorator;

    public ChunkProviderElysian(World par1World, long par2)
    {
        this.worldObj = par1World;
        this.rand = new Random(par2);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.topBlockNoiceForDifferentBiomes = new NoiseGeneratorOctaves(this.rand, 4);
        this.fillerBlockNoiceForDifferentBiomes = new NoiseGeneratorOctaves(this.rand, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.rand, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);

        NoiseGenerator[] noiseGens = {netherNoiseGen1, netherNoiseGen2, netherNoiseGen3, topBlockNoiceForDifferentBiomes, fillerBlockNoiceForDifferentBiomes, netherNoiseGen6, netherNoiseGen7, mobSpawnerNoise};
        noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.rand, noiseGens);
        this.netherNoiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
        this.netherNoiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
        this.netherNoiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
        this.topBlockNoiceForDifferentBiomes = (NoiseGeneratorOctaves) noiseGens[3];
        this.fillerBlockNoiceForDifferentBiomes = (NoiseGeneratorOctaves) noiseGens[4];
        this.netherNoiseGen6 = (NoiseGeneratorOctaves) noiseGens[5];
        this.netherNoiseGen7 = (NoiseGeneratorOctaves) noiseGens[6];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves) noiseGens[7];
    }

   
    /**
     * Generates the shape of the terrain in the nether.
     */
    public void generateNetherTerrain(int posX, int posZ, Block[] abyte)
    {

        byte b0 = 4;
        byte b1 = 32;
        int k = b0 + 1;
        byte b2 = 17;
        int l = b0 + 1;
        
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16); //TODO marked here for noticing the +16
        
//        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(posX, posZ);
        
        BiomeGenElysian biome = null;
        
        if(biomegenbase instanceof BiomeGenElysian)
        	biome = (BiomeGenElysian)biomegenbase;
        
        if(biome == null){
        	System.out.println("skipped " + k +" " + l + " chunk because biome wasnt our elysian biome");
        	return;
        }
        
        float t = biomegenbase.getFloatTemperature(k, 16, l);
     
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, posX * 4 - 2, posZ * 4 - 2, k + 5, l + 5);

        this.noiseField = this.initializeNoiseField(this.noiseField, posX * b0, 0, posZ * b0, k, b2, l);

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < 16; ++k1)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                    double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                    double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                    double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                    double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                        	//int j2 = i2 + i1 * 4 << 12 | 0 + j1 * 4 << 8 | k1 * 8 + l1;
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            //short short1 = 256;
                            //j2 -= short1;
                            short short1 = 128;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            //ouble d15 = d10 - d16;   

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                Block l2 = null;

                                if (k1 * 8 + l1 < b1)
                                {
                                	l2 = biome.fluid;;
                                }
                                //if ! not else if !!
                                if (d15 > 0.0D)
                                {
                                	l2 = biome.fillerBlock;
                                }

                                abyte[j2] = l2;
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

    /**
     * name based on ChunkProviderGenerate
     */
    public void replaceBlocksForBiome(int chunkX, int chunkZ, Block[] chunkBlocks, BiomeGenBase[] possibleBiomes)
    {

        BiomeGenBase base = this.worldObj.getBiomeGenForCoords(chunkX, chunkZ);
        
//        for (BiomeGenBase b : possibleBiomes)
//            System.out.println(b.biomeName);
        
//        System.out.println("1 " + base.biomeName);
        
        BiomeGenElysian biome;
        
        if (!(base instanceof BiomeGenElysian))
            return;
        
        biome = (BiomeGenElysian) base; // TODO biome only reflects one biome, instead of thsoe
                                        // we have, altough the world detects both biomes
        
//        System.out.println(2 + biome.biomeName);
//        System.out.println(biome.fillerBlock);
//        System.out.println(biome.topBlock);
        
        byte waterLevel = 32;
        double d0 = 0.03125D;
        
        for (int y = 0; y < 16; ++y) {
            for (int x = 0; x < 16; ++x) {
                int minusOne = -1;
                Block block = biome.fillerBlock; // this block is above sea level
                Block block1 = biome.fillerBlock; // this block is under sea level
                
                for (int posY = 127; posY >= 0; --posY) { // top down to ground level
                    int blockPos = (x * 16 + y) * 128 + posY;
                    
                    if (posY < 127 - this.rand.nextInt(5) && posY > 0 + this.rand.nextInt(5)) { // all
                                                                                                // blocks
                                                                                                // between
                                                                                                // heightlimit
                                                                                                // and
                                                                                                // bottomlayer(1
                                                                                                // -
                                                                                                // 127)
                        Block block2 = chunkBlocks[blockPos];
                        if (block2 != null) {
                            if (block2.getMaterial() != Material.air) { // if not air
                                if (block2 == biome.fillerBlock) {
                                    if (minusOne == -1) {
                                        
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
                                    else if (minusOne > 0) { // does this even ever get checked
                                                             // ??
                                        --minusOne;
                                        chunkBlocks[blockPos] = block1;
                                    }
                                    if (chunkBlocks[blockPos + 1] == null) {
                                        chunkBlocks[blockPos] = biome.topBlock;
                                    }
                                }
                            }
                            else {
                                minusOne = -1;
                            }
                        }
                    }
                    else {
                        chunkBlocks[blockPos] = biome.barrier;
                    }
                }
            }
        }
    	
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
    public Chunk provideChunk(int par1, int par2)
    {
        this.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        Block[] abyte = new Block[32768];
        this.generateNetherTerrain(par1, par2, abyte);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.replaceBlocksForBiome(par1, par2, abyte, this.biomesForGeneration);
        this.netherCaveGenerator.func_151539_a(this, this.worldObj, par1, par2, abyte);
        //this.genNetherBridge.generate(this, this.worldObj, par1, par2, abyte);
        Chunk chunk = new Chunk(this.worldObj, abyte, par1, par2);
        //BiomeGenBase[] abiomegenbase = (BiomeGenBase[]) this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, par1 * 16, par2 * 16, 16, 16);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)biomesForGeneration[k].biomeID;
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        ((BiomeGenBase) this.theBiomeDecorator).decorate(par1World, par2Random, par3, par4);
    }
    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.noisefield;
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0D, 0.0D, 1.0D);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[par6];
        int i2;       
       
        for (i2 = 0; i2 < par6; ++i2)
        {
            adouble1[i2] = Math.cos((double)i2 * Math.PI * 6.0D / (double)par6) * 2.0D;
            double d2 = (double)i2;

            if (i2 > par6 / 2)
            {
                d2 = (double)(par6 - 1 - i2);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (i2 = 0; i2 < par5; ++i2)
        {
            for (int j2 = 0; j2 < par7; ++j2)
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
                d5 = d5 * (double)par6 / 16.0D;
                ++l1;

                for (int k2 = 0; k2 < par6; ++k2)
                {
                    double d6 = 0.0D;
                    double d7 = adouble1[k2];
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

                    if (k2 > par6 - 4)
                    {
                        d11 = (double)((float)(k2 - (par6 - 4)) / 3.0F);
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    if ((double)k2 < d4)
                    {
                        d11 = (d4 - (double)k2) / 4.0D;

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

                    par1ArrayOfDouble[k1] = d6;
                    ++k1;
                }
            }
        }

        return par1ArrayOfDouble;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
	public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, rand, par2, par3, false));

        int k = par2 * 16;
        int l = par3 * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        //this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.soulRNG, par2, par3);
        int i1;
        int j1;
        int k1;
        int l1 = 0;
        int j2;

        boolean doGen = TerrainGen.populate(par1IChunkProvider, worldObj, rand, par2, par3, false, NETHER_LAVA);
         
        //doGen = TerrainGen.populate(par1IChunkProvider, worldObj, soulRNG, par2, par3, false, DUNGEON);

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, rand, k, l));

        i1 = this.rand.nextInt(this.rand.nextInt(10) + 1) + 1;
        int i2;
       
        doGen = TerrainGen.decorate(worldObj, rand, k, l, SHROOM);
        // Everything not biome related       
    	
        //no need for mineables yet
        // #region Ore Gen
//        WorldGenMinable worldgenminable;
       
//		for (int i = 0; i < 5; i++)
//		{
//			int randPosX = k + soulRNG.nextInt(16);
//			int randPosY = soulRNG.nextInt(128);
//			int randPosZ = l + soulRNG.nextInt(16);
//			(new WorldGenMinable(SoulBlocks.Bauxite.get(), 30, SoulBlocks.Porphyry.get())).generate(worldObj, soulRNG, randPosX, randPosY, randPosZ);
//		}	
//		
//		for (int i = 0; i < 5; i++)
//		{
//			int randPosX = k + soulRNG.nextInt(16);
//			int randPosY = soulRNG.nextInt(128);
//			int randPosZ = l + soulRNG.nextInt(16);
//			(new WorldGenMinable(SoulBlocks.SoulSnowBottom.get(), 40, SoulBlocks.SoulSnowTop.get())).generate(worldObj, soulRNG, randPosX, randPosY, randPosZ);
//		}	

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, rand, k, l));
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, rand, par2, par3, false));

        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "SoulForestLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeGenBase biomegenbase = (BiomeGenBase) this.worldObj.getBiomeGenForCoords(par2, par4);
        return biomegenbase == null ? null : biomegenbase.getSpawnableList(par1EnumCreatureType);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return null;
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }

    public void recreateStructures(int par1, int par2)
    {
        //this.genNetherBridge.generate(this, this.worldObj, par1, par2, (byte[])null);
    }


	@Override
	public ChunkPosition func_147416_a(World var1, String var2, int var3,
			int var4, int var5) {
		return null;
	}


	@Override
	public void saveExtraData() {}
}