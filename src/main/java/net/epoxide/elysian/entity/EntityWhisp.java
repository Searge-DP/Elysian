package net.epoxide.elysian.entity;

import net.epoxide.elysian.Elysian;
import net.epoxide.elysian.client.particles.EntityWhispParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWhisp extends EntityLiving implements IAnimals {

	/**where the entity will spawn*/
	private ChunkCoordinates spawnPosition;

	public EntityWhisp(World par1World) {

		super(par1World);
		setSize(0.2f, 0.2f);
	}

	@Override
	protected void applyEntityAttributes () {

		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
	}

	@Override
	protected boolean isAIEnabled () {

		return true;
	}

	@Override
	public void onUpdate () {

		super.onUpdate();
		this.motionY *= 0.2000000238418579D;

		if(worldObj.isRemote)
			Elysian.proxy.getMinecraft().effectRenderer.addEffect(new EntityWhispParticle(worldObj, posX, posY, posZ));

	}

	@Override
	protected void updateAITasks () {

		super.updateAITasks();

		if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition.posX, this.spawnPosition.posY, this.spawnPosition.posZ) || this.spawnPosition.posY < 1)) {
			this.spawnPosition = null;
		}

		if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0F) {
			this.spawnPosition = new ChunkCoordinates((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = this.spawnPosition.posX + 0.5D - this.posX;
		double d1 = this.spawnPosition.posY + 0.1D - this.posY;
		double d2 = this.spawnPosition.posZ + 0.5D - this.posZ;
		this.motionX += (Math.signum(d0) * 0.2D - this.motionX) * 0.10000000149011612D;
		this.motionY += (Math.signum(d1) * 0.299999988079071D - this.motionY) * 0.10000000149011612D;
		this.motionZ += (Math.signum(d2) * 0.2D - this.motionZ) * 0.10000000149011612D;
		float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
		float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
		this.moveForward = 0.5F;
		this.rotationYaw += f1;

	}

	@Override
	// keep empty so it can fly
	protected void fall (float par1) {

	}

	@Override
	// keep empty so it can fly
	protected void updateFallState (double par1, boolean par3) {

	}

	@Override
	public boolean getCanSpawnHere () {
		return this.rand.nextInt(100) == 0;
	}

}
