/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.handler;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.init.NetherExBlocks;
import nex.init.NetherExItems;
import nex.init.NetherExMaterials;
import nex.util.ArmorUtil;

import java.util.ListIterator;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event)
    {
        RenderBlockOverlayEvent.OverlayType type = event.getOverlayType();

        if(type == RenderBlockOverlayEvent.OverlayType.FIRE)
        {
            EntityPlayer player = event.getPlayer();
            World world = player.getEntityWorld();
            BlockPos pos = player.getPosition();

            for(int x = -1; x < 2; x++)
            {
                for(int z = -1; z < 2; z++)
                {
                    for(int y = -1; y < 2; y++)
                    {
                        BlockPos newPos = pos.add(x, y, z);

                        if(world.getBlockState(newPos).getBlock() == NetherExBlocks.FLUID_ICHOR)
                        {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBoneMealUse(BonemealEvent event)
    {
        IBlockState state = event.getBlock();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainStack = player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack offStack = player.getHeldItem(EnumHand.OFF_HAND);

        if(mainStack.getItem() == NetherExItems.ITEM_BONE_MEAL_WITHERED || offStack.getItem() == NetherExItems.ITEM_BONE_MEAL_WITHERED)
        {
            if(state.getBlock() == Blocks.NETHER_WART)
            {
                int age = state.getValue(BlockNetherWart.AGE);

                if(age < 3)
                {
                    state = state.withProperty(BlockNetherWart.AGE, age + 1);
                    event.getWorld().setBlockState(event.getPos(), state);
                    event.setResult(Event.Result.ALLOW);
                }
            }
            else
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onCropPreGrow(BlockEvent.CropGrowEvent.Pre event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(state.getBlock() == Blocks.NETHER_WART)
        {
            if(world.getBlockState(pos.down()).getBlock() == NetherExBlocks.BLOCK_SAND_SOUL_TILLED)
            {
                event.setResult(Event.Result.ALLOW);
            }
            else
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onSetAttackTarget(LivingSetAttackTargetEvent event)
    {
        if(event.getEntity() instanceof AbstractSkeleton)
        {
            if(event.getTarget() instanceof EntityPlayer)
            {
                if(ArmorUtil.isWearingFullArmorSet((EntityPlayer) event.getTarget(), NetherExMaterials.ARMOR_BONE_WITHERED))
                {
                    ((AbstractSkeleton) event.getEntity()).setAttackTarget(null);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event)
    {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
        DamageSource source = event.getSource();

        if(source == DamageSource.LAVA || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE)
        {
            for(int x = -1; x < 2; x++)
            {
                for(int z = -1; z < 2; z++)
                {
                    for(int y = -1; y < 2; y++)
                    {
                        BlockPos newPos = pos.add(x, y, z);

                        if(world.getBlockState(newPos).getBlock() == NetherExBlocks.FLUID_ICHOR)
                        {
                            entity.extinguish();
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event)
    {
        Random rand = new Random();
        BlockPos deathPoint = event.getEntity().getPosition();

        if(event.getEntity() instanceof EntityGhast)
        {
            event.getDrops().add(new EntityItem(event.getEntity().world, deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.FOOD_MEAT_GHAST_RAW, rand.nextInt(3) + 1, 0)));
        }
        else if(event.getEntity() instanceof EntityWitherSkeleton)
        {
            ListIterator<EntityItem> iter = event.getDrops().listIterator();

            while(iter.hasNext())
            {
                EntityItem entityItem = iter.next();
                ItemStack stack = entityItem.getEntityItem();

                if(stack.getItem() == Items.BONE || stack.getItem() == Items.COAL)
                {
                    iter.remove();
                }
            }

            event.getDrops().add(new EntityItem(event.getEntity().world, deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.ITEM_BONE_WITHERED, rand.nextInt(4), 0)));
        }
    }
}
