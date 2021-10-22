package cheaters.get.banned.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Property {

    Type type();
    String name();
    String parent() default "";
    String credit() default "";
    boolean blatant() default false;

    // Type.NUMBER
    int step() default 1;
    String prefix() default "";
    String suffix() default "";
    int min() default 0;
    int max() default Integer.MAX_VALUE;

    // Type.SELECT
    String[] options() default {};

    enum Type {
        BOOLEAN, FOLDER, NUMBER, SELECT, CHECKBOX
    }

}
