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
package grails.compiler.traits

import groovy.transform.Generated

import grails.views.Component

/**
 * Component render
 *
 * @author Michael Yan
 * @since 1.0.0
 */
trait ComponentRenderer {

    @Generated
    void render(Component component) {
        render(component, [:])
    }

    @Generated
    void render(Component component, Map args) {
        Map argMap = [text: component.render(), contentType: "text/html", encoding: "UTF-8"]
        argMap.putAll(args)
        render(argMap)
    }

}