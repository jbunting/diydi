package io.bunting.diydi;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;

/**
 * TODO: Document this class
 */
@FunctionalInterface
interface DependencyDetector {
    ResolvedDependencies detect(Executable instantiator);
}
