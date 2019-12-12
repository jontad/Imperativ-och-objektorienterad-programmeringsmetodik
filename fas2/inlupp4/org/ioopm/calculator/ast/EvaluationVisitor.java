package org.ioopm.calculator.ast;
import java.util.Stack;
import java.util.LinkedList;


/**
 * @file EvaluationVisitor.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class EvaluationVisitor
 * @brief Class containing method that evaluates different SymbolicExpressions
 */


public class EvaluationVisitor implements Visitor {
    private Stack<Environment> stack = new Stack<Environment>();
    private FunctionEnv funcEnv = new FunctionEnv(); 


// **************************************************
// Public methods
// **************************************************

    /**
     * @brief Get-method used to retrieve declared function variables for function 
     * @return List of declared variables 
     */
    public FunctionEnv getFuncEnv() {return this.funcEnv;}

    
    /**
     * @brief Starting point for the evaluation visitor. Visits expression tree through root.
     * @param topLevel Root node for expression tree
     * @param env Environment in which to evaluate expression
     * @return  Tree with expressions to evaluate 
     */
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env) {
	this.stack.push(env);
	return topLevel.accept(this);
    }

    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Addition
     * @param n Addition that will be evaluated     
     * @return A new Constant if lhs and rhs are constants. Else new Addition
     */
    public SymbolicExpression visit(Addition n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left.isConstant() && right.isConstant()) {
	    
	    return new Constant(left.getValue() + right.getValue());
	} else {
	    return new Addition(left, right);
	}
    }

    
    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Subtraction
     * @param n Subtraction that will be evaluated     
     * @return A new Constant if lhs and rhs are constants. Else new Subtraction
     */
    public SymbolicExpression visit(Subtraction n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);
	
	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() - right.getValue());
	} else {
	    return new Subtraction(left, right);
	}
    }

    
    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Division
     * @param n Division that will be evaluated          
     * @return A new Constant if lhs and rhs are constants. Else new Division
     */
    public SymbolicExpression visit(Division n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);
	
	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() / right.getValue());
	} else {
	    return new Division(left, right);
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Multiplication
     * @param n Multiplication that will be evaluated     
     * @return A new Constant if lhs and rhs are constants. Else new Multiplication
     */   
    public SymbolicExpression visit(Multiplication n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);
	
	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() * right.getValue());
	} else {
	    return new Multiplication(left, right);
	}
    }


    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Assignment
     * @param n Assignment that will be evaluated     
     * @return Evaluation of lhs
     */   
    public SymbolicExpression visit(Assignment n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs();
	
	if (right.isVariable()) {
	    this.stack.peek().put((Variable) right, left);
	} else {
	    throw new IllegalExpressionException("Right hand side expression may not be a named constant");
	}
	return left;
    }

    
    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Variable
     * @param n Variable that will be evaluated          
     * @return SymbolicExpression if Environment contains mapping to n, else return new Variable
     */   
    public SymbolicExpression visit(Variable n) {
        Stack<Environment> savedStack = (Stack<Environment>)stack.clone();
	Environment savedEnv = savedStack.pop();
	SymbolicExpression expr = savedEnv.get(n);

	while(expr == null && !savedStack.empty()) {
	    savedEnv = savedStack.pop();
	    expr = savedEnv.get(n);
	}
	if (expr != null) {
	    return expr;
	} else {
	    return n;
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Constant
     * @param n Constant that will be evaluated          
     * @return n as a constant doesn't need to be evaluated
     */   
    public SymbolicExpression visit(Constant n) {
	return n;
    }

    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Sin
     * @param n Sin that will be evaluated          
     * @return A new Constant if argument is a constant. Else new Sin
     */   
    public SymbolicExpression visit(Sin n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.sin(arg.getValue()));
	} else {
	    return new Sin(arg);
	}
    }

    
    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Cos
     * @param n Cos that will be evaluated          
     * @return A new Constant if argument is a constant. Else new Cos
     */   
    public SymbolicExpression visit(Cos n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.cos(arg.getValue()));
	} else {
	    return new Cos(arg);
	}
    }

    
    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Exp
     * @param n Exp that will be evaluated          
     * @return A new Constant if argument is a constant. Else new Exp
     */   
    public SymbolicExpression visit(Exp n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.exp(arg.getValue()));
	} else {
	    return new Exp(arg);
	}
    }

    
    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Log
     * @param n Log that will be evaluated          
     * @return A new Constant if argument is a constant. Else new Log
     */   
    public SymbolicExpression visit(Log n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.log(arg.getValue()));
	} else {
	    return new Log(arg);
	}
    }

    
    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Negation
     * @param n Negation that will be evaluated          
     * @return A new Constant if argument is a constant. Else new Negation
     */   
    public SymbolicExpression visit(Negation n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(-arg.getValue());
	} else {
	    return new Negation(arg);
	}
    }

    /**
     * @brief Class included in interface that was not needed. Throws exception.
     * @param n Quit that will be evaluated          
     * @return Throws exception
     */ 
    public SymbolicExpression visit(Quit n) {
	throw new IllegalExpressionException("Attempted to evaluate command");
    }

    /**
     * @brief Class included in interface that was not needed. Throws exception.
     * @param n Vars that will be evaluated          
     * @return Throws exception
     */ 
    public SymbolicExpression visit(Vars n) {
	throw new IllegalExpressionException("Attempted to evaluate Command");
    }

    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Scope
     * @param n Scope that will be evaluated          
     * @return Returns what is evaluated in scope
     */        
    public SymbolicExpression visit(Scope n) {
	Environment localEnv = new Environment();
	stack.push(localEnv);

	SymbolicExpression arg = n.getExpr().accept(this);
	stack.pop();

	return arg;
    }

    /**
     * @brief Evaluates SymbolicExpressions that are of the Class Conditional
     * @param n Conditional that will be evaluated          
     * @return One of two scopes (depending of if-else statement)
     */   
    public SymbolicExpression visit(Conditional n) {
	SymbolicExpression left = n.getLeft().accept(this);
	SymbolicExpression right = n.getRight().accept(this);
	
	if(left.isConstant() && right.isConstant()) {
	    if(n.compareConstants(left.getValue(), right.getValue())) {
		return n.getLeftScope().accept(this);
	    } else {
		return n.getRightScope().accept(this);
	    }
	} else {
	    throw new IllegalExpressionException("Conditional using free variable(s)");
	}
    }

    
    private LinkedList<SymbolicExpression> insertArgs(LinkedList<String> argFunc, LinkedList<SymbolicExpression> argFuncCall){
	LinkedList<SymbolicExpression> assArgs = new LinkedList<SymbolicExpression>();
	
	int argCall =  argFuncCall.size();
	for(int i = 0; i < argCall ; i++){
	    SymbolicExpression expr = argFuncCall.get(i);
	    SymbolicExpression result = expr.accept(this);
	    if(result.isConstant()){
		SymbolicExpression newVar =  new Variable(argFunc.get(i));
		assArgs.addFirst(new Assignment(result,newVar));
	    } else if (result.isVariable()){
		throw new IllegalExpressionException("Variable is not assigned.");
	    } else {
		throw new IllegalExpressionException("Argument is not a constant or an identifier.");
	    }
	}
	return assArgs;
    }
    /**
     * @brief Used to declare name and body of a function
     * @param n FunctionDeclaration that will be evaluated          
     * @return Unimportant. 
     */   
    public SymbolicExpression visit(FunctionDeclaration n) {
	String funcName = n.getNameOfFunc();
	String func_low = funcName.toLowerCase();
	if(func_low.equals("quit") || func_low.equals("clear") || func_low.equals("vars")){
	    throw new IllegalExpressionException("Command as function name is not allowed");
	}
	
	Sequence seq = n.getSequence();

	funcEnv.put(funcName, seq);

	return new Variable("");
    }

    
    
    /**
     * @brief Used to call function with arguments 
     * @param n FunctionCall that will be evaluated          
     * @return Result of function
     */   
    public SymbolicExpression visit(FunctionCall n) {
	Environment localEnv = new Environment();
	String id = n.getIdentifier();
	Sequence body = funcEnv.get(id);

	if(body == null){
	    throw new IllegalExpressionException("Function does not exist");
	} else {
	    LinkedList<String> argFunc = body.getArgFunc(); //list of arguments for Sequence  
	    LinkedList<SymbolicExpression> argFuncCall = n.getArgFuncCall(); //list of arguments for FunctionCall  

	    int argSeq = body.amountOfArg();
	    int argCall =  n.amountOfArg();

	    if(argSeq < argCall){
		throw new IllegalExpressionException("Error, function '" + id + "' called with too many arguments. Expected "
						     + argSeq + ", got " + argCall);
	    } else if (argSeq > argCall){
		throw new IllegalExpressionException("Error, function '" + id + "' called with too few arguments. Expected "
						     + argSeq + ", got " + argCall);
	    } else {
		LinkedList<SymbolicExpression> assArgs = insertArgs(argFunc, argFuncCall);
		stack.push(localEnv);

		for(SymbolicExpression assignment : assArgs){
		    assignment.accept(this);
		}
		SymbolicExpression result = body.accept(this);

		stack.pop();
		return result;
	    }
	}
    }

    /**
     * @brief Evaluates body of function
     * @param n Sequence that will be evaluated          
     * @return Last evaluated line in function
     */   
    public SymbolicExpression visit(Sequence n) {
	LinkedList<SymbolicExpression> body = n.getBody();

       	int counter = 1;
	for(SymbolicExpression lineInFunc : body){
	    lineInFunc.accept(this);

	    if(counter == body.size()){
		return lineInFunc.accept(this);
	    }
	    counter++;
	}
	throw new IllegalExpressionException("Function has no body");
    }
}
