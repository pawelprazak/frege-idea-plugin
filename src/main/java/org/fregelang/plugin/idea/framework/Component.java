package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableSet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Objects.toStringHelper;
import static java.lang.String.format;

public class Component {

    private final Artifact artifact;
    private final Kind kind;
    private final Optional<Version> version;
    private final File file;

    public Component(Artifact artifact, Kind kind, Optional<Version> version, File file) {
        this.artifact = artifact;
        this.kind = kind;
        this.version = version;
        this.file = file;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public File getFile() {
        return file;
    }

    public Kind getKind() {
        return kind;
    }

    public Optional<Version> getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(artifact, component.artifact) &&
                Objects.equals(kind, component.kind) &&
                Objects.equals(version, component.version) &&
                Objects.equals(file, component.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifact, kind, version, file);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("artifact", artifact)
                .add("kind", kind)
                .add("version", version)
                .add("file", file)
                .toString();
    }

    public static List<Component> discoverIn(List<File> files) {
        List<ComponentPattern> patterns = Artifact.VALUES.stream()
                .flatMap(artifact ->
                        Kind.VALUES.stream().map(kind ->
                                new ComponentPattern(kind.patternFor(artifact.getPrefix()), artifact, kind)))
                .collect(Collectors.toList());

        return files.stream()
                .filter(it -> it.isFile() && it.getName().endsWith(".jar"))
                .flatMap(file -> patterns.stream()
                        .filter(c -> c.getPattern().matcher(file.getName()).matches())
                        .map(c -> new Component(c.getArtifact(), c.getKind(), c.getArtifact().versionOf(file), file)))
                .collect(Collectors.toList());
    }

    public static class Artifact {

        public static final ImmutableSet<Artifact> VALUES = ImmutableSet.of(
                new FregeLibrary(), new FregeCompiler()
        );

        private final String prefix;
        private final Optional<String> resource;

        public Artifact(String prefix, Optional<String> resource) {
            this.prefix = prefix;
            this.resource = resource;
        }

        public String getPrefix() {
            return prefix;
        }

        public Optional<String> getResource() {
            return resource;
        }

        public String getTitle() {
            return prefix + "*.jar";
        }

        public Optional<Version> versionOf(File file) {
            Optional<Version> external = externalVersionOf(file);
            Optional<Version> internal = internalVersionOf(file);
            return external.isPresent() ? external : internal;
        }

        private Optional<Version> externalVersionOf(File file) {
            Pattern fileNamePattern = Pattern.compile(prefix + "-(.*?)(?:-src|-sources|-javadoc).jar");

            Matcher matcher = fileNamePattern.matcher(file.getName());
            if (matcher.matches()) {
                String number = matcher.group();
                return Optional.of(new Version(number));
            } else {
                return Optional.empty();
            }
        }

        private Optional<Version> internalVersionOf(File file) {
            return resource
                    .flatMap(r -> Artifact.readProperty(file, r, "Bundle-Version"))
                    .map(Version::new);
        }

        private static Optional<String> readProperty(File file, String resource, String name) {
            try {
                URL url = new URL(format("jar:%s!/%s", file.toURI().toString(), resource));
                return Optional.of(url.openStream())
                        .flatMap(it -> {
                            try (InputStream s = new BufferedInputStream(it)) {
                                return readProperty(s, name);
                            } catch (IOException e) {
                                return Optional.empty();
                            }
                        });
            } catch (IOException e) {
                return Optional.empty();
            }
        }

        private static Optional<String> readProperty(InputStream input, String name) throws IOException {
            Properties properties = new Properties();
            properties.load(input);
            return Optional.of(properties.getProperty(name));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Artifact artifact = (Artifact) o;
            return Objects.equals(prefix, artifact.prefix) &&
                    Objects.equals(resource, artifact.resource);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prefix, resource);
        }

        @Override
        public String toString() {
            return com.google.common.base.Objects.toStringHelper(this)
                    .add("prefix", prefix)
                    .add("resource", resource)
                    .toString();
        }

        public static class FregeLibrary extends Artifact {
            public static final FregeLibrary INSTANCE = new FregeLibrary();

            public FregeLibrary() {
                super("frege", Optional.of("META-INF/MANIFEST.MF"));
            }
        }

        public static class FregeCompiler extends Artifact {
            public static final FregeCompiler INSTANCE = new FregeCompiler();

            private FregeCompiler() {
                super("frege-compiler", Optional.of("META-INF/MANIFEST.MF"));
            }
        }
    }

    public static abstract class Kind {

        public static final ImmutableSet<Kind> VALUES = ImmutableSet.of(new Binaries(), new Sources(), new Docs());

        private final String regex;

        public Kind(String regex) {
            this.regex = regex;
        }

        public Pattern patternFor(String prefix) {
            return Pattern.compile(prefix + regex);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Kind kind = (Kind) o;
            return Objects.equals(regex, kind.regex);
        }

        @Override
        public final int hashCode() {
            return Objects.hash(regex);
        }

        @Override
        public String toString() {
            return com.google.common.base.Objects.toStringHelper(this)
                    .add("regex", regex)
                    .toString();
        }

        public static class Binaries extends Kind {
            public static Binaries INSTANCE = new Binaries();

            private Binaries() {
                super(".*(?<!-src|-sources|-javadoc)\\.jar");
            }
        }

        public static class Sources extends Kind {
            public static Sources INSTANCE = new Sources();

            public Sources() {
                super(".*-(src|sources)\\.jar");
            }
        }

        public static class Docs extends Kind {
            public static Docs INSTANCE = new Docs();

            public Docs() {
                super(".*-javadoc\\.jar");
            }
        }
    }

    private static class ComponentPattern {

        private final Pattern pattern;
        private final Artifact artifact;
        private final Kind kind;

        public ComponentPattern(Pattern pattern, Artifact artifact, Kind kind) {
            this.pattern = pattern;
            this.artifact = artifact;
            this.kind = kind;
        }

        public Artifact getArtifact() {
            return artifact;
        }

        public Kind getKind() {
            return kind;
        }

        public Pattern getPattern() {
            return pattern;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComponentPattern that = (ComponentPattern) o;
            return Objects.equals(pattern, that.pattern) &&
                    Objects.equals(artifact, that.artifact) &&
                    Objects.equals(kind, that.kind);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pattern, artifact, kind);
        }

        @Override
        public String toString() {
            return com.google.common.base.Objects.toStringHelper(this)
                    .add("artifact", artifact)
                    .add("pattern", pattern)
                    .add("kind", kind)
                    .toString();
        }
    }
}



