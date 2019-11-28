package org.ioopm.calculator.ast;

public class EvaluationVisitor implements Visitor {
    private Environment env = null;

    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env) {
	this.env = env;
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
	    env.put((Variable) right, left);
	} else {
	    throw new IllegalExpressionException("Right hand side expression may not be a named constant");
	}
	return left;
    }


    public SymbolicExpression visit(Variable n) {
	SymbolicExpression expr = env.get(n);
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
	throw new RuntimeException("Attempted to visit a Command");
    }

    public SymbolicExpression visit(Vars n) {
	throw new RuntimeException("Attempted to visit a Command");
    }
}
