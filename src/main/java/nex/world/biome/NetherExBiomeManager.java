/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

import com.google.common.collect.ImmutableList;
import lex.LibEx;
import lex.config.Config;
import lex.util.FileHelper;
import lex.world.biome.BiomeWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Loader;
import nex.NetherEx;
import nex.handler.ConfigHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SuppressWarnings("ConstantConditions")
public class NetherExBiomeManager
{
    private static final List<BiomeManager.BiomeEntry> BIOMES = new ArrayList<>();
    private static final Map<Biome, BiomeWrapper> BIOME_WRAPPERS = new HashMap<>();
    private static final Map<Biome, Config> BIOME_CONFIGS = new HashMap<>();

    public static void preInit()
    {
        FileHelper.copyDirectoryToDirectory(NetherEx.class.getResource("/assets/nex/biome_configs"), new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes"));
    }

    public static void setupDefaultBiomes()
    {
        NetherEx.LOGGER.info("Setting up default biomes.");
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/minecraft"));
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/nex"));
    }

    public static void setupCompatibleBiomes(MinecraftServer server)
    {
        World world = server.getEntityWorld();

        if(Loader.isModLoaded("biomesoplenty") && ConfigHandler.compatibilityConfig.biomesOPlenty.enableCompat)
        {
            WorldType worldType = world.getWorldType();

            if(worldType.getName().equalsIgnoreCase("BIOMESOP") || worldType.getName().equalsIgnoreCase("lostcities_bop"))
            {
                NetherEx.LOGGER.info("Setting up Biomes O' Plenty biomes.");
                parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/biomesoplenty"));
            }
        }
    }

    public static void setupCustomBiomes()
    {
        NetherEx.LOGGER.info("Setting up custom biomes.");
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/custom"));
    }

    private static void parseBiomeConfigs(File directory)
    {
        if(!directory.exists())
        {
            directory.mkdirs();
        }

        try
        {
            Iterator<Path> pathIter = Files.walk(directory.toPath()).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();
                File configFile = configPath.toFile();

                if(FileHelper.getFileExtension(configFile).equals("json"))
                {
                    wrapBiome(new Config(configFile, true), configFile);
                }
                else if(!configFile.isDirectory())
                {
                    NetherEx.LOGGER.warn("Skipping file located at, {}, as it is not a json file.", configPath.toString());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void wrapBiome(Config config, File configFile)
    {
        BiomeWrapper wrapper = new BiomeWrapperNetherEx(config);
        Biome biome = wrapper.getBiome();

        if(biome != null && wrapper.isEnabled())
        {
            BIOMES.add(new BiomeManager.BiomeEntry(biome, config.getInt("weight", 10)));
            BIOME_WRAPPERS.put(biome, wrapper);
            BIOME_CONFIGS.put(biome, config);
            config.save(configFile);
        }
    }

    public static void resetBiomes()
    {
        BIOMES.clear();
        BIOME_WRAPPERS.clear();
        BIOME_CONFIGS.clear();
    }

    public static List<BiomeManager.BiomeEntry> getBiomes()
    {
        return ImmutableList.copyOf(BIOMES);
    }

    public static BiomeWrapper getBiomeWrapper(Biome key)
    {
        return BIOME_WRAPPERS.get(key);
    }

    public static Config getBiomeConfig(Biome key)
    {
        return BIOME_CONFIGS.get(key);
    }
}