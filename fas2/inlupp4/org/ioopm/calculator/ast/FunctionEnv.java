package org.ioopm.calculator.ast;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;

  /**
   * @brief Environment used to store and evaluate different functions. 
   * Each string (or function name) in the environment is mapped to a function.
   */ 
public class FunctionEnv extends HashMap<String, Sequence>{}
