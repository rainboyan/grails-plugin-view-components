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
package org.rainboyan.plugins.components.taglib

import groovy.transform.CompileStatic
import org.apache.commons.beanutils.BeanUtils as CommonsBeanUtils
import org.springframework.beans.BeanUtils as SpringBeanUtils
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import grails.artefact.TagLibrary
import grails.core.GrailsApplication
import grails.core.GrailsClass
import grails.core.support.GrailsApplicationAware
import grails.gsp.TagLib
import grails.util.GrailsNameUtils
import grails.views.Component

import org.rainboyan.plugins.components.artefact.ComponentArtefactHandler

/**
 * Component TagLib
 *
 * @author Michael Yan
 * @since 1.0.0
 */
@CompileStatic
@TagLib
class ComponentTagLib implements ApplicationContextAware, GrailsApplicationAware, InitializingBean, TagLibrary {

    static namespace = 'vc'
    static defaultEncodeAs = 'raw'

    GrailsApplication grailsApplication
    ApplicationContext applicationContext

    private final Map<String, Class> components = new HashMap<>()

    @Override
    void afterPropertiesSet() {
        GrailsClass[] componentClasses = grailsApplication.getArtefacts(ComponentArtefactHandler.TYPE)
        componentClasses.each {
            String componentName = GrailsNameUtils.getLogicalPropertyName(it.clazz.name, ComponentArtefactHandler.TYPE)
            this.components.put(componentName, it.clazz)
        }
    }

    /**
     * Renders a component. Examples:<br/>
     *
     * &lt;vc:render component="${new ButtonComponent(name: 'Create')}" /&gt;<br/>
     *
     * @attr component REQUIRED The component to render
     */
    Closure render = { Map attrs, body ->
        def component = attrs.component
        Component componentObject
        Class componentClass
        String componentName
        if (component instanceof Component) {
            componentClass = component.class
            componentObject = component as Component
            componentName = GrailsNameUtils.getLogicalPropertyName(componentClass.name, ComponentArtefactHandler.TYPE)
        }
        else {
            componentClass = this.components.get(component)
            componentName = component
            if (componentClass) {
                componentObject = SpringBeanUtils.instantiateClass(componentClass, Component)
                if (componentObject && attrs.model) {
                    CommonsBeanUtils.copyProperties(componentObject, (Map) attrs.model)
                }
            }
            else {
                componentObject = null
            }
        }
        if (componentObject && componentClass) {
            out.write(componentObject.render().toString())
        }
        else {
            throwTagError("Component with name [\"$componentName\"] not found")
        }
        null
    }

}
