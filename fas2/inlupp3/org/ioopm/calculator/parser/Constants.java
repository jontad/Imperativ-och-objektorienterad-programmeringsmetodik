package org.ioopm.calculator.parser;

/**
 * @file Constants.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Constants
 * @brief Class for defining named constants
 */
import java.util.HashMap;

public class Constants{
    public static final HashMap<String, Double> namedConstants = new HashMap<>();

    static {
        Constants.namedConstants.put("pi", Math.PI);
        Constants.namedConstants.put("e",  Math.E);
	Constants.namedConstants.put("Answer",  42.0);
	Constants.namedConstants.put("L", 6.022140857 * Math.pow(10, 23));
    }
}
