package com.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value={CONSTRUCTOR})
@Retention(value=RUNTIME)

public @interface Inject {
}
