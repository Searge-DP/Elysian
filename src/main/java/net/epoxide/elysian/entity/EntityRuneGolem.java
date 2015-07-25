package net.epoxide.elysian.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityRuneGolem extends EntityTameable {
    
    public EntityRuneGolem(World world) {
    
        super(world);
        this.setSize(0.6F, 0.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.setTamed(false);
    }
    
    protected void applyEntityAttributes () {
    
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
        
        if (this.isTamed())
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        else
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    }
    
    @Override
    public boolean isAIEnabled () {
    
        return true;
    }
    
    @Override
    public void setAttackTarget (EntityLivingBase target) {
    
        super.setAttackTarget(target);
        
        if (target == null)
            this.setAngry(false);
        else if (!this.isTamed())
            this.setAngry(true);
    }
    
    @Override
    protected void updateAITick () {
    
        this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
    }
    
    @Override
    protected void entityInit () {
    
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte) 0));
    }
    
    @Override
    protected void func_145780_a (int x, int y, int z, Block block) {
    
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
    }
    
    @Override
    public void writeEntityToNBT (NBTTagCompound nbt) {
    
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Angry", this.isAngry());
    }
    
    @Override
    public void readEntityFromNBT (NBTTagCompound nbt) {
    
        super.readEntityFromNBT(nbt);
        this.setAngry(nbt.getBoolean("Angry"));
    }
    
    @Override
    protected String getLivingSound () {
    
        return this.isAngry() ? "mob.wolf.growl" : (this.rand.nextInt(3) == 0 ? (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }
    
    @Override
    protected String getHurtSound () {
    
        return "mob.wolf.hurt";
    }
    
    @Override
    protected String getDeathSound () {
    
        return "mob.wolf.death";
    }
    
    @Override
    protected float getSoundVolume () {
    
        return 0.4F;
    }
    
    @Override
    protected Item getDropItem () {
    
        return Item.getItemFromBlock(Blocks.stone);
    }
    
    @Override
    public void onLivingUpdate () {
    
        super.onLivingUpdate();
    }
    
    @Override
    public void onUpdate () {
    
        super.onUpdate();
        
        if (this.mustChase())
            this.numTicksToChaseTarget = 10;
    }
    
    @Override
    public float getEyeHeight () {
    
        return this.height * 0.8F;
    }
    
    @Override
    public int getVerticalFaceSpeed () {
    
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }
    
    @Override
    public boolean attackEntityFrom (DamageSource source, float amount) {
    
        if (this.isEntityInvulnerable())
            return false;
        else {
            Entity entity = source.getEntity();
            this.aiSit.setSitting(false);
            
            if (entity instanceof EntityCreeper || source.isExplosion())
                return false;
            
            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
                amount = (amount + 1.0F) / 2.0F;
            
            return super.attackEntityFrom(source, amount);
        }
    }
    
    @Override
    public boolean attackEntityAsMob (Entity entity) {
    
        int i = this.isTamed() ? 4 : 2;
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) i);
    }
    
    @Override
    public void setTamed (boolean isTamed) {
    
        super.setTamed(isTamed);
        
        if (isTamed)
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        else
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    }
    
    @Override
    public boolean interact (EntityPlayer player) {
    
        ItemStack itemstack = player.inventory.getCurrentItem();
        
        if (this.isTamed()) {
            if (itemstack != null) {
                if (Block.getBlockFromItem(itemstack.getItem()).getMaterial().equals(Material.rock) && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
                    if (!player.capabilities.isCreativeMode)
                        --itemstack.stackSize;
                    
                    this.heal(1);
                    
                    if (itemstack.stackSize <= 0)
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    
                    return true;
                }
            }
            
            if (this.func_152114_e(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity) null);
                this.setTarget((Entity) null);
                this.setAttackTarget((EntityLivingBase) null);
            }
        }
        else if (itemstack != null && Block.getBlockFromItem(itemstack.getItem()) == Blocks.mossy_cobblestone && !this.isAngry()) {
            if (!player.capabilities.isCreativeMode)
                --itemstack.stackSize;
            
            if (itemstack.stackSize <= 0)
                player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
            
            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setPathToEntity((PathEntity) null);
                    this.setAttackTarget((EntityLivingBase) null);
                    this.aiSit.setSitting(true);
                    this.setHealth(20.0F);
                    this.func_152115_b(player.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                }
                else {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte) 6);
                }
            }
            
            return true;
        }
        
        return super.interact(player);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate (byte updateByte) {
    
        super.handleHealthUpdate(updateByte);
    }
    
    @Override
    public boolean isBreedingItem (ItemStack stack) {
    
        return false;
    }
    
    @Override
    public int getMaxSpawnedInChunk () {
    
        return 8;
    }
    
    /**
     * Checks if the entity is angry at the player.
     * 
     * @return boolean: True if it is angry.
     */
    public boolean isAngry () {
    
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }
    
    /**
     * Sets if the entity is angry or not.
     * 
     * @param isAngry: Is the wolf angry or not.
     */
    public void setAngry (boolean isAngry) {
    
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        
        if (isAngry) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
        }
        else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
        }
    }
    
    @Override
    public EntityRuneGolem createChild (EntityAgeable entity) {
    
        return null;
    }
    
    @Override
    public boolean canMateWith (EntityAnimal partner) {
    
        return false;
    }
    
    /**
     * Should this entity chase.
     * 
     * @return boolean: true if it will, false if it won't.
     */
    public boolean mustChase () {
    
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }
    
    @Override
    protected boolean canDespawn () {
    
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    @Override
    public boolean func_142018_a (EntityLivingBase victim, EntityLivingBase attacker) {
    
        if (!(victim instanceof EntityGhast)) {
            if (victim instanceof EntityRuneGolem) {
                EntityRuneGolem entitywolf = (EntityRuneGolem) victim;
                
                if (entitywolf.isTamed() && entitywolf.getOwner() == attacker)
                    return false;
            }
            
            return victim instanceof EntityPlayer && attacker instanceof EntityPlayer && !((EntityPlayer) attacker).canAttackPlayer((EntityPlayer) victim) ? false : !(victim instanceof EntityHorse) || !((EntityHorse) victim).isTame();
        }
        else
            return false;
    }
}