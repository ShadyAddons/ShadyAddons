package hy.shadyaddons.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Utils {

    /**
     * Get SkyBlock ID
     * @param item The item to check
     * @return The item's ID, or null if it doesn't have one
     */
    public static String getSkyBlockID(ItemStack item) {
        NBTTagCompound extraAttributes = item.getSubCompound("ExtraAttributes", false);
        if(extraAttributes != null && extraAttributes.hasKey("id")) {
            return extraAttributes.getString("id");
        }
        return null;
    }

}
