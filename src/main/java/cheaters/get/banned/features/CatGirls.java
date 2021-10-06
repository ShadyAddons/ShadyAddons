package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.config.Config;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.utils.MathUtils;
import cheaters.get.banned.utils.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class CatGirls {

    public static ArrayList<CatPerson> currentCatPeople = new ArrayList<>();
    private static final int catGirlCount = 5;
    private static final int catBoyCount = 5;

    private static class CatPerson {
        float percentage;
        Side side;
        ResourceLocation image;
        int size;

        public CatPerson(float percentage, Side side, ResourceLocation image, int size) {
            this.percentage = percentage;
            this.side = side;
            this.image = image;
            this.size = size;
        }

        enum Side {
            TOP, BOTTOM, LEFT, RIGHT
        }
    }

    public static void addRandomCatPerson() {
        String sex = MathUtils.random(0, 1) == 0 ? "boy" : "girl";
        addRandomCatPerson(sex);
    }

    public static void addRandomCatPerson(String sex) {
        int index;
        float percentage = MathUtils.random(20, 80) / 100f;

        if(sex.equals("boy")) {
            index = MathUtils.random(1, catBoyCount);
        } else {
            index = MathUtils.random(1, catGirlCount);
        }

        CatPerson.Side side = CatPerson.Side.values()[MathUtils.random(0, 3)];
        ResourceLocation image = new ResourceLocation("shadyaddons:catpeople/cat"+sex+"_"+index+".png");
        int size = MathUtils.random(75, 200);

        CatPerson catPerson = new CatPerson(percentage, side, image, size);
        currentCatPeople.add(catPerson);
    }

    private int counter = 0;
    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(Config.catGirls) {
            if(counter % 20 == 0) {
                if(MathUtils.random(1, 5) == 5) {
                    if(Config.catGirlsMode == 0) addRandomCatPerson("girl");
                    if(Config.catGirlsMode == 1) addRandomCatPerson("boy");
                    if(Config.catGirlsMode == 2) addRandomCatPerson();
                }
                counter = 0;
            }
            counter++;
        } else {
            currentCatPeople.clear();
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR && Config.catGirls) {
            for(CatPerson catPerson : currentCatPeople) {
                ScaledResolution scaledResolution = new ScaledResolution(Shady.mc);

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
