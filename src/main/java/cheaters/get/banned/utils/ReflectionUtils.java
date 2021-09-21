package cheaters.get.banned.utils;

import cheaters.get.banned.Shady;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Base64;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

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
