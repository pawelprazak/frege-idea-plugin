package org.fregelang.plugin.idea.framework;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.net.HttpConfigurable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Versions {

    @VisibleForTesting
    static final Pattern RELEASE_VERSION = Pattern.compile(".+>(?<full>(?<version>\\d+\\.\\d+\\.\\d+)-(?<revision>.*))/<.*");
    private static final String GROUP_FULL = "full";
    private static final String GROUP_VERSION = "version";
    private static final String GROUP_REVISION = "revision";

    public static List<String> loadFregeVersions() {
        try {
            return loadVersionsOf(FREGE);
        } catch (IOException e) {
            throw new DownloadException(e);
        }
    }

    private static List<String> loadVersionsOf(Entity entity) throws IOException {
        List<String> versions = loadVersionsFrom(loadLinesFrom(entity.url));
        if (versions.isEmpty()) {
            return entity.hardcodedVersions;
        }
        return sortVersions(entity.minVersion, versions);
    }

    @VisibleForTesting
    static List<String> sortVersions(Version minVersion, List<String> versions) {
        return versions.stream()
                .map(Version::new)
                .filter(v -> v.greaterThanOrEqual(minVersion))
                .sorted(Comparator.reverseOrder())
                .map(Version::getNumber)
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    static List<String> loadVersionsFrom(List<String> html) throws IOException {
        return html.stream()
                .flatMap(line -> collectGroup(RELEASE_VERSION, GROUP_FULL, line).stream())
                .collect(Collectors.toList());
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

    private static List<String> collectGroup(Pattern pattern, String name, String value) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            builder.add(matcher.group(name));
        }
        return builder.build();
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
    }

    private static final Entity FREGE = new Entity(
            "http://repo1.maven.org/maven2/org/frege-lang/frege/", new Version("3.22.0"),
            ImmutableList.of("3.22.367", "3.22.524", "3.23.288", "3.23.370", "3.23.401", "3.23.422"));
}
