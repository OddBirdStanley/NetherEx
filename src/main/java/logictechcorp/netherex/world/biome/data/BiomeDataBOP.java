/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex.world.biome.data;

import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.netherex.NetherEx;
import net.minecraft.util.ResourceLocation;

public class BiomeDataBOP extends BiomeData
{
    public BiomeDataBOP(ResourceLocation registryName, int generationWeight, boolean useDefaultDecorations, boolean isSubBiome, boolean isEnabled)
    {
        super(registryName, generationWeight, useDefaultDecorations, isSubBiome, isEnabled);
    }

    @Override
    public boolean isBiomeEnabled()
    {
        return super.isBiomeEnabled() && NetherEx.BIOMES_O_PLENTY_LOADED;
    }
}
