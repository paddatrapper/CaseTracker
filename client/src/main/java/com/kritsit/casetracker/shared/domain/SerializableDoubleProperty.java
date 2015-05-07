package com.kritsit.casetracker.shared.domain;

import javafx.beans.property.DoublePropertyBase;
import java.io.Serializable;

public class SerializableDoubleProperty extends DoublePropertyBase implements Serializable {
    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";
    private static final long serialVersionUID = 10L;

    private final Object bean;
    private final String name;

    public SerializableDoubleProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    public SerializableDoubleProperty(double initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    public SerializableDoubleProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    public SerializableDoubleProperty(Object bean, String name, double initialValue) {
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
