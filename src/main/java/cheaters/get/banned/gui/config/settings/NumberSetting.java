package cheaters.get.banned.gui.config.settings;

import cheaters.get.banned.gui.config.Property;
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

    @Override
    public boolean set(Object value) {
        return super.set(MathHelper.clamp_int((int)value, min, max));
    }

    @Override
    public int compareTo(Integer other) {
        try {
            return Integer.compare(get(int.class), other);
        } catch(Exception ignored) {}
        return 0;
    }

}
