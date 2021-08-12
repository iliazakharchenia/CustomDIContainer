package com.dic.provider;

public class ProviderImpl <T> implements Provider {

    private T instance;

    public ProviderImpl(T instance) {
        this.instance = instance;
    }

    @Override
    public T getInstance() throws NullPointerException {
        if (instance == null) { throw new NullPointerException("Instance reference is not defined"); }
        return instance;
    }
}
