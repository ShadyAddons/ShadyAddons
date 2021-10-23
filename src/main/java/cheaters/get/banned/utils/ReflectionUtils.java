package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {

    public static boolean invoke(Class<?> _class, String methodName) {
        try {
            final Method method = _class.getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(Shady.mc);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

    public static Object field(Object object, String name) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch(Exception ignored) {}
        return null;
    }

}
