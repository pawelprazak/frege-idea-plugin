package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableList;
import com.intellij.util.net.HttpConfigurable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Versions {

    private static final Pattern RELEASE_VERSION_LINE = Pattern.compile(".+>(\\d+\\.\\d+\\.\\d+)/<.*");


    public static List<String> loadScalaVersions() {
        try {
            return loadVersionsOf(FREGE);
        } catch (IOException e) {
            throw new DownloadException(e);
        }
    }

    private static List<String> loadVersionsOf(Entity entity) throws IOException {
        List<String> versions = loadVersionsFrom(entity.url);
        if (versions.isEmpty()) {
            return entity.hardcodedVersions;
        }
        return versions.stream()
                .map(Version::new)
                .filter(v -> v.greaterThanOrEqual(entity.minVersion))
                .sorted(Comparator.reverseOrder())
                .map(Version::getNumber)
                .collect(Collectors.toList());
    }

    private static List<String> loadVersionsFrom(String url) throws IOException {
        return loadLinesFrom(url).stream().flatMap(RELEASE_VERSION_LINE::splitAsStream).collect(Collectors.toList());
    }

    private static List<String> loadLinesFrom(String url) throws IOException {
        HttpConfigurable http = HttpConfigurable.getInstance();
        HttpURLConnection connection = null;
        List<String> lines = new ArrayList<>();
        try {
            connection = http.openHttpConnection(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
                lines.add(line);
            in.close();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return ImmutableList.copyOf(lines);
    }

    private static class Entity {

        private final String url;
        private final Version minVersion;
        private final List<String> hardcodedVersions;

        Entity(String url, Version minVersion, List<String> hardcodedVersions) {
            this.url = url;
            this.minVersion = minVersion;

            this.hardcodedVersions = hardcodedVersions;
        }

        String defaultVersion() {
            return hardcodedVersions.get(hardcodedVersions.size() - 1);
        }
    }

    private static final Entity FREGE = new Entity(
            "http://repo1.maven.org/maven2/org/frege-lang/frege/",
            new Version("3.22.0"), ImmutableList.of("3.22.367-g2737683", "3.22.524-gcc99d7e", "3.23.288-gaa3af0c", "3.23.370-g898bc8c", "3.23.401-g7c45277", "3.23.422-ga05a487"));
}
