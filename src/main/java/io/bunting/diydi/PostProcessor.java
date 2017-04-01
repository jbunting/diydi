package io.bunting.diydi;

/**
 * TODO: Document this class
 */
@FunctionalInterface
public interface PostProcessor {
    <T> T postProcess(T object, CollectedDependencies dependencies);
}
