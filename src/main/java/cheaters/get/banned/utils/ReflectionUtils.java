package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;

import java.lang.reflect.Method;

public class ReflectionUtils {

    public static boolean invoke(Class<?> clazz, String methodName) {
        try {
            final Method method = clazz.getDeclaredMethod(methodName, (Class<?>[]) new Class[0]);
            method.setAccessible(true);
            method.invoke(Shady.mc, new Object[0]);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

}
