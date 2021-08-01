package com.DIContainerCore;

import com.Annotations.Inject;
import com.Exceptions.ConstructorNotFoundException;
import com.Exceptions.TooManyConstructorsException;
import com.Interfaces.Injector;
import com.Interfaces.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class InjectorImpl implements Injector {

    @Override
    public <T> Provider<T> getProvider(Class<T> type) throws ConstructorNotFoundException,
                                                                TooManyConstructorsException {
        if (hasMultipleInjections(type)) {
            throw new TooManyConstructorsException(" Founded more than one constructor with @Inject ");
        }

        return null;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {

    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {

    }

    private ArrayList<Annotation> getContructorsInjectAnnotations(Class injectedClass) {
        Constructor<?>[] constructors = injectedClass.getConstructors();
        ArrayList<Annotation> annotations = new ArrayList<Annotation>();
        for (int i=0; i<constructors.length; i++) {
            if (constructors[i].getAnnotation(Inject.class) != null) {
                annotations.add(constructors[i].getAnnotation(Inject.class));
            }
        }

        return annotations;
    }

    private boolean isNoSuchInjections(Class injectedClass) {
        ArrayList<Annotation> annotations = getContructorsInjectAnnotations(injectedClass);

        return annotations.size() == 0;
    }

    private boolean hasMultipleInjections(Class injectedClass) throws ConstructorNotFoundException {
        if (isNoSuchInjections(injectedClass)) {
            throw new ConstructorNotFoundException(" No constructor with @Inject found ");
        }
        ArrayList<Annotation> annotations = getContructorsInjectAnnotations(injectedClass);

        return annotations.size() > 1;
    }
}
