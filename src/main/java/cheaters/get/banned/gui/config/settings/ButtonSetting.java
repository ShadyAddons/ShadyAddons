package cheaters.get.banned.gui.config.settings;

import cheaters.get.banned.gui.config.Property;

import java.lang.reflect.Field;

public class ButtonSetting extends Setting {

    public String buttonText = "Click";
    private Runnable runnable;

    public ButtonSetting(Property annotation, Field field) {
        super(annotation, field);
        if(!annotation.button().equals("")) buttonText = annotation.button();
        runnable = get(Runnable.class);
    }

    public void run() {
        runnable.run();
    }

}
