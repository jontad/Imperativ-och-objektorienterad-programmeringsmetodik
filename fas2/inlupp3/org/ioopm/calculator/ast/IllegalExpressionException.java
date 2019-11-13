package org.ioopm.calculator.ast;

public class IllegalExpressionException extends RuntimeException{
    public IllegalExpressionException() {
        super();
    }
    public IllegalExpressionException(String msg) {
        super(msg);
    }
}
