package org.ioopm.calculator.parser;

/**
 * @file SyntaxErrorException.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class SyntaxErrorException
 * @brief Class for RuntimeException SyntaxErrorException
 */
public class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException() {
        super();
    }
    public SyntaxErrorException(String msg) {
        super(msg);
    }
}
