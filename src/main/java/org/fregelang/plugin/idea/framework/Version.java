package org.fregelang.plugin.idea.framework;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Version implements Comparable<Version>, Comparison<Version> {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");
    private final String number;

    public Version(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(@NotNull Version other) {
        int[] digits = digits();
        int[] otherDigits = other.digits();
        return IntStream.range(
                0, Math.min(digits.length, otherDigits.length))
                .mapToObj(i -> Integer.compare(digits[i], otherDigits[i]))
                .filter(r -> r != 0)
                .findFirst()
                .orElse(0);
    }

    private int[] digits() {
        return Version.INTEGER_PATTERN.splitAsStream(number).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public boolean greaterThan(Version other) {
        return compareTo(other) > 0;
    }

    @Override
    public boolean greaterThanOrEqual(Version other) {
        return compareTo(other) >= 0;
    }

    @Override
    public boolean lessThan(Version other) {
        return compareTo(other) < 0;
    }

    @Override
    public boolean lessThanOrEqual(Version other) {
        return compareTo(other) <= 0;
    }

    public Optional<FregeLanguageLevel> toLanguageLevel() {
        return FregeLanguageLevel.from(this);
    }
}