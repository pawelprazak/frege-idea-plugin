package org.fregelang.plugin.idea.framework;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Version implements Comparable<Version>, Comparison<Version> {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\.|-");

    private final String number;

    public Version(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(@NotNull Version other) {
        String[] thisFragments = fragments();
        String[] otherFragments = other.fragments();
        return IntStream.range(
                0, Math.min(thisFragments.length, otherFragments.length))
                .mapToObj(i -> compareFragment(thisFragments[i], otherFragments[i]))
                .filter(r -> r != 0)
                .findFirst()
                .orElse(0);
    }

    private int compareFragment(String thisFragment, String otherFragment) {
        boolean bothAreIntegers = INTEGER_PATTERN.matcher(thisFragment).matches()
                && INTEGER_PATTERN.matcher(otherFragment).matches();
        if (bothAreIntegers) {
            return Integer.compare(Integer.parseInt(thisFragment), Integer.parseInt(otherFragment));
        } else {
            return thisFragment.compareToIgnoreCase(otherFragment);
        }
    }

    @VisibleForTesting
    String[] fragments() {
        return Version.SPLIT_PATTERN.splitAsStream(number).toArray(String[]::new);
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("number", number)
                .toString();
    }
}