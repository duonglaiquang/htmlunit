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
package org.htmlunit.javascript.host.event;

import org.htmlunit.WebDriverTestCase;
import org.htmlunit.junit.BrowserRunner;
import org.htmlunit.junit.BrowserRunner.Alerts;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link WebGLContextEvent}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class WebGLContextEventTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"1", "2", "3"})
    public void constants() throws Exception {
        final String html = "<html><head><script>\n"
            + LOG_TITLE_FUNCTION
            + "  function test() {\n"
            + "    try {\n"
            + "      log(WebGLContextEvent.CAPTURING_PHASE);\n"
            + "      log(WebGLContextEvent.AT_TARGET);\n"
            + "      log(WebGLContextEvent.BUBBLING_PHASE);\n"
            + "    } catch(e) {log('exception')}\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageVerifyTitle2(html);
    }
}
