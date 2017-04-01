package io.bunting.diydi;

import java.lang.reflect.Executable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * TODO: Document this class
 */
public class Instantiator<T> {

    private final Executable executable;

    public Instantiator(Class<T> type, Executable executable) {
        Class<?> returnType = convert(executable.getAnnotatedReturnType().getType()).orElseThrow(() -> new IllegalArgumentException(executable + " does not return type " + type.getName()));
        if (!type.isAssignableFrom(returnType)) {
            throw new IllegalArgumentException(executable + " does not return type " + type.getName());
        }
        this.executable = executable;
    }

    private Optional<Class> convert(Type type) {
        if (type instanceof Class) {
            return Optional.of((Class) type);
        } else if (type instanceof ParameterizedType) {
            return convert(((ParameterizedType) type).getRawType());
        } else {
            return Optional.empty();
        }
    }
}
