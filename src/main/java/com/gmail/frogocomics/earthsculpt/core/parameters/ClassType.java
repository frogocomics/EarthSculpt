package com.gmail.frogocomics.earthsculpt.core.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
@Retention(RetentionPolicy.SOURCE)
public @interface ClassType {

    Class type() default Object.class;

}
