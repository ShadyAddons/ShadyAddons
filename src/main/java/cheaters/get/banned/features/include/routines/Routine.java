package cheaters.get.banned.features.include.routines;

import cheaters.get.banned.features.include.routines.actions.Action;
import cheaters.get.banned.features.include.routines.triggers.Trigger;

import java.util.ArrayList;

public class Routine {

    public String name;
    public String author;
    public boolean allowConcurrent;
    public Trigger trigger;
    public ArrayList<Action> actions = new ArrayList<>();
    private boolean isRunning = false;

    public void doActions() {
        if(!isRunning || allowConcurrent) {
            isRunning = true;
            new Thread(() -> {
                for(Action action : actions) {
                    action.doAction();
                }
                isRunning = false;
            }, "ShadyAddons-Routine-" + name).start();
        }
    }

}