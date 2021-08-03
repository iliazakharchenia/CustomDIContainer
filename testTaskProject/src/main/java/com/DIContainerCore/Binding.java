package com.DIContainerCore;

public class Binding <T> {
    private Class <T> bindedInterface;
    private Class <? extends T> bindedClass;
    private boolean isSingleton;

    public Class <? extends T> getBindedClass() {
        return bindedClass;
    }

    public Class<T> getBindedInterface() {
        return bindedInterface;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    Binding (Class<T> bindedInterface, Class<? extends T> bindedClass, boolean isSingleton) {
        this.bindedInterface = bindedInterface;
        this.bindedClass = bindedClass;
        this.isSingleton = isSingleton;
    }
}
