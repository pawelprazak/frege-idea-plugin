package org.fregelang.plugin.idea.framework;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FregeLanguageLevelTest {

    @Test
    public void shouldGetDefault() throws Exception {
        FregeLanguageLevel level = FregeLanguageLevel.DEFAULT;

        FregeLanguageLevelProxy proxy = level.proxy();

        assertThat(proxy, is(notNullValue()));
    }
}