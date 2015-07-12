package net.epoxide.elysian.world.gen;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;

import java.util.List;
import java.util.Random;

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
    private MapGenBase elysianCaveGenerator = new MapGenCavesHell();
    
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
    
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(chunkX, chunkZ);
        
        byte b0 = 4;
        byte b1 = 32;
        int k = b0 + 1;
        byte b2 = 17;
        int l = b0 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, chunkX * b0, 0, chunkZ * b0, k, b2, l);
        
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
                                
                                if (k1 * 8 + l1 < b1) {
                                    block = Blocks.water; // TODO custom water block
                                }
                                
                                if (d15 > 0.0D) {
                                    block = biome.fillerBlock; // TODO filler
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
    
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, chunkBlocks, chunkMetas, possibleBiomes, this.worldObj);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY)
            return;
        
        byte b0 = 64;
        double d0 = 0.03125D;
        
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                int j1 = -1;
                Block block = Blocks.stone;// TODO custom block
                Block block1 = Blocks.stone;// TODO custom block
                
                for (int posY = 127; posY >= 0; --posY) {
                    int l1 = (l * 16 + k) * 128 + posY;
                    
                    if (posY < 127 - this.rand.nextInt(5) && posY > 0 + this.rand.nextInt(5)) {
                        Block block2 = chunkBlocks[l1];
                        
                        if (block2 != null && block2.getMaterial() != Material.air) {
                            if (block2 == Blocks.stone)// TODO custom block
                            {
                                if (j1 == -1) {
                                    if (posY >= b0 - 4 && posY <= b0 + 1) {
                                        block = Blocks.stone;// TODO custom block
                                        block1 = Blocks.stone;// TODO custom block
                                    }
                                    
                                    if (posY < b0 && (block == null || block.getMaterial() == Material.air)) {
                                        block = Blocks.water; // TODO custom block
                                    }
                                    
                                    if (posY >= b0 - 1) {
                                        chunkBlocks[l1] = block;
                                    }
                                    else {
                                        chunkBlocks[l1] = block1;
                                    }
                                }
                                else if (j1 > 0) {
                                    --j1;
                                    chunkBlocks[l1] = block1;
                                }
                            }
                        }
                        else {
                            j1 = -1;
                        }
                    }
                    else {
                        chunkBlocks[l1] = Blocks.bedrock;
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
        BiomeGenBase[] possibleBiomes = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, posX * 16, posZ * 16, 16, 16);
        this.prepareChunk(posX, posZ, chunkBlocks);
        this.replaceBiomeBlocks(posX, posZ, chunkBlocks, chunkMetas, possibleBiomes);
        this.elysianCaveGenerator.func_151539_a(this, this.worldObj, posX, posZ, chunkBlocks);
        Chunk chunk = new Chunk(this.worldObj, chunkBlocks, chunkMetas, posX, posZ);
        byte[] chunkBiomes = chunk.getBiomeArray();
        
        for (int currentBiomePos = 0; currentBiomePos < chunkBiomes.length; ++currentBiomePos)
            chunkBiomes[currentBiomePos] = (byte) possibleBiomes[currentBiomePos].biomeID;
        
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
}