/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.item;

import com.google.common.base.CaseFormat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nex.NetherEx;
import nex.block.BlockOvergrownNetherrack;
import nex.init.NetherExBlocks;

import java.util.List;

public class ItemBlockOvergrownNetherrack extends ItemBlockNetherEx
{
    public ItemBlockOvergrownNetherrack()
    {
        super(NetherExBlocks.OVERGROWN_NETHERRACK);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(BlockOvergrownNetherrack.EnumType type : BlockOvergrownNetherrack.EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        String registryName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath());

        switch(stack.getItemDamage())
        {
            case 0:
                return "tile." + NetherEx.MOD_ID + ":" + registryName + ".tainted";
            default:
                return "tile." + NetherEx.MOD_ID + ":" + registryName;
        }
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }
}
