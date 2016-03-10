package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableBiMap;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toMap;

public enum FregeLanguageLevelProxy {

    Frege_3_23(FregeLanguageLevelProxy.Frege_3_23.class),
    Frege_3_22(FregeLanguageLevelProxy.Frege_3_22.class),
    Frege_3_21(FregeLanguageLevelProxy.Frege_3_21.class),
    Frege_3_20(FregeLanguageLevelProxy.Frege_3_20.class);

    private static final ImmutableBiMap<FregeLanguageLevelProxy, Class<? extends FregeLanguageLevel>> levels = ImmutableBiMap.copyOf(
            Arrays.asList(values()).stream().collect(toMap(Function.identity(), FregeLanguageLevelProxy::getType))
    );

    private final Class<? extends FregeLanguageLevel> type;
    private final FregeLanguageLevel instance;

    FregeLanguageLevelProxy(Class<? extends FregeLanguageLevel> type) {
        this.type = type;
        try {
            this.instance = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Class<? extends FregeLanguageLevel> getType() {
        return type;
    }

    public FregeLanguageLevel getInstance() {
        return instance;
    }

    static FregeLanguageLevelProxy levelToProxy(FregeLanguageLevel level) {
        return levels.inverse().get(level.getClass());
    }

    static FregeLanguageLevel proxyToLevel(FregeLanguageLevelProxy level) {
        return level.getInstance();
    }

    public static Optional<FregeLanguageLevel> findFirst(Predicate<FregeLanguageLevel> matcher) {
        return Arrays.asList(values()).stream()
                .map(FregeLanguageLevelProxy::proxyToLevel)
                .filter(matcher)
                .findFirst();
    }

    public static FregeLanguageLevel[] findAll() {
        return Arrays.asList(values()).stream()
                .map(FregeLanguageLevelProxy::getInstance)
                .toArray(FregeLanguageLevel[]::new);
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
