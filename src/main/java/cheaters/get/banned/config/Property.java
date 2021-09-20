package cheaters.get.banned.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Property {

    String value();
    String tooltip() default "";
    boolean hidden() default false;
    String parent() default "";
    String boundTo() default "";
    Setting.Type type() default Setting.Type.SWITCH;

}
