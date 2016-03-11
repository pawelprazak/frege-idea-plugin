package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableBiMap;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

public enum FregeLanguageLevelProxy {

    Frege_3_23(FregeLanguageLevel.Frege_3_23.class),
    Frege_3_22(FregeLanguageLevel.Frege_3_22.class),
    Frege_3_21(FregeLanguageLevel.Frege_3_21.class),
    Frege_3_20(FregeLanguageLevel.Frege_3_20.class);

    private static final ImmutableBiMap<FregeLanguageLevelProxy, Class<? extends FregeLanguageLevel>> levels = ImmutableBiMap.copyOf(
            Arrays.asList(values()).stream().collect(toMap(Function.identity(), FregeLanguageLevelProxy::getType))
    );

    private final Class<? extends FregeLanguageLevel> type;
    private final FregeLanguageLevel instance;

    FregeLanguageLevelProxy(Class<? extends FregeLanguageLevel> type) {
        this.type = requireNonNull(type);
        try {
            this.instance = type.newInstance();
            requireNonNull(this.instance);
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

    static FregeLanguageLevel proxyToLevel(FregeLanguageLevelProxy proxy) {
        return proxy.getInstance();
    }

    public static Optional<FregeLanguageLevel> findFirst(Predicate<FregeLanguageLevel> matcher) {
        return Arrays.asList(values()).stream()
                .map(FregeLanguageLevelProxy::proxyToLevel)
                .filter(matcher)
                .findFirst();
    }
}
