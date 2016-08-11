package nex.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import nex.Settings;
import nex.api.biome.INetherBiome;

public abstract class NEXBiome extends Biome implements INetherBiome
{
    static
    {
        assignBiomeIds();
    }

    public NEXBiome(BiomeProperties properties)
    {
        super(properties);

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();
    }

    public abstract int getId();

    public abstract String getName();

    private static void assignBiomeIds()
    {
        int[] unusedBiomeIds = new int[256];
        checkBiomeIds(unusedBiomeIds);

        Settings.hellBiomeId = assignBiomeId(unusedBiomeIds, Settings.hellBiomeId);
    }

    private static int[] checkBiomeIds(int[] unusedBiomeIds)
    {
        for(int i = 0; i < 256; i++)
        {
            unusedBiomeIds[i] = Biome.getBiome(i) == null ? i : -1;
        }

        return unusedBiomeIds;
    }

    private static int assignBiomeId(int[] unusedBiomeIds, int biomeId)
    {
        if(biomeId == -1)
        {
            biomeId = findNextAvailableBiomeId(unusedBiomeIds);
            Settings.assignedBiomeIds = true;
        }

        return biomeId;
    }

    private static int findNextAvailableBiomeId(int[] unusedBiomeIds)
    {
        for(int i = 0; i < 256; i++)
        {
            if(unusedBiomeIds[i] != -1)
            {
                unusedBiomeIds[i] = -1;
                return i;
            }
        }

        return -1;
    }
}