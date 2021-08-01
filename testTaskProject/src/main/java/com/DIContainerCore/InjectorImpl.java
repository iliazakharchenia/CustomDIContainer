package com.DIContainerCore;

import com.Interfaces.Injector;
import com.Interfaces.Provider;

public class InjectorImpl implements Injector {

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        return null;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {

    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {

    }
}
