package cn.xmp.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title:
 * @BelongProjecet imitate-spring
 * @BelongPackage cn.xmp.spring
 * @Description:
 * @Author: xmp
 * @Date: 2022/4/19 16:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentScan {
    String value() default "";
}
