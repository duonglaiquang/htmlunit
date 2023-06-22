/*
 * Copyright (c) 2002-2023 Gargoyle Software Inc.
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
package org.htmlunit.html;

import static org.htmlunit.BrowserVersionFeatures.SUBMITINPUT_DEFAULT_VALUE_IF_VALUE_NOT_DEFINED;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.htmlunit.BrowserVersion;
import org.htmlunit.SgmlPage;
import org.htmlunit.util.NameValuePair;
import org.htmlunit.util.StringUtils;

/**
 * Wrapper for the HTML element "input".
 *
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author David K. Taylor
 * @author <a href="mailto:cse@dynabean.de">Christian Sell</a>
 * @author Daniel Gredler
 * @author Ahmed Ashour
 * @author Marc Guillemot
 * @author Ronald Brill
 * @author Frank Danek
 */
public class HtmlSubmitInput extends HtmlInput implements LabelableElement {

    /**
     * Value to use if no specified <code>value</code> attribute.
     */
    public static final String DEFAULT_VALUE = "Submit Query";

    /**
     * Creates an instance.
     *
     * @param qualifiedName the qualified name of the element type to instantiate
     * @param page the page that contains this element
     * @param attributes the initial attributes
     */
    HtmlSubmitInput(final String qualifiedName, final SgmlPage page,
            final Map<String, DomAttr> attributes) {
        super(qualifiedName, page, addValueIfNeeded(page, attributes));
    }

    /**
     * Add missing attribute if needed by fixing attribute map rather to add it afterwards as this second option
     * triggers the instantiation of the script object at a time where the DOM node has not yet been added to its
     * parent.
     */
    private static Map<String, DomAttr> addValueIfNeeded(final SgmlPage page,
            final Map<String, DomAttr> attributes) {

        final BrowserVersion browserVersion = page.getWebClient().getBrowserVersion();
        if (browserVersion.hasFeature(SUBMITINPUT_DEFAULT_VALUE_IF_VALUE_NOT_DEFINED)) {
            for (final String key : attributes.keySet()) {
                if (VALUE_ATTRIBUTE.equalsIgnoreCase(key)) {
                    return attributes; // value attribute was specified
                }
            }

            // value attribute was not specified, add it
            final DomAttr newAttr = new DomAttr(page, null, VALUE_ATTRIBUTE, DEFAULT_VALUE, true);
            attributes.put(VALUE_ATTRIBUTE, newAttr);
        }

        return attributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean doClickStateUpdate(final boolean shiftKey, final boolean ctrlKey) throws IOException {
        if (!isDisabled()) {
            final HtmlForm form = getEnclosingForm();
            if (form != null) {
                form.submit(this);
                return false;
            }
        }
        super.doClickStateUpdate(shiftKey, ctrlKey);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final String newValue) {
        unmarkValueDirty();
        setDefaultValue(newValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultChecked(final boolean defaultChecked) {
        // Empty.
    }

    /**
     * {@inheritDoc} This method <b>does nothing</b> for submit input elements.
     * @see SubmittableElement#reset()
     */
    @Override
    public void reset() {
        // Empty.
    }

    /**
     * {@inheritDoc} Doesn't print the attribute if it is <code>value="Submit Query"</code>.
     */
    @Override
    protected void printOpeningTagContentAsXml(final PrintWriter printWriter) {
        printWriter.print(getTagName());

        for (final DomAttr attribute : getAttributesMap().values()) {
            final String name = attribute.getNodeName();
            final String value = attribute.getValue();
            if (!VALUE_ATTRIBUTE.equals(name) || !DEFAULT_VALUE.equals(value)) {
                printWriter.print(" ");
                printWriter.print(name);
                printWriter.print("=\"");
                printWriter.print(StringUtils.escapeXmlAttributeValue(value));
                printWriter.print("\"");
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * Returns "Submit Query" if <code>name</code> attribute is defined and <code>value</code> attribute is not defined.
     */
    @Override
    public NameValuePair[] getSubmitNameValuePairs() {
        if (!getNameAttribute().isEmpty() && !hasAttribute(VALUE_ATTRIBUTE)) {
            return new NameValuePair[]{new NameValuePair(getNameAttribute(), DEFAULT_VALUE)};
        }
        return super.getSubmitNameValuePairs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean propagateClickStateUpdateToParent() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isRequiredSupported() {
        return false;
    }
}
