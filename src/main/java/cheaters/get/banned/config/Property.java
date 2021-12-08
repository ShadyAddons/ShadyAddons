package cheaters.get.banned.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Property {

    Type type();
    String name();
    String parent() default "";
    String note() default "";

    boolean warning() default false;
    boolean beta() default false;

    // Type.BUTTOn
    String button() default "";

    // Type.NUMBER
    int step() default 1;
    String prefix() default "";
    String suffix() default "";
    int min() default 0;
    int max() default Integer.MAX_VALUE;

    // Type.SELECT
    String[] options() default {};

    /*
    Type.BOOLEAN
        Can be toggled on and off. Can have children. When toggled off, all boolean children are also toggled off.

    Type.FOLDER
        Can be toggled open and closed. Not safed in config file. When closed, settings preserve their state.
        Use `FolderSetting.isEnabled(name)` to check the state of direct Type.BOOLEAN or Type.CHECKBOX descendents.

    Type.NUMBER
        Allow for incremental number input. Stored as integer, has prefix/suffix, step, and min/max options.

    Type.SELECT
        Allows for selection of string from array. Stored as integer. Similar UX as Type.NUMBER inputs.

    Type.CHECKBOX
        Functons the same as Type.BOOLEAN, just with a different appearance.

    Type.BUTTON
        Runs a method when clicked. Does not close the settings GUI.
    */

    enum Type {
        BOOLEAN, FOLDER, NUMBER, SELECT, CHECKBOX, BUTTON
    }

}
