package org.fregelang.plugin.idea.framework;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FregeLanguageLevelProxyTest {

    @Test
    public void shouldGetInstance() throws Exception {
        FregeLanguageLevelProxy levelProxy = FregeLanguageLevelProxy.Frege_3_23;

        FregeLanguageLevel level = levelProxy.getInstance();

        assertThat(level, is(notNullValue()));
    }
}