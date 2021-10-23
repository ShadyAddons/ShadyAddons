package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.ResourcePackRefreshEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.ArrayUtils;
import cheaters.get.banned.utils.MathUtils;
import cheaters.get.banned.utils.ReflectionUtils;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class CatGirls {

    public static ArrayList<CatPerson> currentCatPeople = new ArrayList<>();
    private static ArrayList<ResourceLocation> images = new ArrayList<>();
    public static boolean imagesLoaded = false;

    private static class CatPerson {
        float percentage;
        Side side;
        ResourceLocation image;
        int size;

        public CatPerson() {
        }

        enum Side {
            TOP, BOTTOM, LEFT, RIGHT
        }
    }

    public static void load() {
        for(ResourcePackRepository.Entry pack : Shady.mc.getResourcePackRepository().getRepositoryEntries()) {
            Set<String> domains = pack.getResourcePack().getResourceDomains();
            if(domains != null && domains.contains("shadyaddons")) {
                File file = (File) ReflectionUtils.field(pack, "resourcePackFile");
                if(file != null) {
                    Collection<File> pngs = FileUtils.listFiles(file, new String[]{"png"}, true);
                    pngs.removeIf(png -> png.getName().equals("pack.png"));
                    images.clear();
                    for(File png : pngs) {
                        images.add(new ResourceLocation("shadyaddons", png.getParentFile().getName()+"/"+png.getName()));
                    }
                    imagesLoaded = true;
                }
            }
        }
    }

    public static void addRandomCatPerson(int type) {
        CatPerson catPerson = new CatPerson();
        catPerson.percentage = MathUtils.random(20, 80) / 100f;

        Collections.shuffle(images);
        switch(type) {
            case 0: // Girl
                catPerson.image = ArrayUtils.getFirstMatch(images, image -> image.getResourcePath().contains("catgirl"));
                break;

            case 1: // boy
                catPerson.image = ArrayUtils.getFirstMatch(images, image -> image.getResourcePath().contains("catboy"));
                break;

            case 2: // bisexual
                addRandomCatPerson(MathUtils.random(0, 1));
                return;

            case 3: // cat
                catPerson.image = ArrayUtils.getFirstMatch(images, image -> image.getResourcePath().contains("realcat"));
                break;
        }

        catPerson.side = CatPerson.Side.values()[MathUtils.random(0, 3)];
        catPerson.size = MathUtils.random(75, 200);

        currentCatPeople.add(catPerson);
    }

    private int counter = 0;
    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Config.catGirls && imagesLoaded) {
            if(counter % 20 == 0) {
                if(MathUtils.random(1, 5) == 5) {
                    addRandomCatPerson(Config.catGirlsMode);
                }
                counter = 0;
            }
            counter++;
        } else {
            currentCatPeople.clear();
        }
    }

    @SubscribeEvent
    public void onResourcePackLoad(ResourcePackRefreshEvent.Post event) {
        imagesLoaded = false;
        currentCatPeople.clear();
        load();
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR && Config.catGirls) {
            ScaledResolution scaledResolution = new ScaledResolution(Shady.mc);
            for(CatPerson catPerson : currentCatPeople) {
                int x = 0;
                int y = 0;
                int angle = 0;

                switch(catPerson.side) {
                    case BOTTOM:
                        x = (int) (scaledResolution.getScaledWidth() * catPerson.percentage);
                        y = scaledResolution.getScaledHeight() - catPerson.size;
                        break;

                    case TOP:
                        x = (int) (scaledResolution.getScaledWidth() * catPerson.percentage);
                        y = 0;
                        angle = 180;
                        break;

                    case RIGHT:
                        x = scaledResolution.getScaledWidth() - catPerson.size;
                        y = (int) (scaledResolution.getScaledHeight() * catPerson.percentage);
                        angle = -90;
                        break;

                    case LEFT:
                        x = 0;
                        y = (int) (scaledResolution.getScaledHeight() * catPerson.percentage);
                        angle = 90;
                        break;
                }

                RenderUtils.drawRotatedTexture(catPerson.image, x, y, catPerson.size, catPerson.size, angle);
            }
        }
    }

}
