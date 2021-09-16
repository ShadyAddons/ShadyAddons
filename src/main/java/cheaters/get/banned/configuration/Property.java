package cheaters.get.banned.configuration;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Property {

    String value();
    boolean hidden() default false;
    String parent() default "";
    String boundTo() default "";
    Setting.Type type() default Setting.Type.SWITCH;

}
