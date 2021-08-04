package shady.shady.shady.utils;

import shady.shady.shady.Shady;

import java.lang.reflect.Method;

public class ReflectionUtils {

    public static void invoke(Class<?> clazz, String methodName) {
        try {
            final Method method = clazz.getDeclaredMethod(methodName, (Class<?>[]) new Class[0]);
            method.setAccessible(true);
            method.invoke(Shady.mc, new Object[0]);
        } catch(Exception exception) {
            System.out.println("Error invoking "+methodName);
            exception.printStackTrace();
        }
    }

}
