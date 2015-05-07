package com.kritsit.casetracker.shared.domain;

import javafx.beans.property.ObjectPropertyBase;
import java.io.Serializable;

public class SerializableObjectProperty<T> extends ObjectPropertyBase<T> implements Serializable {
    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";
    private static final long serialVersionUID = 10L;

    private final Object bean;
    private final String name;

    public SerializableObjectProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public SerializableObjectProperty(T initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public SerializableObjectProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public SerializableObjectProperty(Object bean, String name, T initialValue) {
        super(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }
}
