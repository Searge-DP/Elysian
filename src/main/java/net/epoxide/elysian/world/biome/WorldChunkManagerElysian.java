package net.epoxide.elysian.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.epoxide.elysian.world.gen.layer.GenLayerElysian;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManagerElysian extends WorldChunkManager {
    
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    private BiomeCache biomeCache;
    private List<BiomeGenBase> biomesToSpawnIn;
    
    public WorldChunkManagerElysian() {
    
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = new ArrayList();
        this.biomesToSpawnIn.addAll(BiomeHandler.elysianBiomes);
    }
    
    public WorldChunkManagerElysian(long seed, WorldType worldType) {
    
        this();
        GenLayer[] agenlayer = GenLayerElysian.makeTheWorld(seed);
        agenlayer = getModdedBiomeGenerators(worldType, seed, agenlayer);
        this.genBiomes = agenlayer[0];
        this.biomeIndexLayer = agenlayer[1];
    }
    
    public WorldChunkManagerElysian(World world) {
    
        this(world.getSeed(), world.getWorldInfo().getTerrainType());
    }
    
    @Override
    public List<BiomeGenBase> getBiomesToSpawnIn () {
    
        return this.biomesToSpawnIn;
    }
    
    @Override
    public BiomeGenBase getBiomeGenAt (int x, int z) {
    
        return this.biomeCache.getBiomeGenAt(x, z);
    }
    
    @Override
    public BiomeGenBase[] getBiomesForGeneration (BiomeGenBase[] biomesArray, int posX, int posz, int width, int height) {
    
        IntCache.resetIntCache();
        
        if (biomesArray == null || biomesArray.length < width * height)
            biomesArray = new BiomeGenBase[width * height];
        
        int[] aint = this.genBiomes.getInts(posX, posz, width, height);
        
        try {
            for (int biomeID = 0; biomeID < width * height; ++biomeID)
                biomesArray[biomeID] = BiomeGenBase.getBiome(aint[biomeID]);
            
            return biomesArray;
        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
            crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomesArray.length));
            crashreportcategory.addCrashSection("x", Integer.valueOf(posX));
            crashreportcategory.addCrashSection("z", Integer.valueOf(posz));
            crashreportcategory.addCrashSection("w", Integer.valueOf(width));
            crashreportcategory.addCrashSection("h", Integer.valueOf(height));
            throw new ReportedException(crashreport);
        }
    }
    
    @Override
    public BiomeGenBase[] loadBlockGeneratorData (BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
    
        return this.getBiomeGenAt(oldBiomeList, x, z, width, depth, true);
    }
    
    @Override
    public BiomeGenBase[] getBiomeGenAt (BiomeGenBase[] listToReuse, int x, int y, int width, int length, boolean cacheFlag) {
    
        IntCache.resetIntCache();
        
        if (listToReuse == null || listToReuse.length < width * length) {
            listToReuse = new BiomeGenBase[width * length];
        }
        
        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (y & 15) == 0) {
            BiomeGenBase[] abiomegenbase1 = this.biomeCache.getCachedBiomes(x, y);
            System.arraycopy(abiomegenbase1, 0, listToReuse, 0, width * length);
            return listToReuse;
        }
        else {
            int[] aint = this.biomeIndexLayer.getInts(x, y, width, length);
            
            for (int i = 0; i < width * length; ++i) {
                listToReuse[i] = BiomeGenBase.getBiome(aint[i]);
            }
            return listToReuse;
        }
    }
    
    @Override
    public boolean areBiomesViable (int x, int y, int z, List allowedBiomes) {
    
        IntCache.resetIntCache();
        int l = x - z >> 2;
        int i1 = y - z >> 2;
        int j1 = x + z >> 2;
        int k1 = y + z >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
        
        try {
            for (int j2 = 0; j2 < l1 * i2; ++j2) {
                BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[j2]);
                
                if (!allowedBiomes.contains(biomegenbase)) {
                    return false;
                }
            }
            
            return true;
        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
            crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
            crashreportcategory.addCrashSection("x", Integer.valueOf(x));
            crashreportcategory.addCrashSection("z", Integer.valueOf(y));
            crashreportcategory.addCrashSection("radius", Integer.valueOf(z));
            crashreportcategory.addCrashSection("allowed", allowedBiomes);
            throw new ReportedException(crashreport);
        }
    }
    
    @Override
    public ChunkPosition findBiomePosition (int x, int y, int z, List par4List, Random random) {
    
        IntCache.resetIntCache();
        int l = x - z >> 2;
        int i1 = y - z >> 2;
        int j1 = x + z >> 2;
        int k1 = y + z >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
        ChunkPosition chunkposition = null;
        int j2 = 0;
        
        for (int k2 = 0; k2 < l1 * i2; ++k2) {
            int l2 = l + k2 % l1 << 2;
            int i3 = i1 + k2 / l1 << 2;
            BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k2]);
            
            if (par4List.contains(biomegenbase) && (chunkposition == null || random.nextInt(j2 + 1) == 0)) {
                chunkposition = new ChunkPosition(l2, 0, i3);
                ++j2;
            }
        }
        
        return chunkposition;
    }
}