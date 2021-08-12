package com.dic.injector;

import com.dic.binding.Binding;
import com.dic.provider.ProviderImpl;
import com.dic.exception.*;
import com.dic.provider.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class InjectorImpl implements Injector {

    private ArrayList<Binding> bindinglist = new ArrayList<Binding>();
    private ArrayList<Object> singletonInstances = new ArrayList<Object>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) throws IllegalAccessException,
            InvocationTargetException, InstantiationException {
        if (hasMultipleInjections(type)) {
            throw new TooManyConstructorsException("Founded more than one constructor with @Inject");
        }
        Class[] parametersTypes = getTypeConstructorArgumentsTypes(type);
        if (parametersTypes.length == 0) {
            T instance = (T) getConstructorWithInjectAnnotation(type.getDeclaredConstructors()).newInstance();
            ProviderImpl<T> provider = new ProviderImpl<T>(instance);

            return provider;
        }
        ArrayList<Object> implementations = new ArrayList<Object>();
        for (int i = 0; i < parametersTypes.length; i++) {
            if (parametersTypes[i].isPrimitive()) {
                throw new ParameterIsNotReferenceTypeException("Parameter on position "+i+" is not Reference type");
            }
            if (!isSuchBindingExists(parametersTypes[i])) {
                throw new BindingNotFoundException("Binding not exist for this parameter type: " + parametersTypes[i]);
            }
            if (!isSingletonBindingExist(parametersTypes[i])) {
                implementations.add(i, getRelatedClassForThisInterface(parametersTypes[i]).getDeclaredConstructors()[0].newInstance());
            } else {
                if (isSingletonInstanceExists(parametersTypes[i])) {
                    implementations.add(i, singletonInstances.get(getIndexOfSingletonInstance(parametersTypes[i])));
                } else {
                    singletonInstances.add(getRelatedClassForThisInterface(parametersTypes[i]).getDeclaredConstructors()[0].newInstance());
                    implementations.add(i, singletonInstances.get(singletonInstances.size()-1));
                }
            }
        }
        T instance = (T) getConstructorWithInjectAnnotation(type.getDeclaredConstructors()).newInstance(implementations.toArray());
        ProviderImpl <T> provider = new ProviderImpl<T>(instance);

        return provider;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        Binding <T> binding = new Binding <T> (intf, impl, false);
        Binding <T> singletonBinding = new Binding <T> (intf, impl, true);
        if (!bindinglist.contains(binding)) {
            if (bindinglist.contains(singletonBinding)) {
                throw new BindingAlreadyExistsException("Such singleton binding is already exists");
            }
            bindinglist.add(binding);
        } else {
            throw new BindingAlreadyExistsException("Such binding is already exists");
        }
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
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

    private int getIndexOfSingletonInstance(Class type) {
        for (int i=0; i<singletonInstances.size(); i++) {
            if (getRelatedClassForThisInterface(type) == singletonInstances.get(i).getClass()) return i;
        }

        return -1;
    }

    private Class getRelatedClassForThisInterface(Class type) {
        for (int i = 0; i < bindinglist.size(); i++) {
            if (bindinglist.get(i).getBindedInterface() == type) return bindinglist.get(i).getBindedClass();
        }

        return null;
    }

    private boolean isSingletonInstanceExists(Class type) {
        if (singletonInstances.isEmpty()) {
            return false;
        }
        ArrayList<Class> instances = new ArrayList<Class>();
        Class singleton = null;
        for (int i = 0; i < bindinglist.size(); i++) {
            if (bindinglist.get(i).getBindedInterface() == type
                    && bindinglist.get(i).isSingleton()) {
                singleton = bindinglist.get(i).getBindedClass();
                break;
            }
        }
        for (int i = 0; i < singletonInstances.size(); i++) {
            if (singletonInstances.get(i).getClass() == singleton) {
                return true;
            }
        }

        return false;
    }

    private Constructor getConstructorWithInjectAnnotation(Constructor[] constructors) {
        for (int i = 0; i < constructors.length; i++) {
            if (constructors[i].getAnnotation(Inject.class) != null) {
                return constructors[i];
            }
        }

        return null;
    }

    private boolean isSingletonBindingExist(Class type) {
        boolean isSingleton = false;
        for (int i = 0; i < bindinglist.size(); i++) {
            if (bindinglist.get(i).getBindedInterface() == type
                    && bindinglist.get(i).isSingleton()) {
                isSingleton = true;
            }
        }

        return isSingleton;
    }

    private boolean isSuchBindingExists(Class type) {
        boolean isSuchBindingExist = false;
        for (int i = 0; i < bindinglist.size(); i++) {
            if (bindinglist.get(i).getBindedInterface() == type) {
                isSuchBindingExist = true;
            }
        }

        return isSuchBindingExist;
    }

    private ArrayList<Annotation> getConstructorsWithInjectAnnotations(Class injectedClass) {
        Constructor<?>[] constructors = injectedClass.getDeclaredConstructors();
        ArrayList<Annotation> annotations = new ArrayList<Annotation>();
        for (int i=0; i<constructors.length; i++) {
            if (constructors[i].getAnnotation(Inject.class) != null) {
                annotations.add(constructors[i].getAnnotation(Inject.class));
            }
        }

        return annotations;
    }

    private <T> Class[] getTypeConstructorArgumentsTypes(Class<T> type) {
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        Constructor constructor = getConstructorWithInjectAnnotation(constructors);
        Class[] parameterTypes = constructor.getParameterTypes();

        return parameterTypes;
    }

    private boolean isNoSuchInjections(Class injectedClass) {
        ArrayList<Annotation> annotations = getConstructorsWithInjectAnnotations(injectedClass);

        return annotations.size() == 0;
    }

    private boolean hasMultipleInjections(Class injectedClass) {
        if (isNoSuchInjections(injectedClass)) {
            throw new ConstructorNotFoundException("No constructor with @Inject found");
        }
        ArrayList<Annotation> annotations = getConstructorsWithInjectAnnotations(injectedClass);

        return annotations.size() > 1;
    }
}
