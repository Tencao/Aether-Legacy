package com.legacy.aether.server.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;

public class AetherWorldProvider extends WorldProvider
{

	private float[] colorsSunriseSunset = new float[4];

	public AetherWorldProvider()
	{
		super();
	}

	@Override
	public float[] calcSunriseSunsetColors(float f, float f1)
	{
		float f2 = 0.4F;
		float f3 = MathHelper.cos(f * 3.141593F * 2.0F) - 0.0F;
		float f4 = -0F;

		if (f3 >= f4 - f2 && f3 <= f4 + f2)
		{
			float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
			f6 *= f6;
			this.colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
			this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[3] = f6;
			return this.colorsSunriseSunset;
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean canCoordinateBeSpawn(int i, int j)
	{
		IBlockState k = this.worldObj.getBlockState(this.worldObj.getTopSolidOrLiquidBlock(new BlockPos(i, 0, j)));

		if (k.getBlock() == Blocks.AIR)
		{
			return false;
		}
		else
		{
			return k.getMaterial().isSolid();
		}
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderAether(this.worldObj, this.worldObj.getSeed());
	}

    public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }

    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }

	@Override
	public String getDepartMessage()
	{
		return "Descending from the Aether";
	}

	@Override
	public Vec3d getFogColor(float f, float f1)
	{
		int i = 0x8080a0;

		float f2 = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
		if (f2 < 0.0F)
		{
			f2 = 0.0F;
		}
		if (f2 > 1.0F)
		{
			f2 = 1.0F;
		}
		float f3 = (i >> 16 & 0xff) / 255F;
		float f4 = (i >> 8 & 0xff) / 255F;
		float f5 = (i & 0xff) / 255F;
		f3 *= f2 * 0.94F + 0.06F;
		f4 *= f2 * 0.94F + 0.06F;
		f5 *= f2 * 0.91F + 0.09F;

		return new Vec3d(f3, f4, f5);
	}

	@Override
	public String getSaveFolder()
	{
		return "Dim-Aether";
	}

	@Override
	public double getVoidFogYFactor()
	{
		return 100;
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Ascending to the Aether";
	}

	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return false;
	}

	@Override
	public boolean isSkyColored()
	{
		return true;
	}

	@Override
	protected void createBiomeProvider()
	{
		this.biomeProvider = new WorldChunkManagerAether();
	}

	@Override
	public double getHorizon()
	{
		return 0.0;
	}

    @Override
    public float getCloudHeight()
    {
        return 8F;
    }

	@Override
	public DimensionType getDimensionType() 
	{
		return AetherWorld.aether_dimension_type;
	}

}