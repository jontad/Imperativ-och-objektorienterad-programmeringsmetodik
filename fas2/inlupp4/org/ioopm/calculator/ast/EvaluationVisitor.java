package org.ioopm.calculator.ast;
import java.util.Stack;
import java.util.LinkedList;


/**
 * @file EvaluationVisitor.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class EvaluationVisitor
 * @brief Class containing method that evaluates different Symbolicexpressions
 */


public class EvaluationVisitor implements Visitor {
    private Stack<Environment> stack = new Stack<Environment>();
    private FunctionEnv funcEnv = new FunctionEnv(); 

    public FunctionEnv getFuncEnv() {return this.funcEnv;}
    
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env) {
	this.stack.push(env);
	return topLevel.accept(this);
    }
   
    public SymbolicExpression visit(Addition n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() + right.getValue());
	} else {
	    return new Addition(left, right);
	}
    }
    
    public SymbolicExpression visit(Subtraction n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);
	
	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() - right.getValue());
	} else {
	    return new Subtraction(left, right);
	}
    }

    
    public SymbolicExpression visit(Division n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);
	
	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() / right.getValue());
	} else {
	    return new Division(left, right);
	}
    }

    
    public SymbolicExpression visit(Multiplication n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);
	
	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() * right.getValue());
	} else {
	    return new Multiplication(left, right);
	}
    }


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

    public SymbolicExpression visit(Constant n) {
	return n;
    }

    public SymbolicExpression visit(Sin n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.sin(arg.getValue()));
	} else {
	    return new Sin(arg);
	}
    }

    public SymbolicExpression visit(Cos n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.cos(arg.getValue()));
	} else {
	    return new Cos(arg);
	}
    }
    
    public SymbolicExpression visit(Exp n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.exp(arg.getValue()));
	} else {
	    return new Exp(arg);
	}
    }

    public SymbolicExpression visit(Log n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(Math.log(arg.getValue()));
	} else {
	    return new Log(arg);
	}
    }

    public SymbolicExpression visit(Negation n) {
	SymbolicExpression arg = n.getExpr().accept(this);
	if (arg.isConstant()) {
	    return new Constant(-arg.getValue());
	} else {
	    return new Negation(arg);
	}
    }

    public SymbolicExpression visit(Quit n) {
	throw new IllegalExpressionException("Attempted to evaluate command");
    }

    public SymbolicExpression visit(Vars n) {
	throw new IllegalExpressionException("Attempted to evaluate Command");
    }

     
    public SymbolicExpression visit(Scope n) {
	Environment localEnv = new Environment();
	stack.push(localEnv);

	SymbolicExpression arg = n.getExpr().accept(this);
	stack.pop();

	return arg;
    }

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

    public SymbolicExpression visit(FunctionDeclaration n) {
	String funcName = n.getNameOfFunc();
	Sequence seq = n.getSequence();

	funcEnv.put(funcName, seq);

	return  new Variable("");
    }
    
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
