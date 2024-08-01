/*
 * Copyright (c) 2002-2024 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.htmlunit.javascript.host.html;

import java.util.ArrayList;
import java.util.List;

import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNode;
import org.htmlunit.javascript.configuration.JsxClass;
import org.htmlunit.javascript.configuration.JsxConstructor;
import org.htmlunit.javascript.configuration.JsxFunction;
import org.htmlunit.javascript.host.dom.RadioNodeList;

/**
 * A JavaScript object for {@code HTMLFormControlsCollection}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 * @author Lai Quang Duong
 */
@JsxClass
public class HTMLFormControlsCollection extends HTMLCollection {

    /**
     * Creates an instance.
     */
    public HTMLFormControlsCollection() {
    }

    public HTMLFormControlsCollection(final DomNode domNode, final boolean attributeChangeSensitive) {
        super(domNode, attributeChangeSensitive);
    }

    HTMLFormControlsCollection(final DomNode domNode, final List<DomNode> initialElements) {
        super(domNode, initialElements);
    }

    /**
     * JavaScript constructor.
     */
    @Override
    @JsxConstructor
    public void jsConstructor() {
        super.jsConstructor();
    }

    /**
     * @see <a href="https://html.spec.whatwg.org/multipage/common-dom-interfaces.html#the-htmlformcontrolscollection-interface">HTML Standard</a>
     */
    @Override
    @JsxFunction
    public Object namedItem(final String name) {
        if (name.isEmpty()) {
            return null;
        }

        List<DomNode> elements = new ArrayList<>();
        for (final Object next : getElements()) {
            if (next instanceof DomElement) {
                final DomElement elem = (DomElement) next;
                final String nodeName = elem.getAttributeDirect(DomElement.NAME_ATTRIBUTE);
                if (name.equals(nodeName)) {
                    elements.add(elem);
                    continue;
                }

                final String id = elem.getId();
                if (name.equals(id)) {
                    elements.add(elem);
                }
            }
        }

        if (elements.isEmpty()) {
            return null;
        }
        if (elements.size() == 1) {
            return getScriptableForElement(elements.get(0));
        }

        RadioNodeList nodeList = new RadioNodeList(getDomNodeOrDie(), elements);
        nodeList.setElementsSupplier(getElementSupplier());
        return nodeList;
    }
}
