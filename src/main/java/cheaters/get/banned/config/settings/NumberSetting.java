package cheaters.get.banned.config.settings;

import cheaters.get.banned.config.properties.Property;
import net.minecraft.util.MathHelper;

import java.lang.reflect.Field;

public class NumberSetting extends Setting implements Comparable<Integer> {

    public int step;
    public int min;
    public int max;
    public String prefix;
    public String suffix;

    public NumberSetting(Property annotation, Field field) {
        super(annotation, field);
        this.step = annotation.step();
        this.min = annotation.min();
        this.max = annotation.max();
        this.prefix = annotation.prefix();
        this.suffix = annotation.suffix();
    }

    public boolean set(int value) {
        return super.set(MathHelper.clamp_int(value, 0, max));
    }

    @Override
    public int compareTo(Integer other) {
        try {
            return Integer.compare((int)get(), other);
        } catch(Exception ignored) {}
        return 0;
    }

}
