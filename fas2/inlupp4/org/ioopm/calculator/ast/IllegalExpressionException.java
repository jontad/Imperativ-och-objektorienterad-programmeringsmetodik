package org.ioopm.calculator.ast;

/**
 * @file IllegalExpressionException.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class IllegalExpressionException
 * @brief Class for RuntimeException IllegalExpressionException
 */
public class IllegalExpressionException extends RuntimeException{
    public IllegalExpressionException() {
        super();
    }
    public IllegalExpressionException(String msg) {
        super(msg);
    }
}
