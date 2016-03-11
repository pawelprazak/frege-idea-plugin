package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableSet;
import org.apache.http.annotation.Immutable;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class FregeLibraryDescriptionTest {

    @Test
    public void shouldGetMavenSdks() throws Exception {
        // when
        List<FregeSdkDescriptor> sdks = new FregeLibraryDescription().mavenSdks();

        // then
        for (FregeSdkDescriptor sdk : ImmutableSet.copyOf(sdks)) {
            System.out.println("sdk = " + sdk);
        }
    }
}