package com.exalt.rpn.repository.model;

public enum OperandEnum {

    DIVISION("/"),
    SUBSTRACTION("-"),
    MULTIPLICATION("*"),
    ADDITION("+");

    private String value;

    OperandEnum(String s) {
        this.value = s;
    }

    public String value() {
        return value;
    }

    public static OperandEnum getOperandEnumName(final String operand) {

        for (OperandEnum oprname : OperandEnum.values()) {
            if (operand.equals(oprname.value)) {
                return oprname;
            }
        }
        return null;
    }
}
