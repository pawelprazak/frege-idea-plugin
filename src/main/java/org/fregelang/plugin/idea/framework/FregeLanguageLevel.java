package org.fregelang.plugin.idea.framework;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FregeLanguageLevel implements Named, Comparison<FregeLanguageLevel> {

    public static final FregeLanguageLevel DEFAULT = new Frege_3_23();
    public static final FregeLanguageLevel[] VALUES = new FregeLanguageLevel[] {
            new Frege_3_23(),
            new Frege_3_22(),
            new Frege_3_21(),
            new Frege_3_20()
    };

    private final int ordinal;
    private final String version;

    public FregeLanguageLevel(int ordinal, String version) {
        this.ordinal = ordinal;
        this.version = version;
    }

    @NotNull
    @Override
    public String getName() {
        return "Frege " + version;
    }

    public String getVersion() {
        return version;
    }

    FregeLanguageLevelProxy proxy() {
        return FregeLanguageLevelProxy.levelToProxy(this);
    }

    public boolean greaterThan(FregeLanguageLevel level) {
        return ordinal > level.ordinal;
    }

    public boolean greaterThanOrEqual(FregeLanguageLevel level) {
        return ordinal >= level.ordinal;
    }

    public boolean lessThan(FregeLanguageLevel level) {
        return ordinal < level.ordinal;
    }

    public boolean lessThanOrEqual(FregeLanguageLevel level) {
        return ordinal <= level.ordinal;
    }

    public static FregeLanguageLevel from(FregeLanguageLevelProxy languageLevel) {
        return languageLevel.getInstance();
    }

    public static Optional<FregeLanguageLevel> from(Version version) {
        return FregeLanguageLevelProxy.findFirst(v -> version.getNumber().startsWith(v.getVersion()));
    }

    public static class Frege_3_20 extends FregeLanguageLevel {
        public Frege_3_20() {
            super(0, "3.20");
        }
    }

    public static class Frege_3_21 extends FregeLanguageLevel {
        public Frege_3_21() {
            super(1, "3.21");
        }
    }

    public static class Frege_3_22 extends FregeLanguageLevel {
        public Frege_3_22() {
            super(2, "3.22");
        }
    }

    public static class Frege_3_23 extends FregeLanguageLevel {
        public Frege_3_23() {
            super(3, "3.23");
        }
    }
}
