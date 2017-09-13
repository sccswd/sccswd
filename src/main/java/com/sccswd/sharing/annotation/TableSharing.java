package com.sccswd.sharing.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableSharing {
    String key();
}
