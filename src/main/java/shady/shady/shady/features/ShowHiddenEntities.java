package shady.shady.shady.features;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shady.shady.shady.config.Config;
import shady.shady.shady.config.Setting;
import shady.shady.shady.utils.ScoreboardUtils;
import shady.shady.shady.utils.Utils;

public class ShowHiddenEntities {

    @SubscribeEvent
    public void onBeforeRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(event.entity.isInvisible()) {
            if(Config.isEnabled(Setting.SHOW_HIDDEN_FELS) && event.entity instanceof EntityEnderman) {
                event.entity.setInvisible(false);
            }

            if(Utils.inDungeon && event.entity instanceof EntityPlayer) {
                if(Config.isEnabled(Setting.SHOW_HIDDEN_SHADOW_ASSASSINS) && event.entity.getName().contains("Shadow Assassin")) {
                    event.entity.setInvisible(false);
                }

                if(Config.isEnabled(Setting.SHOW_STEALTHY_BLOOD_MOBS)) {
                    for(String name : new String[]{"Revoker", "Psycho", "Reaper", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Tear", "Parasite", "Flamer", "Skull", "Mr. Dead", "Vader", "Frost", "Walker", "Wandering Soul", "Bonzo", "Scarf", "Livid"}) {
                        if(event.entity.getName().contains(name)) {
                            event.entity.setInvisible(false);
                            break;
                        }
                    }
                }
            }

            if(Config.isEnabled(Setting.SHOW_HIDDEN_GHOSTS) && event.entity instanceof EntityCreeper && ScoreboardUtils.scoreboardContains("The Mist")) {
                event.entity.setInvisible(false);
            }
        }
    }

}
