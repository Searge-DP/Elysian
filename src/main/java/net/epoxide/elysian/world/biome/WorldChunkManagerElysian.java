package net.epoxide.elysian.world.biome;

import java.util.ArrayList;
import java.util.List;

import net.epoxide.elysian.world.gen.layer.GenLayerElysian;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class WorldChunkManagerElysian extends WorldChunkManager {
    
    public static ArrayList<BiomeGenBase> spawnBiomes = new ArrayList<BiomeGenBase>();
    
    private GenLayer genBiomes;
    private GenLayer elysianBiomeIndexLayer;
    private BiomeCache elysianBiomeCache;
    private List biomesToSpawnIn = new ArrayList();
    
    public WorldChunkManagerElysian() {
    
        this.elysianBiomeCache = new BiomeCache(this);
        this.biomesToSpawnIn.add(ElysianBiomes.biomeElysian);
        
    }
    
    public WorldChunkManagerElysian(long seed, WorldType worldType) {
    
        GenLayer[] agenlayer = GenLayerElysian.makeTheWorld(seed);
        agenlayer = getModdedBiomeGenerators(worldType, seed, agenlayer);
        this.genBiomes = agenlayer[0];
        this.elysianBiomeIndexLayer = agenlayer[1];
    }
    
    @Override
    public List getBiomesToSpawnIn () {
    
        return this.biomesToSpawnIn;
    }
    
    @Override
    public BiomeGenBase getBiomeGenAt (int posX, int posZ) {
    
        BiomeGenBase biome = this.elysianBiomeCache.getBiomeGenAt(posX, posZ);
        return (biome == null) ? ElysianBiomes.biomeElysian : biome;
    }
    
    @Override
    public BiomeGenBase[] getBiomesForGeneration (BiomeGenBase[] biomeList, int posX, int posZ, int width, int depth) {
    
        IntCache.resetIntCache();
        
        if (biomeList == null || biomeList.length < width * depth)
            biomeList = new BiomeGenBase[width * depth];
        
        int[] aint = this.genBiomes.getInts(posX, posZ, width, depth);
        
        try {
            
            for (int currentBiomePos = 0; currentBiomePos < width * depth; ++currentBiomePos) {
                
                if (aint[currentBiomePos] >= 0)
                    biomeList[currentBiomePos] = BiomeGenBase.getBiome(aint[currentBiomePos]);
                
                else
                    biomeList[currentBiomePos] = ElysianBiomes.biomeElysian;
            }
            
            return biomeList;
        }
        
        catch (Throwable throwable) {
            
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
            crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomeList.length));
            crashreportcategory.addCrashSection("x", Integer.valueOf(posX));
            crashreportcategory.addCrashSection("z", Integer.valueOf(posZ));
            crashreportcategory.addCrashSection("w", Integer.valueOf(width));
            crashreportcategory.addCrashSection("h", Integer.valueOf(depth));
            throw new ReportedException(crashreport);
        }
    }
    
    @Override
    public BiomeGenBase[] getBiomeGenAt (BiomeGenBase[] biomeList, int posX, int posZ, int width, int length, boolean flags) {
    
        IntCache.resetIntCache();
        
        if (biomeList == null || biomeList.length < width * length)
            biomeList = new BiomeGenBase[width * length];
        
        if (flags && width == 16 && length == 16 && (posX & 15) == 0 && (posZ & 15) == 0) {
            
            BiomeGenBase[] abiomegenbase1 = this.elysianBiomeCache.getCachedBiomes(posX, posZ);
            System.arraycopy(abiomegenbase1, 0, biomeList, 0, width * length);
            return biomeList;
        }
        
        else {
            
            int[] aint = this.elysianBiomeIndexLayer.getInts(posX, posZ, width, length);
            
            for (int i1 = 0; i1 < width * length; ++i1) {
                
                if (aint[i1] >= 0)
                    biomeList[i1] = BiomeGenBase.getBiome(aint[i1]);
                
                else
                    biomeList[i1] = ElysianBiomes.biomeElysian;
            }
            
            return biomeList;
        }
    }
    
    @Override
    public void cleanupCache () {
    
        this.elysianBiomeCache.cleanupCache();
    }
    
    @Override
    public GenLayer[] getModdedBiomeGenerators (WorldType worldType, long seed, GenLayer[] original) {
    
        WorldTypeEvent.InitBiomeGens event = new WorldTypeEvent.InitBiomeGens(worldType, seed, original);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);
        return event.newBiomeGens;
    }
}