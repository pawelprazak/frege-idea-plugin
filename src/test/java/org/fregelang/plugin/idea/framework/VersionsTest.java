package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class VersionsTest {

    @Test
    public void shouldExctractVersions() throws Exception {
        // given
        List<String> lines = ImmutableList.of(
                "<html>",
                "<head><title>Index of /maven2/org/frege-lang/frege/</title></head>",
                "<body bgcolor=\"white\">",
                "<h1>Index of /maven2/org/frege-lang/frege/</h1><hr><pre><a href=\"../\">../</a>",
                "<a href=\"3.22.367-g2737683/\">3.22.367-g2737683/</a>                                 29-Mar-2015 22:02                   -",
                "<a href=\"3.22.524-gcc99d7e/\">3.22.524-gcc99d7e/</a>                                 17-Apr-2015 02:23                   -",
                "<a href=\"3.23.288-gaa3af0c/\">3.23.288-gaa3af0c/</a>                                 27-Aug-2015 10:24                   -",
                "<a href=\"3.23.370-g898bc8c/\">3.23.370-g898bc8c/</a>                                 20-Oct-2015 02:05                   -",
                "<a href=\"3.23.401-g7c45277/\">3.23.401-g7c45277/</a>                                 21-Nov-2015 21:51                   -",
                "<a href=\"3.23.422-ga05a487/\">3.23.422-ga05a487/</a>                                 27-Nov-2015 01:34                   -",
                "<a href=\"maven-metadata.xml\">maven-metadata.xml</a>                                 27-Nov-2015 01:34                 577",
                "<a href=\"maven-metadata.xml.md5\">maven-metadata.xml.md5</a>                             27-Nov-2015 01:34                  32",
                "<a href=\"maven-metadata.xml.sha1\">maven-metadata.xml.sha1</a>                            27-Nov-2015 01:34                  40",
                "</pre><hr></body>",
                "</html>"
        );
        String[] expectedVersions = new String[]{"3.22.367-g2737683", "3.22.524-gcc99d7e", "3.23.288-gaa3af0c", "3.23.370-g898bc8c", "3.23.401-g7c45277", "3.23.422-ga05a487"};

        List<String> versions = Versions.loadVersionsFrom(lines);

        assertThat(versions, hasItems(expectedVersions));
    }

    @Test
    public void shouldSortVersions() throws Exception {
        Version min = new Version("3.22.0");
        List<String> versions = ImmutableList.of("3.22.367-g2737683", "3.22.524-gcc99d7e", "3.23.288-gaa3af0c", "3.23.370-g898bc8c", "3.23.401-g7c45277", "3.23.422-ga05a487");

        // when
        List<String> sorted = Versions.sortVersions(min, versions);

        // then
        assertThat(sorted.size(), is(versions.size()));
        assertThat(sorted.get(0), is("3.23.422-ga05a487"));
    }
}