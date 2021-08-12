package com.dic.binding;

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

    public Binding(Class<T> bindedInterface, Class<? extends T> bindedClass, boolean isSingleton) {
        this.bindedInterface = bindedInterface;
        this.bindedClass = bindedClass;
        this.isSingleton = isSingleton;
    }

    @Override
    public boolean equals(Object o) {
        Binding binding = (Binding) o;
        if (this.getBindedClass() == ((Binding) o).getBindedClass()
                && this.getBindedInterface() == ((Binding) o).getBindedInterface()
                && this.isSingleton() == ((Binding) o).isSingleton()) return true;

        return false;
    }
}
