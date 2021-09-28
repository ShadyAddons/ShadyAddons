package cheaters.get.banned.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Number {

    String value();
    boolean hidden() default false;
    String parent() default "";
    int step() default 1;
    String prefix() default "";
    String suffix() default "";
    int min() default 0;
    int max();
    String credit() default "";

}
