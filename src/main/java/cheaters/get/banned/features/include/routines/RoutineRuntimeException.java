package cheaters.get.banned.features.include.routines;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;

import java.lang.reflect.InvocationTargetException;

public class RoutineRuntimeException extends Exception {

    public RoutineRuntimeException(String message) {
        super(message);
    }

    /**
     * Displays a {@link RoutineRuntimeException} for any {@link Exception}
     */
    public static void javaException(Throwable exception) {
        if(exception instanceof InvocationTargetException) {
            exception = exception.getCause();
        }

        exception.printStackTrace();
        new RoutineRuntimeException("Java exception '" + exception.getClass().getSimpleName() + "' in routine. Is Shady up to date?").display();
    }

    public void display() {
        System.out.println(getMessage());
        if(Shady.mc.theWorld != null) Utils.sendModMessage("§c" + getMessage());
    }

}
