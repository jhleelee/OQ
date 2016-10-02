package com.jackleeentertainment.oq.debug;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by Jacklee on 2016. 9. 20..
 */

public class L {

    public static void pojo(Object obj){
        for (Field field : obj.getClass().getDeclaredFields()) {
            /**
             * Set the {@code accessible} flag for this object to
             * the indicated boolean value.  A value of {@code true} indicates that
             * the reflected object should suppress Java language access
             * checking when it is used.  A value of {@code false} indicates
             * that the reflected object should enforce Java language access checks.
             *
             * <p>FirstT, if there is a security manager, its
             * {@code checkPermission} method is called with a
             * {@code ReflectPermission("suppressAccessChecks")} permission.
             *
             * <p>A {@code SecurityException} is raised if {@code flag} is
             * {@code true} but accessibility of this object may not be changed
             * (for example, if this element object is a {@link Constructor} object for
             * the class {@link Class}).
             *
             * <p>A {@code SecurityException} is raised if this object is a {@link
             * Constructor} object for the class
             * {@code java.lang.Class}, and {@code flag} is true.
             *
             * @param flag the new value for the {@code accessible} flag
             * @throws SecurityException if the request is denied.
             * @see SecurityManager#checkPermission
             * @see RuntimePermission
             */
            field.setAccessible(true);
            String name = field.getName();
            try {
                Object value = field.get(obj);
                System.out.printf("Field name: %s, Field value: %s%n", name, value);
                Log.d(obj.getClass().getSimpleName(), name + " : " + value);

            } catch (IllegalAccessException e){
                Log.d(obj.getClass().getSimpleName(), e.toString());
            }

        }

    }

}
