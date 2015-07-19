package net.epoxide.elysian.world.biome;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenElysian extends BiomeGenBase {
    
    public Block fluid = null;
    public Block barrier = null;
    
    public BiomeGenElysian(int biomeID) {
    
        super(biomeID);
        
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.setDisableRain();
    }
    
    @Override
    public List getSpawnableList (EnumCreatureType creatureType) {
    
        return creatureType == EnumCreatureType.monster ? this.spawnableMonsterList : (creatureType == EnumCreatureType.creature ? this.spawnableCreatureList : (creatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : (creatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : null)));
    }
    
    @Override
    public void genTerrainBlocks (World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
    
        genBiomeModdedTerrain(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
    
    /**
     * Replaces custom Stone to allow top/filler blocks to work in dimension.
     * 
     * @param world
     * @param random
     * @param replacableBlock
     * @param aByte
     * @param x
     * @param y
     * @param z
     */
    public void genBiomeModdedTerrain (World world, Random random, Block[] replacableBlock, byte[] aByte, int x, int y, double z) {
    
        Block block = this.topBlock;
        byte b0 = (byte) (this.field_150604_aj & 255);
        Block block1 = this.fillerBlock;
        int k = -1;
        int l = (int) (z / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int i1 = x & 15;
        int j1 = y & 15;
        int k1 = replacableBlock.length / 256;
        
        for (int l1 = 255; l1 >= 0; --l1) {
            int i2 = (j1 * 16 + i1) * k1 + l1;
            
            if (l1 <= 0 + random.nextInt(5)) {
                replacableBlock[i2] = barrier;
            }
            else {
                Block block2 = replacableBlock[i2];
                
                if (block2 != null && block2.getMaterial() != Material.air) {
                    if (block2 == fillerBlock) {
                        if (k == -1) {
                            if (l <= 0) {
                                block = null;
                                b0 = 0;
                                block1 = fillerBlock;
                            }
                            else if (l1 >= 32) {
                                block = this.topBlock;
                                b0 = (byte) (this.field_150604_aj & 255);
                                block1 = this.fillerBlock;
                            }
                            
                            if (l1 < 32 && (block == null || block.getMaterial() == Material.air)) {
                                block = fluid;
                                b0 = 0;
                            }
                            
                            k = l;
                            
                            if (l1 >= 62) {
                                replacableBlock[i2] = block;
                                aByte[i2] = b0;
                            }
                            else if (l1 < 56 - l) {
                                block = null;
                                block1 = fillerBlock;
                                replacableBlock[i2] = Blocks.gravel;
                            }
                            else {
                                replacableBlock[i2] = block1;
                            }
                        }
                        else if (k > 0) {
                            --k;
                            replacableBlock[i2] = block1;
                            
                            if (k == 0 && block1 == Blocks.sand) {
                                k = random.nextInt(4) + Math.max(0, l1 - 63);
                                block1 = Blocks.sandstone;
                            }
                        }
                    }
                }
                else {
                    k = -1;
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor (int p_150558_1_, int p_150558_2_, int p_150558_3_) {
    
        return this.color;
    }
}
