package com.dic.injector;

import com.dic.provider.Provider;
import com.dic.exception.*;

import java.lang.reflect.InvocationTargetException;

public interface Injector {

    <T> Provider<T> getProvider(Class<T> type) throws ConstructorNotFoundException, TooManyConstructorsException,
            BindingNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException,
            ParameterIsNotReferenceTypeException;

    <T> void bind(Class<T> intf, Class<? extends T> impl) throws BindingAlreadyExistsException;

    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) throws BindingAlreadyExistsException;
    
}
