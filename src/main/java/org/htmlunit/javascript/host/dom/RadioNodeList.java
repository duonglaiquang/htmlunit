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
package org.htmlunit.javascript.host.dom;

import static org.htmlunit.html.DomElement.ATTRIBUTE_NOT_DEFINED;

import java.util.List;

import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlRadioButtonInput;
import org.htmlunit.javascript.configuration.JsxClass;
import org.htmlunit.javascript.configuration.JsxConstructor;
import org.htmlunit.javascript.configuration.JsxGetter;
import org.htmlunit.javascript.configuration.JsxSetter;

/**
 * A JavaScript object for {@code RadioNodeList}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 * @author Lai Quang Duong
 */
@JsxClass
public class RadioNodeList extends NodeList {

    /**
     * Creates an instance.
     */
    public RadioNodeList() {
    }

    public RadioNodeList(final DomNode domNode) {
        super(domNode, true);
    }

    public RadioNodeList(final DomNode domNode, final List<DomNode> initialElements) {
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
    @JsxGetter
    public String getValue() {
        for (DomNode node : getElements()) {
            if (node instanceof HtmlRadioButtonInput && ((HtmlRadioButtonInput) node).isChecked()) {
                String value = ((HtmlRadioButtonInput)node).getValueAttribute();
                return value == ATTRIBUTE_NOT_DEFINED ? "on" : value;
            }
        }

        return "";
    }

    /**
     * @see <a href="https://html.spec.whatwg.org/multipage/common-dom-interfaces.html#the-htmlformcontrolscollection-interface">HTML Standard</a>
     */
    @JsxSetter
    public void setValue(final String newValue) {
        for (DomNode node : getElements()) {
            if (node instanceof HtmlRadioButtonInput) {
                String value = ((HtmlRadioButtonInput)node).getValueAttribute();
                if (value == ATTRIBUTE_NOT_DEFINED) {
                    value = "on";
                }
                if (newValue.equals(value)) {
                    ((HtmlRadioButtonInput)node).setChecked(true);
                    break;
                }
            }
        }
    }
}
