package shady.shady.shady.utils;

import net.minecraft.client.Minecraft;

import java.lang.reflect.Method;

public class ReflectionUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void invoke(String methodName) {
        try {
            final Method method = mc.getClass().getDeclaredMethod(methodName, (Class<?>[])new Class[0]);
            method.setAccessible(true);
            method.invoke(mc, new Object[0]);
        }
        catch (Exception exception) {
            System.out.println("Error invoking "+methodName);
            exception.printStackTrace();
        }
    }

}
