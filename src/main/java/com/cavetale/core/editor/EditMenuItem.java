package com.cavetale.core.editor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface EditMenuItem {
    boolean deletable() default false;
    boolean settable() default true;
}
