package com.mapapp.mpi.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:00 PM
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginDetails {
    String author();
    String name();
    String description();
    String version() default "1";
}
