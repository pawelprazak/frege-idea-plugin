package org.fregelang.plugin.idea.framework;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class VersionTest {

    @Test
    public void shouldProvideOnlyDigits() throws Exception {
        // given
        Version version = new Version("3.22.0-aa111111");

        // when
        String[] digits = version.fragments();

        // then
        assertThat(digits, is(new String[]{"3", "22", "0", "aa111111"}));
    }

    @Test
    public void shouldCompare() throws Exception {
        // given
        Version oldVersion = new Version("3.3.0-aa111111");
        Version newVersion = new Version("3.22.1-bb111111");

        // then
        assertThat(oldVersion.compareTo(newVersion), is(-1));
        assertThat(oldVersion.compareTo(oldVersion), is(0));
        assertThat(newVersion.compareTo(newVersion), is(0));
        assertThat(newVersion.compareTo(oldVersion), is(1));
    }
}