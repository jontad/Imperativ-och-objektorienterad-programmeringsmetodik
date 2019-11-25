package org.ioopm.calculator.ast;
import java.util.HashMap;

  /**
   * @brief Environment used to store and evaluate different expressions. 
   * Each variable in the environment is mapped to an expression.
   */ 
public class Environment extends HashMap<Variable, SymbolicExpression>{}
