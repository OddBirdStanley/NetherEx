package nex.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.registry.ModBlocks;

import java.util.Random;

public class BlockModMycelium extends BlockBase
{
    public BlockModMycelium()
    {
        super("mycelium", Material.ROCK);

        setHardness(0.4F);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if(!worldIn.isRemote)
        {
            for(int i = 0; i < 4; ++i)
            {
                BlockPos newPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1);
                Block block = worldIn.getBlockState(newPos).getBlock();

                if(block == ModBlocks.NETHERRACK && worldIn.isAirBlock(newPos.up()) && worldIn.isAirBlock(newPos.up()))
                {
                    worldIn.setBlockState(newPos, getDefaultState());
                }
            }

        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if(rand.nextInt(10) == 0)
        {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (double) ((float) pos.getX() + rand.nextFloat()), (double) ((float) pos.getY() + 1.1F), (double) ((float) pos.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }
}
