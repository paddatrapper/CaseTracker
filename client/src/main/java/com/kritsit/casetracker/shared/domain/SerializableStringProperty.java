package com.kritsit.casetracker.shared.domain;

import javafx.beans.property.StringPropertyBase;
import java.io.Serializable;

public class SerializableStringProperty extends StringPropertyBase implements Serializable {
    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";
    private static final long serialVersionUID = 10L;

    private final Object bean;
    private final String name;

    public SerializableStringProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public SerializableStringProperty(String initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public SerializableStringProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public SerializableStringProperty(Object bean, String name, String initialValue) {
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
