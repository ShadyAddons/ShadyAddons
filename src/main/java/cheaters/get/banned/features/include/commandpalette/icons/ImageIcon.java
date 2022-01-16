package cheaters.get.banned.features.include.commandpalette.icons;

import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.util.ResourceLocation;

public class ImageIcon implements IIcon {

    private ResourceLocation resource;

    public ImageIcon(ResourceLocation resource) {
        this.resource = resource;
    }

    public ImageIcon(String fileName) {
        resource = new ResourceLocation("shadyaddons:commandpalette/" + fileName);
    }

    @Override
    public void render(int x, int y) {
        RenderUtils.drawTexture(resource, x, y, 16, 16);
    }

}
