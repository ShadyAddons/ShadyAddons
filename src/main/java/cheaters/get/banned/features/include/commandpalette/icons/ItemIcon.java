package cheaters.get.banned.features.include.commandpalette.icons;

import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.item.ItemStack;

public class ItemIcon implements IIcon {

    private ItemStack item;

    public ItemIcon(ItemStack item) {
        this.item = item;
    }

    @Override
    public void render(int x, int y) {
        RenderUtils.renderItem(item, x, y);
    }

}
