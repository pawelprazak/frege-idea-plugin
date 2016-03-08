package org.fregelang.plugin.idea.framework;

public interface Comparison<T> {
    boolean greaterThan(T other);

    boolean greaterThanOrEqual(T other);

    boolean lessThan(T other);

    boolean lessThanOrEqual(T other);
}
