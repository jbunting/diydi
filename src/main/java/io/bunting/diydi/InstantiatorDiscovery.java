package io.bunting.diydi;

import java.lang.reflect.Method;

/**
 * TODO: Document this class
 */
@FunctionalInterface
public interface InstantiatorDiscovery {
    <T> Method discover(Class<T> type);
}
