/*
This file is part of SecretLife.
https://github.com/Zombi442

SecretLife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

SecretLife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with SecretLife.
If not, see <https://www.gnu.org/licenses/>.
 */
package me.zombi42.secretlife.Util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import java.util.*;

public class DropManager {


    public List<ItemStack> getItems(Double amountToGive) {

        if (amountToGive <= 0 || amountToGive >= 21) {
            throw new IllegalArgumentException("Integer Must be a value between 1 and 20");
        }

        List<ItemStack> list = new ArrayList<>();

        List<ItemStack> finalList = new ArrayList<>();


        ItemStack invisibilityPotion = new ItemStack(Material.POTION);
        PotionMeta invisibilityPotionItemMeta = (PotionMeta) invisibilityPotion.getItemMeta();
        invisibilityPotionItemMeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY, false, false));
        invisibilityPotion.setItemMeta(invisibilityPotionItemMeta);
        list.add(invisibilityPotion);


        ItemStack slowFalling = new ItemStack(Material.POTION);
        PotionMeta slowFallingItemMeta = (PotionMeta) slowFalling.getItemMeta();
        slowFallingItemMeta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, false, false));
        slowFalling.setItemMeta(slowFallingItemMeta);
        list.add(slowFalling);

        list.add(new ItemStack(Material.IRON_BLOCK, 999));
        list.add(new ItemStack(Material.IRON_BLOCK,999));
        list.add(new ItemStack(Material.GOLD_BLOCK, 999));
        list.add(new ItemStack(Material.ANCIENT_DEBRIS, 999));
        list.add(new ItemStack(Material.TNT, 999));


        for (int ii = 0; ii < amountToGive/2; ii++) {
            Collections.shuffle(list);
            if(list.get(0).getAmount() == 999){
                list.get(0).setAmount(new Random().nextInt(1, 3));
            }
            if(list.get(0).getAmount() == 555){
                list.get(0).setAmount(new Random().nextInt(1, 10));
            }
            finalList.add(list.get(0));
        }

        return finalList;

    }

}
