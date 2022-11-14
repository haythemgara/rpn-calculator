package com.exalt.rpn.repository.model;

import org.springframework.util.CollectionUtils;

public class Stack {

    private long id;
    private java.util.Stack<Integer> values;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public java.util.Stack<Integer> getValues() {
        if (CollectionUtils.isEmpty(values)) {
            return new java.util.Stack<>();
        }
        return values;
    }

    public void setValues(java.util.Stack<Integer> values) {
        this.values = values;
    }
}
