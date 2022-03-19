package cheaters.get.banned.features.commandpalette.actions;

public class RunnableAction implements IAction {

    private Runnable runnable;

    public RunnableAction(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void execute() {
        runnable.run();
    }

}
