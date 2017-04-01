# `diydi`

Do-It-Yourself Dependency Injection

The [Dependency Injection][DI] (DI) pattern is probably the most recognized
[Inversion of Control][IoC] pattern in the Java world. Several popular frameworks 
exist for the pattern, 3 of the most popular being [Dagger][], [Spring][], and
[Guice][]. In addition to these popular frameworks, the DI pattern works quite
well without a framework.

   [DI]: https://martinfowler.com/articles/injection.html
   [IoC]: https://martinfowler.com/bliki/InversionOfControl.html
   [Dagger]: https://square.github.io/dagger/
   [Spring]: http://spring.io/
   [Guice]: https://github.com/google/guice

I have, on several occasions, discovered that I want some sort of framework for
DI, but the popular ones are more complex than is appropriate for my project.
Maybe I have teammates who struggle with the ideas behind DI frameworks. Maybe
the popular frameworks allow patterns or functionality that is incompatible with
the rest of my project. Whatever the reason, I find that I want simple DI, with 
a small set of rules that I define myself. I've written this sort of custom, 
simple DI framework on more than one occasion now. The point of DIY DI is to 
make this sort of endeavor simple and straightforward -- solving the reflection
and management issues once. A project, library, or framework building on top
of DIY DI simply needs to define a ruleset for fetching objects and then use the
provided factory to instantiate objects.


## Design Principles

1. All of the hard stuff is handled by the library.
2. `diydi` does not expose any dependencies other than `javax.inject`.
2. `diydi` does not side step any Java visibility rules. Objects injected must 
   be visible to it.
3. `diydi` classes and packages are not exposed to injected objects.
4. `diydi` by default leverages the `javax.inject` set of annotations.
5. All customizations ship with "principle of least surprise"-based default 
   implementations.
6. The implementor can pick and choose which areas to customize and keep default 
   implementations elsewhere.
   
## Using `diydi`

```

import io.bunting.diydi.*;

public class Main {
  public static void main(String ...args) {
    DIFactory factory = DIFactory.create("myDI")
                                 .withInstantiatorDiscovery(InstantiatorDiscovery::defaultMechanism)
                                 .build();
    
    MyObject object = factory.access(MyObject.class);
  }
}

```



### Instantiation

By default, `diydi` will instantiate objects using a public constructor. To 
qualify for instantiation, a class must either: 
1. have no constructor, 
2. have exactly one public constructor
3. have exactly one public constructor annotated with `@Inject`
4. have exactly one static method annotated with `@Inject` that returns this 
   type or a subtype

Implementors may override the discovery of an instantiation method by providing
a `InstantiatorDiscovery` implementation. It has the single method 
signature:

```
@Nonnull
<T> Executable discover(Class<T> type);
```

### Dependency Detection

By default, `diydi` will consider the following to be dependencies of a given
class:

1. Any class specified as a parameter to the instantiation method.
2. Any class specified as a parameter on an instance method which is annotated 
   with `@Inject`.

Implementors may override the detection of dependencies by providing a 
`DependencyDetector` implementation. It has the single method signature:

```
@Nonnull
ResolvedDependencies detect(Method<T> instantiator);
```

### Dependency Resolution

By default, `diydi` will resolve a dependency by calling the `getRawType` method
on the `Dependency` object and returning that class.

Implementors may override the resolution of a dependency by providing a 
`DependencyResolver` implementation.

```
@Nonnull
<T> Optional<Class<? extends T>> resolve(Dependency<T> dependency);
```

### Post Process

Implementors may add additional post processing of an instantiated object by 
providing a `PostProcessor` implementation.

```
@Nonnull
<T> T postProcess(T object, CollectedDependencies dependencies);
```

