package io.bunting.diydi;

import java.util.Optional;

/**
 * TODO: Document this class
 */
@FunctionalInterface
public interface DependencyResolver {
    <T> Optional<Class<? extends T>> resolve(Dependency<T> dependency);
}
