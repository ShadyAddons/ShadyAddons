package cheaters.get.banned.features.jokes;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.ResourcePackRefreshEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.remote.Analytics;
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

public class CatPeople {

    public static ArrayList<CatPerson> catPeople = new ArrayList<>();
    private static ArrayList<ResourceLocation> images = new ArrayList<>();
    public static boolean usingPack = false;

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
        usingPack = false;
        catPeople.clear();

        for(ResourcePackRepository.Entry pack : Shady.mc.getResourcePackRepository().getRepositoryEntries()) {
            Set<String> domains = pack.getResourcePack().getResourceDomains();
            if(domains != null && domains.contains("shadyaddons")) {
                File directory = (File) ReflectionUtils.field(pack, "field_110523_b");
                if(directory == null) directory = (File) ReflectionUtils.field(pack, "resourcePackFile");
                if(directory != null) {
                    Collection<File> images = FileUtils.listFiles(directory, new String[]{"png"}, true);
                    images.removeIf(image -> image.getName().equals("pack.png"));
                    CatPeople.images.clear();
                    for(File image : images) {
                        CatPeople.images.add(new ResourceLocation("shadyaddons", image.getParentFile().getName() + "/" + image.getName()));
                    }
                    usingPack = true;
                    return;
                }
            }
        }

        Config.catGirls = false;
    }

    public static void addRandomCatPerson(int type) {
        CatPerson catPerson = new CatPerson();
        catPerson.percentage = MathUtils.random(10, 80) / 100f;

        Collections.shuffle(images);
        switch(type) {
            case 0: // girl
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

        catPeople.add(catPerson);
    }

    private int counter = 0;

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Config.catGirls && usingPack) {
            if(counter % 20 == 0) {
                if(MathUtils.random(1, 5) == 5) {
                    addRandomCatPerson(Config.catGirlsMode);
                }
                counter = 0;
            }
            counter++;
        } else {
            catPeople.clear();
        }
    }

    @SubscribeEvent
    public void onResourcePackRefresh(ResourcePackRefreshEvent.Post event) {
        for(ResourcePackRepository.Entry pack : Shady.mc.getResourcePackRepository().getRepositoryEntries()) {
            Set<String> resourceDomains = pack.getResourcePack().getResourceDomains();
            if(resourceDomains != null && resourceDomains.contains("shadyaddons")) {
                load();
                Analytics.collect("using_pack", usingPack ? "1" : "0");
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR && Config.catGirls) {
            ScaledResolution scaledResolution = new ScaledResolution(Shady.mc);
            for(CatPerson catPerson : catPeople) {
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
