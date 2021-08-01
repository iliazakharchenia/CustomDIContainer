package com.Interfaces;

import com.Exceptions.ConstructorNotFoundException;
import com.Exceptions.TooManyConstructorsException;

public interface Injector {

    <T> Provider<T> getProvider(Class<T> type) throws ConstructorNotFoundException, TooManyConstructorsException;

    <T> void bind(Class<T> intf, Class<? extends T> impl);

    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl);
    
}
