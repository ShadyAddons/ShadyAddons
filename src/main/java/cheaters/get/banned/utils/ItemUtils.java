package cheaters.get.banned.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Modified from Skyblockcatia under MIT License
 * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
 */
public class ItemUtils {

    public static ItemStack getSkullByName(String username) {
        ItemStack itemStack = new ItemStack(Items.skull, 1, 3);
        NBTTagCompound compound = new NBTTagCompound();
        GameProfile profile = TileEntitySkull.updateGameprofile(new GameProfile(null, username));
        compound.removeTag("SkullOwner");
        compound.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), profile));
        itemStack.setTagCompound(compound);
        return itemStack;
    }

    // TODO: Can this be shortened to just the texture ID?
    public static ItemStack getSkullItemStack(String ownerId, String textureId) {
        ItemStack itemStack = new ItemStack(Items.skull, 1, 3);
        return setSkullSkin(itemStack, ownerId, textureId);
    }

    public static ItemStack setSkullSkin(ItemStack itemStack, String skullId, String skullValue) {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound properties = new NBTTagCompound();
        properties.setString("Id", skullId);
        NBTTagCompound texture = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        NBTTagCompound value = new NBTTagCompound();
        value.setString("Value", toSkullURL(skullValue));
        list.appendTag(value);
        texture.setTag("textures", list);
        properties.setTag("Properties", texture);

        if(!itemStack.hasTagCompound()) {
            compound.setTag("SkullOwner", properties);
            itemStack.setTagCompound(compound);
        } else {
            itemStack.getTagCompound().setTag("SkullOwner", properties);
        }

        return itemStack;
    }

    private static String toSkullURL(String url) {
        return Base64.getEncoder().encodeToString(
                ("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + url + "\"}}}").getBytes(StandardCharsets.UTF_8)
        );
    }

}
