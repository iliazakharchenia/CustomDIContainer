package com.DIContainerCore;

import com.Interfaces.Provider;

public class ProviderImpl <T> implements Provider {
    private T instance;

    ProviderImpl (T instance) {
        this.instance = instance;
    }

    @Override
    public T getInstance() throws NullPointerException {
        if (instance == null) { throw new NullPointerException("Instance reference is not defined"); }
        return instance;
    }
}
