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
package grails.views

import groovy.text.Template
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.Generated
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.codehaus.groovy.runtime.InvokerHelper
import org.rainboyan.plugins.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired

import grails.plugin.markup.view.MarkupViewTemplateEngine
import grails.util.GrailsNameUtils
import grails.web.api.WebAttributes

import org.grails.buffer.FastStringWriter
import org.grails.encoder.Encoder
import org.grails.taglib.encoder.WithCodecHelper
import org.rainboyan.plugins.components.artefact.ComponentArtefactHandler

/**
 * Component trait
 *
 * @author Michael Yan
 * @since 1.0.0
 */
@CompileStatic
trait Component extends WebAttributes {
    public static final String COMPONENT_VIEW_DIR = '/components'

    MarkupViewTemplateEngine markupTemplateEngine
    private Encoder rawEncoder

    @Generated
    @Autowired(required = false)
    void setComponentTemplateEngine(MarkupViewTemplateEngine markupTemplateEngine) {
        this.markupTemplateEngine = markupTemplateEngine
    }

    @Generated
    MarkupViewTemplateEngine getComponentTemplateEngine() {
        this.markupTemplateEngine ?: grailsAttributes.applicationContext.getBean('markupTemplateEngine', MarkupViewTemplateEngine)
    }

    @Generated
    String getComponentName() {
        GrailsNameUtils.getLogicalPropertyName(getClass().name, ComponentArtefactHandler.TYPE)
    }

    @Generated
    Template getTemplate() {
        getTemplate(null)
    }

    @Generated
    Template getTemplate(String templateText) {
        if (templateText) {
            return getComponentTemplateEngine().createTemplate(templateText)
        }
        String viewName = StringUtils.getSnakeCaseName(getClass())
        String templatePath = COMPONENT_VIEW_DIR + '/' + viewName
        WritableScriptTemplate template = getComponentTemplateEngine().resolveTemplate(templatePath)
        if (!template) {
            templatePath = COMPONENT_VIEW_DIR + '/' + getComponentName() + '/' + viewName
            template = getComponentTemplateEngine().resolveTemplate(templatePath)
        }
        template
    }

    @Generated
    def render() {
        render(null)
    }

    @Generated
    String render(String templateText) {
        Map<String, Object> binding = new HashMap<>()
        binding.putAll(DefaultGroovyMethods.getProperties(this))
        FastStringWriter writer = new FastStringWriter()
        getTemplate(templateText).make(binding).writeTo(writer)
        raw(writer.toString())
    }

    @Generated
    String inline(String templateText) {
        render(templateText)
    }

    @Generated
    @CompileDynamic
    def raw(Object value) {
        if (this.rawEncoder == null) {
            this.rawEncoder = WithCodecHelper.lookupEncoder(grailsApplication, 'Raw')
            if (this.rawEncoder == null) {
                return InvokerHelper.invokeMethod(value, 'encodeAsRaw', null)
            }
        }
        this.rawEncoder.encode(value)
    }

}
