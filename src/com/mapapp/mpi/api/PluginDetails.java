package com.mapapp.mpi.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Information about a {@link com.mapapp.mpi.core.exec.Plugin}.
 *
 * @author Ganesh Ravendranathan
 *         Last Modified: 8/31/2014 at 5:00 PM
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginDetails {

    /**
     * The author of the {@link com.mapapp.mpi.core.exec.Plugin}.
     * @return The author of the {@link com.mapapp.mpi.core.exec.Plugin} as a {@link java.lang.String}.
     */
    String author();

    /**
     * The name of the {@link com.mapapp.mpi.core.exec.Plugin}.
     * @return he name of the {@link com.mapapp.mpi.core.exec.Plugin} as a {@link java.lang.String}.
     */
    String name();

    /**
     * A description of the {@link com.mapapp.mpi.core.exec.Plugin}.
     * @return The description of the {@link com.mapapp.mpi.core.exec.Plugin} as a {@link java.lang.String}.
     */
    String description();

    /**
     * The version of this {@link com.mapapp.mpi.core.exec.Plugin}.
     * @return The version of this {@link com.mapapp.mpi.core.exec.Plugin} as a {@link java.lang.String}.
     */
    String version() default "1";

    /**
     * Use this to request your own tab for the {@link com.mapapp.mpi.core.exec.Plugin}.
     *
     * Be sure to add the appropriate .xml file containing the layout of your plugin, as well
     * as the corresponding {@link android.app.Fragment}.
     *
     * @return True if the developer has requested a tab, false if otherwise.
     */
    boolean requestTab() default false;
}
