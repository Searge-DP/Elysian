package net.epoxide.elysian.world.chunk;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;

import java.util.List;
import java.util.Random;

import net.epoxide.elysian.blocks.BlockHandler;
import net.epoxide.elysian.world.biome.BiomeGenElysian;
import net.epoxide.elysian.world.biome.BiomeHandler;
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
    
    private NoiseGeneratorOctaves elysianNoiseGen1;
    private NoiseGeneratorOctaves elysianNoiseGen2;
    private NoiseGeneratorOctaves elysianNoiseGen3;
    private NoiseGeneratorOctaves topBlockNoiseGen;
    private NoiseGeneratorOctaves fillerBlockNoiseGen;
    private NoiseGeneratorOctaves elysianNoiseGen6;
    private NoiseGeneratorOctaves elysianNoiseGen7;
    private World worldObj;
    private double[] noiseField;
    private double[] exclusivelyFillerNoise = new double[256];
    private MapGenBase caveGenerator = new MapGenCavesHell();
    private double[] topBlockNoise = new double[256];
    private double[] fillerNoise = new double[256];
    private Object theBiomeDecorator;
    private Block fallbackFiller = BlockHandler.dirt;
    private Block fallbackFluid = Blocks.water;
    
    /**
     * List of biomes which can be used for generating this chunk.
     */
    private BiomeGenBase[] biomesForGeneration;
    
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData6;
    double[] noiseData7;
    
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
     * Prepares a chunk with a list of all the blocks for a chunk.
     * 
     * @param chunkX: The X position of the chunk.
     * @param chunkZ: The Y position of the chunk.
     * @param chunkBlocks: The list of blocks which makes up the chunk.
     */
    public void prepareChunk (int chunkX, int chunkZ, Block[] chunkBlocks) {
    
        byte chunkOffset = 4;
        byte waterLevel = 70;
        int posX = 5;
        int posZ = 5;
        byte noise = 33;
        
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(posX + 16, posZ + 16);
        float temperature = biome.getFloatTemperature(posX, 16, posZ);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
        this.noiseField = this.initializeNoiseField(this.noiseField, chunkX * chunkOffset, 0, chunkZ * chunkOffset, posX, noise, posZ);
        
        for (int xSize = 0; xSize < chunkOffset; ++xSize) {
            for (int zSize = 0; zSize < chunkOffset; ++zSize) {
                for (int chunkSize = 0; chunkSize < 32; ++chunkSize) {
                    
                    double noiseOffset = 0.125D;
                    double sizeNoise1 = this.noiseField[((xSize + 0) * posZ + zSize + 0) * noise + chunkSize + 0];
                    double sizeNoise2 = this.noiseField[((xSize + 0) * posZ + zSize + 1) * noise + chunkSize + 0];
                    double sizeNoise3 = this.noiseField[((xSize + 1) * posZ + zSize + 0) * noise + chunkSize + 0];
                    double sizeNoise4 = this.noiseField[((xSize + 1) * posZ + zSize + 1) * noise + chunkSize + 0];
                    double sizeNoise5 = (this.noiseField[((xSize + 0) * posZ + zSize + 0) * noise + chunkSize + 1] - sizeNoise1) * noiseOffset;
                    double sizeNoise6 = (this.noiseField[((xSize + 0) * posZ + zSize + 1) * noise + chunkSize + 1] - sizeNoise2) * noiseOffset;
                    double sizeNoise7 = (this.noiseField[((xSize + 1) * posZ + zSize + 0) * noise + chunkSize + 1] - sizeNoise3) * noiseOffset;
                    double sizeNoise8 = (this.noiseField[((xSize + 1) * posZ + zSize + 1) * noise + chunkSize + 1] - sizeNoise4) * noiseOffset;
                    
                    for (int noiseNumber = 0; noiseNumber < 8; ++noiseNumber) {
                        
                        double compactNoiseOffset = 0.25D;
                        double sN1 = sizeNoise1;
                        double sN2 = sizeNoise2;
                        double compactNoiseDouble3n1 = (sizeNoise3 - sizeNoise1) * compactNoiseOffset;
                        double compactNoiseDouble4n2 = (sizeNoise4 - sizeNoise2) * compactNoiseOffset;
                        
                        for (int xSize_bis = 0; xSize_bis < 4; ++xSize_bis) {
                            
                            int zSise_bis = xSize_bis + xSize * 4 << 12 | 0 + zSize * 4 << 8 | chunkSize * 8 + noiseNumber;
                            short worldHeight = 256;
                            double d14 = 0.25D;
                            double sn1_bis = sN1;
                            double d16 = (sN2 - sN1) * d14; // TODO name these !
                            
                            for (int i = 0; i < 4; ++i) {
                                
                                Block block = null;
                                
                                if (chunkSize * 8 + noiseNumber < waterLevel)
                                    block = fallbackFluid;
                                
                                if (sn1_bis > 0.0D)
                                    block = fallbackFiller;
                                
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
     * Replaces biomes in a chunk with those from a specific biome.
     * 
     * @param posX: The X position for the chunk.
     * @param posZ: The Z position for the chunk.
     * @param chunkBlocks: All of the blocks within the chunk.
     * @param meta: All of the corresponding meta values within the chunk.
     * @param biomes: Biomes within the chunk.
     */
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
        
        for (int chunkX = 0; chunkX < 16; ++chunkX) {
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
                
                for (int height = totalWorldHeight - 1; height >= 0; --height) {
                    
                    int chunkSize = (chunkZ * 16 + chunkX) * totalWorldHeight + height;
                    
                    if (height < totalWorldHeight && height > 0) {
                        
                        Block block2 = chunkBlocks[chunkSize];
                        
                        if (block2 != null && block2.getMaterial() != Material.air) {
                            
                            if (block2 == fallbackFiller) {
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
                                if (height == totalWorldHeight - 1) {
                                    int chunkSize2 = (chunkZ * 16 + chunkX) * (totalWorldHeight) + (height - 1);
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
    
    @Override
    public Chunk loadChunk (int posX, int posZ) {
    
        return this.provideChunk(posX, posZ);
    }
    
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
     * Initialized the noise field for the chunk generation.
     * 
     * @param noiseField: The noise field to initialize.
     * @param posX: The X position.
     * @param posY: The Y position.
     * @param posZ: The Z position.
     * @param sizeX: The size of the X axis.
     * @param sizeY: The size on the Y axis.
     * @param sizeZ: The size on the Z axis.
     * @return double[]: An initialized noise field.
     */
    private double[] initializeNoiseField (double[] noiseField, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ) {
    
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, noiseField, posX, posY, posZ, sizeX, sizeY, sizeZ);
        MinecraftForge.EVENT_BUS.post(event);
        
        if (event.getResult() == Result.DENY)
            return event.noisefield;
        
        if (noiseField == null)
            noiseField = new double[sizeX * sizeY * sizeZ];
        
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
                    double noiseData_2 = this.noiseData2[noiseIndex_1_2_3] / 1024.0D; // from
                                                                                      // 512 to
                                                                                      // 1024.
                                                                                      // both
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
                    
                    noiseField[noiseIndex_1_2_3] = adouble;
                    ++noiseIndex_1_2_3;
                }
            }
        }
        
        return noiseField;
    }
    
    @Override
    public boolean chunkExists (int posX, int posZ) {
    
        return true;
    }
    
    @Override
    public void populate (IChunkProvider provider, int posX, int posZ) {
    
        int chunkX = posX * 16;
        int chunkZ = posZ * 16;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(chunkX + 16, chunkZ + 16);
        
        BlockFalling.fallInstantly = true;
        
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, worldObj, rand, posX, posZ, false));
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldObj, rand, chunkX, chunkZ));
        // TODO Add worldgen here.
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldObj, rand, chunkX, chunkZ));
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, worldObj, rand, posX, posZ, false));
        
        BlockFalling.fallInstantly = false;
    }
    
    @Override
    public boolean saveChunks (boolean flag, IProgressUpdate progressUpdate) {
    
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
    
        return "ElysianLevelSource";
    }
    
    @Override
    public List getPossibleCreatures (EnumCreatureType creatureType, int posX, int posY, int posZ) {
    
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(posX, posZ);
        
        if (biome == null || !(biome instanceof BiomeGenElysian))
            biome = BiomeHandler.elysianTest1;
        
        return biome.getSpawnableList(creatureType);
    }
    
    @Override
    public int getLoadedChunkCount () {
    
        return 0;
    }
    
    @Override
    public void recreateStructures (int chunkX, int chunkZ) {
    
    }
    
    @Override
    public ChunkPosition func_147416_a (World world, String someString, int x, int y, int z) {
    
        return null;
    }
}