package org.fregelang.plugin.idea.framework;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FregeLanguageLevel implements Named, Comparison<FregeLanguageLevel> {

    public static final FregeLanguageLevel DEFAULT = FregeLanguageLevelProxy.Frege_3_23.getInstance();
    public static final FregeLanguageLevel[] VALUES = FregeLanguageLevelProxy.findAll();

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
}
