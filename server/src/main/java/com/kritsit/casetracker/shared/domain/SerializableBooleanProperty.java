package com.kritsit.casetracker.shared.domain;

import javafx.beans.property.BooleanPropertyBase;
import java.io.Serializable;

public class SerializableBooleanProperty extends BooleanPropertyBase implements Serializable {
    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";
    private static final long serialVersionUID = 10L;

    private final Object bean;
    private final String name;

    public SerializableBooleanProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public SerializableBooleanProperty(boolean initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public SerializableBooleanProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public SerializableBooleanProperty(Object bean, String name, boolean initialValue) {
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
