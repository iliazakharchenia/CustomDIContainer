package com.Interfaces;

import com.Exceptions.BindingAlreadyExistsException;
import com.Exceptions.ConstructorNotFoundException;
import com.Exceptions.TooManyConstructorsException;

public interface Injector {

    <T> Provider<T> getProvider(Class<T> type) throws ConstructorNotFoundException, TooManyConstructorsException;

    <T> void bind(Class<T> intf, Class<? extends T> impl) throws BindingAlreadyExistsException;

    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) throws BindingAlreadyExistsException;
    
}
