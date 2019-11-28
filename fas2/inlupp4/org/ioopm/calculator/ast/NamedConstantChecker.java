package org.ioopm.calculator.ast;


public class NamedConstantChecker implements Visitor {


    public boolean check(SymbolicExpression expression)
    {
	try {
	    expression.accept(this);
	    return true;
	} catch (IllegalExpressionException e) {
	    System.out.println(e.getMessage());
	    return false;
	}
    }

   
    public SymbolicExpression visit(Assignment a) {
        a.getRhs().accept(this);
        if (!a.getRhs().isVariable()) {
	    throw new IllegalExpressionException("Error, assignment to named constant:" + "\n"
						 + a.getLhs() + " = " + a.getRhs());
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
}
