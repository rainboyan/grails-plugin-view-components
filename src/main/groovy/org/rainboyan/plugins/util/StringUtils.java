/*
 * Copyright 2022-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rainboyan.plugins.util;

import static grails.util.GrailsNameUtils.getNaturalName;

/**
 * StringUtils
 *
 * @author Michael Yan
 * @since 1.0.0
 */
public final class StringUtils {

    /**
     * Retrieves the snake case name of the supplied class.
     * For example MyFunkyGrailsScript would be my_funky_grails_script.
     *
     * @param clazz The class to convert
     * @return The script name representation
     */
    public static String getSnakeCaseName(Class<?> clazz) {
        return clazz == null ? null : getSnakeCaseName(clazz.getName());
    }

    /**
     * Retrieves the snake case name of the given class name.
     * For example MyFunkyGrailsScript would be my_funky_grails_script.
     *
     * @param name The class name to convert.
     * @return The snake case name representation.
     */
    public static String getSnakeCaseName(String name) {
        if (name == null) {
            return null;
        }

        if (name.endsWith(".groovy")) {
            name = name.substring(0, name.length() - 7);
        }
        return getNaturalName(name).replaceAll("\\s", "_").toLowerCase();
    }
}
