package cheaters.get.banned.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Boolean {

    String value();
    boolean hidden() default false;
    String parent() default "";
    String boundTo() default "";
    Setting.BooleanType booleanType() default Setting.BooleanType.SWITCH;
    String credit() default "";

}
