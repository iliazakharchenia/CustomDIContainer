package com.DIContainerCore;

import com.Annotations.Inject;
import com.Exceptions.BindingAlreadyExistsException;
import com.Exceptions.ConstructorNotFoundException;
import com.Exceptions.TooManyConstructorsException;
import com.Interfaces.Injector;
import com.Interfaces.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class InjectorImpl implements Injector {

    private ArrayList<Binding> bindinglist = new ArrayList<Binding>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) throws ConstructorNotFoundException,
                                                                TooManyConstructorsException {
        if (hasMultipleInjections(type)) {
            throw new TooManyConstructorsException("Founded more than one constructor with @Inject");
        }

        return null;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) throws BindingAlreadyExistsException {
        Binding <T> binding = new Binding <T> (intf, impl, false);
        if (!bindinglist.contains(binding)) {
            bindinglist.add(binding);
        }
        else {
            throw new BindingAlreadyExistsException("Such binding is already exists");
        }
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) throws BindingAlreadyExistsException {
        Binding <T> binding = new Binding <T> (intf, impl, true);
        Binding <T> notSingletonBinding = new Binding <T>(intf, impl, false);
        if (bindinglist.contains(binding)) {
            throw new BindingAlreadyExistsException("Such binding is already exists");
        }
        else {
            if (!bindinglist.contains(notSingletonBinding)) {
                bindinglist.add(binding);
            }
            else {
                throw new BindingAlreadyExistsException("Such not singleton binding is already exists");
            }
        }
    }

    private ArrayList<Annotation> getConstructorsInjectAnnotations(Class injectedClass) {
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
        ArrayList<Annotation> annotations = getConstructorsInjectAnnotations(injectedClass);

        return annotations.size() == 0;
    }

    private boolean hasMultipleInjections(Class injectedClass) throws ConstructorNotFoundException {
        if (isNoSuchInjections(injectedClass)) {
            throw new ConstructorNotFoundException("No constructor with @Inject found");
        }
        ArrayList<Annotation> annotations = getConstructorsInjectAnnotations(injectedClass);

        return annotations.size() > 1;
    }
}
