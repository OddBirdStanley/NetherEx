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

package nex.world.biome;

import net.minecraft.entity.monster.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import nex.entity.monster.EntityEmber;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.world.gen.feature.*;
import nex.world.gen.structure.WorldGenHellGrave;
import nex.world.gen.structure.WorldGenHellVillage;

import java.util.Random;

public class BiomeHell extends BiomeNetherEx
{
    private WorldGenerator lavaSpring = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), false);
    private WorldGenerator fire = new WorldGenFire(Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator glowstonePass1 = new WorldGenGlowStone();
    private WorldGenerator glowstonePass2 = new WorldGenGlowStone();
    private WorldGenerator smallBrownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator smallRedMushroom = new WorldGenBush(Blocks.RED_MUSHROOM, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator quartzOre = new WorldGenMinableMeta(Blocks.QUARTZ_ORE.getDefaultState(), 14, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator magma = new WorldGenMinableMeta(Blocks.MAGMA.getDefaultState(), 32, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator lavaTrap = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), true);
    private WorldGenerator village = new WorldGenHellVillage();
    private WorldGenerator grave = new WorldGenHellGrave();

    public BiomeHell()
    {
        super(new BiomeProperties("Hell").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "hell");

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 25, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityEmber.class, 25, 4, 6));

        if(ConfigHandler.Biome.Hell.generateBiome)
        {
            NetherExBiomes.addBiome(this, ConfigHandler.Biome.Hell.biomeRarity, new ItemStack(Blocks.LAVA, 1, 0));
        }
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.Hell.generateLavaSprings)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.lavaSpringRarity; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateFire)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.Hell.fireRarity); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateGlowstonePass1)
        {
            for(int i = 0; i < rand.nextInt(ConfigHandler.Biome.Hell.glowstonePass1Rarity); i++)
            {
                glowstonePass1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateGlowstonePass2)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.glowstonePass2Rarity; i++)
            {
                glowstonePass2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateBrownMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateRedMushrooms)
        {
            if(rand.nextBoolean())
            {
                smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(world, rand, pos));

        if(ConfigHandler.Biome.Hell.generateQuartzOre)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.quartzOreRarity; i++)
            {
                quartzOre.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 8, rand.nextInt(16)));
            }
        }

        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, pos));

        if(ConfigHandler.Biome.Hell.generateMagma)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.magmaRarity; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        if(ConfigHandler.Biome.Hell.generateLavaTraps)
        {
            for(int i = 0; i < ConfigHandler.Biome.Hell.lavaTrapRarity; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 8, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateVillages)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.villageRarity) == 0)
            {
                village.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        if(ConfigHandler.Biome.Hell.generateGraves)
        {
            if(rand.nextInt(ConfigHandler.Biome.Hell.graveRarity) == 0)
            {
                grave.generate(world, rand, pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, pos));
    }
}
