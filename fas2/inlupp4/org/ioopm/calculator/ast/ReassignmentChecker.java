package org.ioopm.calculator.ast;
import java.util.Stack;
import java.util.LinkedList;

/**
 * @file ReassignmentChecker.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class ReassignmentChecker
 * @brief Class for checking if variable has been assigned
 */


public class ReassignmentChecker implements Visitor {
    private Stack<Environment> usedVariables = new Stack<Environment>();
    private FunctionEnv funcEnv = null;

    public ReassignmentChecker(FunctionEnv funcEnv){
	this.funcEnv = funcEnv;
    } 
    
    

    public boolean check(SymbolicExpression expression){
	try {
	    usedVariables.push(new Environment());
	    expression.accept(this);
	    return true;
	} catch (IllegalExpressionException e) {
	    System.out.println(e.getMessage());
	    return false;
	}
    }
    
    public SymbolicExpression visit(Assignment a){
	a.getRhs().accept(this);
	if(usedVariables.peek().containsKey(a.getRhs())){
	    throw new IllegalExpressionException("Error, the variable " + a.getRhs() + " is reassigned.");
	} else {
	    usedVariables.peek().put((Variable) a.getRhs(), a.getLhs());
	}
	return a; 
    }

      public SymbolicExpression visit(Addition a) {
        a.getLhs().accept(this);
        a.getRhs().accept(this);
        return a;
    }

    public SymbolicExpression visit(Subtraction s) {
        s.getLhs().accept(this);
        s.getRhs().accept(this);
        return s;
    }

    public SymbolicExpression visit(Multiplication m) {
        m.getLhs().accept(this);
        m.getRhs().accept(this);
        return m;
    }

    public SymbolicExpression visit(Division d) {
        d.getLhs().accept(this);
        d.getRhs().accept(this);
        return d;
    }
    

    public SymbolicExpression visit(Variable v) {
        return v;
    }
    
    public SymbolicExpression visit(Constant c) {
        return c;
    }

    public SymbolicExpression visit(Sin s) {
        s.getExpr().accept(this);
        return s;
    }
    
    public SymbolicExpression visit(Cos c) {
        c.getExpr().accept(this);
        return c;
    }

    public SymbolicExpression visit(Exp e) {
        e.getExpr().accept(this);
        return e;
    }
    
    public SymbolicExpression visit(Log l) {
        l.getExpr().accept(this);
        return l;
    }
    
    public SymbolicExpression visit(Negation n) {
        n.getExpr().accept(this);
        return n;
    }
    
    public SymbolicExpression visit(Quit q) {
        return q;
    }

    public SymbolicExpression visit(Vars v) {
        return v;
    }

    public SymbolicExpression visit(Scope s) {
	Environment localEnv = new Environment();
	usedVariables.push(localEnv);

	SymbolicExpression arg = s.getExpr().accept(this);
	usedVariables.pop();

	return arg;
    }

    public SymbolicExpression visit(Conditional c) {
        c.getLeftScope().accept(this);
	c.getRightScope().accept(this);

	return c;
    }

    

     public SymbolicExpression visit(FunctionCall f) {
	String id = f.getIdentifier();
	if(funcEnv.containsKey(id)){
	    Sequence seq = funcEnv.get(id);
	    seq.accept(this);
	} else {
	    throw new IllegalExpressionException("Function does not exist");
	}
	return f;
    }


     public SymbolicExpression visit(FunctionDeclaration f) {
	 Sequence seq =  f.getSequence();
	 seq.accept(this);
	 return f;
     }


     public SymbolicExpression visit(Sequence s) {
	 Environment localEnv = new Environment();
	 LinkedList<SymbolicExpression> funcList =  s.getFuncList();

	 usedVariables.push(localEnv);
	 for(SymbolicExpression func : funcList){
	     func.accept(this);
	 }
	 usedVariables.pop();
	 return s;
     }

}
