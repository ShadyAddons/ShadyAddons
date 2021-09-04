package shady.shady.shady.utils;

import shady.shady.shady.Shady;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Base64;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

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

    public static String getMfValue(String key) {
        try {
            Class<Shady> _class = Shady.class;
            String className = _class.getSimpleName() + ".class";
            String classPath = _class.getResource(className).toString();
            if (!classPath.startsWith("jar")) return null;
            String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
            Manifest manifest = new Manifest(new URL(manifestPath).openStream());
            Attributes attr = manifest.getMainAttributes();
            String value = attr.getValue(key);
            return new String(Base64.getDecoder().decode(value+"=="));
        } catch(Exception ignored) {}
        return null;
    }

}
